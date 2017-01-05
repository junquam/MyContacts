package com.junqua.mycontacts.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.junqua.mycontacts.R;
import com.junqua.mycontacts.database.ContactContract;
import com.junqua.mycontacts.models.Contact;

/**
 * Created by Mathieu Junqua on 2017-01-05.
 */

public class EditingDetailActivity extends MasterDetail {

    private EditText name;
    private EditText number;
    private EditText email;
    private EditText address;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_editing_detail;
    }
    @Override
    protected int getLayoutMenuResourceId() {
        return R.menu.editing_detail_menu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Editing " + contact.getName());
    }

    @Override
    protected void populateView(){
        name = (EditText) findViewById(R.id.contact_name);
        name.setText(contact.getName());

        number = (EditText) findViewById(R.id.contact_number);
        number.setText(contact.getNumber());

        email = (EditText) findViewById(R.id.contact_email);
        email.setText(contact.getEmail());

        address = (EditText) findViewById(R.id.contact_address);
        address.setText(contact.getAddress());
    }

    private void saveData(){
        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactsVariables.CONTACT_NAME, name.getText().toString());
        values.put(ContactContract.ContactsVariables.CONTACT_NUMBER, number.getText().toString());
        values.put(ContactContract.ContactsVariables.CONTACT_EMAIL, email.getText().toString());
        values.put(ContactContract.ContactsVariables.CONTACT_ADDRESS, address.getText().toString());

        getContentResolver().update(
                Uri.withAppendedPath(ContactContract.ContactsVariables.CONTACTS_URI, String.valueOf(contact.getId())),
                values, null,null);

        CharSequence savedMessage = name.getText().toString() + " Has been saved";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, savedMessage, duration);
        toast.show();

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save:
                saveData();
                return true;
            case R.id.cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
