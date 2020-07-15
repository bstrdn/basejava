<%@ page import="com.twodonik.webapp.model.Resume" %>


<%@ page import="java.util.ArrayList" %>
<%@ page import="com.twodonik.webapp.model.ContactType" %><%--
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
    <title>Title</title>
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

        <% for (Resume resume : (ArrayList<Resume>) request.getAttribute("resumes")) { %>
        <tr>
            <td><a href="?name=<%=resume.getUuid()%>"><%=resume.getFullName()%></a> </td>
            <td><%=resume.getContact().get(ContactType.MAIL)%></td>
        </tr>
<% } %>
    </table>

</section>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
