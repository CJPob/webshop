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

/**
 * The AdminServlet handles admin-related actions such as viewing users and orders,
 * as well as adding items and setting user roles. It ensures that only users with ADMIN or STAFF roles can access these features.
 * This class also redirects to other servlets and hence also urls.
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        if (userRole == null || !(userRole.equals("ADMIN") || userRole.equals("STAFF"))) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");

        if (action == null) {
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
                case "viewOrders":
                req.getRequestDispatcher("/WEB-INF/jsp/order.jsp").forward(req, resp);
                break;
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
                req.getRequestDispatcher("/item").forward(req, resp);
                break;
            case "setUserRole":
                req.getRequestDispatcher("/user").forward(req, resp);
                break;
            case "setQuantity":
                req.getRequestDispatcher("/item").forward(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
    }
}