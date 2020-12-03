/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lukuvinkisto.media;

import java.util.List;

/**
 *
 * @author Sami
 */
public class Media implements Comparable<Media>   {
    final String title;
    final String author;
    final String link;
    final int length;
    final List<String> tags;

    public Media(String title, String author, String link, int pages, List<String> tags) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.length = pages;
        this.tags = tags;
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

    public List<String> getTags() {
        return tags;
    }

    public String getTagString() {
        if (tags==null || tags.isEmpty()) return "";
        StringBuilder str = new StringBuilder();
        boolean first = true;
        for (String tag : tags) {
            if (first) {
                first = false;
            } else {
                str.append(", ");
            }
            str.append(tag);
        }
        return str.toString();
    }

    
    public String getAsListElement(){
        return toString() + ", Tagit: " + this.getTagString();
    }
    
    @Override
    public int compareTo(Media t) {
        return this.title.compareTo(t.getTitle());
    }
}
