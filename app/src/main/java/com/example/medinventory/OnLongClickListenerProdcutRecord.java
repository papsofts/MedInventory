package com.example.medinventory;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

class OnLongClickListenerProdcutRecord implements View.OnLongClickListener {

    private Context context;
    String id;
    @Override
    public boolean onLongClick(View view) {

        context = view.getContext();
        id = view.getTag().toString();

        final CharSequence[] items = { "Sell","Purchase","Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Product Record")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            saleRecord(Integer.parseInt(id));
                        }
                        else if (item == 1) {
                            purchaseRecord(Integer.parseInt(id));
                        }
                        else if (item == 2) {
                            editRecord(Integer.parseInt(id));
                            ((MainActivity) context).readRecords();
                        }
                        else if (item == 3) {

                            boolean deleteSuccessful = new TableControllerProduct(context).delete(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Product record was deleted.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to delete product record.", Toast.LENGTH_SHORT).show();
                            }

                            ((MainActivity) context).countRecords();
                            ((MainActivity) context).readRecords();

                        }
                        dialog.dismiss();

                    }
                }).show();

        return false;
    }

    private void saleRecord(final int productId) {
    }
    private void purchaseRecord(final int productId) {
    }
    private void editRecord(final int productId) {
        final TableControllerProduct tableControllerProduct = new TableControllerProduct(context);
        ObjectProduct objectProduct = tableControllerProduct.readSingleRecord(productId);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        final View formElementsView = inflater.inflate(R.layout.product_input_form, null, false);

        final EditText editTextProductName = (EditText) formElementsView.findViewById(R.id.editTextProductName);
        final EditText editTextProductType = (EditText) formElementsView.findViewById(R.id.editTextProductType);

        editTextProductName.setText(objectProduct.productName);
        editTextProductType.setText(objectProduct.productType);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit - "+editTextProductName.getText().toString())
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ObjectProduct objectProduct;
                                objectProduct = new ObjectProduct();
                                objectProduct.id = productId;
                                objectProduct.productName = editTextProductName.getText().toString();
                                objectProduct.productType = editTextProductType.getText().toString();

                                boolean updateSuccessful = tableControllerProduct.update(objectProduct);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Product record was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update product record.", Toast.LENGTH_SHORT).show();
                                }

                                dialog.cancel();
                            }

                        }).show();



    }

}