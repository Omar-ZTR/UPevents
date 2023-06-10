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

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> implements Filterable {

    Context context;
    public ArrayList<model> flist;

    static ArrayList<model> list;

    int[] imgevn = {R.drawable.dddddd,R.drawable.epi1,R.drawable.epi2,R.drawable.evvvh,
            R.drawable.pryty,R.drawable.dddddd,R.drawable.epi2,R.drawable.epi1,R.drawable.evvvh};
    public ListAdapter(Context context, ArrayList<model> list) {
        this.context = context;
        this.list = list;
        this.flist = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        model event = list.get(position);
        holder.name.setText(event.getName());
        holder.time.setText(event.getDate());
        holder.imgev.setImageResource(imgevn[position]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter =new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence == null || charSequence.length()== 0){
                    filterResults.values = flist;
                    filterResults.count = flist.size();

                }else {
                    String searchstr = charSequence.toString().toLowerCase();
                    ArrayList<model> list =  new ArrayList<>();
                    for (model model: flist){
                        if (model.getName().toLowerCase().contains(searchstr)){
                            list.add(model);
                        }
                    }
                    filterResults.values = list;
                    filterResults.count = list.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<model>) filterResults.values;
                notifyDataSetChanged();

            }
        };
        return filter;
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
                    i.putExtra("cre",list.get(pos).getCreateur());
                    i.putExtra("time",list.get(pos).getTime());
                    i.putExtra("desc",list.get(pos).getDescription());
                    i.putExtra("phone",list.get(pos).getPhone());
                    i.putExtra("email",list.get(pos).getEmail());
                    i.putExtra("pri",list.get(pos).getPrice());
                    i.putExtra("loc",list.get(pos).getLocalisation());
                    i.putExtra("pos", pos);




                    i.putExtra("name", name.getText().toString());
                    i.putExtra("date", time.getText().toString());



                    view.getContext().startActivity(i);
                }

            });
        }
    }

}