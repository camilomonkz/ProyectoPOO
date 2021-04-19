package com.example.proyectopoo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBAdmin extends  SQLiteOpenHelper{

    // Constructor de la base de datos
    public DBAdmin(Context context) {
        super(context, "DataBaseUsers.db", null, 1);
    }

    //Se crea la tabla y las columnas
    @Override
    public void onCreate(SQLiteDatabase DataBaseUsers) {
        //la tabla users tiene las siguentes columnas (id, username, fullname, password, typeofuser)
        //la columna "typeofuser" guarda si el usuario es un cliente o una tienda
        DataBaseUsers.execSQL("create table users(id integer primary key autoincrement, username text, fullname text, password text, typeofuser text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DataBaseUsers, int oldVersion, int newVersion) {
        DataBaseUsers.execSQL("drop table if exists users");
        onCreate(DataBaseUsers);
    }
    //Metodo para verificar si un usuario ya está en la base de datos
    public boolean CheckUser(String username){

        SQLiteDatabase DataBaseUsers = this.getWritableDatabase(); //abrir la base de datos lectura-escritura

        String[] args = {username};

        Cursor cursor = DataBaseUsers.rawQuery("select * from users where username=?",args); //El cursor almacena los datos relasionados al nombre de usuario
        //Si el cursor contiene datos, entonces el nombre de usuario existe dentro de la tabla.
        if(cursor.moveToFirst()){
            return false; //retorna falso si existe
        }else{
            return true; // retorna verdadero si no existe
        }
    }

    //Metodo para verificar si el nombre de usurio y la contraseña coincide.
    public String CheckUserPass(String username,String password){

        SQLiteDatabase DataBaseUsers = this.getWritableDatabase(); //Se abre la base de datos en lectura-escritura

        String[] selectionargs = {username, password};
        //el cursor guarda el dato guardado en "typeofuser" relacionado al nombre de usuario y a la contraseña
        //Si la contraseña y el nombre de usuario no coinciden entonces el cursor no almacena nada
        Cursor cursor = DataBaseUsers.rawQuery("select typeofuser from users where username =? and password =?",selectionargs);

        String type = "";
        if(cursor.moveToFirst()){
            type = cursor.getString(0); //retorna el tipo de usuario ("cliente" o "tienda")
            return type;
        }else{
            return type; //retorna una cadena vacia si el usuario y la contraseña no coinciden
        }
    }
    //Metodo para guardar valores en la base de datos (registrase)
    public boolean SingUp(String username, String password,String fullname, String typeofuser){

        SQLiteDatabase DataBaseUsers = this.getWritableDatabase(); //abrir la base de datos lectura-escritura

        ContentValues values = new ContentValues(); //se crea un objeto ContentValues

        //El Objeto guarda los valores en una llave (key, value)
        //las llaves tienen el mismo nombre que las columnas en la base de datos
        values.put("username",username);
        values.put("password",password);
        values.put("fullname",fullname);
        values.put("typeofuser",typeofuser);

        long result = DataBaseUsers.insert("users",null, values); // se insertan los datos, esto devuelve un valor numerico

        if(result == -1){
            return false; //si ese valor es -1, entonces la base de datos no guardo ningun dato (retorna falso)
        }else{
            return true; //si el valor es diferente de -1, entonces si se almacenaron los datos (retorna true)
        }
    }
}
