package com.example.harish.hw2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Harish on 9/14/2016.
 */
public class DeleteActivity extends AppCompatActivity{
    ArrayList<Expense> expenses;
    int index;
    private TextView name;
    private Spinner category;
    private TextView amount;
    private TextView date;
    private ImageView uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        name= (TextView) findViewById(R.id.editText);
        category= (Spinner) findViewById(R.id.spinner);
        amount = (TextView) findViewById(R.id.editText2);
        date= (TextView) findViewById(R.id.editText3);
        uri=(ImageView) findViewById(R.id.imageButtonReceipt) ;

        name.setEnabled(false);
        category.setEnabled(false);
        amount.setEnabled(false);
        date.setEnabled(false);
        uri.setEnabled(false);

        expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable(MainActivity.EXPENSE_KEY);
        findViewById(R.id.button_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(DeleteActivity.this);
                builder.setTitle("Pick an Expense");

                ArrayList<String> names=new ArrayList<String>();
                //int i=0;
                for(int i=0;i<expenses.size();i++) {
                    names.add(expenses.get(i).getName());
                }
                final CharSequence[] items= names.toArray(new CharSequence[names.size()]);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Expense expense= expenses.get(i);
                        name.setText(expense.getName());
                        category.setSelection(expense.getCategory()); //hardcoded
                        amount.setText(expense.getAmount().toString());
                        date.setText(expense.getDate());
                        uri.setImageURI(Uri.parse(expense.getUri()));
                        index=i;

                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
            }
        });
        //index on the selected expense
        findViewById(R.id.button_delete1).setOnClickListener(new View.OnClickListener() { //Change button id
            @Override
            public void onClick(View v) {

                expenses.remove(index);
                Intent intent=new Intent();
                intent.putExtra(MainActivity.EXPENSE_KEY,expenses);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}






