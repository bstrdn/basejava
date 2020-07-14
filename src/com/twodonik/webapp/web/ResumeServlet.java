package com.twodonik.webapp.web;

import com.twodonik.webapp.Config;
import com.twodonik.webapp.model.ContactType;
import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.sql.SqlHelper;
import com.twodonik.webapp.storage.SqlStorage;
import com.twodonik.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");

//        SqlStorage sqlStorage = new SqlStorage(new SqlHelper("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres"));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table border=\"1\" border-collapse:\"collapse\" cellspacing = \"0\"><tr><th>FULL_NAME</th><th>EMAIL</th></tr>");

        if (name == null) {
            List<Resume> resumes = storage.getAllSorted();
            for (Resume resume : resumes) {
                stringBuilder.append("<tr><td><a href = \"?name=" + resume.getUuid() + "\">" + resume.getFullName() + "</a></td><td>" + resume.getContact().get(ContactType.MAIL) + "</td></tr>");


            }
        } else {
            Resume resume = storage.get(name);
            stringBuilder.append("<tr><td>" + resume.getFullName() + "</td><td>" + resume.getContact().get(ContactType.MAIL) + "</td></tr>");
        }
        stringBuilder.append("</table>");
        response.getWriter().write(stringBuilder.toString());
    }
}