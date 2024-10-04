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

        <%-- Check if the user is logged in --%>
        <% if (session.getAttribute("userId") == null) { %>
        <li><a href="../../login.jsp">log in</a></li>
        <% } else { %>
        <li><a href="${pageContext.request.contextPath}/user">my account</a></li>  <%-- Redirect to /user --%>
        <% } %>

        <li><a href="${pageContext.request.contextPath}/cart">my cart</a></li>
        <%-- Check if the user is logged in and is an admin, display extra menu --%>
        <%--  <% if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("admin")) { --%>
        <li><a href="../../adminpage.jsp">admin</a></li>
        <%-- } %>   --%>
      </ul>
    </nav>
  </header>

  <main>
    <div class="user-details">
      <h2>User Information</h2>
      <table id="userdetails">
        <thead>
        <tr>
          <th>Name</th>
          <th>Username</th>
          <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <%
          UserInfo user = (UserInfo) request.getAttribute("user");
          if (user != null) {
        %>
        <tr>
          <td><%= user.getName() %></td>
          <td><%= user.getUsername() %></td>
          <td><%= user.getUserRole() %></td>
        </tr>
        <tr>
          <td colspan="3" style="text-align:center;">
            <form action="${pageContext.request.contextPath}/logout" method="POST" class="action-form">
              <button type="submit" class="continue-btn">Log out</button>
            </form>
          </td>
        </tr>
        <% } else { %>
        <tr>
          <td colspan="3">No user information available.</td>
        </tr>
        <% } %>
        </tbody>
      </table>
    </div>
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
