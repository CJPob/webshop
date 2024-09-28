<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>surfboards</title>
    <link href="../css/reset.css" rel="stylesheet" >
    <link href="../images/favicon.ico" rel="icon">
    <link rel="stylesheet" type="text/css" href="../css/main.css">
</head>

<body>
<div id="container">
    <header>
        <h1>SURFBOARDS</h1>
        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/item">home</a></li>
                <li><a href="login.jsp">log in</a></li>
                <li><a href="cart.jsp">my cart</a></li>
                <%-- Check if the user is logged in and is an admin, display extra menu --%>
                <%--  <% if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("admin")) { %>   --%>
                <li><a href="adminpage.jsp">admin</a></li>
                <%--     <% } %>   --%>
            </ul>
        </nav>
    </header>

    <main>
        <div class="login-form">
            <form action="LoginServletURLhere" method="POST" id="loginForm">
                <div class="login-prompt">
                    Log in:
                </div>

                <label for="email">E-mail:* </label>
                <input  type="email"
                        id="email"
                        name="email"
                        required>

                <label for="password">Password:* </label>
                <input  type="password"
                        id="password"
                        name="password"
                        required>

                <button type="submit"
                        class="continue-btn">Continue</button>
            </form>

            <div class="pwdrecovery-prompt">
                <a href="passwordrecovery.jsp" class="forgot-password">I forgot my password </a>
            </div>

            <div class="register-prompt">
                <p>Or, sign up here:</p>
                <button type="button" class="register-btn" onclick="location.href='signup.jsp'">Sign up</button>
            </div>
        </div>
    </main>

    <footer>
        <ul>
            <li>Payment & Delivery:  </li>
            <li>Contact:  </li>
            <li>Social media: </li>
        </ul>
    </footer>
</div>

<div id="copyright">
    <ul>
        <li>&copy; No Copyright </li>
        <li>Design: HTML5</li>
    </ul>
</div>

<div id="authors">
    <ul>
        <li>KTH HI1031 Labb1 HT2024</li>
        <li>cjpob@somemail.se</li>
        <li>jzbk@somemail.se</li>
    </ul>
</div>

</body>
</html>
