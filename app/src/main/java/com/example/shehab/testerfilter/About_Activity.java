package com.example.shehab.testerfilter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class About_Activity extends AppCompatActivity {

    ImageView facebook,mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_);
        facebook = (ImageView) findViewById(R.id.facebook);
        mail = (ImageView) findViewById(R.id.mail);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/bebo.osama.12")));
                        }
                        catch (ActivityNotFoundException e)
                        {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/bebo.osama.12")));
                        }


            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                        String subject = "Explain About App (Einstein World)";

                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"shehabosama0122421@gmail.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, subject);
                        //i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                        try {
                            startActivity(Intent.createChooser(i, "Send mail ..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(About_Activity.this, "There are no email clients installed", Toast.LENGTH_SHORT).show();
                        }

            }
        });
    }
}
