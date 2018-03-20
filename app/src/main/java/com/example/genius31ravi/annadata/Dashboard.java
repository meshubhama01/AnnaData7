package com.example.genius31ravi.annadata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity implements View.OnClickListener{
TextView agri1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findViewById(R.id.info).setOnClickListener(this);
        findViewById(R.id.air).setOnClickListener(this);
      agri1=  findViewById(R.id.agri1);
      agri1.setClickable(true);
        agri1.setMovementMethod(LinkMovementMethod.getInstance());
        String text="<a href ='www.agriculture.gov.in'>MINISTRY</a>";
        agri1.setText(Html.fromHtml(text));

        findViewById(R.id.bookmark).setOnClickListener(this);
        findViewById(R.id.events).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.info:
                startActivity(new Intent(Dashboard.this, ApiRequest.class));
                break;
            case R.id.agri:
              //  startActivity(new Intent(Dashboard.this, AgiMinPortal.class));
                break;
            case R.id.air:
                startActivity(new Intent(Dashboard.this, Youtube.class));
                break;

            case R.id.bookmark:
                startActivity(new Intent(Dashboard.this, Bookmark.class));
                break;
            case R.id.events:
                startActivity(new Intent(Dashboard.this, Events.class));
                break;
        }
    }
}
