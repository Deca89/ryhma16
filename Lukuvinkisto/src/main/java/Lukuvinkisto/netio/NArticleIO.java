package Lukuvinkisto.netio;

import Lukuvinkisto.dao.TietokantaDAO;
import Lukuvinkisto.media.Media;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author laaatte
 */
public class NArticleIO {

    private TietokantaDAO db;

    public NArticleIO(TietokantaDAO db) {
        this.db = db;
    }

    public List<Media> fetch() {
        List<Media> works = db.listArticles(null);
        Collections.sort(works);
        return works;
    }

    public List<Media> fetch(String input) {
        List<Media> works = db.listArticles(input);
        Collections.sort(works);
        return works;
    }

    public List<Media> fetchWithId(String input) {
        List<Media> work = db.getArticleById(input);
        return work;
    }
    
    public List<Media> separateByTag(List<Media> articles, String input) {
        List<Media> works = new ArrayList();
        if (articles.isEmpty()) return articles;
        for (Media article : articles) {
            if (article.getTags().contains(input)) {
                works.add(article);
            }
        }
        return works;
    }

    public boolean remove(String title) {
        return db.removeArticle(title);
    }

    public boolean add(String title, String link, List<String> tags) {
        if (this.validate(title, link)) {
            return db.addArticle(title, link, tags);
        }
        return false;
    }
    
    
    public boolean modify(String id, String title, String link, List<String> tags, String status){
        if (this.validate(title, link)) {
            return db.modifyArticle(id, title, link, tags, status);
        }
        return false;
    }
    
    private boolean validate(String title, String link) {
        return title.length() > 2 && link.length() > 2;
    }
}
