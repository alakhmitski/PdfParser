package com.parser.pdf.editor;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.parser.pdf.entity.Article;
import com.parser.pdf.service.ParserService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ManagedBean
@SessionScoped
public class FileUploadManagedBean implements Serializable {

    private boolean isCanceled = false;
    private boolean isCompleted = false;

    @ManagedProperty("#{parseService}")
    private ParserService service;

    private List<Article> articles = new CopyOnWriteArrayList<Article>();

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        articles.add(service.createArticle(file));
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void clear() {
        isCanceled = false;
        isCompleted = false;
        articles.clear();
    }

    public void uploadComplete() {
        if (isCanceled) {
            clear();
        } else {
            if (!isCompleted) {
                isCompleted = true;
                service.saveArticles(this.articles);
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
