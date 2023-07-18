package com.tap.application;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tap.dao.AdminDao;
import com.tap.model.Admin;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

@WebServlet("/")
public class AdminServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
    private AdminDao adminDAO;

    public void init() {
        adminDAO = new AdminDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertAdmin(request, response);
                    break;
                case "/delete":
                    deleteAdmin(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateAdmin(request, response);
                    break;
                default:
                    listAdmin(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listAdmin(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException, ServletException {
        List < Admin > listAdmin = adminDAO.selectAllAdmins();
        request.setAttribute("listAdmin", listAdmin);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Admin existingAdmin = adminDAO.selectAdmin(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin-form.jsp");
        request.setAttribute("admin", existingAdmin);
        dispatcher.forward(request, response);

    }

    private void insertAdmin(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Admin newAdmin = new Admin(name, email, password);
        adminDAO.insertAdmin(newAdmin);
        response.sendRedirect("list");
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Admin admin = new Admin(id, name, email, password);
        adminDAO.updateAdmin(admin);
        response.sendRedirect("list");
    }

    private void deleteAdmin(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        adminDAO.deleteAdmin(id);
        response.sendRedirect("list");

    }
	
}
