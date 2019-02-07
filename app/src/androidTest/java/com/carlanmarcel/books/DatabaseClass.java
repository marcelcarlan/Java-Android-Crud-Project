package com.carlanmarcel.books;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.carlanmarcel.books.database.AppDatabase;
import com.carlanmarcel.books.database.UserDAO;
import com.carlanmarcel.books.model.Book;
import com.carlanmarcel.books.database.BookDAO;
import com.carlanmarcel.books.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DatabaseClass {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private BookDAO bookDAO;
    private UserDAO userDAO;

    @Before
    public void createDB(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context,AppDatabase.class).build();
        bookDAO = mDb.bookDAO();
        userDAO = mDb.userDAO();
        Log.i(TAG, "createDB");
    }

    @After
    public void closeDB() {
        mDb.close();
        Log.i(TAG, "closeDB");
    }

    @Test
    public void createAndRetrieveBooks(){
        bookDAO.insertAll(SampleData.getBooks());
        int count = bookDAO.getCount();
        Log.i(TAG, "createAndRetrieveBooks: count="+count);
        assertEquals(SampleData.getBooks().size(),count);
    }

    @Test
    public void compareStrings(){
        bookDAO.insertAll(SampleData.getBooks());
        Book original = SampleData.getBooks().get(0);
        Book fromDb = bookDAO.getBookById(1L);
        assertEquals(original.getTitle(),fromDb.getTitle());
        assertEquals(Long.valueOf(1),fromDb.getId());
    }

    @Test
    public void getUserByUsernameTest(){
        String username = "username";
        String password = "password";
        userDAO.insertUser(new User(username,password));
        User user = userDAO.getUserByUsername(username);
        assertEquals(user.getUsername(), username);
        assertEquals(user.getPassword(), password);
    }
}
