package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.ContactType;
import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.sql.SqlHelper;
import com.twodonik.webapp.sql.SqlTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
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
            try (PreparedStatement ps = conn.prepareStatement("delete from contact where resume_uuid = ?")) {
                setPrepareStatement(ps, uuid).execute();
            }
            insertContact(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecutor((SqlTransaction<Connection>) conn -> {
            try (PreparedStatement ps = conn.prepareStatement("insert into resume (uuid, full_name) values (?, ?)")) {
                setPrepareStatement(ps, resume.getUuid(), resume.getFullName()).execute();
            }
            insertContact(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("select r.uuid, r.full_name, c.type, c.value\n" +
                        "from resume r\n" +
                        "left join contact c on r.uuid = c.resume_uuid\n" +
                        "where uuid = ?;",
                ps -> {
                    ResultSet rs = setPrepareStatement(ps, uuid).executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContactToResume(rs, r);
                    } while (rs.next());
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
            addContact(conn, resumes);
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

    private void addContact(Connection conn, Map<String, Resume> resumes) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("select * from contact")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addContactToResume(rs, resumes.get(rs.getString("resume_uuid")));
            }
        }
    }

    private void addContactToResume(ResultSet rs, Resume r) throws SQLException {
        String contactType = rs.getString("type");
        String value = rs.getString("value");
        if (contactType != null && value != null) {
            r.addContact(ContactType.valueOf(contactType), value);
        }
    }


    private void insertContact(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("insert into contact (resume_uuid, type, value) values (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> m : resume.getContact().entrySet()) {
                setPrepareStatement(ps, resume.getUuid(), m.getKey().name(), m.getValue()).addBatch();
            }
            ps.executeBatch();
        }
    }
}