<%--
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
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.twodonik.webapp.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>

    <h1> ${resume.fullName}  <a href="?uuid=${resume.uuid}&action=edit">Edit</a> </h1>

    <h3>Contact:</h3>
<c:forEach items="${resume.contact}" var="contact">
    <jsp:useBean id="contact" type="java.util.Map.Entry<com.twodonik.webapp.model.ContactType, java.lang.String>"/>
    <%=contact.getKey().toHtml(contact.getValue())%>
<%--    ${contact.key}  ${contact.value} --%>
    <br>

</c:forEach>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
