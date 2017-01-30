package com.example.harish.hw2;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static String EXPENSE_KEY="EXPENSE";
    ArrayList<Expense> expenseList=new ArrayList<>();
    private static int REQ_CODE=100;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    //Expense expense = new Expense(); //set parameters
                    intent.putExtra(EXPENSE_KEY, expenseList);

                    startActivityForResult(intent,REQ_CODE);


            }


        });

        findViewById(R.id.button_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                //Expense expense = new Expense(); //set parameters
                intent.putExtra(EXPENSE_KEY, expenseList);
                startActivityForResult(intent,REQ_CODE);


            }


        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                //Expense expense = new Expense(); //set parameters
                intent.putExtra(EXPENSE_KEY, expenseList);
                startActivityForResult(intent,REQ_CODE);


            }


        });

        findViewById(R.id.button_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                //Expense expense = new Expense(); //set parameters
                if(expenseList.size()>0) {
                    intent.putExtra(EXPENSE_KEY, expenseList);
                    startActivityForResult(intent, REQ_CODE);
                }
                else {
                    Toast.makeText(getBaseContext(),"No expenses to show!",Toast.LENGTH_LONG).show();
                }


            }


        });

        findViewById(R.id.button_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

               if (resultCode == RESULT_OK) {
                   /*Expense expense = (Expense) data.getExtras().getSerializable(MainActivity.EXPENSE_KEY);
                   expenseList.add(expense);*/
                   expenseList = (ArrayList<Expense>) data.getExtras().getSerializable(MainActivity.EXPENSE_KEY);
                   for (Expense e : expenseList) {
                       Log.d("demo", e.getName());
                   }
               }

   }

}
