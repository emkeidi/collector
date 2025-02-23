package com.blobplop.collector.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
//@Table(name = "quote", schema = "public")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quoteId;

    // duplicate quotes are allowed unless it becomes an issue
    // add logic later if users for some reason keep entering the same quote
    @Column(nullable = false, columnDefinition = "TEXT")
    private String quote;

    @Column()
    private String source;

    @Column()
    private long userId;

    @Column(length = 500)
    private String link;

    @Column(length = 50)
    private String category;

    @Column
    private LocalDate dateAdded;

    @Column
    private boolean isApproved;

    @Column
    private boolean isFlagged;

    @Column
    private String flagReason;

    @Column
    private boolean isDeleted;

    @Column
    private String deleteReason;

    @Column
    private LocalDate dateDeleted;

    @Column
    private Double rating;

    public Quote() {
    }

    public Quote(String quote, String source, long userId, String link, String category, LocalDate dateAdded) {
        this.quote = quote;
        this.source = source;
        this.userId = userId;
        this.link = link;
        this.category = category;
        this.dateAdded = dateAdded;
    }

    public Quote(String quote, String source, long userId, String link, String category, LocalDate dateAdded, boolean isApproved, Double rating) {
        this.quote = quote;
        this.source = source;
        this.userId = userId;
        this.link = link;
        this.category = category;
        this.dateAdded = dateAdded;
        this.isApproved = isApproved;
        this.rating = rating;
    }

    @PrePersist
    public void prePersist() {
        if (dateAdded == null) {
            dateAdded = LocalDate.now();
        }
    }

    public boolean isApproved() {
        return isApproved;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote1 = (Quote) o;
        return quoteId == quote1.quoteId && userId == quote1.userId && isApproved == quote1.isApproved && isFlagged == quote1.isFlagged && isDeleted == quote1.isDeleted && Objects.equals(quote, quote1.quote) && Objects.equals(source, quote1.source) && Objects.equals(link, quote1.link) && Objects.equals(category, quote1.category) && Objects.equals(dateAdded, quote1.dateAdded) && Objects.equals(flagReason, quote1.flagReason) && Objects.equals(deleteReason, quote1.deleteReason) && Objects.equals(dateDeleted, quote1.dateDeleted) && Objects.equals(rating, quote1.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quoteId, quote, source, userId, link, category,
                dateAdded, isApproved, isFlagged, flagReason, isDeleted, deleteReason, dateDeleted, rating);
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteId=" + quoteId +
                ", quote='" + quote + '\'' +
                ", author='" + source + '\'' +
                ", userId=" + userId +
                ", link='" + link + '\'' +
                ", category='" + category + '\'' +
                ", dateAdded=" + dateAdded +
                '}';
    }





}
