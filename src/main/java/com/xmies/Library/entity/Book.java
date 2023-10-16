package com.xmies.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @NotNull(message = "Cant be empty")
    @Size(min = 1, max = 512, message = "Book title must be between 1 and 512 characters")
    private String title;

    @OneToMany(cascade = CascadeType.REMOVE,mappedBy = "book")
    private List<Review> reviews;

    @ManyToMany()
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

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
        if (!(o instanceof Book book)) return false;
        return id == book.id;
    }

    /**
     * Returns a hash code value for the object. This method computes a hash code
     * based on the ID of the Book object using the Objects hash method.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {

        if (authors == null) {
            authors = new ArrayList<>();
        }

        authors.add(author);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {

        if (reviews == null) {
            reviews = new ArrayList<>();
        }

        reviews.add(review);
    }



    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
