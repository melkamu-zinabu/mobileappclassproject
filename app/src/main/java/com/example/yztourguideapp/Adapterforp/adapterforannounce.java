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

import com.example.yztourguideapp.ADMIN.selectlistnerforannounce;
import com.example.yztourguideapp.MODEL.Tourmodel;
import com.example.yztourguideapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterforannounce  extends RecyclerView.Adapter<adapterforannounce.myviewholder> {
    static Context context;
    ArrayList<Tourmodel> list;
    private selectlistnerforannounce listner;
    public adapterforannounce(Context context, ArrayList<Tourmodel> list, selectlistnerforannounce listner) {
        this.context = context;
        this.list = list;
        this.listner=listner;
    }
    public  void setFilteredList(ArrayList<Tourmodel> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public adapterforannounce.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_layoutforannounce,parent,false );
        return new adapterforannounce.myviewholder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull adapterforannounce.myviewholder holder, int position) {
        Tourmodel model=list.get(position);
        holder.textViewforannounce.setText(model.getTitle());
        Picasso.get().load(model.getImageurl()).into(holder.imageViewforannounce);
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
    public int getItemCount() {
        return list.size();
    }
    public  static class myviewholder extends RecyclerView.ViewHolder{
        public ImageView imageViewforannounce;
        public TextView textViewforannounce;
        public RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            imageViewforannounce=itemView.findViewById(R.id.imageforannounce);
            textViewforannounce=itemView.findViewById(R.id.textViewforannounce);
            relativeLayout=itemView.findViewById(R.id.rlannounce);
        }

    }
}


