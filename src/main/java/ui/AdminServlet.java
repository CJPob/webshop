package ui;

import bo.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check user role
        HttpSession session = req.getSession(false);
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        if (userRole == null || !(userRole.equals("ADMIN") || userRole.equals("STAFF"))) {
            // User is not logged in or does not have the right role
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");

        if (action == null) {
            // No action specified, forward to adminpage.jsp
            req.getRequestDispatcher("/WEB-INF/jsp/adminpage.jsp").forward(req, resp);
            return;
        }

        switch (action) {
            case "viewUsers":
                Collection<UserInfo> users = UserHandler.getAllUsers();
                req.setAttribute("users", users);
                req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
                break;
            case "viewItems":
                req.getRequestDispatcher("/WEB-INF/jsp/inventory.jsp").forward(req, resp);
                break;
            case "addItemPage":
                req.getRequestDispatcher("/WEB-INF/jsp/additem.jsp").forward(req, resp);
                break;
                /* case "viewOrders":
                req.getRequestDispatcher("/order").forward(req, resp);
                break;*/
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
    }


@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter");
            return;
        }

        switch (action) {
            case "addItem":
                // Forward to ItemServlet to add item
                req.getRequestDispatcher("/item").forward(req, resp);
                break;
            case "setUserRole":
                // Forward to UserServlet to set user role
                req.getRequestDispatcher("/user").forward(req, resp);
                break;
            case "setQuantity":
                // Forward to ItemServlet to set new quantity
                req.getRequestDispatcher("/item").forward(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
    }
}