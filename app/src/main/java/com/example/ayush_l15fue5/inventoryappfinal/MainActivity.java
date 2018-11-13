package com.example.ayush_l15fue5.inventoryappfinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush_l15fue5.inventoryappfinal.CustomClasses.Product;
import com.example.ayush_l15fue5.inventoryappfinal.DataBaseFunctions.DatabaseHelper;

import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private TextView productID;
    private ImageView productImage;
    private TextView productName;
    private TextView supplierName;
    private TextView supplierContact;
    private TextView productPrice;
    private TextView quantity;
    private Button addDummy;
    private Button viewDummy;
    private Button viewError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        //Textview
        productID = findViewById(R.id.productID);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        quantity = findViewById(R.id.productQuantity);
        supplierName = findViewById(R.id.supplierName);
        supplierContact = findViewById(R.id.supplierContact);
        productImage = findViewById(R.id.productimage);
        //Buttons
        addDummy = findViewById(R.id.adddummydata);
        addDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product(1, "Dummy Image", getURLForResource(R.drawable.ic_dummyimage).toString(), 25f, 25, "Dummy Supplier", "9999999999");
                databaseHelper.insertData(product);
                Toast.makeText(getApplicationContext(), "id: 1 created", Toast.LENGTH_LONG).show();
            }
        });
        viewDummy = findViewById(R.id.viewdummydata);
        viewDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = databaseHelper.queryProduct(1);
                productID.setText(product.getId() + "");
                productName.setText(product.getName());
                try {
                    productImage.setImageBitmap(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(product.getImage())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                productPrice.setText("$" + product.getPrice());
                quantity.setText(product.getStock() + " items");
                supplierName.setText(product.getSupplierName());
                supplierContact.setText(product.getSupplierContact());
            }
        });
        viewError = findViewById(R.id.errordummydata);
        viewError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //id 19 doesn't exist so its used to check what the app will show if the element is absent
                Product product = databaseHelper.queryProduct(19);
                productID.setText(product.getId() + "");
                productName.setText(product.getName());
                try {
                    productImage.setImageBitmap(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(product.getImage())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                productPrice.setText("$ " + product.getPrice());
                quantity.setText(product.getStock() + " items");
                supplierName.setText(product.getSupplierName());
                supplierContact.setText(product.getSupplierContact());
            }
        });

    }

    public Uri getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId);
    }
}
