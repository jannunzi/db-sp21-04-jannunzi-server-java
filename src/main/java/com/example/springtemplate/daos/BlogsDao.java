package com.example.springtemplate.daos;

import com.example.springtemplate.models.Blog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogsDao {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String HOST = "localhost:3306";
    static final String SCHEMA = "db_design";
    static final String CONFIG = "serverTimezone=UTC";
    static final String DB_URL =
            "jdbc:mysql://"+HOST+"/"+SCHEMA+"?"+CONFIG;
    static final String USER = "cs3200";
    static final String PASS = "cs3200";

    public void createBlog() {}

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

    public Blog findBlogById(Integer blogId) {
        
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
    public void updateBlog() {}
    public void deleteBlog() {}
    public static void main(String[] args) throws Exception {
        
        BlogsDao dao = new BlogsDao();
//        List<Blog> blogs = dao.findAllBlogs();
//        for(Blog blog: blogs) {
//            System.out.println(blog);
//        }
        
        Blog spacex = dao.findBlogById(3);
        System.out.println(spacex);
    }
}
