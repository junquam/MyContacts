package com.junqua.mycontacts.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.junqua.mycontacts.R;
import com.junqua.mycontacts.activities.HomeActivity;
import com.junqua.mycontacts.database.ContactContract;

/**
 * Created by Mathieu Junqua on 2017-01-05.
 */


public class ContactAdapter extends CursorAdapter{
    
    private Context context;

    public ContactAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_contacts, parent, false);

        this.context = context;
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView)view.findViewById(R.id.list_item_contact_name);
        name.setText(cursor.getString(cursor.getColumnIndex(ContactContract.ContactsVariables.CONTACT_NAME)));

    }
}
