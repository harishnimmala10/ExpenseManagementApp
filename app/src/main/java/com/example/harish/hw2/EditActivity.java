package com.example.harish.hw2;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static java.lang.Double.*;

/**
 * Created by Harish on 9/14/2016.
 */
public class EditActivity extends AppCompatActivity{

    ArrayList<Expense> expenses;
    int index;
    private TextView name;
    private Spinner category;
    private TextView amount;
    //private TextView date1;
    ImageView uri;
    final int PERMISSION_REQUEST_MANAGE_DOCUMENTS=1;
    EditText date1;
    ImageButton im;
    private DatePickerDialog.OnDateSetListener  date;
    Calendar myCalendar;
    Uri fullPhotoUri;
    private static final int REQUEST_IMAGE_GET = 1;

    EditActivity() {
        myCalendar=null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        name= (TextView) findViewById(R.id.editText);
        category= (Spinner) findViewById(R.id.spinner);
        amount = (TextView) findViewById(R.id.editText2);
        date1= (EditText) findViewById(R.id.editText3);
        uri= (ImageView) findViewById(R.id.imageButtonReceipt);



        expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable(MainActivity.EXPENSE_KEY);

        myCalendar = Calendar.getInstance();
        //myCalendar.setTime(date1);
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
                new DatePickerDialog(EditActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        findViewById(R.id.button_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(EditActivity.this);
                builder.setTitle("Pick an Expense");

                ArrayList<String> names=new ArrayList<String>();
                //int i=0;
                for(int i=0;i<expenses.size();i++) {
                    names.add(expenses.get(i).getName());
                }
                final CharSequence[] items= names.toArray(new CharSequence[names.size()]);
                int permissionCheck = ContextCompat.checkSelfPermission(EditActivity.this,
                        android.Manifest.permission.MANAGE_DOCUMENTS);

                if(permissionCheck != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(EditActivity.this,
                            new String[] {android.Manifest.permission.MANAGE_DOCUMENTS},
                            PERMISSION_REQUEST_MANAGE_DOCUMENTS);
                }
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Expense expense= expenses.get(i);
                        name.setText(expense.getName());
                        category.setSelection(expense.getCategory()); //hardcoded
                        amount.setText(expense.getAmount().toString());
                        date1.setText(expense.getDate());

                            uri.setImageURI(Uri.parse(expense.getUri()));
                        fullPhotoUri=Uri.parse(expense.getUri());
                        /*Log.d("imageEdit",expense.getUri());
                        Log.d("imageParse",Uri.parse(expense.getUri())+"");*/
                        index=i;

                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
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



        //index on the selected expense
        findViewById(R.id.button_edit1).setOnClickListener(new View.OnClickListener() { //Change button id

            @Override
            public void onClick(View v){
                if(validate()) {
                    Expense expense = null;

                    expense = new Expense(name.getText().toString(), category.getSelectedItemPosition(), valueOf(amount.getText().toString())
                            , date1.getText().toString(), fullPhotoUri.toString());

                    //expenses.add(expense);
                    expenses.set(index, expense);
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
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_MANAGE_DOCUMENTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("editactivity", "GRANTED!!!!!!");


                } else {
                    Log.d("editactivity", "DENIED!!!!!!");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        EditText edittext = (EditText) findViewById(R.id.editText3);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean validate(){

        if(null!=name.getText().toString() && null!= category.getSelectedItem().toString() && Double.parseDouble(amount.getText().toString()) >0
                && null != date1.getText().toString() && null != fullPhotoUri){
            return true;

        }else {

            return false;
        }
    }

}
