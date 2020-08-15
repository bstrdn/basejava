<%@ page import="com.twodonik.webapp.model.ContactType" %>
<%@ page import="com.twodonik.webapp.model.ListSection" %>
<%@ page import="com.twodonik.webapp.model.OrganizationSection" %>
<%@ page import="com.twodonik.webapp.model.SectionType" %><%--
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
            <jsp:useBean id="type" type="com.twodonik.webapp.model.SectionType"/>
            <dl>
                <dt>${type.title}</dt>

                <c:choose>
                    <c:when test="<%=type == SectionType.PERSONAL || type == SectionType.OBJECTIVE%>">
                        <p>
                        <dd><textarea form="form" rows="3" cols="100"
                                      name="${type.name()}">${resume.section.get(type)}</textarea></dd>
                        </p>
                    </c:when>

                    <c:when test="<%=type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATION%>">
                        <c:if test="${resume.section.get(type) != null}"><p>
                            <dd><textarea form="form" rows="14" cols="100" name="${type.name()}">
<c:forEach items="<%=((ListSection) resume.section.get(type)).getItems()%>" var="value">${value}
</c:forEach></textarea>
                            </dd>
                            </p>
                        </c:if>
                        <c:if test="${resume.section.get(type) == null}">
                            <dd><textarea form="form" rows="14" cols="100" name="${type.name()}"></textarea></dd>
                        </c:if>

                    </c:when>

                    <c:when test="<%=type == SectionType.EXPERIENCE || type == SectionType.EDUCATION%>">
                        <c:if test="${resume.section.get(type) != null}">
                            <c:forEach items="<%=((OrganizationSection) resume.section.get(type)).getOrganizations()%>"
                                       var="organization">
                                <dd><input type="hidden" name="${type.name()}" value="new"></dd>
                                <p>
                                <dd>Company <input type="text" name="${type.name()}" size="12"
                                                   value="${organization.link.name}"></dd>
                                <dd>Url <input type="text" name="${type.name()}" size="20"
                                               value="${organization.link.url}">
                                </dd>
                                <c:forEach items="${organization.positions}" var="position">
                                    <dd><input type="hidden" name="${type.name()}" value="newpos"></dd>

                                    <p>
                                    <dd>С <input type="text" name="${type.name()}" size="3"
                                                 value="${position.startDate}">
                                    </dd>
                                    <dd>По <input type="text" name="${type.name()}" size="3"
                                                  value="${position.endDate}">
                                    </dd>
                                    <c:if test="${position.title != ' '}">
                                        <dd>Должность <input type="text" name="${type.name()}" size="30"
                                                             value="${position.title}"></dd>
                                    </c:if>
                                    <c:if test="${position.title == ' '}"><input type="hidden"
                                                                                 name="${type.name()}" value=" ">
                                    </c:if>

                                    </p>
                                    <p>
                                    <dd><input type="text" name="${type.name()}" size="90"
                                               value="${position.description}">
                                    </dd>
                                    </p>
                                </c:forEach>
                                <dd><input type="hidden" name="${type.name()}" value="newpos"></dd>

                                <p>
                                <dd>С <input type="text" name="${type.name()}" size="3"
                                             value="${position.startDate}">
                                </dd>
                                <dd>По <input type="text" name="${type.name()}" size="3"
                                              value="${position.endDate}">
                                </dd>
                                <c:if test="${type.name().equals('EXPERIENCE')}">

                                    Должность: <input type="text"
                                                      name="${type.name()}" value=" ">
                                </c:if>
                                <c:if test="${type.name().equals('EDUCATION')}">

                                    <input type="hidden"
                                           name="${type.name()}" value=" ">
                                </c:if>
                                </p>
                                <p>
                                <dd><input type="text" name="${type.name()}" size="90"
                                           value="${position.description}">
                                </dd>
                                </p>
                                </p>
                            </c:forEach>
                        </c:if>
                        <dd><input type="hidden" name="${type.name()}" value="new"></dd>
                        <p>
                        <dd>Company <input type="text" name="${type.name()}" size="12"></dd>
                        <dd>Url <input type="text" name="${type.name()}" size="20">
                        </dd>
                        <dd><input type="hidden" name="${type.name()}" value="newpos"></dd>
                        <p>
                        <dd>С <input type="text" name="${type.name()}" size="3">
                        </dd>
                        <dd>По <input type="text" name="${type.name()}" size="3">
                        </dd>
                        <c:if test="${type.name().equals('EXPERIENCE')}">

                            Должность: <input type="text"
                                              name="${type.name()}" value=" ">
                        </c:if>
                        <c:if test="${type.name().equals('EDUCATION')}">

                            <input type="hidden"
                                   name="${type.name()}" value=" ">
                        </c:if>
                        </p>
                        <p>
                        <dd><input type="text" name="${type.name()}" size="90">
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
