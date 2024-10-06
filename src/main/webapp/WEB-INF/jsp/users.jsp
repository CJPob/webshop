<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="ui.UserInfo" %>
<%@ page import="java.util.Collection" %>
<!DOCTYPE html>

<html>
<head>
    <title>surfboards</title>
    <link href="../../frontend/css/reset.css" rel="stylesheet" >
    <link href="../../frontend/images/favicon.ico" rel="icon">
    <link rel="stylesheet" type="text/css" href="../../frontend/css/main.css">
</head>

<body>
<div id="container">
    <header>
        <h1>SURFBOARDS</h1>
        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/item">home</a></li>

                <%-- Check if the user is logged in, toggle log in / my account--%>
                <% if (session.getAttribute("userId") == null) { %>
                <li><a href="login.jsp">log in</a></li>
                <% } else { %>
                <li><a href="${pageContext.request.contextPath}/user">my account</a></li>
                <% } %>

                <%-- my cart button --%>
                <li><a href="${pageContext.request.contextPath}/cart">my cart</a></li>

                <%-- Check if the user is logged in and is an admin or staff , display extra menu button "admin"--%>
                <%
                    String userRole = (String) session.getAttribute("userRole");
                    if (userRole != null && (userRole.equals("ADMIN") || userRole.equals("STAFF"))) {
                %>
                <li><a href="${pageContext.request.contextPath}/admin">admin</a></li>
                <% } %>
            </ul>
        </nav>
    </header>

    <main>
        <table id="userstable">
            <thead>
            <tr>
            <tr>
                <th>Name</th>
                <th>Username</th>
                <th>Current Role</th>
                <th>Change Role</th>
            </tr>
            </thead>
            <tbody>
            <%
                Collection<UserInfo> users = (Collection<UserInfo>) request.getAttribute("users");
                for (UserInfo user : users) {
            %>
            <tr>
                <td><%= user.getName() %></td>
                <td><%= user.getUsername() %></td>
                <td><%= user.getUserRole() %></td>
                <td>
                    <form action="${pageContext.request.contextPath}/user" method="POST">
                        <input type="hidden" name="action" value="setRole">
                        <input type="hidden" name="username" value="<%= user.getUsername() %>">
                        <select name="newRole">
                            <option value="ADMIN" <%= "ADMIN".equals(user.getUserRole().name()) ? "selected" : "" %>>Admin</option>
                            <option value="STAFF" <%= "STAFF".equals(user.getUserRole().name()) ? "selected" : "" %>>Staff</option>
                            <option value="CUSTOMER" <%= "CUSTOMER".equals(user.getUserRole().name()) ? "selected" : "" %>>Customer</option>

                        </select>
                        <button type="submit">Update Role</button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </main>


    <footer>
        <ul>
            <li>Payment & Delivery:  </li>
            <li>Contact:  </li>
            <li>Social media: </li>
        </ul>
    </footer>
</div><!-- end of container -->

<div id="copyright">
    <ul>
        <li>&copy; No Copyright </li>
        <li>Design: HTML5</li>
    </ul>
</div><!-- end of copyright -->

<div id="authors">
    <ul>
        <li>KTH HI1031 Labb1 HT2024</li>
        <li>cjpob@somemail.se</li>
        <li>jzbk@somemail.se</li>
    </ul>
</div><!-- end of authors -->
</body>
</html>
