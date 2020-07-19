package com.twodonik.webapp.web;

import com.twodonik.webapp.Config;
import com.twodonik.webapp.model.ContactType;
import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim() != null) {
                r.addContact(type, value);
            } else {
                r.getContact().remove(type);
            }
        }
storage.update(r);
        response.sendRedirect("resume");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "edit":
            case "view":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException(action + "is illegal");
        }

        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp").forward(request, response);

//        request.setAttribute("resumes", storage.getAllSorted());
//        request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//
////        SqlStorage sqlStorage = new SqlStorage(new SqlHelper("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres"));
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("<table border=\"1\" border-collapse:\"collapse\" cellspacing = \"0\"><tr><th>FULL_NAME</th><th>EMAIL</th></tr>");
//
//        if (name == null) {
//            List<Resume> resumes = storage.getAllSorted();
//            for (Resume resume : resumes) {
//                stringBuilder.append("<tr><td><a href = \"?name=" + resume.getUuid() + "\">" + resume.getFullName() + "</a></td><td>" + resume.getContact().get(ContactType.MAIL) + "</td></tr>");
//
//
//            }
//        } else {
//            Resume resume = storage.get(name);
//            stringBuilder.append("<tr><td>" + resume.getFullName() + "</td><td>" + resume.getContact().get(ContactType.MAIL) + "</td></tr>");
//        }
//        stringBuilder.append("</table>");
//        response.getWriter().write(stringBuilder.toString());
    }
}