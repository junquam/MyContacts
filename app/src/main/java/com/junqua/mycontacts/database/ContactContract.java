package com.junqua.mycontacts.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mathieu Junqua on 2017-01-05.
 */

public class ContactContract {

    private ContactContract(){}

    public static final String CONTENT_AUTHORITY_CONTACT = "com.junqua.mycontacts.contacts";
    public static final Uri BASE_CONTENT_URI_CONTACT = Uri.parse("content://" + CONTENT_AUTHORITY_CONTACT);
    public static final String PATH_CONTACTS = "contacts";

    public static class ContactsVariables implements BaseColumns {

        public static final Uri CONTACTS_URI = Uri.withAppendedPath(BASE_CONTENT_URI_CONTACT, PATH_CONTACTS);

        public static final String CONTACTS = "Contacts";
        public static final String CONTACT_ID = "_id";
        public static final String CONTACT_NAME = "_name";
        public static final String CONTACT_NUMBER = "_number";
        public static final String CONTACT_PICTURE = "_picture";
        public static final String CONTACT_ADDRESS = "_address";
        public static final String CONTACT_EMAIL = "_email";

        public static final String CONTENT_CONTACTS_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY_CONTACT + "/" + PATH_CONTACTS;

        public static final String CONTENT_CONTACT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY_CONTACT + "/" + PATH_CONTACTS;
    }
}
