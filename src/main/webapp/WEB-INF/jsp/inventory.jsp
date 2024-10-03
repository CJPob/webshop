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
        <h1>Inventory</h1>

        <%
            Collection<ItemInfo> items = (Collection<ItemInfo>) request.getAttribute("items");
            if (items != null && !items.isEmpty()) {
        %>
        <!-- Start of inventory list -->
        <table>
            <thead>
            <tr>
                <th>| Name:  </th>
                <th> | Type:  </th>
                <th> | Colour:  </th>
                <th> | Price:  </th>
                <th> | Quantity:  </th>
                <th> | Description:  </th>
            </tr>
            </thead>
            <tbody>
            <%
                for (ItemInfo item : items) {
            %>
            <tr>
                <td><%= item.getName() %></td>
                <td><%= item.getType().getType() %></td>
                <td><%= item.getColour().name() %></td>
                <td>$<%= item.getPrice() %></td>
                <td><%= item.getQuantity() %></td>
                <td><%= item.getDescription() %></td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <!-- End of inventory list -->
        <%
        } else {
        %>
        <p>No items available in the inventory.</p>
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

