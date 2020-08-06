package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.AbstractSection;
import com.twodonik.webapp.model.ContactType;
import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.model.SectionType;
import com.twodonik.webapp.sql.SqlHelper;
import com.twodonik.webapp.sql.SqlTransaction;
import com.twodonik.webapp.util.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.twodonik.webapp.model.SectionType.valueOf;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        sqlHelper.execute("delete from resume");
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        sqlHelper.transactionExecutor((SqlTransaction<Connection>) conn -> {
            try (PreparedStatement ps = conn.prepareStatement("update resume set full_name = ? where uuid = ?")) {
                if (setPrepareStatement(ps, resume.getFullName(), uuid).executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            deleteAttributes(conn, "delete from contact where resume_uuid = ?", uuid);
            deleteAttributes(conn, "delete from section where resume_uuid = ?", uuid);
            insertContacts(conn, resume);
            insertSections(conn, resume);
            return null;
        });
    }

    private void deleteAttributes(Connection conn, String sql, String uuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            setPrepareStatement(ps, uuid).execute();
        }
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecutor((SqlTransaction<Connection>) conn -> {
            try (PreparedStatement ps = conn.prepareStatement("insert into resume (uuid, full_name) values (?, ?)")) {
                setPrepareStatement(ps, resume.getUuid(), resume.getFullName()).execute();
            }
            insertContacts(conn, resume);
            insertSections(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionExecutor(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("select r.uuid, r.full_name, c.type, c.value\n" +
                    "from resume r\n" +
                    "left join contact c on r.uuid = c.resume_uuid\n" +
                    "where uuid = ?;")) {
                ResultSet rs = setPrepareStatement(ps, uuid).executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContactToResume(rs, r);
                } while (rs.next());
            }
            try (PreparedStatement ps = conn.prepareStatement("select * from section where resume_uuid = ?")) {
                ResultSet rs = setPrepareStatement(ps, r.getUuid()).executeQuery();
                while (rs.next()) {
                    addTextSectionToResume(rs, r);
                }
            }
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("delete from resume where uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        sqlHelper.transactionExecutor((SqlTransaction<Connection>) conn -> {
            try (PreparedStatement ps = conn.prepareStatement("select * from resume order by full_name, uuid;")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    resumes.put(rs.getString("uuid"), new Resume(rs.getString("uuid"),
                            rs.getString("full_name")));
                }
            }
            insertContacts(conn, resumes);
            insertSections(conn, resumes);
            return null;
        });
        return new ArrayList<>(resumes.values());
    }

    @Override
    public int size() {
        return sqlHelper.execute("select count(*) from resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }


    private PreparedStatement setPrepareStatement(PreparedStatement ps, String... strings) throws SQLException {
        ps.clearParameters();
        for (int i = 1; i <= strings.length; i++) {
            ps.setString(i, strings[i - 1]);
        }
        return ps;
    }

    private void insertContacts(Connection conn, Map<String, Resume> resumes) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("select * from contact")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addContactToResume(rs, resumes.get(rs.getString("resume_uuid")));
            }
        }
    }

    private void insertSections(Connection conn, Map<String, Resume> resumes) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("select * from section")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addTextSectionToResume(rs, resumes.get(rs.getString("resume_uuid")));
            }
        }
    }

    private void addTextSectionToResume(ResultSet rs, Resume r) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {
            SectionType type = valueOf(rs.getString("type"));
            r.addSection(type, JsonParser.read(content, AbstractSection.class));
        }

    }

    private void addContactToResume(ResultSet rs, Resume r) throws SQLException {
        String contactType = rs.getString("type");
        String value = rs.getString("value");
        if (contactType != null && value != null) {
            r.addContact(ContactType.valueOf(contactType), value);
        }
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("insert into contact (resume_uuid, type, value) values (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> m : resume.getContact().entrySet()) {
                setPrepareStatement(ps, resume.getUuid(), m.getKey().name(), m.getValue()).addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("insert into section (resume_uuid, type, content) values (?, ?, ?)")) {
            for (Map.Entry<SectionType, AbstractSection> map : resume.getSection().entrySet()) {
                ps.clearParameters();
                setPrepareStatement(ps, resume.getUuid(), map.getKey().name(),
                        JsonParser.write(map.getValue(), AbstractSection.class)).addBatch();
                ps.executeBatch();
            }
        }
    }
}