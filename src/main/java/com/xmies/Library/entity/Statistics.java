package com.xmies.Library.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "statistics")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "menu_entries")
    private int menuEntries;

    @Column(name = "book_list_entries")
    private int bookListEntries;

    @Column(name = "author_list_entries")
    private int authorListEntries;

    @Column(name = "admin_only_requests")
    private int adminOnlyRequests;

    @Column(name = "publicly_available_requests")
    private int publiclyAvailableRequests;

    public Statistics() {
    }

    public Statistics(int menuEntries, int bookListEntries, int authorListEntries, int adminOnlyRequests, int publiclyAvailableRequests) {
        this.menuEntries = menuEntries;
        this.bookListEntries = bookListEntries;
        this.authorListEntries = authorListEntries;
        this.adminOnlyRequests = adminOnlyRequests;
        this.publiclyAvailableRequests = publiclyAvailableRequests;
    }

    /**
     * Compares the current entity's entries with another entity's entries.
     *
     * @param o the reference object with which to compare.
     * @return true if the IDs are the same, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistics that)) return false;
        return menuEntries == that.menuEntries && bookListEntries == that.bookListEntries && authorListEntries == that.authorListEntries && adminOnlyRequests == that.adminOnlyRequests && publiclyAvailableRequests == that.publiclyAvailableRequests;
    }

    /**
     * Returns a hash code value for the object. This method computes a hash code
     * based on the actual value of entries in Statistics object using the Objects hash method.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(menuEntries, bookListEntries, authorListEntries, adminOnlyRequests, publiclyAvailableRequests);
    }


    public int getMenuEntries() {
        return menuEntries;
    }

    public void setMenuEntries(int menuEntries) {
        this.menuEntries = menuEntries;
    }

    public int getBookListEntries() {
        return bookListEntries;
    }

    public void setBookListEntries(int bookListEntries) {
        this.bookListEntries = bookListEntries;
    }

    public int getAuthorListEntries() {
        return authorListEntries;
    }

    public void setAuthorListEntries(int authorListEntries) {
        this.authorListEntries = authorListEntries;
    }

    public int getAdminOnlyRequests() {
        return adminOnlyRequests;
    }

    public void setAdminOnlyRequests(int adminOnlyRequests) {
        this.adminOnlyRequests = adminOnlyRequests;
    }

    public int getPubliclyAvailableRequests() {
        return publiclyAvailableRequests;
    }

    public void setPubliclyAvailableRequests(int publiclyAvailableRequests) {
        this.publiclyAvailableRequests = publiclyAvailableRequests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "menuEntries=" + menuEntries +
                ", bookListEntries=" + bookListEntries +
                ", authorListEntries=" + authorListEntries +
                ", adminOnlyRequests=" + adminOnlyRequests +
                ", publiclyAvailableRequests=" + publiclyAvailableRequests +
                '}';
    }
}
