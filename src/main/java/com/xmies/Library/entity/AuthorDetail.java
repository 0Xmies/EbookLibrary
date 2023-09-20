package com.xmies.Library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "author_detail")
public class AuthorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "year_of_birth")
    private int year_of_birth;

    @OneToOne(mappedBy = "authorDetail",cascade = CascadeType.ALL)
    private Author author;

    public AuthorDetail() {
    }

    public AuthorDetail(String countryOfOrigin, String hobby, int year_of_birth, Author author) {
        this.countryOfOrigin = countryOfOrigin;
        this.hobby = hobby;
        this.year_of_birth = year_of_birth;
        this.author = author;
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

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
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
                ", year_of_birth=" + year_of_birth +
                ", author name =" + author.getFirstName() + " " + author.getLastName() +
                '}';
    }
}
