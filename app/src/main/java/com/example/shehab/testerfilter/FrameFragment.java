package com.example.shehab.testerfilter;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shehab.testerfilter.Adapter.FrameAdapter;
import com.example.shehab.testerfilter.Interface.Add_frame_listener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FrameFragment extends BottomSheetDialogFragment implements FrameAdapter.FrameAdapterListener{


    static FrameFragment instance;
    RecyclerView recyclerframe;
    Button btn_add_frame;

    int frame_select = -1;

    Add_frame_listener listener;

    public void setListener(Add_frame_listener listener) {
        this.listener = listener;
    }

    public static FrameFragment getInstance()
    {
        if(instance == null)
            instance = new FrameFragment();
        return instance;
    }

    public FrameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_frame, container, false);
        recyclerframe = (RecyclerView)itemView.findViewById(R.id.recycler_frame);
        btn_add_frame =(Button)itemView.findViewById(R.id.add_frame);
        recyclerframe.setHasFixedSize(true);
        recyclerframe.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerframe.setAdapter(new FrameAdapter(getContext(),this));

        btn_add_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddFrame(frame_select);
            }
        });

        return itemView;
    }

    @Override
    public void onFrameSelect(int frame) {

        frame_select =frame;
    }
}
