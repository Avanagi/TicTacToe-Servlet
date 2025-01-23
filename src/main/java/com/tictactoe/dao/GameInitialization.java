package com.tictactoe.dao;

import com.tictactoe.entity.Field;
import com.tictactoe.entity.Sign;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@WebServlet(name = "GameInitialization", value = "/start")
public class GameInitialization extends HttpServlet {
    private final String gamePage = "/index.jsp";

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            HttpSession httpSession = httpServletRequest.getSession(true);
            Field field = new Field();
            List<Sign> data = field.getFieldData();
            httpSession.setAttribute("field", field);
            httpSession.setAttribute("data", data);
            getServletContext().getRequestDispatcher(gamePage).forward(httpServletRequest, httpServletResponse);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}