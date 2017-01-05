package com.junqua.mycontacts.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.junqua.mycontacts.R;
import com.junqua.mycontacts.database.ContactContract;
import com.junqua.mycontacts.models.Contact;

/**
 * Created by Mathieu Junqua on 2017-01-05.
 */

public class DetailActivity extends MasterDetail {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail;
    }
    @Override
    protected int getLayoutMenuResourceId() {
        return R.menu.detail_menu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(contact.getName());
    }

    @Override
    protected void populateView(){
        TextView name = (TextView) findViewById(R.id.contact_name);
        name.setText(contact.getName());

        TextView number = (TextView) findViewById(R.id.contact_number);
        number.setText(contact.getNumber());

        TextView address = (TextView) findViewById(R.id.contact_address);
        address.setText(contact.getAddress());

        TextView email = (TextView) findViewById(R.id.contact_email);
        email.setText(contact.getEmail());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(this, EditingDetailActivity.class);
                i.putExtra("CONTACT_ID", contactId);
                startActivity(i);
                return true;
            case R.id.delete:
                deleteContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteContact(){
        getContentResolver().delete(
                Uri.withAppendedPath(ContactContract.ContactsVariables.CONTACTS_URI, String.valueOf(contact.getId())),
                null,
                null
        );

        CharSequence deletedMessage = contact.getName() + " has been deleted";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, deletedMessage, duration);
        toast.show();

        finish();
    }
}
