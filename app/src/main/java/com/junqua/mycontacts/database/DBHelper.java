package com.junqua.mycontacts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mathieu Junqua on 2017-01-05.
 */

public class DBHelper  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyContacts.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";

    private static final String SQL_CREATE_CONTACTS =
            "create table " + ContactContract.ContactsVariables.CONTACTS
                    + "(" + ContactContract.ContactsVariables.CONTACT_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT, "
                    + ContactContract.ContactsVariables.CONTACT_NAME + TEXT_TYPE + " not null, "
                    + ContactContract.ContactsVariables.CONTACT_NUMBER + TEXT_TYPE + ", "
                    + ContactContract.ContactsVariables.CONTACT_ADDRESS + TEXT_TYPE + ", "
                    + ContactContract.ContactsVariables.CONTACT_EMAIL + TEXT_TYPE + ", "
                    + ContactContract.ContactsVariables.CONTACT_PICTURE + TEXT_TYPE
                    +");";

    private static final String SQL_DELETE_CONTACTS =
            "DROP TABLE IF EXISTS " + ContactContract.ContactsVariables.CONTACTS;

    private static final String INSERT_DUMMY_CONTACT =
            "INSERT INTO " + ContactContract.ContactsVariables.CONTACTS
                    + "(" + ContactContract.ContactsVariables.CONTACT_NAME + ","
                    + ContactContract.ContactsVariables.CONTACT_NUMBER + ","
                    + ContactContract.ContactsVariables.CONTACT_ADDRESS + ","
                    + ContactContract.ContactsVariables.CONTACT_EMAIL + ") "
                    + " VALUES ( " + "'Mathieu',"
                    + "'+1 (647) 869 - 6625',"
                    + "'1054 Dovercourd RD, Toronto',"
                    + "'mathieu.junqua@gmail.com'"
                    +");";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){

        db.execSQL(SQL_CREATE_CONTACTS);
        db.execSQL(INSERT_DUMMY_CONTACT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_CONTACTS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
