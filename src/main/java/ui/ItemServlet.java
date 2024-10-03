package ui;

import bo.ItemHandler;
import bo.ItemType;
import bo.ItemColour;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;


@WebServlet("/item")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemName = request.getParameter("name");
        String itemType = request.getParameter("type");
        String itemColour = request.getParameter("colour");

        Collection<ItemInfo> items = null;
        try {
            if (itemName != null && !itemName.isEmpty()) {
                items = ItemHandler.searchByName(itemName);
            } else if (itemColour != null && !itemColour.isEmpty()) {
                items = ItemHandler.searchByColour(itemColour);
            } else if (itemType == null || itemType.isEmpty()) {
                items = ItemHandler.searchAll();
            } else if ("IN_STOCK".equals(itemType)) {
                items = ItemHandler.searchByInStock();
            } else {
                items = ItemHandler.searchByType(itemType);
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while fetching items: " + e.getMessage());
            e.printStackTrace();
        }

        request.setAttribute("items", items);
        request.getRequestDispatcher("/WEB-INF/jsp/mainpage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            int price = Integer.parseInt(req.getParameter("price"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            String type = req.getParameter("type");
            String colour = req.getParameter("colour");

            boolean success = ItemHandler.createItem(
                    name,
                    ItemType.valueOf(type),
                    ItemColour.valueOf(colour),
                    price,
                    quantity,
                    description
            );

            if (success) {
                req.setAttribute("successMessage", "success!");
            } else {
                req.setAttribute("error", "Item creation failed.");
            }

        } catch (Exception e) {
            req.setAttribute("error", "An error occurred: " + e.getMessage());
        } finally {
            req.getRequestDispatcher("/WEB-INF/jsp/mainpage.jsp").forward(req, resp);
        }
    }
}