package com.twodonik.webapp.web;

import com.twodonik.webapp.Config;
import com.twodonik.webapp.model.*;
import com.twodonik.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (isItNull(value)) {
                r.addContact(type, value);
            } else {
                r.getContact().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            switch (type.name()) {
                case "PERSONAL":
                case "OBJECTIVE":
                    r.addSection(type, new TextSection(request.getParameter(type.name())));
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATION":
                    r.addSection(type, new ListSection(withoutNull(request.getParameterValues(type.name()))));
                    break;
                case "EXPERIENCE":
                case "EDUCATION":
//                    String[] list = request.getParameterValues(type.name());
//                    List<Organization> organization = new ArrayList<>();
//                    for (int i = 0; i < list.length; i++) {
//                        if (list[i].equals("new")) {
//                            Link link = new Link(list[i + 1], list[i + 2]);
//                            YearMonth start = YearMonth.parse(list[i + 4]);
//                            YearMonth end = YearMonth.parse(list[i + 5]);
//
//                            String title = list[i + 6];
//                            String description = list[i + 7];
//                            if (list[i + 6].equals("new")) {
//                                organization.add(new Organization(link, new Position(start, end, null, title)));
//                                i = i + 7;
//                                continue;
//                            }
//
//                            i = i + 2;
//
//                        }
//                    }
//                    r.addSection(type, new OrganizationSection(organization));
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

    }

    boolean isItNull(String value) {
        return (value != null && value.trim() != null);
    }

    private List<String> withoutNull(String[] values) {
        List<String> list = new ArrayList<>(Arrays.asList(values));
        list.removeAll(Arrays.asList("", null));

        return list;
    }
}