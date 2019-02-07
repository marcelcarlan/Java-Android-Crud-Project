package com.carlanmarcel.books.service;

import com.carlanmarcel.books.model.Book;
import com.carlanmarcel.books.model.DeleteRequest;
import com.carlanmarcel.books.model.ResponseBook;
import com.carlanmarcel.books.model.Token;
import com.carlanmarcel.books.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface WebService {

    String BASE_URL = "http://192.168.1.8:8080/";
    String LOGIN_PATH = "users/signin";
    String GET_ALL_BOOKS = "book/all";
    String POST_BOOKS = "book/create/multiple";
    String DELETE_BOOK = "book/delete";

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();

    @POST(LOGIN_PATH)
    Call<Token> login(@Body User user);

    @GET(GET_ALL_BOOKS)
    Call<List<ResponseBook>> getAllBooks(@Header("Authorization") String authToken);

    @POST(POST_BOOKS)
    Call<List<ResponseBook>> postBooks(@Header("Authorization") String authToken, @Body List<ResponseBook> books);

    @POST(DELETE_BOOK)
    Call<Boolean> deleteBooks(@Header("Authorization") String authToken, @Body DeleteRequest request);
}
