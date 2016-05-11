package com.parser.pdf.service;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.parser.pdf.dao.ArticleDao;
import com.parser.pdf.entity.Article;
import com.parser.pdf.exceptions.UnsupportedFileExtension;
import com.parser.pdf.utils.FileParserUtils;
import org.primefaces.model.UploadedFile;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.*;
import java.util.List;

/**
 * Service for creating, saving, finding Articles
 * */
@ManagedBean(name = "parseService")
@ApplicationScoped
public class ParserService implements Serializable {
    private ArticleDao dao = new ArticleDao();
    private static final String START_POINT_STR = "Equipamento de Série";
    private static final String END_POINT_STR = "Equipamento Opcional";
    private static final String PDF_FILE_EXTENTION = "PDF";

    /**
     * Creates Article object off of uploaded file
     * */
    public Article createArticle(UploadedFile uploadFile) throws UnsupportedFileExtension {
        try {
            String fileExtension = FileParserUtils.getFileExtension(uploadFile.getFileName());
            if (PDF_FILE_EXTENTION.equalsIgnoreCase(fileExtension)) {
                InputStream inputstream = uploadFile.getInputstream();
                String text = parseFile(inputstream);
                String content = FileParserUtils.getContent(text.toString(), START_POINT_STR, END_POINT_STR);
                String formattedContent = FileParserUtils.formattingContent(content);
                String fileName = FileParserUtils.getFileName(uploadFile.getFileName());
                Article article = new Article(fileName, formattedContent, uploadFile.getContents());
                return article;
            } else {
                throw new UnsupportedFileExtension("Invalid file type: " + fileExtension);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets file text content from InputStream
     * */
    String parseFile(InputStream inputstream) throws IOException {
        PdfReader reader = new PdfReader(inputstream);
        StringBuilder text = new StringBuilder();
        for (int pageCount = 1; pageCount <= reader.getNumberOfPages(); ++pageCount) {
            TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            text.append(PdfTextExtractor.getTextFromPage(reader, pageCount, strategy));
        }
        reader.close();
        return text.toString();
    }

    /**
     * Saves list of Articles
     * */
    public void  saveArticles(List<Article> articles) {
        for (Article article : articles) {
            saveArticle(article);
        }
    }

    /**
     * Saves a single Article
     * */
    private void saveArticle(Article article) {
        dao.save(article);
    }

    /**
     * Finds an Article by id
     * */
    public Article findArticle(Integer id) {
        Article article = dao.getArticleById(id);
        return article;
    }

}
