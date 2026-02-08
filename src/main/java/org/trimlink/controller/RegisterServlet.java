package org.trimlink.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.trimlink.dao.UserDao;
import org.trimlink.model.User;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");


        if (username == null || username.isBlank() || password == null || password.isBlank()) {

            req.setAttribute("error", "Username and password are required");
            req.getRequestDispatcher("register.jsp").forward(req, res);
            return;
        }

        UserDao userDao = new UserDao();
        User newUser = new User();

        newUser.setUserName(username);
        newUser.setPassword(password);

        boolean userRegistered = userDao.registerUser(newUser);

        if (userRegistered) {
            res.sendRedirect("login.jsp");
        } else {
            req.setAttribute("error", "Registration failed. Try again.");
            req.getRequestDispatcher("register.jsp").forward(req, res);
        }
    }
}
