package com.example.shehab.testerfilter.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shehab.testerfilter.Interface.FontAdapterListener;
import com.example.shehab.testerfilter.R;

import java.util.ArrayList;
import java.util.List;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.FontViewHolder> {


    Context context;
    FontAdapterListener listener;
    List<String> fontlist;
    int row_select = -1;

    public FontAdapter(Context context, FontAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        fontlist = loadfontList();
    }

    private List<String> loadfontList() {

        List<String> result = new ArrayList<>();
        result.add("3DAnimals.ttf");
        result.add("AisyKhadijah.otf");
        result.add("At The Midday_Font_Demo.otf");
        result.add("Beatific Margella Regular.otf");
        result.add("blazium.ttf");
        result.add("BRICK.TTF");
        result.add("Casino3DFilledMarquee.ttf");
        result.add("Coltan-Gea-Bold-demo-FFP.ttf");
        result.add("Demonstration.ttf");
        result.add("Denka Demo.ttf");
        result.add("FloralCapitals.ttf");
        result.add("GCRANK.TTF");
        result.add("Gradientico.ttf");
        result.add("HAWAIIAH.TTF");
        result.add("HollywoodActors.ttf");
        result.add("JMH Belicosa.otf");
        result.add("JMH CRYPT.otf");
        result.add("Kamikaze3DGradient.ttf");
        result.add("LeaveNoFingerprints.ttf");
        result.add("ManiacGrunged.ttf");
        result.add("Meshes.ttf");
        result.add("ModishGradient.ttf");
        result.add("moinho_1.otf");
        result.add("multivac-ghost.ttf");
        result.add("Narcissus.otf");
        result.add("Orhydea Demo.otf");
        result.add("Partizano-Demo-FFP.ttf");
        result.add("Riztteen.otf");
        result.add("Rye-Regular.ttf");
        result.add("Smashed.ttf");
        result.add("Spooky-Font.ttf");
        result.add("Spooky-Font.ttf");
        result.add("The Black Festival - DEMO.ttf");
        result.add("Tulips .ttf");
        result.add("velocity_demo.ttf");
        result.add("vtks lightness 2.ttf");
        result.add("Vtks Ultramein.ttf");
        result.add("Vtks-Challenge.ttf");





        return result;
    }

    @NonNull
    @Override
    public FontViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.font_item,viewGroup,false);

        return new FontViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FontViewHolder fontViewHolder, int i)
    {
        if(row_select == i)
            fontViewHolder.check_Image.setVisibility(View.VISIBLE);
        else
            fontViewHolder.check_Image.setVisibility(View.INVISIBLE);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(),new StringBuilder("fonts/")
                .append(fontlist.get(i)).toString());

        fontViewHolder.text_font_name.setText(fontlist.get(i));
        fontViewHolder.text_fond_demo.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return fontlist.size();
    }

    public class FontViewHolder extends RecyclerView.ViewHolder{
        TextView text_font_name,text_fond_demo;
        ImageView check_Image;
        public FontViewHolder(@NonNull View itemView) {
            super(itemView);

            text_fond_demo = (TextView)itemView.findViewById(R.id.text_font_demo);
            text_font_name = (TextView)itemView.findViewById(R.id.text_font_name);
            check_Image = (ImageView)itemView.findViewById(R.id.image_check);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onFontsSelected(fontlist.get(getAdapterPosition()));
                    row_select = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
