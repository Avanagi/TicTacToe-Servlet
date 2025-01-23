package com.tictactoe.dao;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "GameRestart", value = "/restart")
public class GameRestart extends HttpServlet {
    private final String startingPage = "/start";

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            httpServletRequest.getSession().invalidate();
            httpServletResponse.sendRedirect(startingPage);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}