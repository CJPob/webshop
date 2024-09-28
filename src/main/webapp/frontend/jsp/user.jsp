<%@ page import="ui.UserInfo" %>
<%@ page import="java.util.Collection" %><%--
  Created by IntelliJ IDEA.
  User: CJayp
  Date: 2024-09-28
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>User Search Results</title>
</head>
<body>
<h1>Users Found</h1>

<%
  Collection<ui.UserInfo> users = (Collection<UserInfo>) request.getAttribute("users");

  if (users == null || users.isEmpty()) {
%>
<p>No users found.</p>
<%
} else {
%>
<ul>
  <%
    // Display all user
    for (ui.UserInfo user : users) {
  %>
  <li><strong>Username:</strong> <%= user.getUsername() %>, <strong>Name:</strong> <%= user.getName() %></li>
  <%
    }
  %>
</ul>
<%
  }
%>
</body>
</html>
