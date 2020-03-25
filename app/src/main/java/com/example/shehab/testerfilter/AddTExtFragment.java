package com.example.shehab.testerfilter;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.shehab.testerfilter.Adapter.ColorAdapter;
import com.example.shehab.testerfilter.Adapter.FontAdapter;
import com.example.shehab.testerfilter.Interface.AddTextFragmentListener;
import com.example.shehab.testerfilter.Interface.FontAdapterListener;

import ja.burhanrashid52.photoeditor.PhotoEditor;



public class AddTExtFragment extends BottomSheetDialogFragment implements ColorAdapter.ColorAdapterListener, FontAdapterListener {

    int ColorSelected = Color.parseColor("#000000");

    AddTextFragmentListener listener;


    EditText edt_add_text;
    RecyclerView recyclercolor,recyclerfont;
    Button done;
    Typeface typefaceselected = Typeface.DEFAULT;

    public void setListener(AddTextFragmentListener listener) {
        this.listener = listener;
    }

    static AddTExtFragment instance;
    public static AddTExtFragment getInstance()
    {
        if(instance == null)
            instance = new AddTExtFragment();
        return instance;
    }

    public AddTExtFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemview =  inflater.inflate(R.layout.fragment_add_text, container, false);

        recyclercolor = (RecyclerView)itemview.findViewById(R.id.Recycler_color);
        recyclerfont =(RecyclerView)itemview.findViewById(R.id.Recycler_font);
        done  = (Button)itemview.findViewById(R.id.btn_add_text);
        edt_add_text = (EditText)itemview.findViewById(R.id.edt_add_text);

        recyclercolor.setHasFixedSize(true);
        recyclercolor.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        ColorAdapter colorAdapter  =new ColorAdapter(getActivity(),this);
        recyclercolor.setAdapter(colorAdapter);


        recyclerfont.setHasFixedSize(true);
        recyclerfont.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        FontAdapter fontAdapter = new FontAdapter(getContext(),this);
        recyclerfont.setAdapter(fontAdapter);



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddTextButtonClick(typefaceselected,edt_add_text.getText().toString(),ColorSelected);
            }
        });
        return itemview;
    }

    @Override
    public void onColorSelected(int color) {
        ColorSelected = color;
    }

    @Override
    public void onFontsSelected(String font) {

        typefaceselected = Typeface.createFromAsset(getContext().getAssets(),new StringBuilder("fonts/")
                .append(font).toString());
    }
}
