package com.twodonik.webapp.web;

import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.sql.SqlHelper;
import com.twodonik.webapp.storage.SqlStorage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");

        SqlStorage sqlStorage = new SqlStorage(new SqlHelper("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres"));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table border=\"1\" border-collapse:\"collapse\" cellspacing = \"0\"><tr><th>UUID</th><th>FULL_NAME</th></tr>");

        if (name == null) {
            List<Resume> resumes = sqlStorage.getAllSorted();
            for (Resume resume : resumes) {
                stringBuilder.append("<tr><td>" + resume.getUuid() + "</td><td>" + resume.getFullName() + "</td></tr>");
            }
        } else {
            Resume resume = sqlStorage.get(name);
            stringBuilder.append("<tr><td>" + resume.getUuid() + "</td><td>" + resume.getFullName() + "</td></tr>");
        }
        stringBuilder.append("</table>");
        response.getWriter().write(stringBuilder.toString());
    }
}