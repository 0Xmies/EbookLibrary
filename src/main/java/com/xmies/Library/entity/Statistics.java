package com.xmies.Library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "statistics")
public class Statistics {

    @Column(name = "times_borrowed")
    private int timesBorrowed;

    @Column(name = "timesBought")
    private int timesBought;

    @Column(name = "now_borrowed")
    private int nowBorrowed;

    public Statistics() {
    }

    public Statistics(int timesBorrowed, int timesBought, int nowBorrowed) {
        this.timesBorrowed = timesBorrowed;
        this.timesBought = timesBought;
        this.nowBorrowed = nowBorrowed;
    }

    public int getTimesBorrowed() {
        return timesBorrowed;
    }

    public void setTimesBorrowed(int timesBorrowed) {
        this.timesBorrowed = timesBorrowed;
    }

    public int getTimesBought() {
        return timesBought;
    }

    public void setTimesBought(int timesBought) {
        this.timesBought = timesBought;
    }

    public int getNowBorrowed() {
        return nowBorrowed;
    }

    public void setNowBorrowed(int nowBorrowed) {
        this.nowBorrowed = nowBorrowed;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "timesBorrowed=" + timesBorrowed +
                ", timesBought=" + timesBought +
                ", nowBorrowed=" + nowBorrowed +
                '}';
    }
}
