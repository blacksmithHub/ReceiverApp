package com.pelaez.jc.receiverapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class SecondActvity extends AppCompatActivity {
    ListView lvsum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_actvity);
        lvsum = (ListView)findViewById(R.id.listviewSum);
        receiveData();
    }
    private void receiveData(){

        Intent i=this.getIntent();
        DoginfoCollection doginfoBreed = (DoginfoCollection) i.getSerializableExtra("DOGBREED");

        lvsum.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, doginfoBreed.getDoginfo()));


    }

}
