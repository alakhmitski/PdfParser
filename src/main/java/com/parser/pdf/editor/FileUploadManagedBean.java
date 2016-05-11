package com.parser.pdf.editor;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.parser.pdf.entity.Article;
import com.parser.pdf.exceptions.UnsupportedFileExtension;
import com.parser.pdf.service.ParserService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Contains methods used when user uploads files
 * */
@ManagedBean
@SessionScoped
public class FileUploadManagedBean implements Serializable {

    private boolean isCanceled = false;
    private boolean isCompleted = false;
    private List<Article> articles = new CopyOnWriteArrayList<Article>();

    @ManagedProperty("#{parseService}")
    private ParserService service;

    /**
     * Creates Article object from uploaded file
     * */
    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        try {
            Article article = service.createArticle(file);
            articles.add(article);
        } catch (UnsupportedFileExtension ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Article> getArticles() {
        return articles;
    }

    /**
     * Clears articles list and resets canceled and completed flags each time files are uploaded
     */
    public void clear() {
        isCanceled = false;
        isCompleted = false;
        articles.clear();
    }

    /**
     * Saves Article list or cancels an upload
     */
    public void uploadComplete() {
        if (isCanceled) {
            clear();
        } else {
            if (!isCompleted) {
                isCompleted = true;
                service.saveArticles(articles);
            }
        }
    }

    public void cancelBtnEvent() {
        isCanceled = true;
    }

    public void setService(ParserService service) {
        this.service = service;
    }

}
