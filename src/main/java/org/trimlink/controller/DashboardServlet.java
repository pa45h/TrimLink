package org.trimlink.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.trimlink.dao.UrlDao;
import org.trimlink.model.Url;

import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        int userId = (int) session.getAttribute("userId");

        UrlDao urlDao = new UrlDao();
        List<Url> urls = urlDao.getUrlsByUserId(userId);

        req.setAttribute("urls", urls);
        req.getRequestDispatcher("dashboard.jsp").forward(req, res);
    }
}


