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
                <li><a href="../../index.jsp">home</a></li>
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
        <div class="submenu">
            <button class="continue-btn admin-orders-btn" onclick="window.location.href='orders.jsp'">See Orders and Their Statuses</button>
            <button class="continue-btn admin-inventory-btn" onclick="window.location.href='inventory.jsp'">Inventory (Current Items Saldo)</button>
            <button class="continue-btn admin-additem-btn" onclick="window.location.href='additem.jsp'">Add Item</button>
            <button class="continue-btn admin-removeitem-btn" onclick="window.location.href='removeitem.jsp'">Remove Item</button>
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

