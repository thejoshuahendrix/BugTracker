package com.hendrix.finalproject_hendrixjoshua;
//Joshua Hendrix -CIT 2561 FINAL PROJECT
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {
    DataBaseHelper myDB;
    Button btnBack, btnSaveList;
    String dataString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_contents);

        ListView listView = (ListView) findViewById(R.id.listview);
        myDB = new DataBaseHelper(this);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSaveList = (Button) findViewById(R.id.btnSave);
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getAllData();
        Back();
        SavetoFile();
        //Populate the listView with database data and writing it to a string for file saving purposes.
        if (data.getCount() == 0) {
            Toast.makeText(ViewListContents.this, "Empty Database", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(" Ticket ID: " + data.getString(0) + "\n Name: " + data.getString(1) + "\n Email: " + data.getString(2) + "\n Description of Problem: \n " + data.getString(3));
                dataString = dataString + " Ticket ID: " + data.getString(0) + "\n Name: " + data.getString(1) + "\n Email: " + data.getString(2) + "\n Description of Problem: \n " + data.getString(3)+"\n";
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        }

    }
    public static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        }
        catch (IOException e) {
            return false;
        }}
//Saves list over the local database text file called ReportData.txt
    public void SavetoFile(){
        btnSaveList.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
            FileOutputStream fos = null;
            String filename = "ReportData.txt";
            if (isFilenameValid(filename) && !filename.isEmpty()) {

                try {
                    fos = openFileOutput(filename, MODE_PRIVATE);
                    fos.write(dataString.getBytes());
                } catch (FileNotFoundException e) { //This is file Not Found case.
                    e.printStackTrace();
                } catch (IOException e) { //This is a general exception
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getApplicationContext(), "Check File name", Toast.LENGTH_SHORT).show();
            }}});}
            public void Back(){
        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewListContents.this.finish();
                    }

                }
        );
    }
}
