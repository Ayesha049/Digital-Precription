package com.ayeshapp.digitalprescription;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String> getQuotes(String s) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select distinct BrandName from alldrugs where BrandName like " + "'%" + s + "%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getGenerics(String s) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select distinct GenericName from alldrugs where GenericName like " + "'%" + s + "%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getGenericNamebyBarnd(String s) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select distinct GenericName from alldrugs where BrandName like " + "'%" + s + "%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getDosagebyBarnd(String s) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select distinct DosageDescription from alldrugs where BrandName like " + "'%" + s + "%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getDosagebyBAndG(String b, String g) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select distinct DosageDescription from alldrugs where BrandName like " + "'%" + b + "%'" + " and GenericName like  " + "'%" + g + "%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getStrengthbyBAndG(String b, String g) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select distinct Strength from alldrugs where BrandName like " + "'%" + b + "%'" + " and GenericName like  " + "'%" + g + "%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getStrengthbyBAndGAndD(String b, String g, String d) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select distinct Strength from alldrugs where BrandName like " + "'%" + b + "%'" + " and GenericName like  " + "'%" + g + "%'" +
                " and DosageDescription like  " + "'%" + d + "%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getStrengthbyBarnd(String s) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select distinct Strength from alldrugs where BrandName like " + "'%" + s + "%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}