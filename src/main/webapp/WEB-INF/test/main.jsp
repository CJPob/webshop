<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="ui.ItemInfo" %>
<%@ page import="java.util.Collection" %>
<!DOCTYPE html>

<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Items</title>
</head>

<body>
<h1>Display Items</h1>

<form method="get" action="item">

  <label for="name">Search by Name:</label>
  <input type="text" id="name" name="name" placeholder="Enter item name">

  <!-- Type Selection -->
  <label for="type">Select Item Type:</label>
  <select name="type" id="type">
    <option value="">All Items</option>
    <option value="IN_STOCK">In Stock</option>
    <option value="SURFBOARD">Surfboard</option>
    <option value="TOWEL">Towel</option>
    <option value="WETSUIT">Wetsuit</option>
    <option value="SUNSCREEN">Sunscreen</option>
  </select>

  <!-- Colour Selection -->
  <label for="colour">Select Colour:</label>
  <select name="colour" id="colour">
    <option value="">All Colours</option>
    <option value="RED">Red</option>
    <option value="BLUE">Blue</option>
    <option value="GREEN">Green</option>
    <option value="YELLOW">Yellow</option>
    <option value="BLACK">Black</option>
    <option value="WHITE">White</option>
  </select>

  <button type="submit">Filter</button>
</form>

<main>
  <%
    // Get the items collection from the request scope
    Collection<ItemInfo> items = (Collection<ItemInfo>) request.getAttribute("items");
    if (items != null && !items.isEmpty()) {
  %>
  <ul>
    <%
      for (ItemInfo item : items) {
    %>
    <li>
      <strong>ID:</strong> <%= item.getId() %>,
      <strong>Name:</strong> <%= item.getName() %>,
      <strong>Type:</strong> <%= item.getType() %>,
      <strong>Colour:</strong> <%= item.getColour() %>,
      <strong>Price:</strong> $<%= item.getPrice() %>,
      <strong>Quantity:</strong> <%= item.getQuantity() %>,
      <strong>Description:</strong> <%= item.getDescription() %>
    </li>
    <%
      }
    %>
  </ul>
  <%
  } else {
  %>
  <p>No items available.</p>
  <%
    }
  %>
</main>

</body>
</html>
