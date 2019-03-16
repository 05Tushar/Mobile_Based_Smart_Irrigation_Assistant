package com.example.books;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FieldDetailsActivity extends AppCompatActivity {

    //private EditText mValue_editTxt;
    //private EditText mLongitude_editTxt;
    //private EditText mLatitude_editText;
    private Spinner mCrop_categories_spinner;

    private Button mUpdate_btn;
    //private Button mDelete_btn;
    private Button mBack_btn;
    private TextView mHeading;

    private String key;
    private String value;
    private String longitude;
    private String latitude;
    private String crop;
    private String canal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_details);

        key=getIntent().getStringExtra("key");
        value=getIntent().getStringExtra("value");
        longitude=getIntent().getStringExtra("longitude");
        latitude=getIntent().getStringExtra("latitude");
        crop=getIntent().getStringExtra("crop");
        canal=getIntent().getStringExtra("canal");

        //mValue_editTxt=(EditText) findViewById(R.id.value_editTxt);
        //mValue_editTxt.setText(value);
        //mLatitude_editText=(EditText) findViewById(R.id.latitude_editTxt);
        //mLatitude_editText.setText(latitude);
        //mLongitude_editTxt=(EditText) findViewById(R.id.longitude_editTxt);
        //mLongitude_editTxt.setText(longitude);
        mCrop_categories_spinner=(Spinner) findViewById(R.id.crop_spinner);
        mCrop_categories_spinner.setSelection(getIndex_SpinnerItem(mCrop_categories_spinner,crop));
        mHeading=(TextView) findViewById(R.id.heading_txtView);
        mHeading.setText("Select Crop");

        mUpdate_btn=(Button) findViewById(R.id.update_btn);
        //mDelete_btn=(Button) findViewById(R.id.delete_btn);
        mBack_btn=(Button) findViewById(R.id.back_btn);

        mUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sensor sensor=new Sensor();
                sensor.setValue(value);
                sensor.setLatitude(latitude);
                sensor.setLongitude(longitude);
                sensor.setCanal(canal);
                sensor.setCrop(mCrop_categories_spinner.getSelectedItem().toString());

                new FirebaseDatabaseHelper().updateSensor(key, sensor, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Sensor> sensors, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(FieldDetailsActivity.this,"Crop has been"+" updated successfully",Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        /*mDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().deleteSensor(key, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Sensor> sensors, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(FieldDetailsActivity.this,"Data has been "+"deleted successfully",Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                });
            }
        });*/

        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });
    }

    private int getIndex_SpinnerItem(Spinner spinner, String item){
        int index=0;
        for (int i=0;i<spinner.getCount();i++)
        {
            if (spinner.getItemAtPosition(i).equals(item)){
                index=1;
                break;
            }
        }
        return index;
    }
}
