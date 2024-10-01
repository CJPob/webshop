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
        <li><a href="../../login.jsp">log in</a></li>
        <li><a href="${pageContext.request.contextPath}/cart">my cart</a></li>
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
    <!-- Start of items grid -->
    <div class="items-grid">
      <%
        for (ItemInfo item : items) {
      %>
      <div class="item">
        <h3><%= item.getName() %></h3>
        <p>Price: $<%= item.getPrice() %></p>
        <p>Description: <%= item.getDescription() %></p>

        <!-- add to cart button -->
        <form>
          <button type="submit">Add to Cart</button>
        </form>
      </div>
      <%
        }
      %>
    </div>  <!-- End of items grid -->
    <%
    } else {
    %>
    <p>No items available.</p>
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