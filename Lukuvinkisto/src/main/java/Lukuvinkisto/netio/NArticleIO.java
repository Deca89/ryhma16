package Lukuvinkisto.netio;

import Lukuvinkisto.dao.TietokantaDAO;
import Lukuvinkisto.media.Article;
import Lukuvinkisto.media.Media;
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

    public boolean remove(Article article) {
        return db.removeArticle(article);
    }

    public boolean add(Article article) {
        return db.addArticle(article.getTitle(), article.getLink(), article.getTags());
    }
}
