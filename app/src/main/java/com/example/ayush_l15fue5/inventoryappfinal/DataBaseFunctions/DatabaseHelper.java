package com.example.ayush_l15fue5.inventoryappfinal.DataBaseFunctions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.ayush_l15fue5.inventoryappfinal.CustomClasses.Product;
import com.example.ayush_l15fue5.inventoryappfinal.R;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String TEXT_TYPE = " TEXT";
        final String COMMA_SEP = ",";
        final String INTEGER_TYPE = " INTEGER";
        final String FLOAT_TYPE = " REAL";
        String CREATE_TABLE = "CREATE TABLE " + DatabaseContract.ProductEntry.TABLE_NAME + "("
                + DatabaseContract.ProductEntry.COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY,"
                + DatabaseContract.ProductEntry.COLUMN_PRODUCT_NAME + TEXT_TYPE + COMMA_SEP
                + DatabaseContract.ProductEntry.COLUMN_PRODUCT_IMAGE + TEXT_TYPE + COMMA_SEP
                + DatabaseContract.ProductEntry.COLUMN_PRODUCT_PRICE + FLOAT_TYPE + COMMA_SEP
                + DatabaseContract.ProductEntry.COLUMN_PRODUCT_STOCK + INTEGER_TYPE + COMMA_SEP
                + DatabaseContract.ProductEntry.COLUMN_SUPPLIER_NAME + INTEGER_TYPE + COMMA_SEP
                + DatabaseContract.ProductEntry.COLUMN_SUPPLIER_CONTACT + TEXT_TYPE + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ProductEntry.TABLE_NAME);
        // Creating tables again
        onCreate(db);
    }
    public void insertData(Product product){
        // Insert into database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ProductEntry.COLUMN_PRODUCT_NAME, product.getName());
        values.put(DatabaseContract.ProductEntry.COLUMN_PRODUCT_IMAGE, product.getImage());
        values.put(DatabaseContract.ProductEntry.COLUMN_PRODUCT_PRICE, product.getPrice());
        values.put(DatabaseContract.ProductEntry.COLUMN_PRODUCT_STOCK, product.getStock());
        values.put(DatabaseContract.ProductEntry.COLUMN_SUPPLIER_NAME, product.getSupplierName());
        values.put(DatabaseContract.ProductEntry.COLUMN_SUPPLIER_CONTACT, product.getSupplierContact());

        // Inserting Row
        db.insert(DatabaseContract.ProductEntry.TABLE_NAME, null, values);
        db.close(); // Closing database connection

    }

    public Cursor queryData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.ProductEntry.TABLE_NAME,
                new String[]{
                        DatabaseContract.ProductEntry.COLUMN_PRODUCT_ID,
                        DatabaseContract.ProductEntry.COLUMN_PRODUCT_NAME,
                        DatabaseContract.ProductEntry.COLUMN_PRODUCT_IMAGE,
                        DatabaseContract.ProductEntry.COLUMN_PRODUCT_PRICE,
                        DatabaseContract.ProductEntry.COLUMN_PRODUCT_STOCK,
                        DatabaseContract.ProductEntry.COLUMN_SUPPLIER_NAME,
                        DatabaseContract.ProductEntry.COLUMN_SUPPLIER_CONTACT
                },
                DatabaseContract.ProductEntry.COLUMN_PRODUCT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Product product = new Product();

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public Product queryProduct(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.ProductEntry.TABLE_NAME,
                new String[]{
                        DatabaseContract.ProductEntry.COLUMN_PRODUCT_ID,
                        DatabaseContract.ProductEntry.COLUMN_PRODUCT_NAME,
                        DatabaseContract.ProductEntry.COLUMN_PRODUCT_IMAGE,
                        DatabaseContract.ProductEntry.COLUMN_PRODUCT_PRICE,
                        DatabaseContract.ProductEntry.COLUMN_PRODUCT_STOCK,
                        DatabaseContract.ProductEntry.COLUMN_SUPPLIER_NAME,
                        DatabaseContract.ProductEntry.COLUMN_SUPPLIER_CONTACT
                },
                DatabaseContract.ProductEntry.COLUMN_PRODUCT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Product product = new Product();

        if (cursor != null) {
            if(cursor.getCount() <= 0){
                String imageUri = getURLForResource(R.drawable.ic_noimagefound).toString();
                product.setId(0);
                product.setName("Not Found");
                product.setImage(imageUri);
                product.setPrice(0);
                product.setStock(0);
                product.setSupplierName("Not Found");
                product.setSupplierContact("Not Found");
               // return false;
            }else {
                cursor.moveToFirst();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setName(cursor.getString(1));
                product.setImage(cursor.getString(2));
                product.setPrice(cursor.getFloat(3));
                product.setStock(Integer.parseInt(cursor.getString(4)));
                product.setSupplierName(cursor.getString(5));
                product.setSupplierContact(cursor.getString(6));
            }



        }

        return product;
    }
    public Uri getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId);
    }
}
