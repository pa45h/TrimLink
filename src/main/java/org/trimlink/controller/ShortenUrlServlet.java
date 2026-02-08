package org.trimlink.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.trimlink.dao.UrlDao;

import java.io.IOException;

@WebServlet("/shorten")
public class ShortenUrlServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String longUrl = req.getParameter("originalUrl");
        String slug = req.getParameter("customSlug");

        HttpSession session = req.getSession(false);
        int userId = (int) session.getAttribute("userId");

        if(longUrl == null || slug == null){
            System.out.println("Enter proper details..");
        }

        UrlDao urlDao = new UrlDao();

        boolean urlGenerated = urlDao.generateShortUrl(longUrl,slug,userId);

        if (urlGenerated){
            System.out.println("URL generated");
            res.sendRedirect(req.getContextPath() + "/dashboard");
        }else{
            System.out.println("Error during url generating");
        }

    }
}
