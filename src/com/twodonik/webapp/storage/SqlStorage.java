package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        sqlHelper.query(conn -> {
            PreparedStatement ps = conn.prepareStatement("delete from resume");
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.query(conn -> {
            PreparedStatement ps = conn.prepareStatement("update resume set full_name = ? where uuid = ?");
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.query(conn -> {
            PreparedStatement ps = conn.prepareStatement("insert into resume (uuid, full_name) values (?, ?)");
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });
    }


    @Override
    public Resume get(String uuid) {
        return sqlHelper.query(conn -> {
            PreparedStatement ps = conn.prepareStatement("select * from resume r where r.uuid = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.query(conn -> {
            PreparedStatement ps = conn.prepareStatement("delete from resume where uuid = ?");
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {

        return sqlHelper.query(conn -> {
            PreparedStatement ps = conn.prepareStatement("select * from resume");
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").replaceAll("\\s", ""), rs.getString("full_name")));
            }
            return list;
        });

    }

    @Override
    public int size() {
        return sqlHelper.query(conn -> {
            PreparedStatement ps = conn.prepareStatement("select count(*) from resume");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }
}
