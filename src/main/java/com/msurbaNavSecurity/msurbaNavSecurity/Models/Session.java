package com.msurbaNavSecurity.msurbaNavSecurity.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
public class Session {

    @Id
    private String _id;
    private boolean active;
    private int code;

    @DBRef
    private User user;

    public Session(boolean active, int code) {
        this.active = active;
        this.code = code;
    }

    public String get_id() {
        return _id;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
