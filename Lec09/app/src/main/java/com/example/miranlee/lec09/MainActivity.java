package com.example.miranlee.lec09;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText pid;
    EditText pname;
    EditText pquantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        pid = (EditText)findViewById(R.id.editid);
        pname = (EditText)findViewById(R.id.editname);
        pquantity = (EditText)findViewById(R.id.editquantity);
    }

    public void newProduct(View v){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int quantity = Integer.parseInt(pquantity.getText().toString());
        Product product = new Product(pname.getText().toString(), quantity);
        dbHandler.addProduct(product);
    }

    public void deleteProduct(View v){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        boolean result = dbHandler.deleteProduct(pname.getText().toString());
        if(result){
            pid.setText("");
            pquantity.setText("");
            pname.setText("");
            Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Match Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void findProduct(View v){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Product product = dbHandler.findProduct(pname.getText().toString());
        if(product != null){
            pid.setText(String.valueOf(product.getID()));
            pquantity.setText(String.valueOf(product.getQuantity()));
        } else {
            Toast.makeText(this, "No Match Found", Toast.LENGTH_SHORT).show();
        }
    }
}
