/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linh.booksclient.servlet;

import com.linh.booksclient.dto.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author nguye
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/Search.do"})
public class SearchServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public Book getABook(HttpServletRequest request) {

        String baseURI = "http://localhost:8080/BooksWS/api/books";

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(baseURI);

        String isbn = request.getParameter("isbn");
        System.out.println("isbn"+isbn);

        //gọi service để tìm 1 cuốn sách ta cần thêm tham số {isbn} đưa vào
        //do đó hàm gọi cần bổ sung thêm .path(mã-sách-đưa-vào)
        //tương đương gọi qua url .../api/books/mã-sách-đưa-vào
        Book book = target.path(isbn).request().accept(MediaType.APPLICATION_JSON).get(Book.class);

        Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, "LinhCN checkpoint > SWT Book Store > Get a book: " + book);
        //gọi thầm tên em, in kết quả cuốn sách tìm thấy vào log file để kiểm tra

        return book;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Book book = getABook(request);
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>The book info</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h5>Debug info: This Servlet BookList is invoked from " + request.getContextPath() + "</h1>");
            if (book == null) {
                out.println("<h1>The book not found!!!</h1>");
            } else {
                out.println("<h1>The book you are looking for!!!</h1>");
                out.println("<table border='1px' style='border-collapse: collapse'>"
                        + "<tr>"
                        + "<th>ISBN</th>"
                        + "<th>Title</th>"
                        + "<th>Author</th>"
                        + "<th>Edition</th>"
                        + "<th>Year</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>" + book.getIsbn() + "</td>"
                        + "<td>" + book.getTitle() + "</td>"
                        + "<td>" + book.getAuthor() + "</td>"
                        + "<td>" + book.getEdition() + "</td>"
                        + "<td>" + book.getPublishedYear() + "</td>"
                        + "</tr>"
                        + "</table>");
            }

            out.println("<br><a href='search.html'>Return to the search page</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
