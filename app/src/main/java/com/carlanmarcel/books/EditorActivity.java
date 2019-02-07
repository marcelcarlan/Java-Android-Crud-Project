package com.carlanmarcel.books;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.carlanmarcel.books.model.BookCategory;
import com.carlanmarcel.books.utilities.Converter;
import com.carlanmarcel.books.viewModel.EditorViewModel;

import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.carlanmarcel.books.utilities.Constants.BOOK_ID_KEY;
import static com.carlanmarcel.books.utilities.Constants.EDITING_KEY;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.book_title)
    TextView titleText;

    @BindView(R.id.book_author)
    TextView authorText;

    @BindView(R.id.book_rating)
    TextView ratingText;

    @BindView(R.id.book_category)
    Spinner categoryText;

    private EditorViewModel mViewModel;
    private boolean mNewBook;
    private boolean mEditing;

    private ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        if(savedInstanceState !=null){
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }
        this.categoryAdapter = new ArrayAdapter<String>(
                EditorActivity.this,android.R.layout.simple_list_item_1,
                Converter.convertEnumToStringArray(BookCategory.class)
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryText.setAdapter(categoryAdapter);
        initViewModel();
    }

    private void initViewModel() {


        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);
        mViewModel.mLiveBook.observe(this, book -> {
            if (book != null && !mEditing) {
                titleText.setText(book.getTitle());
                authorText.setText(book.getAuthor());
                ratingText.setText(Converter.convertDoubleToOneDecimalString(book.getRating()));
                categoryText.setSelection(categoryAdapter.getPosition(book.getBookCategory()));
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(R.string.new_book);
            mNewBook = true;
        }else {
            setTitle(R.string.edit_book);
            Long bookId = extras.getLong(BOOK_ID_KEY);
            mViewModel.loadData(bookId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewBook){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor,menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            saveAndReturn();
            return true;
        }else if(item.getItemId() == R.id.action_delete){
            mViewModel.deleteBook();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndReturn() {
        mViewModel.saveBook(
                titleText.getText().toString(),
                authorText.getText().toString(),
                ratingText.getText().toString(),
                categoryText.getSelectedItem().toString()
        );
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY,true);
        super.onSaveInstanceState(outState);
    }
}
