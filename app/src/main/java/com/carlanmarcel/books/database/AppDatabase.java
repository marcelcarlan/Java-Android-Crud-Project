package com.carlanmarcel.books.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.carlanmarcel.books.model.Book;
import com.carlanmarcel.books.model.User;

@Database(entities = {Book.class, User.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Booksdb.db";

    private static volatile AppDatabase instance;

    private static final Object LOCK  = new Object();

    public abstract BookDAO bookDAO();

    public abstract UserDAO userDAO();


    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            synchronized (LOCK) {
                if (instance == null){
                    instance = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
