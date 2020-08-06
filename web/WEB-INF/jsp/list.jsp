<%@ page import="com.twodonik.webapp.model.ContactType" %>


<%--
  Created by IntelliJ IDEA.
  User: bystrovdk
  Date: 15.07.2020
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Resume list</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1">
        <tr>
            <th>NAME</th>
            <th>MAIL</th>
            <th></th>
            <th></th>
        </tr>
        <%--        <jsp:useBean id="resume" type="com.twodonik.webapp.model.Resume"/>--%>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.twodonik.webapp.model.Resume"/>

            <tr>
                <td><a href="?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.MAIL.toHtml(resume.contact.get(ContactType.MAIL))%>
                </td>
                <td><a href="?uuid=${resume.uuid}&action=delete">Delete</a></td>
                <td><a href="?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>
            </tr>
        </c:forEach>

        <form method="post" enctype="application/x-www-form-urlencoded" action="resume">
            <input type="hidden" name="action" value="new"/>
            <tr>
                <td>
                    <input type="text" name="fullName" size="20"/>
                </td>
                <td>
                    <input type="text" name="email" size="20"/>
                </td>
                <td>
                    <button type="submit">Save</button>
                </td>
                <td></td>
            </tr>
            </dl>
        </form>
        <%--        <% for (Resume resume : (ArrayList<Resume>) request.getAttribute("resumes")) { %>--%>
        <%--        <tr>--%>
        <%--            <td><a href="?name=<%=resume.getUuid()%>"><%=resume.getFullName()%></a> </td>--%>
        <%--            <td><%=resume.getContact().get(ContactType.MAIL)%></td>--%>
        <%--        </tr>--%>
        <%--<% } %>--%>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
