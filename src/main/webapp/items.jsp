<%@ page import="ui.ItemInfo" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="bo.ItemHandler" %><%--
  Created by IntelliJ IDEA.
  User: CJayp
  Date: 2024-09-27
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Items List</title>
</head>
<body>
<h2>Items List</h2>

<ul>
  <c:forEach var="item" items="${items}">
    <li>
      Name: ${item.name}, Type: ${item.type}, Colour: ${item.colour}, Price: ${item.price}, Quantity: ${item.quantity}, Description: ${item.description}
    </li>
  </c:forEach>
</ul>

</body>
</html>
