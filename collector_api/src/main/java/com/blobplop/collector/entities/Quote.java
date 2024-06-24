package com.blobplop.collector.entities;

import jakarta.persistence.*;

@Entity
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quoteId;

    // duplicate quotes are allowed unless it becomes an issue
    // add logic later if users for some reason keep entering the same quote
    @Column(columnDefinition = "TEXT default 'Anonymous'", nullable = false)
    private String quote;

    // author is nullable because some quotes are anonymous
    // feature: assist user in finding the author, default to "Anonymous"
    @Column(nullable = false)
    private String author;

    @Column(nullable = true)
    private long userId;

    public Quote() {
    }

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    public long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(long quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteId=" + quoteId +
                ", quote='" + quote + '\'' +
                ", author='" + author + '\'' +
                '}';
    }


}
