package ui;

import bo.ItemColour;
import bo.ItemHandler;
import bo.ItemType;
import bo.UserHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


// still needs to check userrole here from session
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/test/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        String colour = req.getParameter("colour");
        int price = Integer.parseInt(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String description = req.getParameter("description");

        boolean success = ItemHandler.createItem(
                name,
                ItemType.valueOf(type.toUpperCase()),
                ItemColour.valueOf(colour.toUpperCase()),
                price,
                quantity,
                description
        );

        if (success) {
            req.setAttribute("message", "Item created successfully!");
        } else {
            req.setAttribute("message", "Failed to create item.");
        }
        req.getRequestDispatcher("/WEB-INF/test/admin.jsp").forward(req, resp);
    }
}
