package com.junqua.mycontacts.activities;

import android.content.ContentUris;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.junqua.mycontacts.R;
import com.junqua.mycontacts.adapters.ContactAdapter;
import com.junqua.mycontacts.database.ContactContract;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * Created by Mathieu Junqua on 2017-01-05.
 */

public class HomeActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<Cursor>{

    ContactAdapter mContactsAdapter;
    private static final int CARS_LOADER = 0;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(R.string.title);

        mContactsAdapter = new ContactAdapter(this, null, 0);

        getSupportLoaderManager().initLoader(CARS_LOADER, null, this);

        setList();

        setAddButton();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Uri contactsUri = ContactContract.ContactsVariables.CONTACTS_URI;

        return new CursorLoader(this,
                contactsUri,
                null,
                null,
                null,
                ContactContract.ContactsVariables.CONTACT_NAME);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mContactsAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        mContactsAdapter.swapCursor(null);
    }

    private void setList(){
        final ListView listView = (ListView) findViewById(R.id.listview_cars);
        listView.setAdapter(mContactsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                //get contact id
                Cursor cur = mContactsAdapter.getCursor();
                cur.moveToPosition(position);
                final int contactId = cur.getInt(cur.getColumnIndexOrThrow(ContactContract.ContactsVariables.CONTACT_ID));

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("CONTACT_ID", contactId);
                startActivity(intent);
            }
        });
    }

    private void setAddButton(){

        addButton = (ImageButton) findViewById(R.id.addContact);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(view.getContext());
                View mView = layoutInflaterAndroid.inflate(R.layout.add_contact_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(view.getContext());
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.newContactName);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                addContact(userInputDialogEditText.getText().toString());
                            }
                        })

                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                alertDialogAndroid.show();
            }
        });
    }

    private void addContact(String name){

        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactsVariables.CONTACT_NAME, name);
        Uri uri = getContentResolver().insert(ContactContract.ContactsVariables.CONTACTS_URI, values);
        int newId = (int) ContentUris.parseId(uri);

        getSupportLoaderManager().restartLoader(0, null, this);

        Intent intent = new Intent(getApplicationContext(), EditingDetailActivity.class);
        intent.putExtra("CONTACT_ID", newId);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        getSupportLoaderManager().restartLoader(0, null, this);
    }
}
