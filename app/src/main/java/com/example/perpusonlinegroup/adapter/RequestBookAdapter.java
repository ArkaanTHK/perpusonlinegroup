package com.example.perpusonlinegroup.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.perpusonlinegroup.R;
import com.example.perpusonlinegroup.RequestDetailActivity;
import com.example.perpusonlinegroup.model.Book;
import com.example.perpusonlinegroup.model.Request;
import com.example.perpusonlinegroup.model.User;
import com.example.perpusonlinegroup.service.BookService;
import com.example.perpusonlinegroup.service.UserService;

import java.util.Vector;

public class RequestBookAdapter extends RecyclerView.Adapter<RequestBookAdapter.ViewHolder> {

    private Context context;
    private Vector<Request> request;
    private UserService userService;
    private BookService bookService;
    public RequestBookAdapter(Context context, Vector<Request> requests){
        this.context = context;
        this.request = requests;
        this.bookService = new BookService(context);
        this.userService = new UserService(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_request_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request requests = request.get(position);
        User userreq = userService.GetByID(requests.getRequesterID());

        Log.i("A", requests.getReceiverID().toString());

        Book books = bookService.GetByID(requests.getBookID());
        if(requests.getReceiverID() == null || requests.getReceiverID() == 0)
            holder.receiver.setText("Received from -");
        else {
            User userrec = userService.GetByID(requests.getReceiverID());
            holder.receiver.setText(String.format("Received from %s", userrec.getEmail()));
        }
        holder.requester.setText(String.format("Requested from %s", userreq.getEmail()));
        holder.title.setText(books.getName());
        holder.author.setText(books.getAuthor());
        Glide.with(context).load(books.getCoverURL()).into(holder.image);

        holder.parents.setOnClickListener(view -> {
            Intent goToRequestBookDetail = new Intent(context, RequestDetailActivity.class);
            goToRequestBookDetail.putExtra("RequestID", requests.getID());
            context.startActivity(goToRequestBookDetail);
        });

    }

    @Override
    public int getItemCount() {
        return request.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, author, requester, receiver;
        private LinearLayout parents;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.main_request_list_title);
            this.author = itemView.findViewById(R.id.main_request_list_author);
            this.requester = itemView.findViewById(R.id.main_request_list_requester);
            this.receiver = itemView.findViewById(R.id.main_request_list_receiver);
            this.parents = itemView.findViewById(R.id.main_request_list_parent);
            this.image = itemView.findViewById(R.id.main_request_list_image);

        }
    }
}
