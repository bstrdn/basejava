package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.sql.ConnectionFactory;
import com.twodonik.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.List;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String db, String login, String password, SqlHelper sqlHelper) {
        connectionFactory = () -> DriverManager.getConnection(db, login, password);
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        sqlHelper.delete("delete from resumes.public.resume");
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("delete from resumes.public.resume")) {
//            ps.execute();
//        } catch (SQLException throwables) {
//            throw new StorageException(throwables);
//        }
    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void save(Resume resume) {

        query(new CodeBlock() {
            @Override
            public Resume execute(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement("insert into resume (uuid, full_name) values (?, ?)");
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ps.execute();
                    return null;
                }
            });


//        try (Connection conn = connectionFactory.getConnection();
//        PreparedStatement ps = conn.prepareStatement("insert into resume (uuid, full_name) values (?, ?)")) {
//            ps.setString(1, resume.getUuid());
//            ps.setString(2, resume.getFullName());
//            ps.execute();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        }



    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("select * from resumes.public.resume r where r.uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException(uuid, "Resume " + uuid + " is not exist");
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            return r;
        } catch (SQLException throwables) {
            throw new StorageException(throwables);
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }


    private Resume query(CodeBlock cb) {

        try (Connection conn = connectionFactory.getConnection()) {

            cb.execute(conn);
        }
         catch (SQLException throwables) {
            throw new StorageException(throwables);
        }
    }
}
