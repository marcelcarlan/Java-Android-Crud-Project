package com.carlanmarcel.books.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.carlanmarcel.books.model.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("select * from book_users where id = :id")
    User getUserById(Long id);

    @Query("select * from book_users where username = :username")
    User getUserByUsername(String username);

}
