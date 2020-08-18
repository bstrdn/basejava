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
import java.util.stream.Collectors;

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

        String action = request.getParameter("action").trim();
        Resume r;
        if (action.contains("new") && request.getParameter("fullName") != "") {
            r = new Resume(request.getParameter("fullName"));
            String email = request.getParameter("email").trim();
            if (email != "") {
                r.addContact(ContactType.MAIL, email);
            }
            storage.save(r);
        } else if (action.contains("edit")) {
            String uuid = request.getParameter("uuid");
            String fullName = request.getParameter("fullName");
            r = storage.get(uuid);
            r.setFullName(fullName);
            for (ContactType type : ContactType.values()) {
                String value = request.getParameter(type.name());
                if (value != null && value.trim() != "") {
                    r.addContact(type, value);
                } else {
                    r.getContact().remove(type);
                }
            }

            for (SectionType type : SectionType.values()) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        String value = request.getParameter(type.name());
                        if (value != "") {
                            r.addSection(type, new TextSection(value));
                        }
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        ArrayList<String> list = (ArrayList<String>) Arrays.stream(request.getParameter(type.name()).split("\r\n"))
                                .map(String::trim).filter(s1 -> !s1.equals("")).collect(Collectors.toList());
                        r.addSection(type, new ListSection(list));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        String[] values = request.getParameterValues(type.name());
                        List<Organization> organization = new ArrayList<>();
                        for (int i = 0; i < values.length; i++) {
                            if (values[i + 1].equals("")) {
                                break;
                            }
                            if (values[i].equals("new")) {
                                Link link = new Link(values[i + 1], values[i + 2]);
                                i += 2;
                                ArrayList<Position> newpos = new ArrayList<>();
                                while (values[i + 1].equals("newpos")) {
                                    if (!values[i + 2].equals("")) {
                                        YearMonth start = YearMonth.parse(values[i + 2]);
                                        YearMonth end = YearMonth.parse(values[i + 3]);
                                        String title = values[i + 4];
                                        String description = values[i + 5];
                                        newpos.add(new Position(start, end, title, description));
                                    }
                                    i += 5;
                                    if (i == values.length - 1) {
                                        break;
                                    }
                                }
                                organization.add(new Organization(link, newpos));
                            }
                        }
                        r.addSection(type, new OrganizationSection(organization));
                }
            }
            storage.update(r);
        }
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
                r = storage.get(uuid);
//                for (SectionType type : new SectionType[]{EXPERIENCE, EDUCATION}) {
//                    OrganizationSection section = (OrganizationSection) r.getSection(type);
//                    List<Organization> listWithEmpty = new ArrayList<>();
//                    listWithEmpty.add(Organization.EMPTY);
//                    if (section != null) {
//                        section
//                    }
//                }
                break;
            case "view":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException(action + "is illegal");
        }

        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp").forward(request, response);

    }
}