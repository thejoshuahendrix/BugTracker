package com.hendrix.finalproject_hendrixjoshua;
//Joshua Hendrix -CIT 2561 FINAL PROJECT
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//The onClick listeners in this activity are defined as methods then called in the onCreate constructor.
public class MainActivity extends AppCompatActivity {
    DataBaseHelper myDB;
    EditText editName,editEmail,editDescription;
    Button btnAddData,btnClear,btnDelete,btnViewList,btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DataBaseHelper(this);
        editName = (EditText) findViewById(R.id.txtName);
        editEmail = (EditText) findViewById(R.id.txtEmail);
        editDescription = (EditText) findViewById(R.id.txtDescription);
        btnAddData = (Button) findViewById(R.id.btnSubmit);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnViewList = (Button) findViewById(R.id.btnView);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        AddData();
        viewALL();
        UpdateData();
        DeleteData();
        ClearFields();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                return true;
                case R.id.menu_about:
                    showMessage("About","This app was created by Joshua Hendrix, and made to display my Android Studio abilities as well as be a helpful tool in tracking bugs");
                    return true;
                    default:return super.onOptionsItemSelected(item);}}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {getMenuInflater().inflate(R.menu.main_menu, menu);return true;}
    public void ClearFields(){
        btnClear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editName.setText("");
                        editEmail.setText("");
                        editDescription.setText("");
                    }
                }
        );
    }
    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer isDelete = myDB.deleteData(editName.getText().toString());
                        if (isDelete != 0) {
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }
    public void UpdateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDB.updateData(editName.getText().toString(),editEmail.getText().toString(),editDescription.getText().toString());
                        if(isUpdate ==true) {
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Data NOT Updated",Toast.LENGTH_LONG).show();
                        }
                    }



                }
        );
    }
    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(editName.getText().toString(),editEmail.getText().toString(),editDescription.getText().toString());
                        if (isInserted =true)
                            Toast.makeText(MainActivity.this,"Entry Accepted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Entry Error",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void viewALL() {
        btnViewList.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new  Intent(MainActivity.this,ViewListContents.class);
                        startActivity(intent);
                        /*Cursor res = myDB.getAllData();
                        if(res.getCount() == 0){
                            showMessage("Error","No Data Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("ID:"+res.getString(0)+"\n");
                            buffer.append("NAME:"+res.getString(1)+"\n");
                            buffer.append("EMAIL:"+res.getString(2)+"\n");
                            buffer.append("DESCRIPTION:"+res.getString(3)+"\n\n");
                        }
                        showMessage("Data",buffer.toString());*/
                    }

                }
        );
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}