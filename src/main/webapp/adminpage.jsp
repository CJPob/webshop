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

                <%-- Check if the user is logged in --%>
                <% if (session.getAttribute("userId") == null) { %>
                <li><a href="login.jsp">log in</a></li>
                <% } else { %>
                <li><a href="${pageContext.request.contextPath}/user">my account</a></li>  <%-- Redirect to /user --%>
                <% } %>

                <li><a href="${pageContext.request.contextPath}/cart">my cart</a></li>
                <%-- Check if the user is logged in and is an admin, display extra menu --%>
                <%--  <% if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("admin")) { %>   --%>
                <li><a href="adminpage.jsp">admin</a></li>
                <%--     <% } %>   --%>
            </ul>
        </nav>
    </header>

    <main>
        <div class="submenu">
            <button class="continue-btn admin-orders-btn" onclick="window.location.href='orders.jsp'">See orders and their statuses</button>
            <button class="continue-btn admin-inventory-btn" onclick="window.location.href
                    ='${pageContext.request.contextPath}/item?filter=all'">Inventory / current items saldo</button>
            <button class="continue-btn admin-additem-btn" onclick="window.location.href=
                    '${pageContext.request.contextPath}/admin?action=addItemPage'">Add / create new item</button>
            <button class="continue-btn admin-removeitem-btn" onclick="window.location.href='removeitem.jsp'">Change saldo or remove item</button>
            <button class="continue-btn admin-seeusers-btn" onclick="window.location.href=
                    '${pageContext.request.contextPath}/admin?action=viewUsers'">See users, set privileges</button>


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
can we also fix the path tu list of users?

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