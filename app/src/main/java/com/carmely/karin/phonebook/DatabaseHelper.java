package com.carmely.karin.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "phoneBookManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE if not exists " + TABLE_CONTACTS + "(" +KEY_NAME + " TEXT," +
                KEY_PHONE + " TEXT PRIMARY KEY" + ")" ;

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i , int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
    }

    public void upDate(String newNumber, String oldNumber, String name)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(KEY_PHONE, newNumber);
        args.put(KEY_NAME, name);


        db.update(TABLE_CONTACTS, args, KEY_PHONE + "=?" , new String[] {oldNumber});
        db.close();
    }

    void addContact(Contact contact)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, contact.getName());
        contentValues.put(KEY_PHONE, contact.getNumber());

        db.insert(TABLE_CONTACTS, null, contentValues);
        db.close();

    }

    public void deleteContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_PHONE  + "=?", new String[]{String.valueOf(contact.getNumber())});
        db.close();


    }

    public List<Contact> getAllContacts()
    {
        List<Contact> contactList = new ArrayList<>();
        String selectQuery = "SELECT *FROM "  + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do {
                Contact contact = new Contact();
                contact.setName(cursor.getString(0));
                contact.setNumber(cursor.getString(1));
                contactList.add(contact);

            }

            while (cursor.moveToNext());
        }

        return contactList;

    }

}
