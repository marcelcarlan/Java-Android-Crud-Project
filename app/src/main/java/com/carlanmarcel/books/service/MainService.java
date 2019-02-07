package com.carlanmarcel.books.service;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.carlanmarcel.books.database.AppRepository;
import com.carlanmarcel.books.model.Book;
import com.carlanmarcel.books.model.DeleteRequest;
import com.carlanmarcel.books.model.ResponseBook;
import com.carlanmarcel.books.model.Token;
import com.carlanmarcel.books.model.User;
import com.carlanmarcel.books.utilities.Converter;
import com.carlanmarcel.books.utilities.NetworkHelper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainService {
    private static MainService ourInstance;
    private Context context;

    private AppRepository appRepository;
    private WebService webService;
    private static String token;
    private User currentAuthenticated;

    public static MainService getInstance(Context context)
    {
        if(ourInstance == null)
            ourInstance = new MainService(context);
        return ourInstance;
    }

    private MainService(Context context) {
        this.context=context;
        appRepository = AppRepository.getInstance(context);
        webService = WebService.retrofit.create(WebService.class);
    }

    public void deleteAllBooks() {
        appRepository.deleteAllBooks();
    }

    public Book getBookById(Long bookId) {
        return appRepository.getBookById(bookId);
    }

    public void insertBook(Book book) {
        appRepository.insertBook(book);
        addBooksFromDatabase();
    }

    public void deleteBook(Book book) {
        if(checkNotNetworkAccess() && book!=null && book.getId()!=null){
            Call<Boolean> call = this.webService.deleteBooks(token, new DeleteRequest(book.getServerId()));
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    appRepository.deleteBookById(book.getId());
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(context,"Failed to delete book", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(context, "You can't delete a book offline", Toast.LENGTH_LONG).show();
        }
    }

    public User getUserByUsername(String username) {
        return appRepository.getUserByUsername(username);
    }
    private boolean checkNotNetworkAccess(){
        return NetworkHelper.hasNetworkAccess(context);
    }
    public boolean loginWeb(User user) {
        Call<Token> call = this.webService.login(user);
            try {
                Token bodyToken = call.execute().body();
                if(bodyToken != null){
                    token = "Bearer "+ bodyToken.getToken();
                    User existing = appRepository.getUserByUsername(user.getUsername());
                    if(existing==null)
                        appRepository.insertUser(user);
                    addBooksFromDatabase();
                    getAllBooks();
                    return true;
                }
            }
            catch (IOException e) {
                return false;
            }
            return false;
    }

    private void addBooksFromDatabase() {
        if(checkNotNetworkAccess() && token !=null){

           Thread thread = new Thread(new Runnable() {
               @Override
               public void run() {
                   List<Book> localBooks = appRepository.getAllLocalBooks();

                   Call<List<ResponseBook>> call = webService.postBooks(token,Converter.convertToResponseBook(localBooks));
                   call.enqueue(new Callback<List<ResponseBook>>() {
                       @Override
                       public void onResponse(Call<List<ResponseBook>> call, Response<List<ResponseBook>> response) {
                           if(response.isSuccessful() && response.body()!=null){
                               List<Book> newBooks = Converter.convertResponseBookToBook(response.body());
                               for (Book localBook:localBooks) {
                                   appRepository.deleteBook(localBook);
                               }
                               appRepository.insertAll(newBooks);
                           }
                       }

                       @Override
                       public void onFailure(Call<List<ResponseBook>> call, Throwable t) {

                       }
                   });
               }
           });
           thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context,"Couldn't connect to server",Toast.LENGTH_SHORT).show();
        }
    }

    public LiveData<List<Book>> getAllBooks() {
        if(checkNotNetworkAccess() && token !=null ) {
           Call<List<ResponseBook>> call = webService.getAllBooks(token);
           call.enqueue(new Callback<List<ResponseBook>>() {
               @Override
               public void onResponse(Call<List<ResponseBook>> call, Response<List<ResponseBook>> response) {
                   if(response.isSuccessful() && response.body()!=null){
                       appRepository.deleteAllBooks();
                       for (ResponseBook book:response.body()) {
                           appRepository.insertBook(Converter.convertResponseBookToBook(book));
                       }
                   }
               }
               @Override
               public void onFailure(Call<List<ResponseBook>> call, Throwable t) {

               }
           });
        }
        return appRepository.getAllBooks();
    }


    public Boolean login(String mUsername, String mPassword) {

        User user = new User(mUsername, mPassword);
        if (checkNotNetworkAccess()) {
            currentAuthenticated=user;
            return loginWeb(user);
        }
        else{
            User databaseUser = appRepository.getUserByUsername(mUsername);
            if(databaseUser!=null){
                currentAuthenticated=user;
                return databaseUser.getPassword().equals(mPassword);
            }
            return false;
        }
    }

    public void reloadData() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> login(currentAuthenticated.getUsername(), currentAuthenticated.getPassword()));

    }


    public void updateBook(Book book) {
        if(checkNotNetworkAccess()) {
            deleteBook(book);
            book.setServerId(null);
            insertBook(book);
        }
        else
        {
            Toast.makeText(context, "You can't update offline!",Toast.LENGTH_LONG).show();
        }
    }


    public List<Book> getAllBooksSimple() {
        return appRepository.getAllBooksSimple();
    }
}
