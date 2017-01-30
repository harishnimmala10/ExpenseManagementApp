package com.example.harish.hw2;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Harish on 9/13/2016.
 */
public class AddActivity extends AppCompatActivity implements Serializable {
    ArrayList<Expense> expenses;
    private TextView name;
    private Spinner category;
    private TextView amount;
    private DatePickerDialog.OnDateSetListener  date;
    Calendar myCalendar;
    private ImageView uri;
    private Uri uriValue;
    static final int REQUEST_IMAGE_GET = 1;
    EditText date1;
    ImageButton im;
    Uri fullPhotoUri;

    public AddActivity() {
        myCalendar = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name= (TextView) findViewById(R.id.editText);
        category= (Spinner) findViewById(R.id.spinner);
        amount = (TextView) findViewById(R.id.editText2);
        date1= (EditText) findViewById(R.id.editText3);
        uri= (ImageView) findViewById(R.id.imageButtonReceipt);
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        im = (ImageButton) findViewById(R.id.imageButton);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable(MainActivity.EXPENSE_KEY);
        findViewById(R.id.button_add1).setOnClickListener(new View.OnClickListener() { //Change button id
            @Override
            public void onClick(View v) {
                if(validate()) {
                    Expense expense = null;

                        expense = new Expense(name.getText().toString(), category.getSelectedItemPosition(), Double.parseDouble(amount.getText().toString())
                                , date1.getText().toString(), fullPhotoUri.toString());

                    expenses.add(expense);
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.EXPENSE_KEY, expenses);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    Toast.makeText(getBaseContext(),"Please enter all the values!",Toast.LENGTH_LONG).show();
                }

            }

        });
        findViewById(R.id.imageButtonReceipt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET);
                }
            }
        });
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            //Bitmap thumbnail = data.getParcelable("data");

            fullPhotoUri = data.getData();

            // Show the Selected Image on ImageView

            findViewById(R.id.imageButtonReceipt).setSelected(true);
            uri.setImageURI(fullPhotoUri);
            Log.d("d/image",fullPhotoUri.toString());

            // Do work with photo saved at fullPhotoUri

        }
    }
    private boolean validate(){

        if(null!=name.getText().toString() && null!= category.getSelectedItem().toString() && Double.parseDouble(amount.getText().toString()) >0
                && null != date1.getText().toString() && null != fullPhotoUri){
            return true;

        }else {

            return false;
        }
    }
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        EditText edittext = (EditText) findViewById(R.id.editText3);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }


}
