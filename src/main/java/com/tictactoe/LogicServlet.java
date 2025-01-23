package com.tictactoe;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        HttpSession httpSession = httpServletRequest.getSession();
        Field field = extractField(httpSession);
        int index = getSelectedIndex(httpServletRequest);

        Sign currentSign = field.getField().get(index);
        if (Sign.EMPTY != currentSign) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
            return;
        }
        field.getField().put(index, Sign.CROSS);
        if (checkWin(httpServletResponse, httpSession, field)) {
            return;
        }
        int emptyFieldIndex = field.getEmptyFieldIndex();
        if (emptyFieldIndex >= 0) {
            field.getField().put(emptyFieldIndex, Sign.NOUGHT);
        }
        if (emptyFieldIndex >= 0) {
            field.getField().put(emptyFieldIndex, Sign.NOUGHT);
            if (checkWin(httpServletResponse, httpSession, field)) {
                return;
            }
        } else {
            httpSession.setAttribute("draw", true);
            List<Sign> fieldData = field.getFieldData();
            httpSession.setAttribute("data", fieldData);
            httpServletResponse.sendRedirect("/index.jsp");
            return;
        }
        List<Sign> fieldData = field.getFieldData();
        httpSession.setAttribute("data", fieldData);
        httpSession.setAttribute("field", field);
        httpServletResponse.sendRedirect("/index.jsp");
    }

    private Field extractField(HttpSession httpSession) {
        Object fieldAttribute = httpSession.getAttribute("field");
        if (Field.class != fieldAttribute.getClass()) {
            httpSession.invalidate();
            throw new RuntimeException("Session is broken, try one more time");
        }
        return (Field) fieldAttribute;
    }

    private int getSelectedIndex(HttpServletRequest httpServletRequest) {
        String click = httpServletRequest.getParameter("click");
        boolean isNumeric = click.chars().allMatch(Character::isDigit);
        return isNumeric ? Integer.parseInt(click) : 0;
    }

    private boolean checkWin(HttpServletResponse httpServletResponse, HttpSession httpSession, Field field) throws IOException {
        Sign winner = field.checkWin();
        if (Sign.CROSS == winner || Sign.NOUGHT == winner) {
            httpSession.setAttribute("winner", winner);
            List<Sign> fieldData = field.getFieldData();
            httpSession.setAttribute("data", fieldData);
            httpServletResponse.sendRedirect("/index.jsp");
            return true;
        }
        return false;
    }
}
