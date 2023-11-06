package com.msurbaNavSecurity.msurbaNavSecurity.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document()
public class Pqrs {
    @Id
    private String _id;
    private String type;
    private String description;
    private LocalDate date;

    @DBRef
    private User user;

    public Pqrs(String type, String description, LocalDate date, User user) {
        this.type = type;
        this.description = description;
        this.date = date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
