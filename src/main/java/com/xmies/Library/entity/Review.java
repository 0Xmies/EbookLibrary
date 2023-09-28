package com.xmies.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "rating")
    @Min(value = 0)
    @Max(value = 10)
    private int rating;

    @Column(name = "comment")
    @NotNull(message = "Cant be empty")
    @Size(min = 1, max = 512, message = "Comment must be between 1 and 512 characters")
    private String comment;

    @ManyToOne()
    @JoinColumn(name = "book_id")
    private Book book;

    public Review() {
    }

    public Review(int rating, String comment, com.xmies.Library.entity.Book book) {
        this.rating = rating;
        this.comment = comment;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public com.xmies.Library.entity.Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", book name =" + book.getTitle() +
                '}';
    }
}
