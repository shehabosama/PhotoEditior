package com.example.shehab.testerfilter;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shehab.testerfilter.Adapter.EmojeAdapter;
import com.example.shehab.testerfilter.Interface.EmojiFragmentListener;

import ja.burhanrashid52.photoeditor.PhotoEditor;


public class EmojiFragment extends BottomSheetDialogFragment implements EmojeAdapter.EmojiAdapterListner {

    RecyclerView recycleremoji;
    static EmojiFragment instance;

    EmojiFragmentListener listener;

    public void setListener(EmojiFragmentListener listener) {
        this.listener = listener;
    }

    public static EmojiFragment getInstance()
    {
        if(instance == null)
            instance = new EmojiFragment();
        return instance;
    }

    public EmojiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemview= inflater.inflate(R.layout.fragment_emoji, container, false);

        recycleremoji = (RecyclerView)itemview.findViewById(R.id.recycler_emoji);
        recycleremoji.setHasFixedSize(true);
        recycleremoji.setLayoutManager(new GridLayoutManager(getActivity(),5));

        EmojeAdapter emojeAdapter = new EmojeAdapter(getContext(), PhotoEditor.getEmojis(getContext()),this);
        recycleremoji.setAdapter(emojeAdapter);
        return itemview;
    }

    @Override
    public void onEmojiItemSelected(String emoji)
    {
        listener.EmojiSelected(emoji);
    }
}
