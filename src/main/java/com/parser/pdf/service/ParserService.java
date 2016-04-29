package com.parser.pdf.service;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.parser.pdf.dao.ArticleDao;
import com.parser.pdf.entity.Article;
import com.parser.pdf.utils.FileParserUtils;
import org.primefaces.model.UploadedFile;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.*;
import java.util.List;

@ManagedBean(name = "parseService")
@ApplicationScoped
public class ParserService implements Serializable {
    private ArticleDao dao = new ArticleDao();
    private static final String START_POINT_STR = "Equipamento de Série";
    private static final String END_POINT_STR = "Equipamento Opcional";

    public Article createArticle(UploadedFile uploadFile) {
        try {
            InputStream inputstream = uploadFile.getInputstream();
            String text = parseFile(inputstream);
            String content = FileParserUtils.getContent(text.toString(), START_POINT_STR, END_POINT_STR);
            String formattedContent = FileParserUtils.formattingContent(content);
            String fileName = FileParserUtils.getFileName(uploadFile.getFileName());
            Article article = new Article(fileName, formattedContent, uploadFile.getContents());
            return article;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void  saveArticles(List<Article> articles) {
        for (Article article : articles) {
            saveArticle(article);
        }
    }

    private Article saveArticle(Article article) {
        dao.save(article);
        return article;
    }

    public Article findArticle(Integer id) {
        Article article = dao.getArticleById(id);
        return article;
    }

}
