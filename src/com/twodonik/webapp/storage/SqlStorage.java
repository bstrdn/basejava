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
            try (PreparedStatement ps = conn.prepareStatement("update contact set type = ?, value = ? where resume_uuid = ?")) {
                for (Map.Entry<ContactType, String> contact : resume.getContact().entrySet()) {
                    setPrepareStatement(ps, contact.getKey().name(), contact.getValue(), uuid).addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });


        sqlHelper.execute("update resume set full_name = ? where uuid = ?", ps -> {
            if (setPrepareStatement(ps, resume.getFullName(), uuid).executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecutor((SqlTransaction<Connection>) conn -> {
            try (PreparedStatement ps = conn.prepareStatement("insert into resume (uuid, full_name) values (?, ?)")) {
                setPrepareStatement(ps, resume.getUuid(), resume.getFullName()).execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("insert into contact (resume_uuid, type, value) values (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> m : resume.getContact().entrySet()) {
                    setPrepareStatement(ps, resume.getUuid(), m.getKey().name(), m.getValue()).addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });


    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("select r.uuid, r.full_name, c.type, c.value\n" +
                        "from resume r\n" +
                        "left join contact c on r.uuid = c.resume_uuid\n" +
                        "where uuid = ?;",
                ps -> getResume(ps, uuid));
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
        List<Resume> listResume = new ArrayList<>();
        sqlHelper.transactionExecutor((SqlTransaction<Connection>) conn -> {
            List<String> s = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement("select r.uuid from resume r order by full_name, uuid;")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    s.add(rs.getString("uuid"));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("select r.uuid, r.full_name, c.type, c.value\n" +
                    "from resume r\n" +
                    "left join contact c on r.uuid = c.resume_uuid\n" +
                    "where uuid = ?")) {
                for (String uuid : s) {
                    listResume.add(getResume(ps, uuid));
                }
            }
            return null;
        });
        return listResume;
    }

    @Override
    public int size() {
        return sqlHelper.execute("select count(*) from resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }


    private Resume getResume(PreparedStatement ps, String uuid) throws SQLException {
        ps.setString(1, uuid);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            throw new NotExistStorageException(uuid);
        }
        Resume r = new Resume(uuid, rs.getString("full_name"));
        do {
            String contactType = rs.getString("type");
            String value = rs.getString("value");
            if (contactType != null && value != null) {
                r.addContact(ContactType.valueOf(contactType), value);
            }
        } while (rs.next());
        return r;
    }


    private PreparedStatement setPrepareStatement(PreparedStatement ps, String... strings) throws SQLException {
        for (int i = 1; i <= strings.length; i++) {
            ps.setString(i, strings[i - 1]);
        }
        return ps;
    }
}