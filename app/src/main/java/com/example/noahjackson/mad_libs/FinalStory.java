package com.example.noahjackson.mad_libs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class FinalStory extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_story);

        Intent intent = getIntent();
        ArrayList<String> madLib = intent.getStringArrayListExtra("madLib");
        ArrayList<String> yourWords = intent.getStringArrayListExtra("yourWords");

        String displayText = "";
        for(int i = 0; i < yourWords.size(); i++)
        {
            displayText += madLib.get(i);
            displayText += yourWords.get(i);
        }
        displayText += madLib.get(madLib.size()-1);

        TextView story = (TextView) findViewById(R.id.storyView);
        story.setMovementMethod(new ScrollingMovementMethod());
        story.setText(displayText);
    }

    public void returnToStart(View view) {
        finish();
    }
}
