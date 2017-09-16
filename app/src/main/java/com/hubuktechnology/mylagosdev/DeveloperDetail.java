package com.hubuktechnology.mylagosdev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by israelxoftsoul on 14/09/2017 AD.
 */

public class DeveloperDetail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.develper_detail_activity);
        Intent intent = getIntent();
        String devName = intent.getStringExtra("dev");
        TextView texttView = (TextView)findViewById(R.id.username);
        texttView.setText(devName);
        ImageView imageView =(ImageView)findViewById(R.id.Dpicture);
        String picture = intent.getStringExtra("display");
        Picasso.with(getApplicationContext()).load(picture).into(imageView);

    }

}
