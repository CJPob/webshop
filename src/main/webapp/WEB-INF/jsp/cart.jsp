<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Collection" %>
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


    <main>
        <%
            Collection<ItemInfo> cartItems = (Collection<ItemInfo>) request.getAttribute("cartItems");
            double totalAmount = 0.0; // To calculate total amount

            if (cartItems != null && !cartItems.isEmpty()) {
        %>
        <table id="shoppingcart">
            <thead>
            <tr>
                <th>Product Name</th>
                <th>Unit Price</th>
                <th>Quantity</th>
                <th>Total Price</th>
                <th>Actions</th> <!-- Added for the Remove button -->
            </tr>
            </thead>
            <tbody>
            <%
                for (ItemInfo item : cartItems) {
                    double itemTotal = item.getPrice() * item.getQuantity();
                    totalAmount += itemTotal;
            %>
            <tr>
                <td><%= item.getName() %></td>
                <td>$<%= item.getPrice() %></td>
                <td><%= item.getQuantity() %></td>
                <td>$<%= itemTotal %></td>
                <td>
                    <form action="#" method="POST"> <!-- Placeholder for future action -->
                        <button type="button" class="removeitem">Remove</button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
            <tr>
                <td colspan="3" style="text-align: right; font-weight: bold;">Total Amount to Pay:</td>
                <td style="font-weight: bold;">$<%= totalAmount %></td>
                <td></td> <!-- Empty for the actions column -->
            </tr>
            <tr>
                <td colspan="5" style="text-align: right;">
                    <form action="#" method="POST" style="display: inline-block;">
                        <button type="button" class="continue-btn">Empty Cart</button>
                    </form>
                    <form action="<%= request.getContextPath() %>/order" method="POST" style="display: inline-block;">
                        <input type="hidden" name="action" value="placeOrder">
                        <button type="submit" class="continue-btn">Place Order</button>
                    </form>
                </td>
            </tr>
            </tbody>
        </table>
        <%
        } else {
        %>
        <p>Your cart is empty.</p>
        <%
            }
        %>
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

