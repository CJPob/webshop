<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ui.OrderInfo" %>
<%@ page import="ui.ItemInfo" %>
<!DOCTYPE html>

<html>
<head>
  <title>Orders Overview</title>
  <link href="../../frontend/css/reset.css" rel="stylesheet" >
  <link href="../../frontend/images/favicon.ico" rel="icon">
  <link rel="stylesheet" type="text/css" href="../../frontend/css/main.css">
</head>

<body>
<div id="container">
  <header>
    <h1>Orders Overview</h1>
    <nav>
      <ul>
        <li><a href="${pageContext.request.contextPath}/item">Home</a></li>
      </ul>
    </nav>
  </header>

  <main>
    <section>
      <h3>Send an Order</h3>
      <form action="<%= request.getContextPath() %>/order" method="POST">
        <input type="hidden" name="action" value="sendOrder">
        <label for="orderId">Enter Order ID to send:</label>
        <input type="number" id="orderId" name="orderId" required>
        <button type="submit">Send Order</button>
      </form>
    </section>

    <hr>

    <%
      Collection<OrderInfo> allOrders = (Collection<OrderInfo>) request.getAttribute("orders");

      if (allOrders != null && !allOrders.isEmpty()) {
    %>
    <table id="orders">
      <thead>
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
        for (OrderInfo order : allOrders) {
          for (ItemInfo item : order.getItems()) {
      %>
      <tr>
        <td><%= order.getOrderId() %></td>
        <td><%= order.getUserId() %></td>
        <td><%= item.getId() %></td> <!-- Item ID -->
        <td><%= item.getQuantity() %></td> <!-- Display Quantity -->
        <td><%= order.getStatus() %></td> <!-- Order Status -->
      </tr>
      <%
          } // End of item loop
        } // End of order loop
      %>
      </tbody>
    </table>
    <%
    } else {
    %>
    <p>No orders available.</p>
    <%
      }
    %>
  </main>

  <footer>
    <ul>
      <li>Payment & Delivery</li>
      <li>Contact</li>
      <li>Social media</li>
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
