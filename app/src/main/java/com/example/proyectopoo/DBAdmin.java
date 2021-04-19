package com.example.proyectopoo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBAdmin extends  SQLiteOpenHelper{
    private static final String DB_NAME = "DataBaseUsers.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "users";


    public DBAdmin(Context context) {
        super(context, "DataBaseUsers.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DataBaseUsers) {
        DataBaseUsers.execSQL("create table users(id integer primary key autoincrement, username text, fullname text, password text, typeofuser text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DataBaseUsers, int oldVersion, int newVersion) {
        DataBaseUsers.execSQL("drop table if exists users");
        onCreate(DataBaseUsers);
    }
    public boolean CheckUser(String username){

        SQLiteDatabase DataBaseUsers = this.getWritableDatabase();

        String[] args = {username};

        Cursor cursor = DataBaseUsers.rawQuery("select * from users where username=?",args);

        if(cursor.moveToFirst()){
            return false;
        }else{
            return true;
        }
    }

    public String CheckUserPass(String username,String password){

        SQLiteDatabase DataBaseUsers = this.getWritableDatabase();

        String[] selectionargs = {username, password};

        Cursor cursor = DataBaseUsers.rawQuery("select typeofuser from users where username =? and password =?",selectionargs);

        String type = "";
        if(cursor.moveToFirst()){
            type = cursor.getString(0);
            return type;
        }else{
            return type;
        }
    }

    public boolean SingUp(String username, String password,String fullname, String typeofuser){

        SQLiteDatabase DataBaseUsers = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("username",username);
        values.put("password",password);
        values.put("fullname",fullname);
        values.put("typeofuser",typeofuser);

        long result = DataBaseUsers.insert("users",null, values);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}
