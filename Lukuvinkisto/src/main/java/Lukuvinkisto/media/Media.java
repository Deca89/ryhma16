/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lukuvinkisto.media;

/**
 *
 * @author Sami
 */
public class Media implements Comparable<Media>   {
    final String title;
    final String author;
    final String link;
    final int length;

    public Media(String title, String author, String link, int pages) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.length = pages;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public String getLink() {
        return link;
    }
    
    @Override
    public int compareTo(Media t) {
        return this.title.compareTo(t.getTitle());
    }
}
