package com.main.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private List<Item> mList;
    private List<Item> bList;
    private Context context;
    private ItemListener itemListener;

    public ItemAdapter(List<Item> mList, Context context) {
        this.mList = mList;
        this.context = context;
        this.bList = mList;
    }
    public List<Item> getbList(){
        return bList;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public Item getItemAt(int position){
        return mList.get(position);
    }
    public void add(Item item){
        mList.add(item);
        bList.add(item);
    }
    public void update(int position,Item item){
        mList.set(position,item);
        bList.set(position,item);
        notifyDataSetChanged();
    }
    public void filterList(List<Item> filterList){
        mList = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mList.get(position);
        if(item == null){
            return;
        }
        holder.imageView.setImageResource(item.getImg());
        holder.job.setText(item.getJob());
        holder.space.setText("-");
        holder.date.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView job, space, date;
        private Button btnRemove;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            job = itemView.findViewById(R.id.job);
            space = itemView.findViewById(R.id.space);
            date = itemView.findViewById(R.id.date);
            btnRemove = itemView.findViewById(R.id.remove);
            itemView.setOnClickListener(view -> onClick(itemView));
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bList.remove(getAdapterPosition());
                    mList.remove(getAdapterPosition());
                    Toast.makeText(context.getApplicationContext(), "REMOVE SUCCESS", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });

        }

        @Override
        public void onClick(View view) {
            if(itemListener == null) return;
            itemListener.onItemCLick(view,getAdapterPosition());
        }
    }
    public interface ItemListener{
        void onItemCLick(View view, int position);
    }
}
