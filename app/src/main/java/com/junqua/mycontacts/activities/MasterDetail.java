package com.junqua.mycontacts.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.junqua.mycontacts.R;
import com.junqua.mycontacts.database.ContactContract;
import com.junqua.mycontacts.models.Contact;

/**
 * Created by Mathieu Junqua on 2017-01-05.
 */

public abstract class MasterDetail extends AppCompatActivity {

    protected abstract int getLayoutResourceId();
    protected abstract int getLayoutMenuResourceId();

    protected Contact contact;
    protected int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResourceId());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            contactId = extras.getInt("CONTACT_ID");
        }

        getData();

        populateView();
    }

    protected void getData(){
        String[] projection = {
                ContactContract.ContactsVariables._ID,
                ContactContract.ContactsVariables.CONTACT_NAME,
                ContactContract.ContactsVariables.CONTACT_NUMBER,
                ContactContract.ContactsVariables.CONTACT_PICTURE,
                ContactContract.ContactsVariables.CONTACT_ADDRESS,
                ContactContract.ContactsVariables.CONTACT_EMAIL
        };

        String selection = ContactContract.ContactsVariables._ID + " =? ";
        String[] args = {String.valueOf(contactId)};

        Cursor c = getContentResolver().query(ContactContract.ContactsVariables.CONTACTS_URI, projection, selection, args, null);
        while(c.moveToNext()){
            contact = new Contact();
            contact.setId(c.getInt(c.getColumnIndex(ContactContract.ContactsVariables.CONTACT_ID)));
            contact.setName(c.getString(c.getColumnIndex(ContactContract.ContactsVariables.CONTACT_NAME)));
            contact.setNumber(c.getString(c.getColumnIndex(ContactContract.ContactsVariables.CONTACT_NUMBER)));
            contact.setPicture(c.getString(c.getColumnIndex(ContactContract.ContactsVariables.CONTACT_PICTURE)));
            contact.setAddress(c.getString(c.getColumnIndex(ContactContract.ContactsVariables.CONTACT_ADDRESS)));
            contact.setEmail(c.getString(c.getColumnIndex(ContactContract.ContactsVariables.CONTACT_EMAIL)));
        }
    }

    protected void populateView(){   }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(getLayoutMenuResourceId(), menu);
        return true;
    }
}
