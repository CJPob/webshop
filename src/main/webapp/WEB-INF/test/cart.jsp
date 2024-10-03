<%@ page import="ui.ItemInfo" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Your Cart</title>
  <style>
    table {
      width: 100%;
      border-collapse: collapse;
    }
    table, th, td {
      border: 1px solid black;
    }
    th, td {
      padding: 10px;
      text-align: left;
    }
  </style>
</head>
<body>

<h2>Your Cart</h2>

<!-- Check if the cart is empty -->
<%
  Collection<ItemInfo> cartItems = (Collection<ItemInfo>) request.getAttribute("cartItems");
  if (cartItems == null || cartItems.isEmpty()) {
%>
<p>Your cart is empty.</p>
<%
} else {
%>
<table>
  <thead>
  <tr>
    <th>Item ID</th>
    <th>Item Name</th>
    <th>Type</th>
    <th>Colour</th>
    <th>Price</th>
    <th>Quantity</th>
    <th>Description</th>
  </tr>
  </thead>
  <tbody>
  <%
    for (ItemInfo item : cartItems) {
  %>
  <tr>
    <td><%= item.getId() %></td>
    <td><%= item.getName() %></td>
    <td><%= item.getType() %></td>
    <td><%= item.getColour() %></td>
    <td><%= item.getPrice() %></td>
    <td><%= item.getQuantity() %></td>
    <td><%= item.getDescription() %></td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>
<%
  }
%>

<!-- Display success or error messages -->
<%
  String message = (String) request.getAttribute("message");
  String error = (String) request.getAttribute("error");

  if (message != null) {
%>
<p style="color: green;"><%= message %></p>
<%
  }

  if (error != null) {
%>
<p style="color: red;"><%= error %></p>
<%
  }
%>

<!-- Form to add an item to the cart -->
<h3>Add Item to Cart</h3>
<form action="<%= request.getContextPath() %>/cart" method="POST">
  <label for="itemId">Item ID:</label>
  <input type="text" id="itemId" name="itemId" required><br><br>

  <label for="quantity">Quantity:</label>
  <input type="number" id="quantity" name="quantity" min="1" value="1" required><br><br>

  <input type="submit" value="Add to Cart">
</form>

</body>
</html>
