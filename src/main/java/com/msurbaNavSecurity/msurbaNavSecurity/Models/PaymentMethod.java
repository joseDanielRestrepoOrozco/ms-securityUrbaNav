package com.msurbaNavSecurity.msurbaNavSecurity.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document()
public class PaymentMethod {
    @Id
    private String _id;
    private String type;
    private String cardNumber;
    private String cardCVV;
    private LocalDate expiryDate;
    @DBRef
    private User user;

    public PaymentMethod(String type, String cardNumber, String cardCVV, LocalDate expiryDate) {
        this.type = type;
        this.cardNumber = cardNumber;
        this.cardCVV = cardCVV;
        this.expiryDate = expiryDate;
    }

    public String get_id() {
        return _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
