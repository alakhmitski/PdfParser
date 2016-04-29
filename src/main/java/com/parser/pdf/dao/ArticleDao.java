package com.parser.pdf.dao;

import com.parser.pdf.db.DataBase;
import com.parser.pdf.entity.Article;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class ArticleDao {

    private static String INSERT_ARTICLE_QUERY = "INSERT INTO articles (`name`, `content`, `binaryData`) VALUES (?, ?, ?);";
    private static String GET_ARTICLE_BY_ID = "SELECT * FROM articles WHERE id = ?;";
    private static String DELETE_ARTICLE_BY_ID = "DELETE FROM articles WHERE id = ?;";

    public void save(Article article) {
        DataBase db = new DataBase();
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet queryResult = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_ARTICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, article.getName());
            preparedStatement.setString(2, article.getContent());
            ByteArrayInputStream articleBinaryDate = new ByteArrayInputStream(article.getBinaryData());
            preparedStatement.setBlob(3, articleBinaryDate);
            preparedStatement.executeUpdate();
            queryResult = preparedStatement.getGeneratedKeys();
            if (queryResult.next()) {
                Integer id = queryResult.getInt(1);
                article.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finishDbJob(connection, preparedStatement, queryResult);
        }
    }

    public Article getArticleById(Integer id) {
        DataBase db = new DataBase();
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet queryResult = null;
        try {
            preparedStatement = connection.prepareStatement(GET_ARTICLE_BY_ID);
            preparedStatement.setInt(1, id);
            queryResult = preparedStatement.executeQuery();
            if (queryResult.next()) {
                Integer dbId = queryResult.getInt("id");
                String name = queryResult.getString("name");
                String content = queryResult.getString("content");
                Blob binaryData = queryResult.getBlob("binaryData");
                InputStream binaryStream = binaryData.getBinaryStream();
                return new Article(dbId, name, content, IOUtils.toByteArray(binaryStream));
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            finishDbJob(connection, preparedStatement, queryResult);
        }
        return null;
    }

    public void deleteArticleById(Integer id) {
        DataBase db = new DataBase();
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_ARTICLE_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finishDbJob(connection, preparedStatement, null);
        }
    }

    private void finishDbJob(Connection connection, PreparedStatement preparedStatement, ResultSet queryResult) {
        try {
            if (queryResult != null) {
                queryResult.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
