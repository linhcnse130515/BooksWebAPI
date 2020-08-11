/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linh.booksws;

import com.linh.dao.BookDAO;
import com.linh.model.Book;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author nguye
 */
@Path("/books")             //.../api/books
public class BooksWS {
    //cac ham tra ve du lieu tho: XML, JSON, String, ... lay tu DB
    //tra qua GET method

    @Context
    UriInfo ui;
    //tiem chich/injection nhung mon cua ben Web app truyen thong
    //dem sang ben class nay xai, do class nay no k extend Servlet
    BookDAO dao = BookDAO.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return dao.getAll();
    }
//        @GET
//        @Produces(MediaType.TEXT_PLAIN)
//        public String sayHello(){
//            return "Ahihi, do ngoc. This is messages comes from RestAPI via GET method";
//        }
//        @GET
//        @Path("/getABook")      //.../api/books/getABook
//        @Produces(MediaType.APPLICATION_JSON)
//        public Book getABook(){
//            Book tt = new Book("1234", "ttbdgbn", "Rosie Nguyen", 2, 2018);
//            return tt;
//        }
//        @GET
//        @Path("/getAllBook")      //.../api/books/getABook
//        @Produces(MediaType.APPLICATION_JSON)
//        public List<Book> getAllBook(){
//            List<Book> books = dao.getAll();
//            return books;
//        }
    //muon insert data thi POST method
    //muon delete data thi DELETE method
    //muon update data thi PUT method

    //ham api tra ve 1 cuon sach bat ki neu minh dua vao isbn can lay
    //ta tra ve cuon sach qua viec go URL
    //GET method, tham so dua the nao qua URL:
    //.../api/getOne?isbn=value-ma-cuon-sach  -->GET truyen thong?
    //API khac chut: dau / va phia sau la tham so
    @GET
    @Path("{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getABook(@PathParam("isbn") String isbn) {
        return dao.getOne(isbn);
    }

    //insert data xuong CSDL thong qua API
    //sure, giang ho noi rang vi minh chen nhieu data, do do "k the k nen"
    //choi voi GET, vi ta can lay nhieu input tu nguoi dung
    //do do ta can <FORM>, phuong thuc POST
    //Web app truyen thong, ban tao form, tao servlet hung param
    //chuyen cho DAO xu li
    //API da lo viec xuong DB roi, ban chi can dua data thoi
    //{JSON object la duoc roi, XML object} va goi ham API
    //k can controller, chi goi API la du
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOne(Book book) throws URISyntaxException {

        String newIsbn = dao.add(book);
        URI uri = new URI(ui.getBaseUri() + "books/" + newIsbn);
        //goi API getOne nay /ma-cuon-sach
        //la kiem tra duoc cuon sach vua vao DB
        if (newIsbn != null) {
            return Response.created(uri).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
