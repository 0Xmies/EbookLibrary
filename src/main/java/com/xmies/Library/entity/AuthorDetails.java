package com.xmies.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Objects;

@Entity
@Table(name = "author_details")
public class AuthorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "country_of_origin")
    @NotNull(message = "is required")
    @Size(min = 3, max = 128, message = "Country must be between 3 and 128 characters")
    private String countryOfOrigin;

    @Column(name = "hobby")
    @NotNull(message = "is required")
    @Size(min = 3, max = 64, message = "hobby must be between 3 and 64 characters")
    private String hobby;

    @Column(name = "year_of_birth")
    @NotNull(message = "is required")
    @Min(value = 0, message = "Min is 0" )
    @Max(value = 3000, message = "Max is 3000")
    private int yearOfBirth;

    @OneToOne(mappedBy = "authorDetails",cascade = CascadeType.ALL)
    private Author author;

    public AuthorDetails() {
    }

    public AuthorDetails(String countryOfOrigin, String hobby, int yearOfBirth) {
        this.countryOfOrigin = countryOfOrigin;
        this.hobby = hobby;
        this.yearOfBirth = yearOfBirth;
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
        if (!(o instanceof AuthorDetails that)) return false;
        return id == that.id;
    }

    /**
     * Returns a hash code value for the object. This method computes a hash code
     * based on the ID of the AuthorDetails object using the Objects hash method.
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

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "AuthorDetail{" +
                "id=" + id +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", hobby='" + hobby + '\'' +
                ", year_of_birth=" + yearOfBirth +
                ", author name =" + author.getFirstName() + " " + author.getLastName() +
                '}';
    }
}
