package com.carlanmarcel.books.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carlanmarcel.books.EditorActivity;
import com.carlanmarcel.books.R;
import com.carlanmarcel.books.model.Book;
import com.carlanmarcel.books.utilities.Converter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.carlanmarcel.books.utilities.Constants.BOOK_ID_KEY;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private final List<Book> mBooks;
    private final Context mContext;

    public BooksAdapter(List<Book> mBooks, Context mContext) {
        this.mBooks = mBooks;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.book_list_item,viewGroup,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Book book = mBooks.get(i);
        viewHolder.titleText.setText(book.getTitle());
        viewHolder.authorText.setText(book.getAuthor());
        Double bookRating = book.getRating();
        viewHolder.ratingText.setText(Converter.convertDoubleToOneDecimalString(bookRating));

        viewHolder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditorActivity.class);
                intent.putExtra(BOOK_ID_KEY,book.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.book_title)
        TextView titleText;
        @BindView(R.id.book_author)
        TextView authorText;
        @BindView(R.id.book_rating)
        TextView ratingText;

        @BindView(R.id.fab)
        FloatingActionButton mFab;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
