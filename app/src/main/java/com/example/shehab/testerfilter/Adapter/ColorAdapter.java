package com.example.shehab.testerfilter.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shehab.testerfilter.R;

import java.util.ArrayList;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder>  {

    Context context;
    List<Integer> colorlist;

    ColorAdapterListener listener;

    public ColorAdapter(Context context, ColorAdapterListener listener) {
        this.context = context;
        this.colorlist = genColorlist();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(context).inflate(R.layout.color_item,viewGroup,false);
        return new ColorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder colorViewHolder, int i) {
        colorViewHolder.color_section.setCardBackgroundColor(colorlist.get(i));

    }

    @Override
    public int getItemCount() {
        return colorlist.size();
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {

        public CardView color_section;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            color_section = (CardView)itemView.findViewById(R.id.color_section);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onColorSelected(colorlist.get(getAdapterPosition()));
                }
            });
        }
    }


    private List<Integer> genColorlist() {
        List<Integer> colorlist = new ArrayList<>();
        colorlist.add(Color.parseColor("#00FFFFFF"));
        colorlist.add(Color.parseColor("#008000"));
        colorlist.add(Color.parseColor("#ffff00"));
        colorlist.add(Color.parseColor("#ff8000"));
        colorlist.add(Color.parseColor("#8000ff"));
        colorlist.add(Color.parseColor("#0000ff"));
        colorlist.add(Color.parseColor("#ffe7ab"));
        colorlist.add(Color.parseColor("#fbc1f0"));
        colorlist.add(Color.parseColor("#c3f6fe"));
        colorlist.add(Color.parseColor("#ec7676"));
        colorlist.add(Color.parseColor("#532cea"));
        colorlist.add(Color.parseColor("#fdbdc6"));
        colorlist.add(Color.parseColor("#fc1e0d"));
        colorlist.add(Color.parseColor("#990027"));
        colorlist.add(Color.parseColor("#000000"));


        return colorlist;
    }



    public interface ColorAdapterListener
    {
        void onColorSelected(int color);

    }
}
