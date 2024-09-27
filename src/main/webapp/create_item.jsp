<%--
  Created by IntelliJ IDEA.
  User: CJayp
  Date: 2024-09-27
  Time: 23:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Create Item</title>
</head>
<body>
<h2>Create New Item</h2>

<form action="${pageContext.request.contextPath}/item" method="POST">
  <label for="name">Name:</label>
  <input type="text" id="name" name="name" required><br><br>

  <label for="description">Description:</label>
  <input type="text" id="description" name="description" required><br><br>

  <label for="price">Price:</label>
  <input type="number" id="price" name="price" required><br><br>

  <label for="quantity">Quantity:</label>
  <input type="number" id="quantity" name="quantity" required><br><br>

  <label for="type">Item Type:</label>
  <select id="type" name="type" required>
    <option value="SURFBOARD">Surfboard</option>
    <option value="TOWELS">Towels</option>
  </select><br><br>

  <label for="colour">Item Colour:</label>
  <select id="colour" name="colour" required>
    <option value="RED">Red</option>
    <option value="BLUE">Blue</option>
    <option value="GREEN">Green</option>
    <option value="YELLOW">Yellow</option>
    <option value="BLACK">Black</option>
  </select><br><br>

  <input type="submit" value="Create Item">
</form>

<c:if test="${not empty error}">
  <p style="color:red">${error}</p>
</c:if>

</body>
</html>
