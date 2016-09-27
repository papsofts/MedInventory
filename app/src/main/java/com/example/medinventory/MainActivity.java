package com.example.medinventory;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.product_input_form, null, false);

                final EditText editTextProductName = (EditText) formElementsView.findViewById(R.id.editTextProductName);
                final EditText editTextProductType = (EditText) formElementsView.findViewById(R.id.editTextProductType);

                new AlertDialog.Builder(context)
                        .setView(formElementsView)
                        .setTitle("Create Product")
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String productName = editTextProductName.getText().toString();
                                        String productType = editTextProductType.getText().toString();

                                        ObjectProduct ObjectProduct = new ObjectProduct();
                                        ObjectProduct.productName= productName;
                                        ObjectProduct.productType= productType;

                                        boolean createSuccessful = new TableControllerProduct(context).create(ObjectProduct);
                                        if(createSuccessful){
                                            Toast.makeText(context, "Product information was saved.", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(context, "Unable to save product information.", Toast.LENGTH_SHORT).show();
                                        }
                                        countRecords();
                                        ((MainActivity) context).readRecords();
                                        dialog.cancel();
                                    }

                                }).show();
            }

        });
        countRecords();
        readRecords();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void countRecords() {
        int recordCount = new TableControllerProduct(this).count();
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }

    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<ObjectProduct> products = new TableControllerProduct(this).read();

        if (products.size() > 0) {

            for (ObjectProduct obj : products) {

                int id = obj.id;
                String productName = obj.productName;
                String productType = obj.productType;

                String textViewContents = productName + " - " + productType;

                TextView textViewProductItem= new TextView(this);
                textViewProductItem.setPadding(20, 10, 20, 10);
                textViewProductItem.setText(textViewContents);
                textViewProductItem.setTag(Integer.toString(id));
                textViewProductItem.setOnLongClickListener(new OnLongClickListenerProdcutRecord());

                linearLayoutRecords.addView(textViewProductItem);
            }

        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }

    }
}
