package com.example.medinventory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
        /*
        boolean updateCounts = this.updateStock();

        if (updateCounts==false){
            return;
        }*/
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_main);

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
    @SuppressLint("SetTextI18n")
    public void countRecords() {
        int recordCount = new TableControllerProduct(this).count();
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }

    @SuppressLint("SetTextI18n")
    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<ObjectProduct> products = new TableControllerProduct(this).read();
        TableControllerPurchased purchase=new TableControllerPurchased(this);
        TableControllerDispensed dispense=new TableControllerDispensed(this);
        //Add header
        /*String textViewContents= "Product Type" + " - " + "Product Name";
        TextView textViewProductItem= new TextView(this);
        textViewProductItem.setPadding(20, 10, 20, 10);
        textViewProductItem.setText(textViewContents);
        textViewProductItem.setTag(Integer.toString(0));
        linearLayoutRecords.addView(textViewProductItem);*/
        //Header end
        if (products.size() > 0) {

            for (ObjectProduct obj : products) {

                int id = obj.id;
                String productName = obj.productName;
                String productType = obj.productType;
                int stock=purchase.readProductCount(id)-dispense.readProductCount(id);

                String textViewContents = productType + " - " + productName + " - " + stock;

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

    public boolean updateStock() {
        List<ObjectProduct> products=new TableControllerProduct(this).read();
        TableControllerPurchased purchase=new TableControllerPurchased(this);
        TableControllerDispensed dispense=new TableControllerDispensed(this);
        if (products.size() > 0) {

            for (ObjectProduct obj : products) {

                int id = obj.id;
                String productName = obj.productName;
                String productType = obj.productType;
                int stock=purchase.readProductCount(id)-dispense.readProductCount(id);

                String textViewContents = productType + " - " + productName + " - " + stock;

            }

        }
        return true;
    }

    public String displayStock(Context context,int productId) {
        ObjectProduct product=new TableControllerProduct(context).readSingleRecord(productId);
        TableControllerPurchased purchase=new TableControllerPurchased(context);
        TableControllerDispensed dispense=new TableControllerDispensed(context);
        String textViewContents=null;
        if (product!=null) {

                int id = product.id;
                String productName = product.productName;
                String productType = product.productType;
                int stock=purchase.readProductCount(id)-dispense.readProductCount(id);

                textViewContents = productType + " - " + productName + " - " + stock;

        }
        return textViewContents;
    }
}
