package com.parser.pdf.editor;

import com.parser.pdf.entity.Article;
import com.parser.pdf.service.ParserService;
import org.apache.commons.io.IOUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@ManagedBean(name = "articleView")
@SessionScoped
public class ArticleView implements Serializable {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/pdf";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String CACHE_CONTROL_VALUE = "no-cache";

    private Article article;

    @ManagedProperty("#{parseService}")
    private ParserService service;

    public void findArticle(Integer id) {
        article = service.findArticle(id);
        show();
    }

    private void show() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse)
                externalContext.getResponse();
        try {
            response.reset();
            response.setHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
            response.setHeader(CACHE_CONTROL, CACHE_CONTROL_VALUE);
            byte[] binaryData = article.getBinaryData();
            IOUtils.copy(new ByteArrayInputStream(binaryData), response.getOutputStream());
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        facesContext.responseComplete();
    }

    public Article getArticle() {
        return article;
    }

    public void setService(ParserService service) {
        this.service = service;
    }
}
