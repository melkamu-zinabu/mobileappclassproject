package com.example.yztourguideapp.Adapterforp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yztourguideapp.ADMIN.selectlistnerforbook;
import com.example.yztourguideapp.MODEL.Bookmodel;
import com.example.yztourguideapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class adapterforbook extends RecyclerView.Adapter<adapterforbook.myviewholder> {
    static Context context;
    ArrayList<Bookmodel> list;
    private selectlistnerforbook listner;
    public adapterforbook(Context context, ArrayList<Bookmodel> list, selectlistnerforbook listner) {
        this.context = context;
        this.list = list;
        this.listner=listner;
    }
    public  void setFilteredList(ArrayList<Bookmodel> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_layoutforbook,parent,false );
        return new myviewholder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        Bookmodel model=list.get(position);
        holder.textViewforbook.setText(model.getDestination());
        Picasso.get().load(model.getImageurl()).into(holder.imageViewforbook);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onitemclick(list.get(holder.getBindingAdapterPosition()));
               // Intent intent=new Intent(context, MANAGEBOOK.class);
               // intent.putExtra("bookkey",ge);
               // context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){
        return list.size();
    }
    public  static class myviewholder extends RecyclerView.ViewHolder{
        public ImageView imageViewforbook;
        public TextView textViewforbook;
        public RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            imageViewforbook=itemView.findViewById(R.id.imagebook);
            textViewforbook=itemView.findViewById(R.id.destinationofbook);
            relativeLayout=itemView.findViewById(R.id.rlv);
        }
        
    }
}
