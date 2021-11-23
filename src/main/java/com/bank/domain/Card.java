package com.bank.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private int cardNumber;
    @Column(nullable = false)
    private int pin;
    @Column(nullable = false)
    private LocalDate expirationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", unique=true)
    private Account account_id;

    public Card() {
    }

    public Card(int cardNumber, int pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.expirationDate = LocalDate.now().plusYears(3);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Account getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Account account_id) {
        this.account_id = account_id;
    }

}
