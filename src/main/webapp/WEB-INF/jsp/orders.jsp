<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ui.OrderInfo" %>
<%@ page import="ui.ItemInfo" %>
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

<%-- CJPob --%>
    <main>
        <table id="orderstable">
            <thead>
            <tr>
                <th colspan="5">Orders</th>
            </tr>
            <tr>
                <th colspan="5">
                    <form action="<%= request.getContextPath() %>/order" method="POST" style="text-align: center;">
                        <input type="hidden" name="action" value="sendOrder">
                        <label for="orderId" style="margin-right: 10px;">Enter Order ID to send:</label>
                        <input type="number" id="orderId" name="orderId" required style="margin-right: 10px;">
                        <button type="submit">Send Order</button>
                    </form>
                </th>
            </tr>
            <tr>
                <th>Order ID</th>
                <th>User ID</th>
                <th>Item ID</th>
                <th>Quantity</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <%
                Collection<OrderInfo> allOrders = (Collection<OrderInfo>) request.getAttribute("orders");
                if (allOrders != null && !allOrders.isEmpty()) {
                    for (OrderInfo order : allOrders) {
                        for (ItemInfo item : order.getItems()) {
            %>
            <tr>
                <td><%= order.getOrderId() %></td>
                <td><%= order.getUserId() %></td>
                <td><%= item.getId() %></td>
                <td><%= item.getQuantity() %></td>
                <td><%= order.getStatus() %></td>
            </tr>
            <%
                    } // End of item loop
                } // End of order loop
            } else {
            %>
            <tr>
                <td colspan="5">No orders available.</td>
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