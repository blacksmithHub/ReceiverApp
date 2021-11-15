package com.pelaez.jc.receiverapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sqlitelib.DataBaseHelper;
import com.sqlitelib.SQLite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView validlv, invalidlv, alllv;
    ArrayList dogbreedinfo =new ArrayList();
    TextView txtvalidz,txtinvalidz;
    Button btnsum;
    DataBaseHelper dbhelper;
    public int valueId[];
    int totalvalid,totalinvalid;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validlv = (ListView)findViewById(R.id.listviewValid);
        invalidlv = (ListView)findViewById(R.id.listviewInvalid);
        alllv = (ListView)findViewById(R.id.listviewAll);
        txtvalidz = (TextView)findViewById(R.id.txtvalid);
        txtinvalidz = (TextView)findViewById(R.id.txtinvalid);
        btnsum = (Button)findViewById(R.id.btnsummary);

        try {
            setnum();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dogbreedinfo.size()!=0){
                    sendData();
                }
            }
        });


        dbhelper = new DataBaseHelper(MainActivity.this, "DogInfoDatabase",2);

        //         Get intent object sent from the SMSBroadcastReceiver
      refreshall();

        Intent sms_intent = getIntent();
        Bundle b = sms_intent.getExtras();
        if (b!=null) {
            SMSClass smsObj = (SMSClass)b.getSerializable("sms_obj");
            final SQLiteDatabase dbAdd = dbhelper.getWritableDatabase();
            String message = smsObj.getMessage().toString();
            String[] separated = message.split("#");
            String sqlStr = "INSERT INTO tbldoginfo (number, candidate, position, code) VALUES ('"
                    + smsObj.getNumber().toString() + "','"
                    + separated[0]+ "','"
                    + separated[1]+ "','"
                    + separated[2] + "')";

            dbAdd.execSQL(sqlStr);
            refreshall();
            refresh3();
                totalvalid  = validlv.getAdapter().getCount();
                totalinvalid = invalidlv.getAdapter().getCount();
                txtvalidz.setText("" + totalvalid);
                txtinvalidz.setText("" + totalinvalid);


        }
    }

    public void refresh(){
        SQLiteDatabase dbDoginfo = dbhelper.getWritableDatabase();
        //get table from sqlite_master
        Cursor cDoginfo = dbDoginfo.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='tbldoginfo'", null);
        cDoginfo.moveToNext();

        if (cDoginfo.getCount() == 0) { //check if the database is exisitng
            SQLite.FITCreateTable("DogInfoDatabase", this, "tbldoginfo", "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "number VARCHAR(90),candidate VARCHAR(90),position VARCHAR(90),code VARCHAR(90)"); //create table


        }
        cDoginfo = dbDoginfo.rawQuery("SELECT id, number, candidate, position, code  FROM tbldoginfo  order by id desc", null);


        String list[] = new String[cDoginfo.getCount()];
        int valueCurrentId[] = new int[cDoginfo.getCount()];


        int ctrl = 0;


        while (cDoginfo.moveToNext()) {
            String strFor = "";


            strFor += "Number: : " + cDoginfo.getString(cDoginfo.getColumnIndex("number"));
            strFor += System.lineSeparator() + "Candidate  : " + cDoginfo.getString(cDoginfo.getColumnIndex("candidate"));
            strFor += System.lineSeparator() + "Position  : " + cDoginfo.getString(cDoginfo.getColumnIndex("position"));
            strFor += System.lineSeparator() + "Code  : " + cDoginfo.getString(cDoginfo.getColumnIndex("code"));

            valueCurrentId[ctrl]= cDoginfo.getInt(cDoginfo.getColumnIndex("id"));
            list[ctrl] = strFor;
            valueId = Arrays.copyOf(valueCurrentId, cDoginfo.getCount());

            ctrl++;
        }


        alllv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list));
    }
    public void refresh1(){
        SQLiteDatabase dbDoginfo = dbhelper.getWritableDatabase();
        //get table from sqlite_master
        Cursor cDoginfo = dbDoginfo.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='tbldoginfo'", null);
        cDoginfo.moveToNext();

        if (cDoginfo.getCount() == 0) { //check if the database is exisitng
            SQLite.FITCreateTable("DogInfoDatabase", this, "tbldoginfo", "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "number VARCHAR(90),candidate VARCHAR(90),position VARCHAR(90),code VARCHAR(90)"); //create table


        }
        cDoginfo = dbDoginfo.rawQuery("SELECT id, number, candidate, position, code  FROM tbldoginfo  WHERE code = 'VC98C' order by id desc", null);


        String list[] = new String[cDoginfo.getCount()];
        int valueCurrentId[] = new int[cDoginfo.getCount()];


        int ctrl = 0;


        while (cDoginfo.moveToNext()) {
            String strFor = "";


            strFor += "Number: : " + cDoginfo.getString(cDoginfo.getColumnIndex("number"));
            strFor += System.lineSeparator() + "Candidate  : " + cDoginfo.getString(cDoginfo.getColumnIndex("candidate"));
            strFor += System.lineSeparator() + "Position  : " + cDoginfo.getString(cDoginfo.getColumnIndex("position"));
            strFor += System.lineSeparator() + "Code  : " + cDoginfo.getString(cDoginfo.getColumnIndex("code"));

            valueCurrentId[ctrl]= cDoginfo.getInt(cDoginfo.getColumnIndex("id"));
            list[ctrl] = strFor;
            valueId = Arrays.copyOf(valueCurrentId, cDoginfo.getCount());

            ctrl++;
        }


        validlv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list));
    }
    public void refresh2(){
        SQLiteDatabase dbDoginfo = dbhelper.getWritableDatabase();
        //get table from sqlite_master
        Cursor cDoginfo = dbDoginfo.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='tbldoginfo'", null);
        cDoginfo.moveToNext();

        if (cDoginfo.getCount() == 0) { //check if the database is exisitng
            SQLite.FITCreateTable("DogInfoDatabase", this, "tbldoginfo", "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "number VARCHAR(90),candidate VARCHAR(90),position VARCHAR(90),code VARCHAR(90)"); //create table


        }
        cDoginfo = dbDoginfo.rawQuery("SELECT id, number, candidate, position, code  FROM tbldoginfo  WHERE code != 'VC98C' order by id desc", null);


        String list[] = new String[cDoginfo.getCount()];
        int valueCurrentId[] = new int[cDoginfo.getCount()];


        int ctrl = 0;


        while (cDoginfo.moveToNext()) {
            String strFor = "";


            strFor += "Number: : " + cDoginfo.getString(cDoginfo.getColumnIndex("number"));
            strFor += System.lineSeparator() + "Candidate  : " + cDoginfo.getString(cDoginfo.getColumnIndex("candidate"));
            strFor += System.lineSeparator() + "Position  : " + cDoginfo.getString(cDoginfo.getColumnIndex("position"));
            strFor += System.lineSeparator() + "Code  : " + cDoginfo.getString(cDoginfo.getColumnIndex("code"));

            valueCurrentId[ctrl]= cDoginfo.getInt(cDoginfo.getColumnIndex("id"));
            list[ctrl] = strFor;
            valueId = Arrays.copyOf(valueCurrentId, cDoginfo.getCount());

            ctrl++;
        }


        invalidlv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list));
    }
    public void refresh3(){
        SQLiteDatabase dbDoginfo = dbhelper.getWritableDatabase();
        //get table from sqlite_master
        Cursor cDoginfo = dbDoginfo.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='tbldoginfo'", null);
        cDoginfo.moveToNext();

        if (cDoginfo.getCount() == 0) { //check if the database is exisitng
            SQLite.FITCreateTable("DogInfoDatabase", this, "tbldoginfo", "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "number VARCHAR(90),candidate VARCHAR(90),position VARCHAR(90),code VARCHAR(90)"); //create table


        }

            cDoginfo = dbDoginfo.rawQuery("SELECT position,number,candidate,count(DISTINCT number) as tcount  FROM tbldoginfo WHERE code = 'VC98C' group by candidate ", null);

        String list[] = new String[cDoginfo.getCount()];
        int valueCurrentId[] = new int[cDoginfo.getCount()];


        int ctrl = 0;


        while (cDoginfo.moveToNext()) {
            String strFor = "";



            strFor += System.lineSeparator() + "Candidate : " + cDoginfo.getString(cDoginfo.getColumnIndex("candidate"));
            strFor += System.lineSeparator() + "Position : " + cDoginfo.getString(cDoginfo.getColumnIndex("position"));
            strFor += System.lineSeparator() + "Total Counted Votes : " + cDoginfo.getString(cDoginfo.getColumnIndex("tcount"));


            list[ctrl] = strFor;
            dogbreedinfo.add(strFor);


            ctrl++;
        }



    }
    public void refreshall () {
        refresh();
        refresh1();
        refresh2();




    }

    private DoginfoCollection getBreed()
    {

        DoginfoCollection doginfoCollection =new DoginfoCollection();
        doginfoCollection.setDoginfo(dogbreedinfo);
        return doginfoCollection;
    }

    private void sendData() {

        Intent intent=new Intent(this,SecondActvity.class);
        intent.putExtra("DOGBREED",this.getBreed());

        startActivity(intent);
    }
    public void setnum () {
        totalvalid  = validlv.getAdapter().getCount();
        totalinvalid = invalidlv.getAdapter().getCount();
        txtvalidz.setText("" + totalvalid);
        txtinvalidz.setText("" + totalinvalid);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void add(){

    }
}
