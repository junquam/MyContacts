package com.junqua.mycontacts.models;

/**
 * Created by Mathieu Junqua on 2017-01-05.
 */

public class Contact {

    private int _id;
    private String _name;
    private String _number;
    private String _picture;
    private String _address;
    private String _email;

    public int getId() { return _id; }
    public void setId(int id) { this._id = id; }

    public String getName() { return _name; }
    public void setName(String name) { this._name = name; }

    public String getNumber() { return _number; }
    public void setNumber(String number) { this._number = number; }

    public String getPicture() { return _picture; }
    public void setPicture(String picture) { this._picture = picture; }

    public String getAddress() { return _address; }
    public void setAddress(String address) { this._address = address; }

    public String getEmail() { return _email; }
    public void setEmail(String email) { this._email = email; }
}
