package com.carlanmarcel.books.database;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import com.carlanmarcel.books.model.Book;
import com.carlanmarcel.books.model.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static  AppRepository ourInstance;

    public static AppRepository getInstance(Context context) {
        if(ourInstance == null)
            ourInstance = new AppRepository(context);
        return ourInstance;
    }
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();
    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        insertUser(new User("admin","admin"));
    }
    public LiveData<List<Book>> getAllBooks(){
        return mDb.bookDAO().getAll();
    }
    public List<Book> getAllLocalBooks() { return mDb.bookDAO().gallAllLocalBooks(); }
    public List<Book> getAllBooksSimple(){
        return mDb.bookDAO().getAllBooksSimple();
    }
    public void deleteAllBooks() {
        executor.execute(() -> mDb.bookDAO().deleteAll());
    }
    public Book getBookById(Long bookId) {
        return mDb.bookDAO().getBookById(bookId);
    }
    public void insertBook(final Book book) {
        executor.execute(() -> mDb.bookDAO().insertBook(book));
    }
    public void insertAll(final List<Book> books){
        executor.execute(() -> mDb.bookDAO().insertAll(books));
    }
    public void deleteBook(final Book book) {
        executor.execute(() -> mDb.bookDAO().deleteNote(book));
    }
    public void deleteBookById(final Long id){
        executor.execute(() -> mDb.bookDAO().deleteById(id));
    }
    public void insertUser( final  User user){
        executor.execute(() -> mDb.userDAO().insertUser(user));
    }
    public User getUserByUsername(String username){
        return mDb.userDAO().getUserByUsername(username);
    }
}
