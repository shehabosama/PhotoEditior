package com.example.shehab.testerfilter.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shehab.testerfilter.R;

import java.util.ArrayList;
import java.util.List;

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.FrameViewHolder> {

    Context context;
    List<Integer> framelist;
    FrameAdapterListener listener;
    int row_selected = -1;
    public FrameAdapter(Context context, FrameAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.framelist = getFrameList();
    }

    @NonNull
    @Override
    public FrameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.frame_item,viewGroup,false);

        return new FrameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FrameViewHolder frameViewHolder, int i)
    {
        if (row_selected == i)
            frameViewHolder.checkImage.setVisibility(View.VISIBLE);
        else
            frameViewHolder.checkImage.setVisibility(View.INVISIBLE);

        frameViewHolder.FrmaeImage.setImageResource(framelist.get(i));

    }

    @Override
    public int getItemCount() {
        return framelist.size();
    }

    public List<Integer> getFrameList() {
        List<Integer> result = new ArrayList<>();
        result.add(R.drawable.card_1_resize);
        result.add(R.drawable.card_2_resize);
        result.add(R.drawable.card_3_resize);
        result.add(R.drawable.card_4_resize);
        result.add(R.drawable.card_5_resize);
        result.add(R.drawable.card_6_resize);
        result.add(R.drawable.card_7_resize);
        result.add(R.drawable.card_8_resize);
        result.add(R.drawable.hair_1);
        result.add(R.drawable.hair_2);
        result.add(R.drawable.hair_3);
        result.add(R.drawable.hair_5);
        result.add(R.drawable.hair_6);
        result.add(R.drawable.hair_7);
        result.add(R.drawable.chin_1);
        result.add(R.drawable.chin_2);
        result.add(R.drawable.chin_3);
        result.add(R.drawable.lips_2);
        result.add(R.drawable.lips_3);
        result.add(R.drawable.lenses_1);

        return result;
    }

    public class FrameViewHolder extends RecyclerView.ViewHolder {
        ImageView checkImage,FrmaeImage;
        public FrameViewHolder(@NonNull View itemView) {
            super(itemView);
            checkImage = (ImageView)itemView.findViewById(R.id.image_check);
            FrmaeImage = (ImageView)itemView.findViewById(R.id.image_frame);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFrameSelect(framelist.get(getAdapterPosition()));
                    row_selected = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface FrameAdapterListener
    {
        void onFrameSelect(int frame);
    }
}
