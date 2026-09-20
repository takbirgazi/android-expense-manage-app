package com.nexzatech.expenses;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class History extends AppCompatActivity {

    ListView historyList;
    DatabaseHelper dbHelper;

    TextView tvTime, tvAmount, tvNoteItem, tvTotalAmount, tvTotalTransactions, tvRecentTitle;
    ImageView btnDelete;
    public static boolean IS_EXPANSE=true;

    ArrayList<HashMap<String, String>> arrayList;
    HashMap<String, String> hashmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyList = findViewById(R.id.historyList);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTotalTransactions = findViewById(R.id.tvTotalTransactions);
        tvRecentTitle = findViewById(R.id.tvRecentTitle);
        dbHelper = new DatabaseHelper(this);

        loadData();

    }


    // For Multiple time use =================
    public void loadData(){
        Cursor cursor = dbHelper.allHistory(IS_EXPANSE);
        if(cursor != null && cursor.getCount() > 0){

            tvTotalTransactions.setText(""+ cursor.getCount());
            tvTotalAmount.setText("৳ "+ (IS_EXPANSE ? dbHelper.totalExpanse() : dbHelper.totalIncome()));

            arrayList = new ArrayList<>();
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                double amount = cursor.getDouble(1);
                String reason = cursor.getString(2);
                double time = cursor.getDouble(3);

                hashmap = new HashMap<>();

                hashmap.put("id", Integer.toString(id));
                hashmap.put("amount", Double.toString(amount));
                hashmap.put("reason", reason.toString());
                hashmap.put("time", Double.toString(time));

                arrayList.add(hashmap);

            }

            historyList.setAdapter(new MyAdapter());


        }else {
            arrayList = new ArrayList<>();
            historyList.setAdapter(null);

            tvTotalTransactions.setText("0");
            tvTotalAmount.setText("৳ 0");

            tvRecentTitle.setText("No Data Found!");
            tvRecentTitle.setGravity(Gravity.CENTER);
            tvRecentTitle.setPadding(0,100,0,0);
        }

    }
    // ==================

    // Adapter
    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = getLayoutInflater();
            View myView =  inflater.inflate(R.layout.list,viewGroup ,false);

            btnDelete = myView.findViewById(R.id.btnDelete);
            tvTime = myView.findViewById(R.id.tvDate);
            tvAmount = myView.findViewById(R.id.tvAmount);
            tvNoteItem = myView.findViewById(R.id.tvNoteItem);

            hashmap = arrayList.get(i);

            String id = hashmap.get("id");
            String amount = hashmap.get("amount");
            String reason = hashmap.get("reason");
            String time = hashmap.get("time");

            double timestamp = Double.parseDouble(time);

            SimpleDateFormat sdf =
                    new SimpleDateFormat("dd MMM yyyy • hh:mm a");

            String formattedTime =
                    sdf.format(new Date((long) timestamp));

            tvAmount.setText(amount);
            tvNoteItem.setText(reason);
            tvTime.setText(formattedTime);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean deleted = dbHelper.deleteHistory(IS_EXPANSE, Integer.parseInt(id));

                    if(deleted){
                        Toast.makeText(History.this, "Data Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(History.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                    }

                    loadData();
                }
            });

            return myView;
        }
    }



    // ===============================================
}