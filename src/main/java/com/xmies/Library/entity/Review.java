package com.xmies.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Objects;

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

    /**
     * Compares the current entity's ID with another entity's ID. This method is intended
     * for use with persisted or managed entities to check whether they are the same entity
     * in the database.
     *
     * @param o the reference object with which to compare.
     * @return true if the IDs are the same, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return id == review.id;
    }

    /**
     * Returns a hash code value for the object. This method computes a hash code
     * based on the ID of the Review object using the Objects hash method.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
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
                ", book=" + (book != null ? book : "Don't have a book yet") +
                '}';
    }
}
