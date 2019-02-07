package com.carlanmarcel.books.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;
    private String author;
    private Double rating;
    private String bookCategory;
    private Long serverId;
    @Ignore
    public Book() {
    }

    @Ignore
    public Book(String title, String author, Double rating, String bookCategory, Long serverId) {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.bookCategory=bookCategory;
        this.serverId = serverId;
    }

    public Book(Long id, String title, String author, Double rating, String bookCategory, Long serverId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.bookCategory = bookCategory;
        this.serverId = serverId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }



    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", rating=" + rating +
                ", bookCategory='" + bookCategory + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
}
