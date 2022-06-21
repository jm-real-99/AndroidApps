package com.example.quizp1.BD;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.quizp1.SortByRanking;
import com.example.quizp1.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "usuario";
    public static final String NOMBRE = "nombre";
    public static final String PUNTUACION = "puntuacion";


    public DataBase (@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBase (@Nullable Context context) {
        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table usuario(nombre text primary key, puntuacion int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insert(String nombre, String contraseña){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contenido = new ContentValues();
        contenido.put("nombre",nombre);
        contenido.put("puntuacion",0);

        long ins = db.insert("usuario",null,contenido);

        if (ins==1){

            return false;

        }else {

            return true;
        }
    }


    @SuppressLint("Range")
    public Usuario getUsuario(String nombre){

        SQLiteDatabase db = this.getReadableDatabase();

        Usuario user = new Usuario();

        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+nombre+" = ?";

        Cursor cursor = db.rawQuery("Select * from usuario where nombre = '"+nombre+"'",null);

        if (cursor.moveToFirst()){
            user.setNombre(cursor.getString(cursor.getColumnIndex(NOMBRE)));
            user.setPuntuacion(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PUNTUACION))));
        }
        else{
            return null;
        }

        cursor.close();
        return user;
    }

    public boolean insertOne(Usuario user){

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues content = new ContentValues();

        String punt = String.valueOf(user.getPuntuacion());

        content.put(NOMBRE,user.getNombre());
        content.put(PUNTUACION,punt);

        long insert = db.insert(TABLE_NAME, null, content);

        db.close();
        if (insert == -1){
            return false;
        }else{
            return true;
        }

    }

    public int actualizar(Usuario user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        String punt = String.valueOf(user.getPuntuacion());

        content.put(NOMBRE,user.getNombre());
        content.put(PUNTUACION,punt);

        int d = db.update(TABLE_NAME,content,"nombre = '"+user.getNombre()+"'",null);
        db.close();
        return d;

    }




    public List<Usuario> getUserByRanking(){

        List<Usuario> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



        //Deberíamos de obtener un cursos con los usuarios ordenados según la puntuación.
        Cursor cursor = db.rawQuery("Select * from usuario" ,null);

        //Hay que ignorar este error, arriba está la misma sentencia y la da bien. Ya nos aseguramos antes de que el valor introducido es >0
        if(cursor.moveToFirst()){
            do{
                Usuario user = new Usuario();
                user.setNombre(cursor.getString(cursor.getColumnIndex(NOMBRE)));
                user.setPuntuacion(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PUNTUACION))));

                lista.add(user);

            }while(cursor.moveToNext());

        }
        cursor.close();

        //Como el orden dado no está ordenado, ya que la puntuacion es text. Por no hacer el lío de cambiarlo todo de nuevo.
        //Ordenamos el array obtenido mediante Collections.sort
        Collections.sort(lista, new SortByRanking());

        return lista;

    }



}


