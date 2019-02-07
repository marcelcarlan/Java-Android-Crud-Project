package com.carlanmarcel.books.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.carlanmarcel.books.model.Book;

import java.util.List;

@Dao
public interface BookDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Book> books);

    @Delete
    void deleteNote(Book book);

    @Query("select * from books where id = :id")
    Book getBookById(Long id);

    @Query("delete from books where id = :id")
    int deleteById(Long id);

    @Query("select * from books order by rating desc")
    LiveData<List<Book>> getAll();

    @Query("delete from books")
    int deleteAll();

    @Query("select count(*) from books")
    int getCount();

    @Query("select * from books where serverId is null")
    List<Book> gallAllLocalBooks();

    @Query("select * from books")
    List<Book> getAllBooksSimple();
}
