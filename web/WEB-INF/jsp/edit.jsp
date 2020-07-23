<%@ page import="com.twodonik.webapp.model.ContactType" %>
<%@ page import="com.twodonik.webapp.model.ListSection" %>
<%@ page import="com.twodonik.webapp.model.SectionType" %>
<%@ page import="com.twodonik.webapp.model.OrganizationSection" %><%--
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
    <meta http-equiv="Content-Type" content="text/html" ; charset=UTF-8">
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

        <h4>Section:</h4>

        <c:forEach items="<%=SectionType.values()%>" var="type">
            <jsp:useBean id="type" type="com.twodonik.webapp.model.SectionType"/>
            <dl>
                <dt>${type.title}</dt>

                <c:choose>
                    <c:when test="<%=type == SectionType.PERSONAL || type == SectionType.OBJECTIVE%>">
                        <dd><input type="text" name="${type.name()}" size="100" value="${resume.section.get(type)}"></dd>
                    </c:when>

                    <c:when test="<%=type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATION%>">
                        <c:forEach items="<%=((ListSection) resume.section.get(type)).getItems()%>" var="value">
                            <p>
                            <dd><input type="text" name="${type.name()}" size="100" value="${value}"></dd>
                            </p>
                        </c:forEach>
                        <p>
                        <dd><input type="text" name="${type.name()}" size="100" value=""></dd>
                        </p>

                    </c:when>

                    <c:when test="<%=type == SectionType.EXPERIENCE || type == SectionType.EDUCATION%>">
                        <c:forEach items="<%=((OrganizationSection) resume.section.get(type)).getOrganizations()%>"
                                   var="organization">
                            <dd><input type="hidden" name="${type.name()}" value="new"></dd>
                            <p>
                            <dd>Company <input type="text" name="${type.name()}" size="12" value="${organization.link.name}"></dd>
                            <dd>Url <input type="text" name="${type.name()}" size="20" value="${organization.link.url}"></dd>

<%--                            <a href="${organization.link.url}">${organization.link.name}</a>--%>
                                <c:forEach items="${organization.positions}" var="position">
                                                                        <p>
                                    <dd>С <input type="text" name="${type.name()}" size="3" value="${position.startDate}"></dd>
                                   <dd>По <input type="text" name="${type.name()}" size="3" value="${position.endDate}"></dd>
                                  <c:if test="${position.title != ' '}">
                                      <dd>Должность <input type="text" name="${type.name()}" size="30" value="${position.title}"></dd>
                                  </c:if>
                                   </p>
                                    <p><dd><input type="text" name="${type.name()}" size="90" value="${position.description}"></dd>

                                    </p>
                                </c:forEach>
                            </p>



                        </c:forEach>



                    </c:when>

                </c:choose>


            </dl>


        </c:forEach>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Back</button>
    </form>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
