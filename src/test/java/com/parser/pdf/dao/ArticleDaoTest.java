package com.parser.pdf.dao;

import com.parser.pdf.entity.Article;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArticleDaoTest {

    private static ArticleDao articleDao;

    @BeforeClass
    public static void initArticleDao() {
        articleDao = new ArticleDao();
    }

    @Test
    public void testSave() {
        Article article = new Article("testName", "TestContent", "testBinaryData".getBytes());
        articleDao.save(article);
        Integer id = article.getId();
        assertNotNull(id);
        articleDao.deleteArticleById(id);
    }

    @Test
    public void testGetArticleById() {
        Article article = new Article("testName", "TestContent", "testBinaryData".getBytes());
        articleDao.save(article);
        Integer id = article.getId();
        assertNotNull(id);
        Article expectedArticle = articleDao.getArticleById(id);
        assertNotNull(expectedArticle);
        assertEquals(id, expectedArticle.getId());
        articleDao.deleteArticleById(id);
    }

    @Test
    public void testDeleteArticleBIdy() {
        Article article = new Article("testName", "TestContent", "testBinaryData".getBytes());
        articleDao.save(article);
        Integer id = article.getId();
        assertNotNull(id);
        articleDao.deleteArticleById(id);
        Article expectedArticle = articleDao.getArticleById(id);
        assertNull(expectedArticle);
    }

}
