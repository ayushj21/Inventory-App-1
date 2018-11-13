package com.example.ayush_l15fue5.inventoryappfinal.DataBaseFunctions;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String DATABASE_NAME = "ProductInventory";
    // Database Version
    public static final int DATABASE_VERSION = 1;

    public class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "Info";
        public static final String COLUMN_PRODUCT_ID = "PId";
        public static final String COLUMN_PRODUCT_NAME = "PName";
        public static final String COLUMN_PRODUCT_IMAGE = "PPic";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_STOCK = "availableStock";
        public static final String COLUMN_SUPPLIER_NAME = "supplierName";
        public static final String COLUMN_SUPPLIER_CONTACT = "supplierContactInfo";
    }

}
