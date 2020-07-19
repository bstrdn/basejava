<%@ page import="com.twodonik.webapp.model.ContactType" %><%--
  Created by IntelliJ IDEA.
  User: bstrd
  Date: 18.07.2020
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.twodonik.webapp.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>


    <form method="post" enctype="application/x-www-form-urlencoded" action="resume">
        <input type="hidden" name="uuid" value="${resume.uuid}">

        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"/>
        </dl>
        <h4>Contact:</h4>

        <c:forEach items="<%=ContactType.values()%>" var="type">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30" value="${resume.contact.get(type)}"></dd>
            </dl>

        </c:forEach>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Back</button>
    </form>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
