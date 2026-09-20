package com.nexzatech.expenses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvBalance, tvExpenseAmount, tvIncomeAmount;

    Button btnAddExpense, btnShowExpense, btnAddIncome, btnShowIncome;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBalance = findViewById(R.id.tvBalance);
        tvExpenseAmount = findViewById(R.id.tvExpenseAmount);
        tvIncomeAmount = findViewById(R.id.tvIncomeAmount);
        // Button
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnShowExpense = findViewById(R.id.btnShowExpense);
        btnAddIncome = findViewById(R.id.btnAddIncome);
        btnShowIncome = findViewById(R.id.btnShowIncome);
        dbHelper = new DatabaseHelper(this);

        // Add Expense
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddIncomAndExpense.EXPANSE=true;
                startActivity(new Intent(MainActivity.this, AddIncomAndExpense.class));
            }
        });

        // Add Income
        btnAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddIncomAndExpense.EXPANSE=false;
                startActivity(new Intent(MainActivity.this, AddIncomAndExpense.class));
            }
        });

        // Expense History
        btnShowExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                History.IS_EXPANSE= true;
                startActivity(new Intent(MainActivity.this, History.class));
            }
        });

        // Income History
        btnShowIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                History.IS_EXPANSE= false;
                startActivity(new Intent(MainActivity.this, History.class));
            }
        });


        updateState();
    }


    // State Update
    public void updateState(){
        tvExpenseAmount.setText("৳ "+dbHelper.totalExpanse());
        tvIncomeAmount.setText("৳ "+dbHelper.totalIncome());
        double total = dbHelper.totalIncome() - dbHelper.totalExpanse();
        tvBalance.setText("৳ "+total);

        if(total < 0){
            tvBalance.setTextColor(Color.parseColor("#F44336"));
        }else{

            tvBalance.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        updateState();

    }


//===================================================================
}