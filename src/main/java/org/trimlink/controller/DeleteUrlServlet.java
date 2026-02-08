package org.trimlink.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.trimlink.dao.UrlDao;

import java.io.IOException;

@WebServlet("/delete")
public class DeleteUrlServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int urlId = Integer.parseInt(req.getParameter("urlId"));

        UrlDao urlDao = new UrlDao();
        urlDao.deleteUrlById(urlId);
        res.sendRedirect(req.getContextPath() + "/dashboard");
    }
}
