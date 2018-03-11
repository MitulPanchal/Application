package com.example.mitul.application;

public class UserInfo {

    private int _id;
    private String _name;
    private String _email;
    private String _password;

    public UserInfo(){
        //Empty Constructor
    }

    public UserInfo(int id, String name, String email, String password){
        this._id = id;
        this._name = name;
        this._email = email;
        this._password  = password;
    }

    public UserInfo(String name, String email, String password){
        this._name = name;
        this._email = email;
        this._password  = password;
    }

    //getting and setting ID
    public int get_id(){
        return this._id;
    }

    public void set_id(int id){
        this._id = id;
    }

    //getting and setting name
    public String get_name(){
        return this._name;
    }

    public void set_name(String name){
        this._name = name;
    }

    //getting and setting email
    public String get_email(){
        return this._email;
    }

    public void set_email(String email){
        this._email = email;
    }

    //getting and setting password
    public String get_password(){
        return this._password;
    }

    public void set_password(String password){
        this._password = password;
    }
}
