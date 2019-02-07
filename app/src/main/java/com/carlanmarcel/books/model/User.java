package com.carlanmarcel.books.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "book_users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String username;
    private String password;

    @Ignore
    public User(){

    }

    @Ignore
    public User(String username, String password){
        this.username=username;
        this.password=password;
    }

    public User (Long id, String username, String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
