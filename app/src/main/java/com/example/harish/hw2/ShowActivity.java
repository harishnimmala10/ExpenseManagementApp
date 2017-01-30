package com.example.harish.hw2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Harish on 9/14/2016.
 */
public class ShowActivity extends AppCompatActivity{

    ArrayList<Expense> expenses;
    int index=0;
    private TextView name;
    private TextView category;
    private TextView amount;
    private TextView date;

    private ImageView uri;
    String[] expenseCategoryArray={"Groceries","Invoice","Transportation","Shopping","Rent"
            ,"Trips","Utilities","Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        name= (TextView) findViewById(R.id.expense_name);
        category= (TextView) findViewById(R.id.expense_category);
        amount = (TextView) findViewById(R.id.expense_amount);
        date= (TextView) findViewById(R.id.expense_date);
        uri= (ImageView) findViewById(R.id.imageButton5);


        expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable(MainActivity.EXPENSE_KEY);

        ((TextView) findViewById(R.id.expense_name)).setText(expenses.get(index).getName());
        ((TextView) findViewById(R.id.expense_category)).setText(expenseCategoryArray[expenses.get(index).getCategory()]);
        ((TextView) findViewById(R.id.expense_amount)).setText(expenses.get(index).getAmount().toString());
        ((TextView) findViewById(R.id.expense_date)).setText(expenses.get(index).getDate());
         ((ImageView) findViewById(R.id.imageButton5)).setImageURI(Uri.parse(expenses.get(index).getUri()));

        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() { //Change button id
            @Override
            public void onClick(View v) {
                index=0;

                ((TextView) findViewById(R.id.expense_name)).setText(expenses.get(index).getName());
                ((TextView) findViewById(R.id.expense_category)).setText(expenseCategoryArray[expenses.get(index).getCategory()]);
                ((TextView) findViewById(R.id.expense_amount)).setText(expenses.get(index).getAmount().toString());
                ((TextView) findViewById(R.id.expense_date)).setText(expenses.get(index).getDate());
                ((ImageView) findViewById(R.id.imageButton5)).setImageURI(Uri.parse(expenses.get(index).getUri()));
            }
        });

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() { //Change button id
            @Override
            public void onClick(View v) {
                index=index-1;
                if(index<=0) index=0;
                ((TextView) findViewById(R.id.expense_name)).setText(expenses.get(index).getName());
                ((TextView) findViewById(R.id.expense_category)).setText(expenseCategoryArray[expenses.get(index).getCategory()]);
                ((TextView) findViewById(R.id.expense_amount)).setText(expenses.get(index).getAmount().toString());
                ((TextView) findViewById(R.id.expense_date)).setText(expenses.get(index).getDate());
                ((ImageView) findViewById(R.id.imageButton5)).setImageURI(Uri.parse(expenses.get(index).getUri()));
            }
        });

        findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() { //Change button id
            @Override
            public void onClick(View v) {
                index=index+1;
                if(index>=expenses.size()-1) index=expenses.size()-1;
                ((TextView) findViewById(R.id.expense_name)).setText(expenses.get(index).getName());
                ((TextView) findViewById(R.id.expense_category)).setText(expenseCategoryArray[expenses.get(index).getCategory()]);
                ((TextView) findViewById(R.id.expense_amount)).setText(expenses.get(index).getAmount().toString());
                ((TextView) findViewById(R.id.expense_date)).setText(expenses.get(index).getDate());
                ((ImageView) findViewById(R.id.imageButton5)).setImageURI(Uri.parse(expenses.get(index).getUri()));
            }
        });

        findViewById(R.id.imageButton4).setOnClickListener(new View.OnClickListener() { //Change button id
            @Override
            public void onClick(View v) {
                index=expenses.size()-1;

                ((TextView) findViewById(R.id.expense_name)).setText(expenses.get(index).getName());
                ((TextView) findViewById(R.id.expense_category)).setText(expenseCategoryArray[expenses.get(index).getCategory()]);
                ((TextView) findViewById(R.id.expense_amount)).setText(expenses.get(index).getAmount().toString());
                ((TextView) findViewById(R.id.expense_date)).setText(expenses.get(index).getDate());
                ((ImageView) findViewById(R.id.imageButton5)).setImageURI(Uri.parse(expenses.get(index).getUri()));
            }
        });

        findViewById(R.id.button_finish_show).setOnClickListener(new View.OnClickListener() { //Change button id
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.putExtra(MainActivity.EXPENSE_KEY, expenses);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
