<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="ui.ItemInfo" %>
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
                <li><a href="../../login.jsp">log in</a></li>
                <li><a href="cart.jsp">my cart</a></li>
                <%-- Check if the user is logged in and is an admin, display extra menu --%>
                <%--  <% if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("admin")) { %>   --%>
                <li><a href="../../adminpage.jsp">admin</a></li>
                <%--     <% } %>   --%>
            </ul>
        </nav>
    </header>


    <main>
        <%
            // Retrieve the cartItems from the servlet
            Collection<ItemInfo> cartItems = (Collection<ItemInfo>) request.getAttribute("cartItems");

            // Check if the cart is empty
            if (cartItems == null || cartItems.isEmpty()) {
        %>
        <p>Your cart is empty.</p>
        <%
        } else {
        %>
        <h2>Total Items: <%= cartItems.size() %></h2>
        <ul>
            <%
                // Loop through the cart items and display them
                for (ItemInfo item : cartItems) {
            %>
            <li>
                <strong>Item:</strong> <%= item.getName() %><br>
                <strong>Quantity:</strong> <%= item.getQuantity() %><br>
                <strong>Price:</strong> $<%= item.getPrice() %><br>
                <strong>Subtotal:</strong> $<%= item.getPrice() * item.getQuantity() %>

                <!-- Form for removing the item -->
                <form action="<%= request.getContextPath() %>/cart" method="post">
                    <input type="hidden" name="action" value="remove">
                    <input type="hidden" name="itemID" value="<%= item.getId() %>">
                    <input type="submit" value="Remove from Cart">
                </form>
            </li>
            <hr>
            <%
                }
            %>
        </ul>
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