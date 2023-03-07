package com.example.pacrat_good_empty;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CollectionDatabase extends SQLiteOpenHelper {
    public static final int Database_Version = 1;
    public static final String DATABASE_NAME = "collectionDatabase.db";
    private static CollectionDatabase database;
    public static final String TABLE_NAME = "collectionDetails";
    public static final String NAME = "NAME";
    public static final String RELEASED = "Date Released";
    public static final String PURCHASED = "Date Purchased";
    public static final String PHOTO = "PHOTO";
    private static final String ID = "ID";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static int counter = 0;
    private static Bitmap map;

    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    public CollectionDatabase(Context context) {

        super(context, DATABASE_NAME, null, Database_Version);


    }

    public static CollectionDatabase instanceOfDatabase(Context context) {
        if (database == null) {
            database = new CollectionDatabase(context);

        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql;
        sql = new StringBuilder().append("CREATE TABLE")

                .append(TABLE_NAME)
                .append("(")
                .append(ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT , ")
                .append(counter)
                .append(" INT , ")
                .append(NAME)
                .append(" Text , ")
                .append(RELEASED)
                .append(" Date , ")
                .append(PURCHASED)
                .append(" Date , ")
                .append(DESCRIPTION)
                .append(" TEXT , ")
                .append(PHOTO)
                .append("BLOB)");
        db.execSQL(sql.toString());
    }

    public void addNewItem(String name ,String released , String purchased , String description , String map ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();

        val.put(NAME ,  name);
        val.put(PURCHASED , purchased);
        val.put(RELEASED,released);
        val.put(DESCRIPTION,description);
        val.put(PHOTO , map);

        db.insert(TABLE_NAME,null,val);

        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        switch (oldVersion) {
//            case 1:
//                db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT ");
//            case 2:
//                db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT ");
//        }


    }



}
