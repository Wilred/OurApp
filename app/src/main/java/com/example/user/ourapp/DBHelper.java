package com.example.user.ourapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // Класс для создания базы данных,
//    пример заполнения
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="contactDB";//название базы данных
    private static final String TABLE_CONTACTS="contacts"; //Константы для заголовков столбцов таблицы
    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";

    private static final String KEY_MAIL="mail";





     DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
//db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER)");

    @Override //метод для создания БД
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_CONTACTS+ "(" + KEY_ID + " integer primary key autoincrement," + KEY_NAME+ " text,"+ KEY_MAIL+ " text" + ")");
        //запрос создает таблицу

        // метод для чтения и для записи
        // Можно добавить новую запись в таблицу с помощью SQL команды INSERT, а можно воспользоваться методом db.insert:

        SQLiteDatabase db2=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(KEY_ID,1);
        cv.put(KEY_NAME,"Sales");
        db.insert(KEY_MAIL, KEY_ID, cv);



    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACTS);

        onCreate(db);

    }







}
