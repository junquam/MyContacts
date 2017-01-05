package com.junqua.mycontacts.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.junqua.mycontacts.database.ContactContract;
import com.junqua.mycontacts.database.DBHelper;

/**
 * Created by Mathieu Junqua on 2017-01-05.
 */

public class ContactsProvider extends ContentProvider {
    public static final String LOG_TAG = "MY TAG";

    private DBHelper mDbHelper;

    private static final int CONTACTS = 100;
    private static final int CONTACT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ContactContract.CONTENT_AUTHORITY_CONTACT, ContactContract.PATH_CONTACTS, CONTACTS);
        sUriMatcher.addURI(ContactContract.CONTENT_AUTHORITY_CONTACT, ContactContract.PATH_CONTACTS + "/#", CONTACT_ID);
    }

    @Override
    public boolean onCreate(){
        mDbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c;
        int match = sUriMatcher.match(uri);

        switch (match){
            case CONTACTS :
                c = db.query(ContactContract.ContactsVariables.CONTACTS, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CONTACT_ID :
                selection = ContactContract.ContactsVariables.CONTACT_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                c = db.query(ContactContract.ContactsVariables.CONTACTS, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default :
                throw new IllegalArgumentException("Cannot query unknown URI : " + uri);
        }

        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues){
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTACTS:
                return insertContact(uri, contentValues);
            default :
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertContact(Uri uri, ContentValues contentValues){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newId = db.insert(ContactContract.ContactsVariables.CONTACTS, null, contentValues);

        if (newId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, newId);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTACTS:
                return updateContact(contentValues, selection, selectionArgs);
            case CONTACT_ID:

                selection = ContactContract.ContactsVariables.CONTACT_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateContact(contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateContact(ContentValues contentValues, String selection, String[] selectionArgs){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int nbAffected = db.update(ContactContract.ContactsVariables.CONTACTS, contentValues, selection, selectionArgs);
        return nbAffected;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTACTS:
                return deleteContact(selection, selectionArgs);
            case CONTACT_ID:
                selection = ContactContract.ContactsVariables.CONTACT_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return deleteContact(selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Delete is not supported for " + uri);
        }
    }

    public int deleteContact(String selection, String[] selectionArgs){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int nbAffected = db.delete(ContactContract.ContactsVariables.CONTACTS, selection, selectionArgs);
        return nbAffected;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTACTS:
                return ContactContract.ContactsVariables.CONTENT_CONTACTS_TYPE;
            case CONTACT_ID:
                return ContactContract.ContactsVariables.CONTENT_CONTACT_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
