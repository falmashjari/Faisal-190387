package com.example.faisal_190387;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBAdapter extends SQLiteOpenHelper {
    public static final String KEY_ID = "user_id";
    public static final String KEY_NAME = "first_name";
    public static final String KEY_LAST = "last_name";
    public static final String KEY_MOBILE = "user_mobile";
    public static final String KEY_EMAIL = "user_email";

    private static final String DATABASE_NAME = "searchhome";
    private static final String DATABASE_MARKSTABLE = "register";
    private static final String DATABASE_UPLOADDATA = "userdata";
    private static final String DATABASE_CITY = "city";
    private static final int DATABASE_VERSION = 1;
    public static String updatecomment = "";
    public static int userid = 0;
    public static ArrayList<String> city;
    public static ArrayList<String> area;
    static String name = "users.sqlite";
    static String path = "";
    static ArrayList<userData> a;
    static SQLiteDatabase sdb;


    private DBAdapter(Context v) {

        super(v, name, null, 1);
        path = "/data/data/" + v.getApplicationContext().getPackageName()
                + "/databases";
    }

    public static synchronized DBAdapter getDBAdapter(Context v) {
        return (new DBAdapter(v));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + DATABASE_MARKSTABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY  NOT NULL  UNIQUE, " +
                KEY_NAME + " VARCHAR NOT NULL, " +
                KEY_LAST + " VARCHAR NOT NULL, " +
                KEY_MOBILE + " VARCHAR NOT NULL, " +
                KEY_EMAIL + " VARCHAR NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    public boolean checkDatabase() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(path + "/" + name, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
        }
        if (db == null) {
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public void createDatabase(Context v) {
        this.getReadableDatabase();
        try {
            InputStream myInput = v.getAssets().open(name);
            // Path to the just created empty db
            String outFileName = path + "/" + name;
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] bytes = new byte[1024];
            int length;
            while ((length = myInput.read(bytes)) > 0) {
                myOutput.write(bytes, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();

            InputStream is = v.getAssets().open("users.sqlite");
            System.out.println(new File(path + "/" + name).getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(path + "/" + name);
            int num = 0;
            while ((num = is.read()) > 0) {
                fos.write((byte) num);
            }
            fos.close();
            is.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void openDatabase() {
        try {
            sdb = SQLiteDatabase.openDatabase(path + "/" + name, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertDB(String firstname, String lastname, String mobile, String email) {
        ContentValues cv = new ContentValues();
        cv.put("first_name", firstname);
        cv.put("last_name", lastname);
        cv.put("user_mobile", mobile);
        cv.put("user_email", email);

        sdb.insert("register", null, cv);
    }

    public ArrayList<userData> getUserData() {
        try {
            Cursor c1 = sdb.rawQuery("select * from register ", null);
            a = new ArrayList<userData>();

            while (c1.moveToNext()) {
                userData q1 = new userData();
                q1.setFirstName(c1.getString(1));
                q1.setLastName(c1.getString(2));
                q1.setEmail(c1.getString(4));
                q1.setPhone(c1.getString(3));
                a.add(q1);
            }
            return a;
        } catch (Exception e) {
            Log.d("DBAdapter", e + "");
        }
        return a;
    }

    public void Updateime(int id, String stu) {
        sdb.execSQL("UPDATE photoDetails set status='" + stu + "' WHERE id='" + id + "'");
    }

    public void UpdateUser(String firstName, String lastName, String mob, String email) {
        sdb.execSQL("UPDATE register set first_name='" + firstName + "' , last_name='" + lastName + "' , user_mobile='" + mob + "' WHERE user_email='" + email + "'");
    }

    public void DeleteUser(String email) {
        try {
            sdb.execSQL("DELETE FROM register WHERE user_email='" + email + "'");
        } catch (Exception e) {
            Log.d("Delete Query", e + "");
        }
    }


}
