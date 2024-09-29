package ui;

import bo.UserHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean loginSuccess = UserHandler.loginUser(username, password);

        if (loginSuccess) {
            req.getSession().setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/item");
        } else { // TODO this only reloads the page, need to impl error message or something
            resp.sendRedirect(req.getContextPath() + "/frontend/jsp/login.jsp");
        }
    }
}