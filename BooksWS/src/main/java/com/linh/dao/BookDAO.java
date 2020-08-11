package com.linh.dao;

import com.linh.model.Book;
import com.linh.util.DBUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * © giáo.làng | fb/giao.lang.bis | FPT University - HCMC Campus 
 * Version 20.0705
 */

//Using Singleton pattern
public class BookDAO implements Serializable {

    private static BookDAO instance;
    private Connection conn = DBUtil.makeConnection();

    //cấm new trực tiếp BookDAO, chỉ đi qua con đường lấy trực tiếp BookDAO từ hàm static
    //Singleton pattern: ki thuat viet code de k co dc 2 new DAO trong RAM
    private BookDAO() {

    }

    public static BookDAO getInstance() {

        if (instance == null) {
            instance = new BookDAO();
        }
        return instance;
    }

    public List<Book> getAll() {

        PreparedStatement stm;
        ResultSet rs;

        List<Book> bookList = new ArrayList();
        try {

            String sql = "SELECT * FROM BOOK";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                bookList.add(new Book(rs.getString("Isbn"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("Edition"),
                        rs.getInt("PublishedYear")));
            }
        } catch (Exception ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookList;
    }

    public Book getOne(String isbn) {

        PreparedStatement stm;
        ResultSet rs;

        try {

            String sql = "SELECT * FROM BOOK WHERE ISBN = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, isbn);

            rs = stm.executeQuery();
            if (rs.next()) {
                return new Book(rs.getString("Isbn"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("Edition"),
                        rs.getInt("PublishedYear"));
            }

        } catch (Exception ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String add(Book book) {

        try {

            String sql = "INSERT INTO Book (Isbn, Title, Author, Edition, PublishedYear) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm;

            stm = conn.prepareStatement(sql);

            stm.setString(1, book.getIsbn());
            stm.setString(2, book.getTitle());
            stm.setString(3, book.getAuthor());
            stm.setInt(4, book.getEdition());
            stm.setInt(5, book.getPublishedYear());
            if (stm.executeUpdate() > 0) {
                return book.getIsbn();
            }
        } catch (Exception ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean update(Book book) {

        //TODO...
        return false;
    }

    public boolean delete(String isbn) {

        //TODO...
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        //Test select *
        System.out.println("All of books: \n" + getInstance().getAll()); //gọi thầm tên em
    }
}
