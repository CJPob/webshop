<%-- Import necessary classes --%>
<%@ page import="ui.ItemInfo" %>
<%@ page import="java.util.Collection" %>

<html>
<head>
  <title>Shopping Cart</title>
</head>
<body>
<h1>Your Shopping Cart</h1>

<%
  // Retrieve the cartItems passed from the servlet
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
    <form action="cart" method="post">
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

<!-- Form for adding items to the cart -->
<h2>Add Item to Cart</h2>
<form action="cart" method="post">
  <p>
    <label for="itemID">Item ID:</label>
    <input type="number" id="itemID" name="itemID" required>
  </p>
  <p>
    <label for="quantity">Quantity:</label>
    <input type="number" id="quantity" name="quantity" value="1" required>
  </p>
  <p>
    <input type="hidden" name="action" value="add">
    <input type="submit" value="Add to Cart">
  </p>
</form>

</body>
</html>
