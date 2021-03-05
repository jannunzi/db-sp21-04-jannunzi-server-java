package com.example.springtemplate.daos;

import com.example.springtemplate.models.Blog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownServiceException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BlogsDao {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String HOST = "localhost:3306";
    static final String SCHEMA = "db_design";
    static final String CONFIG = "serverTimezone=UTC";
    static final String DB_URL =
            "jdbc:mysql://"+HOST+"/"+SCHEMA+"?"+CONFIG;
    static final String USER = "cs3200";
    static final String PASS = "cs3200";

    @GetMapping("/create/blog")
    public Integer createBlog(
            @RequestParam("name") String name,
            @RequestParam("topic") String topic) throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL,  USER, PASS);
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO blogs (name, topic) VALUES ('"+name+"', '"+topic+"')";
        return statement.executeUpdate(sql);
    }

    @GetMapping("/find/all/blogs")
    public List<Blog> findAllBlogs() throws ClassNotFoundException, SQLException {
        System.out.println("Welcome to findAllBlogs");

        List<Blog> blogs = new ArrayList<Blog>();

        Connection connection = null;
        Statement statement = null;
        
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        statement = connection.createStatement();
        String sql = "SELECT * FROM blogs";
        ResultSet results = statement.executeQuery(sql);
        while(results.next()) {
            Integer id = results.getInt("id");
            String name = results.getString("name");
            String topic = results.getString("topic");
            Timestamp created = results.getTimestamp("created");
            Timestamp updated = results.getTimestamp("updated");
            Integer user_id = results.getInt("user");
            Blog blog = new Blog();
            blog.setId(id);
            blog.setName(name);
            blog.setTopic(topic);
            blog.setCreated(created);
            blog.setUpdated(updated);
            blog.setUserId(user_id);
            
            blogs.add(blog);
        }
        return blogs;
    }

    @GetMapping("/find/blog")
    public Blog findBlogById(
            @RequestParam("id") Integer blogId) {
        
        Blog blog = null;
        
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            String sql = "SELECT * FROM blogs WHERE id=" + blogId;
            ResultSet results = statement.executeQuery(sql);
            if(results.next()) {
                Integer id = results.getInt("id");
                String name = results.getString("name");
                String topic = results.getString("topic");
                Timestamp created = results.getTimestamp("created");
                Timestamp updated = results.getTimestamp("updated");
                Integer user_id = results.getInt("user");
                blog = new Blog();
                blog.setId(id);
                blog.setName(name);
                blog.setTopic(topic);
                blog.setCreated(created);
                blog.setUpdated(updated);
                blog.setUserId(user_id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return blog;
    }
    
    @GetMapping("/update/blog")
    public Integer updateBlog(
            @RequestParam("id") Integer id,
            @RequestParam("name") String newName) throws ClassNotFoundException, SQLException {
         Class.forName(JDBC_DRIVER);
         Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement statement = connection.createStatement();
         String sql = "UPDATE blogs SET name='"+newName+"' WHERE id=" + id;
         return statement.executeUpdate(sql);
    }
    
    @GetMapping("/delete/blog")
    public Integer deleteBlog(
            @RequestParam("id") Integer id) throws ClassNotFoundException, SQLException {
        Connection connection;
        Statement statement;
        
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        statement = connection.createStatement();
        String sql = "DELETE FROM blogs WHERE id=" + id;
        return statement.executeUpdate(sql);
    }
    public static void main(String[] args) throws Exception {
        
        BlogsDao dao = new BlogsDao();
        
//        dao.deleteBlog(17);
//        dao.createBlog("My Brand New SpaceX Blog", "SPACE");
        
        dao.updateBlog(25, "SN10 Blew Up After a Bumpy Landing");
        
        List<Blog> blogs = dao.findAllBlogs();
        for(Blog blog: blogs) {
            System.out.println(blog);
        }
        
//        Blog spacex = dao.findBlogById(3);
//        System.out.println(spacex);
    }
}
