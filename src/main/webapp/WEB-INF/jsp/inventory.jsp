<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="ui.ItemInfo, bo.ItemType" %>
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

                <%-- Check if the user is logged in --%>
                <% if (session.getAttribute("userId") == null) { %>
                <li><a href="../../login.jsp">log in</a></li>
                <% } else { %>
                <li><a href="${pageContext.request.contextPath}/user">my account</a></li>  <%-- Redirect to /user --%>
                <% } %>

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
            Collection<ItemInfo> items = (Collection<ItemInfo>) request.getAttribute("items");
            if (items != null && !items.isEmpty()) {
        %>
        <!-- Start of inventory list -->
        <table id="inventorytable" style="width: 80%; border-collapse: collapse; margin: 40px 20px;">
            <thead>
            <tr>
                <th colspan="7">Warehouse / Inventory</th>
            </tr>
            <tr>
                <td colspan="3">
                    <button onclick="window.location.href='${pageContext.request.contextPath}/admin?action=addItemPage'">Add New Item</button>

                </td>
                <td colspan="6">
                    <form action="${pageContext.request.contextPath}/item" method="get">
                        <label for="type">Filter by Category:</label>
                        <select name="type" id="type">
                            <option value="">All</option>
                            <% for (ItemType itemType : ItemType.values()) { %>
                            <option value="<%= itemType.name() %>"
                                    <%= itemType.name().equals(request.getParameter("type")) ? "selected" : "" %>>
                                <%= itemType.getType() %>
                            </option>
                            <% } %>
                        </select>
                        <button type="submit">Filter</button>
                    </form>
                </td>

            </tr>
            <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Colour</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Description</th>
                <th>Change Saldo</th>
            </tr>
            </thead>
            <tbody>
            <% for (ItemInfo item : items) { %>
            <tr>
                <td><%= item.getName() %></td>
                <td><%= item.getType().getType() %></td>
                <td><%= item.getColour().name() %></td>
                <td>$<%= item.getPrice() %></td>
                <td><%= item.getQuantity() %></td>
                <td><%= item.getDescription() %></td>
                <td>
                    <form action="${pageContext.request.contextPath}/item" method="post">
                        <input type="hidden" name="action" value="updateQuantity" />
                        <input type="hidden" name="itemId" value="<%= item.getId() %>" />
                        <input type="number" name="changeAmount" placeholder="+/- amount" style="width: auto;" required />
                        <button type="submit">Update</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <!-- End of inventory list -->
        <% } else { %>
        <p>No items available in the inventory.</p>
        <% } %>
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