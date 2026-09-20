package com.nexzatech.expenses;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddIncomAndExpense extends AppCompatActivity {

    EditText etAmount, etReason;
    TextView tvPageTitle;
    Button btnSaveTransaction;

    public static boolean EXPANSE=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_incom_and_expense);

        tvPageTitle = findViewById(R.id.tvPageTitle);
        etAmount = findViewById(R.id.etAmount);
        etReason = findViewById(R.id.etReason);
        btnSaveTransaction = findViewById(R.id.btnSaveTransaction);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        tvPageTitle.setText("Add New "+ (EXPANSE ? "Expense" : "Income" ));

        // Action
        btnSaveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amount = etAmount.getText().toString().trim();
                String reason = etReason.getText().toString().trim();

                // ১. প্রথমেই চেক করুন ইনপুট ফিল্ডগুলো খালি কি না (String অবস্থায়)
                if (amount.isEmpty() || reason.isEmpty()) {
                    Toast.makeText(AddIncomAndExpense.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                    return; // এখানেই কোড থেমে যাবে, নিচে আর যাবে না
                }

                // ২. ফিল্ড খালি না থাকলে এবার নিরাপদে String থেকে Double-এ কনভার্ট করুন
                double dblAmount;
                try {
                    dblAmount = Double.parseDouble(amount);
                } catch (NumberFormatException e) {
                    Toast.makeText(AddIncomAndExpense.this, "Invalid number format!", Toast.LENGTH_SHORT).show();
                    return;
                }



                // ৩. ডাটাবেজে ডাটা ইনসার্ট করা
                boolean isSuccess;
                if(EXPANSE){
                    isSuccess = dbHelper.addExpanse(dblAmount, reason);
                }else {
                    isSuccess = dbHelper.addIncome(dblAmount, reason);
                }


                if (isSuccess) {
                    Toast.makeText(AddIncomAndExpense.this, "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
                    // ডাটা সেভ হওয়ার পর ইনপুট ফিল্ডগুলো খালি করে দেওয়া ভালো
                    etAmount.setText("");
                    etReason.setText("");
                } else {
                    Toast.makeText(AddIncomAndExpense.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    // Back Press Active

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}