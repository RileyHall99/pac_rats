package com.example.pacrat_good_empty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class CollectionDatabase extends SQLiteOpenHelper {
    public static final int Database_Version = 1;
    public static final String DATABASE_NAME = "collectionDatabase.db";
    private static CollectionDatabase database;
    public static final String TABLE_NAME = "collectionDetails";
    public static final String NAME = "NAME";
    public static final String RELEASED = "Date_Released";
    public static final String PURCHASED = "Date_Purchased";
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
//        sql = new StringBuilder().append("CREATE TABLE")
//
//                .append(TABLE_NAME)
//                .append("(")
//                .append(ID)
//                .append(" INTEGER PRIMARY KEY AUTOINCREMENT , ")
//                .append(counter)
//                .append(" INT , ")
//                .append(NAME)
//                .append(" Text , ")
//                .append(RELEASED)
//                .append(" Date , ")
//                .append(PURCHASED)
//                .append(" Date , ")
//                .append(DESCRIPTION)
//                .append(" TEXT , ")
//                .append(PHOTO)
//                .append("BLOB)");
//        db.execSQL(sql.toString());
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, "
                + PURCHASED + " DATE, "
                + RELEASED + " TEXT, "
                + PHOTO + " BLOB,"
                + DESCRIPTION + " TEXT)";
        db.execSQL(query);

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

    public ArrayList<Dictionary> readFromDB(){
        ArrayList <Dictionary> list = new ArrayList<Dictionary>();
        Dictionary <String , String> dict = new Hashtable<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * FROM " + TABLE_NAME , null);

        if(cursor.moveToFirst()) {
            do{
                dict.remove(dict.keys());
                dict.put("Name" , cursor.getString(1));
                dict.put("Purchased" , cursor.getString(2));
                dict.put("Released" , cursor.getString(3));
                dict.put("Desc" , cursor.getString(4));
                dict.put("photo" , cursor.getString(5));
                list.add(dict);
            }while (cursor.moveToNext());
        }

        return list;
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
