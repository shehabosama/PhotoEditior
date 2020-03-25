package com.example.shehab.testerfilter.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shehab.testerfilter.R;

import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconTextView;

public class EmojeAdapter extends RecyclerView.Adapter<EmojeAdapter.EmojiViewHolder> {
    Context context;
    List<String> emojilist;
    EmojiAdapterListner listner;

    public EmojeAdapter(Context context, List<String> emojilist, EmojiAdapterListner listner) {
        this.context = context;
        this.emojilist = emojilist;
        this.listner = listner;
    }

    @NonNull
    @Override
    public EmojiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.emoji_item,viewGroup,false);

        return new EmojiViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiViewHolder emojiViewHolder, int i) {
        emojiViewHolder.emojiconTextView.setText(emojilist.get(i));

    }

    @Override
    public int getItemCount() {
        return emojilist.size();
    }

    public class EmojiViewHolder extends RecyclerView.ViewHolder
    {
        EmojiconTextView emojiconTextView;

        public EmojiViewHolder(@NonNull View itemView) {
            super(itemView);
            emojiconTextView = (EmojiconTextView)itemView.findViewById(R.id.emoji_textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onEmojiItemSelected(emojilist.get(getAdapterPosition()));
                }
            });
        }
    }
    public interface EmojiAdapterListner
    {
        void onEmojiItemSelected(String emoji);
    }
}
