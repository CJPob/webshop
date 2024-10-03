package ui;

import bo.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        switch (action) {
            case "viewUsers":
                // Retrieve all users and set them as a request attribute
                Collection<UserInfo> users = UserHandler.getAllUsers();
                req.setAttribute("users", users);
                // Forward to the users.jsp page (in the protected directory WEB-INF)
                req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
                break;
            case "viewItems":
                req.getRequestDispatcher("/WEB-INF/jsp/inventory.jsp").forward(req, resp);
                break;
            case "viewOrders":
                req.getRequestDispatcher("/order").forward(req, resp);
                break;
            case "addItemPage":
                // redirect to the additem.jsp page (in the protected WEB-INF directory)
                req.getRequestDispatcher("/WEB-INF/jsp/additem.jsp").forward(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        switch (action) {
            case "addItem":
                req.getRequestDispatcher("/item").forward(req, resp); // Forward to ItemServlet to add item
                break;
            case "setUserRole":
                req.getRequestDispatcher("/user").forward(req, resp); // Forward to UserServlet to set user role
                break;
            case "setQuantity":
                req.getRequestDispatcher("/item").forward(req, resp); // Forward to ItemServlet to set new quantity
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
    }
}
