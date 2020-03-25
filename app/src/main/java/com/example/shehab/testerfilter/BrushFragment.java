package com.example.shehab.testerfilter;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.example.shehab.testerfilter.Adapter.ColorAdapter;
import com.example.shehab.testerfilter.Interface.BrushFragmentListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrushFragment extends BottomSheetDialogFragment implements ColorAdapter.ColorAdapterListener {


    SeekBar brush_Size,brush_opacity;
    RecyclerView recycler_color;
    ToggleButton btn_brush_state;
    ColorAdapter colorAdapter;
    BrushFragmentListener listener;

    static BrushFragment instance;

    public static BrushFragment getInstance() {
        if(instance == null)
            instance = new BrushFragment();
        return instance;
    }
    public void setListener(BrushFragmentListener listener) {
        this.listener = listener;
    }

    public BrushFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemview =  inflater.inflate(R.layout.fragment_brush, container, false);

        brush_Size = (SeekBar)itemview.findViewById(R.id.seekbar_brush_Size);
        brush_opacity = (SeekBar)itemview.findViewById(R.id.seekbar_brush_opacity);
        recycler_color = (RecyclerView) itemview.findViewById(R.id.Recycler_color);
        btn_brush_state = (ToggleButton) itemview.findViewById(R.id.btn_brush_state);

        recycler_color.setHasFixedSize(true);
        recycler_color.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

         colorAdapter = new ColorAdapter(getContext(),this);

         recycler_color.setAdapter(colorAdapter);

         brush_opacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 listener.onBrushOpacityChangeListener(progress);
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
         });
         brush_Size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 listener.onBrushSizeChangeListener(progress);
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
         });
         btn_brush_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 listener.onBrushStateChangeListener(isChecked);
             }
         });

        return itemview;
    }


    @Override
    public void onColorSelected(int color) {

        listener.onBrushColorChangeListener(color);
    }
}
