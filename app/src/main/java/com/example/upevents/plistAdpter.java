package com.example.upevents;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class plistAdpter extends RecyclerView.Adapter<plistAdpter.MyViewHolder>  {

    Context context;


    static ArrayList<Myevnt> listp;

    int[] imgevn = {R.drawable.dddddd,R.drawable.epi1,R.drawable.epi2,R.drawable.evvvh,
            R.drawable.pryty,R.drawable.dddddd,R.drawable.epi2,R.drawable.epi1,R.drawable.evvvh};
    public plistAdpter(Context context, ArrayList<Myevnt> listp) {
        this.context = context;
        this.listp = listp;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.listp,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Myevnt event = listp.get(position);
        holder.name.setText(event.getName());
        holder.time.setText(event.getDate());
        holder.imgev.setImageResource(imgevn[position]);
    }

    @Override
    public int getItemCount() {
        return listp.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,time;
        ImageView imgev;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameev);
            time= itemView.findViewById(R.id.timeev);
            imgev=itemView.findViewById(R.id.iml);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), info.class);
                    int pos = getAdapterPosition();

                    i.putExtra("cre",listp.get(pos).getCreateur());
                    i.putExtra("time",listp.get(pos).getTime());
                    i.putExtra("desc",listp.get(pos).getDescription());
                    i.putExtra("phone",listp.get(pos).getPhone());
                    i.putExtra("email",listp.get(pos).getEmail());
                    i.putExtra("pri",listp.get(pos).getPrice());
                    i.putExtra("loc",listp.get(pos).getLocalisation());
                    i.putExtra("pos", pos);


                    i.putExtra("name", name.getText().toString());
                    i.putExtra("date", time.getText().toString());



                    view.getContext().startActivity(i);
                }

            });
        }
    }

}