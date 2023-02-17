package com.example.yztourguideapp.Adapterforp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yztourguideapp.ADMIN.selectlistnerforhelp;
import com.example.yztourguideapp.MODEL.Helpmodel;
import com.example.yztourguideapp.R;

import java.util.ArrayList;

public class adapterforhelp extends RecyclerView.Adapter<adapterforhelp.myviewholder> {

    static Context context;
    ArrayList<Helpmodel> listitem;
    private selectlistnerforhelp listner;
    public adapterforhelp(Context context, ArrayList<Helpmodel> listitem, selectlistnerforhelp listner) {
        this.context = context;
        this.listitem = listitem;
        this.listner=listner;
    }
    public  void setFilteredList(ArrayList<Helpmodel> filteredList){
        this.listitem=filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_layoutforhelp,parent,false );

        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        Helpmodel model=listitem.get(position);
        holder.textViewforhelptitle.setText(model.getTitle());
        holder.textViewforhelpdescr.setText(model.getDescription());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onitemclick(listitem.get(holder.getBindingAdapterPosition()));

                // Intent intent=new Intent(context, MANAGEBOOK.class);
                // intent.putExtra("bookkey",ge);
                // context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listitem.size();
    }
    public  static class myviewholder extends RecyclerView.ViewHolder{
        public TextView textViewforhelptitle,textViewforhelpdescr;
        public ConstraintLayout constraintLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            textViewforhelpdescr=itemView.findViewById(R.id.textViewforhelpdescr);
            textViewforhelptitle=itemView.findViewById(R.id.textViewforhelpfortitle);
            constraintLayout=itemView.findViewById(R.id.cl);
        }

    }
}
