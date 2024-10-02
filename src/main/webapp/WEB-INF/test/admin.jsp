<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Create Item (Test Admin)</title>
</head>

<body>
<h1>Create a New Item</h1>

<!-- Display success or error message -->
<%
  String message = (String) request.getAttribute("message");
  if (message != null) {
%>
<p><strong><%= message %></strong></p>
<%
  }
%>

<!-- Form to create a new item -->
<form method="post" action="admin">
  <label for="name">Item Name:</label>
  <input type="text" id="name" name="name" required><br>

  <label for="type">Item Type:</label>
  <select name="type" id="type" required>
    <option value="SURFBOARD">Surfboard</option>
    <option value="TOWEL">Towel</option>
    <option value="WETSUIT">Wetsuit</option>
    <option value="SUNSCREEN">Sunscreen</option>
  </select><br>

  <label for="colour">Item Colour:</label>
  <select name="colour" id="colour" required>
    <option value="RED">Red</option>
    <option value="BLUE">Blue</option>
    <option value="GREEN">Green</option>
    <option value="YELLOW">Yellow</option>
    <option value="BLACK">Black</option>
    <option value="WHITE">White</option>
  </select><br>

  <label for="price">Price:</label>
  <input type="number" id="price" name="price" min="0" required><br>

  <label for="quantity">Quantity:</label>
  <input type="number" id="quantity" name="quantity" min="0" required><br>

  <label for="description">Description:</label>
  <textarea id="description" name="description" required></textarea><br>

  <button type="submit">Create Item</button>
</form>

</body>
</html>
