package org.trimlink.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.trimlink.dao.UserDao;
import org.trimlink.model.User;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserDao userDao = new UserDao();

        User user = userDao.loginUser(req.getParameter("username"), req.getParameter("password"));

        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("username", user.getUserName());
            session.setAttribute("userId", user.getUserId());

            res.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("login.jsp").forward(req, res);
        }
    }
}
