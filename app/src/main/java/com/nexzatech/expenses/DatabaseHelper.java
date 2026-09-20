package com.nexzatech.expenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "digital_expense";
    public static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createExpenseTableSQL = "CREATE TABLE expense (id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, reason TEXT, time DOUBLE)";
        String createIncomeTableSQL = "CREATE TABLE income (id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, reason TEXT, time DOUBLE)";

        sqLiteDatabase.execSQL(createExpenseTableSQL);
        sqLiteDatabase.execSQL(createIncomeTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS expense");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS income");

        onCreate(sqLiteDatabase); // CRITICAL: Recreate the table after dropping it
    }

    // Here Personal Function Start =============================================================================================

    // Expanse
    public boolean addExpanse(double amount, String reason){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conVal = new ContentValues();

        conVal.put("amount", amount);
        conVal.put("reason", reason);
        conVal.put("time", System.currentTimeMillis());

        long result  = db.insert("expense", null, conVal);

        return result != -1;
    }

    // Total Expanse
    public double totalExpanse(){

        SQLiteDatabase database = this.getReadableDatabase();

        double totalExpanse = 0;
        String query = "SELECT * FROM expense";
        Cursor cursor =  database.rawQuery(query, null);

        if(cursor != null & cursor.getCount() > 0){
            while (cursor.moveToNext()){
                totalExpanse += cursor.getDouble(1);
            }
        }

        return totalExpanse;
    }

    // Income
    public boolean addIncome(double amount, String reason){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conVal = new ContentValues();

        conVal.put("amount", amount);
        conVal.put("reason", reason);
        conVal.put("time", System.currentTimeMillis());

        long result  = db.insert("income", null, conVal);

        return result != -1;
    }

    // Total Income
    public double totalIncome(){

        SQLiteDatabase database = this.getReadableDatabase();

        double totalIncome = 0;
        String query = "SELECT * FROM income";
        Cursor cursor =  database.rawQuery(query, null);

        if(cursor != null & cursor.getCount() > 0){
            while (cursor.moveToNext()){
                totalIncome += cursor.getDouble(1);
            }
        }

        return totalIncome;
    }

    // History
    public Cursor allHistory(boolean IS_EXPANSE){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+(IS_EXPANSE ? "expense" : "income") +" ORDER BY id DESC";

        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    // Delete History
    public boolean deleteHistory(boolean IS_EXPANSE, int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String sql = "DELETE FROM "+ (IS_EXPANSE ? "expense" : "income") +" WHERE id = " + id;
            db.execSQL(sql);

            return true;
        } catch (Exception e) {

            return false;
        }
    }











    // Here Personal Function End =============================================================================================
}
