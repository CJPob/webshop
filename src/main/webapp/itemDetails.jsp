<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <title>surfboards</title>
    <link href="frontend/css/reset.css" rel="stylesheet" >
    <link href="frontend/images/favicon.ico" rel="icon">
    <link rel="stylesheet" type="text/css" href="frontend/css/main.css">
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
                <li><a href="login.jsp">log in</a></li>
                <% } else { %>
                <li><a href="${pageContext.request.contextPath}/user">my account</a></li>  <%-- Redirect to /user --%>
                <% } %>

                <li><a href="${pageContext.request.contextPath}/cart">my cart</a></li>
                <%-- Check if the user is logged in and is an admin, display extra menu --%>
                <%--  <% if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("admin")) { %>   --%>
                <li><a href="adminpage.jsp">admin</a></li>
                <%--     <% } %>   --%>
            </ul>
        </nav>
    </header>


    <main>
        <table id="display-item">
            <tr>
                <td>
                    <img src="#" alt="item image">
                </td>
                <td>
                    <p><strong>Name:</strong> <%= request.getParameter("name") %></p>
                    <p><strong>Price:</strong> $<%= request.getParameter("price") %></p>
                    <p><strong>Description:</strong> <%= request.getParameter("description") %></p>

                    <!-- add to cart form -->
                    <form action="${pageContext.request.contextPath}/cart" method="POST">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="itemID" value="<%= request.getParameter("itemID") %>">
                        <label for="quantity">Quantity:</label>
                        <input type="number" id="quantity" name="quantity" min="1" value="1" required><br><br>
                        <button type="submit">Add to Cart</button>
                    </form>

                </td>
            </tr>
        </table>
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
