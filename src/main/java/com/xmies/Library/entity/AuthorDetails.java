package com.xmies.Library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "author_details")
public class AuthorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "year_of_birth")
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
