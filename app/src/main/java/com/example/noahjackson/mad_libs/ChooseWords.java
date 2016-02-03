package com.example.noahjackson.mad_libs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Scanner;

public class ChooseWords extends AppCompatActivity {

    private int wordsLeft;
    private ArrayList<String> madLib;
    private ArrayList<String> yourWords;
    private ArrayList<String> yourHints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_words);
        madLib = new ArrayList<String>();
        yourWords = new ArrayList<String>();
        yourHints = new ArrayList<String>();

        openMadLibs();

        TextView wordCount = (TextView) findViewById(R.id.wordsLeft);
        wordCount.setText(wordsLeft + " Word(s) Left");

        EditText chooseWords = (EditText) findViewById(R.id.wordChosen);
        chooseWords.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        chooseWords.setHint(yourHints.get(0));
    }

    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("wordsLeft", wordsLeft);
        outState.putStringArrayList("yourWords", yourWords);
        outState.putStringArrayList("yourHints", yourHints);
        outState.putStringArrayList("madLib", madLib);
    }

    public void onRestoreInstanceState(Bundle inState){
        super.onSaveInstanceState(inState);
        wordsLeft = inState.getInt("wordsLeft");
        madLib = inState.getStringArrayList("madLib");
        yourWords = inState.getStringArrayList("yourWords");
        yourHints = inState.getStringArrayList("yourHints");

        TextView wordCount = (TextView) findViewById(R.id.wordsLeft);
        wordCount.setText(wordsLeft + " Word(s) Left");

        EditText chooseWords = (EditText) findViewById(R.id.wordChosen);
        chooseWords.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        chooseWords.setHint(yourHints.get(yourHints.size() - wordsLeft));
    }


    public void addClick(View view) {
        EditText chooseWords = (EditText) findViewById(R.id.wordChosen);
        if(!chooseWords.getText().toString().equals(""))
        {
            wordsLeft--;
            yourWords.add(chooseWords.getText().toString());
            chooseWords.setText("");
            if(wordsLeft == 0)
            {
                Intent intent = new Intent(this,FinalStory.class);
                intent.putExtra("yourWords", yourWords);
                intent.putExtra("madLib", madLib);
                startActivity(intent);
                finish();
            }
            else
            {
                chooseWords.setHint(yourHints.get(yourHints.size() - wordsLeft));
                TextView wordCount = (TextView) findViewById(R.id.wordsLeft);
                wordCount.setText(wordsLeft + " Word(s) Left");
            }

        }

    }

    public void openMadLibs()  {
        Intent intent = getIntent();
        String fileName = intent.getStringExtra("fileName");
        int fileID = getResources().getIdentifier(fileName, "raw", getPackageName());
        Scanner scanny = new Scanner(getResources().openRawResource(fileID));
        String wholefile = "";

        while(scanny.hasNextLine())
        {
            wholefile += scanny.nextLine();
        }
        String splitting[] = wholefile.split("_");
        boolean hint = false;
        for(int i = 0; i < splitting.length; i++)
        {
            if(hint)
            {
                yourHints.add(splitting[i]);
            }
            else
            {
                madLib.add(splitting[i]);
            }
            hint = !hint;
        }
        wordsLeft = yourHints.size();

    }
}
