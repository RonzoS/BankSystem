package com.bank.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "transferlogs")
public class Transferlog {

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    @Column(nullable = false)
    private double value;
    @Column(nullable = false)
    private double valueBefore;
    @Column(nullable = false)
    private double valueAfter;
    @Column(nullable = false, updatable = false)
    private LocalDateTime date;
    private int otherAccount;

    public Transferlog() {
    }

    public Transferlog(String description, double value, double valueBefore, double valueAfter,
                       int otherAccount) {
        this.description = description;
        this.value = value;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.date = LocalDateTime.now();
        this.otherAccount = otherAccount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValueBefore() {
        return valueBefore;
    }

    public void setValueBefore(double valueBefore) {
        this.valueBefore = valueBefore;
    }

    public double getValueAfter() {
        return valueAfter;
    }

    public void setValueAfter(double valueAfter) {
        this.valueAfter = valueAfter;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getOtherAccount() {
        return otherAccount;
    }

    public void setOtherAccount(int otherAccount) {
        this.otherAccount = otherAccount;
    }

    @Override
    public String toString() {
        return "Transferlog{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", valueBefore=" + valueBefore +
                ", valueAfter=" + valueAfter +
                ", date=" + date +
                ", otherAccount=" + otherAccount +
                '}';
    }
}