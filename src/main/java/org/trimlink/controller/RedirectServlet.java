package org.trimlink.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.trimlink.dao.UrlDao;
import org.trimlink.model.Url;

import java.io.IOException;

@WebServlet("/url/*")
public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null || path.length() <= 1) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String slug = path.substring(1);

        UrlDao urlDao = new UrlDao();
        Url url = urlDao.findBySlug(slug);

        if (url == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        res.sendRedirect(url.getLongUrl());
    }
}
