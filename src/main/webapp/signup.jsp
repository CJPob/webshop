<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <title>surfboards</title>
    <link href="frontend/css/reset.css" rel="stylesheet" >
    <link href="frontend/images/favicon.ico" rel="icon">
    <link rel="stylesheet" type="text/css" href="frontend/css/main.css">
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
        <div class="signup-form">
            <form action="${pageContext.request.contextPath}/user" method="POST" id="signupForm">
                <input type="hidden" name="action" value="signup">
                <!-- Name field for t_user -->
                <label for="name">Name:* </label>
                <input  type="text"
                        id="name"
                        name="name"
                        required>

                <label for="username">Username:* </label>
                <input  type="text"
                        id="username"
                        name="username"
                        required>

                <label for="password">Password:* </label>
                <input  type="password"
                        id="password"
                        name="password"
                        required>

                <label for="password2">Repeat password:* </label>
                <input  type="password"
                        id="password2"
                        name="password2"
                        required>

                <div class="checkboxes">
                    <label class="checkbox-label">
                        <input type="checkbox" id="privacyPolicy" name="privacyPolicy" required>
                        I have read and understood the&nbsp;<a href="#">Privacy Policy</a>.
                    </label>

                    <label class="checkbox-label">
                        <input type="checkbox" id="termsOfService" name="termsOfService" required>
                        I have read and accept the&nbsp;<a href="#">Terms of Service</a>.
                    </label>

                    <label class="checkbox-label">
                        <input type="checkbox" id="offers" name="offers">
                        I would like to subscribe to the newsletter.
                    </label>
                </div>

                <button type="submit" class="register-btn">Submit</button>
            </form>
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