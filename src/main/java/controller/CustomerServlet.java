package controller;

import model.Customer;
import service.CustomerDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CustomerServlet", value = "/customers")
public class CustomerServlet extends HttpServlet {
    CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreateForm(request,response);
                break;
            case "edit":
                try {
                    showEditForm(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "delete":
                try {
                    deleteCustomer(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "find":
                try {
                    showFindForm(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                showList(request, response);
        }
    }

    private void showFindForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
   RequestDispatcher requestDispatcher=request.getRequestDispatcher("customer/find.jsp");
//   String name =request.getParameter("name");
//   List<Customer> customers=customerDAO.findByName(name);
//   request.setAttribute("find",customers);
   requestDispatcher.forward(request,response);
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
   int id = Integer.parseInt(request.getParameter("id"));
   customerDAO.delete(id);
   response.sendRedirect("/customers");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
    RequestDispatcher requestDispatcher=request.getRequestDispatcher("customer/edit.jsp");
    int id = Integer.parseInt(request.getParameter("id"));
    Customer customer =customerDAO.findById(id);
    request.setAttribute("khCanSua",customer);
    requestDispatcher.forward(request,response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   RequestDispatcher requestDispatcher =request.getRequestDispatcher("customer/create.jsp");
   requestDispatcher.forward(request,response);
    }

    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer/list.jsp");
        List<Customer> customers = customerDAO.findAll();
        request.setAttribute("dskh", customers);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                try {
                    save(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "edit":
                try {
                    editCustomer(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "find":
                try {
                    findCustomer(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    private void findCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer/find.jsp");
        String key = request.getParameter("name");
        List<Customer> customers = customerDAO.findByName(key);
        request.setAttribute("find", customers);
        requestDispatcher.forward(request, response);
    }



    private void editCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
     int id = Integer.parseInt(request.getParameter("id"));
     String name = request.getParameter("name");
     int age = Integer.parseInt(request.getParameter("age"));
    customerDAO.update(id,new Customer(id,name,age));
    response.sendRedirect("/customers");
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name =request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        customerDAO.add(new Customer(id,name,age));
        response.sendRedirect("/customers");

    }
}

