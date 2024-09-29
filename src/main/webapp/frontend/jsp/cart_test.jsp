<%--
  Created by IntelliJ IDEA.
  User: CJayp
  Date: 2024-09-29
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ui.ItemInfo" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Shopping Cart</title>
</head>
<body>
<h1>Your Shopping Cart</h1>

<%
  Collection<ItemInfo> cartItems = (Collection<ItemInfo>) request.getAttribute("cartItems");

  if (cartItems == null || cartItems.isEmpty()) {
%>
<p>Your cart is empty.</p>
<%
} else {
%>
<h2>Total Items: <%= cartItems.size() %></h2>
<ul>
  <%
    for (ItemInfo item : cartItems) {
  %>
  <li>
    <strong>Item:</strong> <%= item.getName() %>,
    <strong>Quantity:</strong> <%= item.getQuantity() %>,
    <strong>Price:</strong> $<%= item.getPrice() %>
  </li>
  <%
    }
  %>
</ul>
<%
  }
%>
</body>
</html>
