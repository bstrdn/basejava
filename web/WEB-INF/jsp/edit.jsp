<%@ page import="com.twodonik.webapp.model.ContactType" %>
<%@ page import="com.twodonik.webapp.model.ListSection" %>
<%@ page import="com.twodonik.webapp.model.OrganizationSection" %>
<%@ page import="com.twodonik.webapp.model.SectionType" %>
<%@ page import="static com.twodonik.webapp.model.SectionType.PERSONAL" %>
<%@ page import="static com.twodonik.webapp.model.SectionType.OBJECTIVE" %>
<%@ page import="static com.twodonik.webapp.model.SectionType.*" %><%--
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


    <form method="post" enctype="application/x-www-form-urlencoded" action="resume" id="form">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="action" value="edit"/>

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
            <c:set value="${resume.section.get(type)}" var="section"/>
            <jsp:useBean id="type" type="com.twodonik.webapp.model.SectionType"/>
            <jsp:useBean id="section" type="com.twodonik.webapp.model.AbstractSection"/>
            <h3>${type.title}</h3>
            <br>
            <c:choose>
                <c:when test="<%=type == PERSONAL || type == OBJECTIVE%>">
                    <textarea form="form" rows="3" cols="100" name="${type}">${section}</textarea>
                </c:when>
                <c:when test="<%=type == ACHIEVEMENT || type == QUALIFICATION%>">
                    <c:if test="${resume.section.get(type) != null}">
                            <textarea rows="14" cols="100" name="${type}"><%=String.join("\n\n",
                                    ((ListSection) section).getItems())%></textarea>
                    </c:if>
                    <c:if test="${section == null}">
                        <textarea rows="14" cols="100" name="${type}"></textarea>
                    </c:if>
                </c:when>

                <c:when test="<%=type == EXPERIENCE || type == EDUCATION%>">
                    <c:if test="${section != null}">
                        <c:forEach items="<%=((OrganizationSection) section).getOrganizations()%>"
                                   var="organization" varStatus="counter">
                            <dd><input type="hidden" name="${type}" value="new"></dd>
                            <p>
                            <dd>Company <input type="text" name="${type}" size="12"
                                               value="${organization.link.name}"></dd>
                            <dd>Url <input type="text" name="${type}" size="20"
                                           value="${organization.link.url}">
                            </dd>
                            <c:forEach items="${organization.positions}" var="position">
                                <dd><input type="hidden" name="${type}" value="newpos"></dd>

                                <p>
                                <dd>From: <input type="text" name="${type}" size="3"
                                                 value="${position.startDate}" placeholder="yyyy-MM">
                                </dd>
                                <dd>To: <input type="text" name="${type}" size="3"
                                               value="${position.endDate}" placeholder="yyyy-MM">
                                </dd>
                                <c:if test="${position.title != ' '}">
                                    <dd>Position: <input type="text" name="${type}" size="30"
                                                         value="${position.title}"></dd>
                                </c:if>
                                <c:if test="${position.title == ' '}"><input type="hidden"
                                                                             name="${type}" value=" ">
                                </c:if>

                                </p>
                                <p>
                                <dd>Description: <input type="text" name="${type}" size="90"
                                                        value="${position.description}">
                                </dd>
                                </p>
                            </c:forEach>
                            <dd><input type="hidden" name="${type}" value="newpos"></dd>

                            <p>
                            <dd>From: <input type="text" name="${type}" size="3"
                                             value="${position.startDate}" placeholder="yyyy-MM">
                            </dd>
                            <dd>To: <input type="text" name="${type}" size="3"
                                           value="${position.endDate}" placeholder="yyyy-MM">
                            </dd>
                            <c:if test="${type.name().equals('EXPERIENCE')}">

                                Position: <input type="text"
                                                 name="${type}" value=" ">
                            </c:if>
                            <c:if test="${type.name().equals('EDUCATION')}">

                                <input type="hidden"
                                       name="${type}" value=" ">
                            </c:if>
                            </p>
                            <p>
                            <dd>Description: <input type="text" name="${type}" size="90"
                                                    value="${position.description}">
                            </dd>
                            </p>
                            </p>
                        </c:forEach>
                    </c:if>
                    <dd><input type="hidden" name="${type}" value="new"></dd>
                    <p>
                    <dd>Company <input type="text" name="${type}" size="12"></dd>
                    <dd>Url <input type="text" name="${type}" size="20">
                    </dd>
                    <dd><input type="hidden" name="${type}" value="newpos"></dd>
                    <p>
                    <dd>From: <input type="text" name="${type}" size="3">
                    </dd>
                    <dd>To: <input type="text" name="${type}" size="3">
                    </dd>
                    <c:if test="${type.equals('EXPERIENCE')}">

                        Position: <input type="text"
                                         name="${type}" value=" ">
                    </c:if>
                    <c:if test="${type.equals('EDUCATION')}">

                        <input type="hidden"
                               name="${type}" value=" ">
                    </c:if>
                    </p>
                    <p>
                    <dd>Description: <input type="text" name="${type}" size="90">
                    </dd>
                    </p>
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
