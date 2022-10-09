package com.example.perpusonlinegroup.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.perpusonlinegroup.BookDetailActivity;
import com.example.perpusonlinegroup.R;
import com.example.perpusonlinegroup.model.Book;
import com.example.perpusonlinegroup.model.Request;

import java.net.URL;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainBookAdapter extends RecyclerView.Adapter<MainBookAdapter.ViewHolder> {

    private Context context;
    private Vector<Book> books;

    public MainBookAdapter(Context context, Vector<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public MainBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_book_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainBookAdapter.ViewHolder holder, int position) {
        Book now_book = books.get(position);

        holder.title.setText(now_book.getName());
        holder.author.setText(now_book.getAuthor());
        holder.synopsis.setText(now_book.getSynopsis());

        Glide.with(context).load(now_book.getCoverURL()).into(holder.image);

        holder.parents.setOnClickListener(v -> {
            Intent goToBookDetail = new Intent(context, BookDetailActivity.class);
            goToBookDetail.putExtra("BookID", now_book.getID());
            context.startActivity(goToBookDetail);

        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, author, synopsis;
        private ImageView image;
        private LinearLayout parents;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.parents = itemView.findViewById(R.id.main_book_list_parent);
            this.image = itemView.findViewById(R.id.main_book_list_image);
            this.title = itemView.findViewById(R.id.main_book_list_title);
            this.author = itemView.findViewById(R.id.main_book_list_author);
            this.synopsis = itemView.findViewById(R.id.main_book_list_synopsis);

        }
    }
}
