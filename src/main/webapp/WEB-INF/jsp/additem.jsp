<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

                <li><a href="${pageContext.request.contextPath}/cart">my cart</a></li>
                <%-- Check if the user is logged in and is an admin, display extra menu --%>
                <%--  <% if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("admin")) { %>   --%>
                <li><a href="../../adminpage.jsp">admin</a></li>
                <%--     <% } %>   --%>
            </ul>
        </nav>
    </header>


    <main>
        <form action="${pageContext.request.contextPath}/item" method="POST" id="additemForm">
            <input type="hidden" name="action" value="addItem" />
            <p>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </p>
            <p>
                <label for="description">Description:</label>
                <input type="text" id="description" name="description" required>
            </p>
            <p>
                <label for="price">Price:</label>
                <input type="number" id="price" name="price" required>
            </p>
            <p>
                <label for="quantity">Quantity:</label>
                <input type="number" id="quantity" name="quantity" required>
            </p>
            <p>
                <label for="type">Item Type:</label>
                <select id="type" name="type" required>
                    <option value="SURFBOARD">Surfboard</option>
                    <option value="TOWEL">Towels</option>
                    <option value="WETSUIT">Wetsuit</option>
                    <option value="SUNSCREEN">Sunscreen</option>
                </select>
            </p>
            <p>
                <label for="colour">Item Colour:</label>
                <select id="colour" name="colour" required>
                    <option value="RED">Red</option>
                    <option value="BLUE">Blue</option>
                    <option value="GREEN">Green</option>
                    <option value="YELLOW">Yellow</option>
                    <option value="BLACK">Black</option>
                    <option value="WHITE">White</option>
                </select>
            </p>
            <p>
                <input type="submit" value="Add Item" />
                <input type="reset" value="Clear the form" />
            </p>
        </form>
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