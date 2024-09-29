package ui;

import bo.ShoppingCartHandler;
import ui.ItemInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;


// hardcoded servlet only checks johndoe fix later
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = "johndoe";
        Collection<ItemInfo> cartItems = ShoppingCartHandler.getItemsForUser(username);
        req.setAttribute("cartItems", cartItems);
        req.getRequestDispatcher("/frontend/jsp/cart_test.jsp").forward(req, resp);
    }

}
