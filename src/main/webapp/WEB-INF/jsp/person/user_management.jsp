<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>User Management</title>
</head>
<body>
<script>
    function loadAll() {
        document.forms["loadAll"].submit();
    }
</script>
<%@include file="../header.jsp" %>
<div style="height:80%;margin-left: 30px" align="left">
    <br>
    <sec:authorize access="hasAnyRole('ADMIN')">
        <form action="${pageContext.servletContext.contextPath}/api/user" method="post">
            <div class="form-group">
                <br><br>
                <h2>create/register user</h2>
                <br>
                <input type="hidden" name="id" value="${entity.id}">
                <input type="hidden" name="version" value="${entity.version}">
                <input type="text" name="username" placeholder="username" class="form-control"
                       value="${entity.username}"
                       style="width: 200px;">
                <br>
                <input type="text" name="password" placeholder="password" class="form-control"
                       value="${entity.password}"
                       style="width: 200px;">
                <br>
                <button type="submit" class="btn btn-primary" dir="ltr">Register</button>
            </div>
        </form>
    </sec:authorize>
    <hr>
    <sec:authorize access="hasAnyRole('ADMIN','CUSTOMER')">
        <form id="loadAll" name="loadAll" action="${pageContext.servletContext.contextPath}/api/users" method="get">
            <button type="submit">load</button>
        </form>
    </sec:authorize>
    <table>
        <tr class="bg-danger " >
            <td style="padding: 5px;margin-bottom: 10px" class="col-md-2">id</td>
            <td style="padding: 5px;margin-bottom: 10px" class="col-md-3">username</td>
            <td style="padding: 5px;margin-bottom: 10px" class="col-md-4">action</td>
        </tr>
        <c:forEach var="item" items="${userList}">
            <tr>
                <td class="col-md-2">${item.id}</td>
                <td class="col-md-3">${item.username}</td>

                <td class="col-md-7">
                    <table>
                        <tr>
                            <td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <form action="${pageContext.servletContext.contextPath}/api/users/${item.id}"
                                          method="post">
                                        <button type="submit" class="btn btn-primary" dir="ltr">delete</button>
                                    </form>
                                </sec:authorize>
                            </td>
                            <td>
                                <sec:authorize access="hasRole('ADMIN')">
                                    <form action="${pageContext.servletContext.contextPath}/api/updateUser/${item.id}"
                                          method="post">
                                        <button class="btn btn-primary" dir="ltr">update</button>
                                    </form>
                                </sec:authorize>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>