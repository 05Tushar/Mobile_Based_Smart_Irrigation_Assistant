package com.example.books;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecyclerView_Config
{
    private Context mContext;
    private SensorsAdapter mSensorsAdapter;
    public void setConfig(RecyclerView recyclerView,Context context, List<Sensor> sensors ,List<String> keys){
        mContext=context;
        mSensorsAdapter=new SensorsAdapter(sensors, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mSensorsAdapter);
    }


    class BookItemView extends RecyclerView.ViewHolder  {
        private TextView mValue;
        private TextView mLongitude;
        private TextView mLatitude;
        private TextView mCrop;
        private TextView mIrrigation;
        private TextView mWeather;
        private TextView mCity;
        private TextView mTap;
        private TextView mCanal;

        private String key;
        public BookItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.book_list_item, parent, false ));

            mValue=(TextView) itemView.findViewById(R.id.title_txtView);
            mLongitude=(TextView) itemView.findViewById(R.id.author_txtView);
            mLatitude = (TextView) itemView.findViewById(R.id.category_txtView);;
            mCrop= (TextView) itemView.findViewById(R.id.isbn_txtView);
            mIrrigation=(TextView) itemView.findViewById(R.id.irrigation_txtView);
            mWeather=(TextView) itemView.findViewById(R.id.weather_txtView);
            mCity=(TextView) itemView.findViewById(R.id.city_txtView);
            mTap=(TextView) itemView.findViewById(R.id.tap_txtView);
            mCanal=(TextView) itemView.findViewById(R.id.canal_txtView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,FieldDetailsActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("value",mValue.getText().toString());
                    intent.putExtra("longitude",mLongitude.getText().toString());
                    intent.putExtra("latitude",mLatitude.getText().toString());
                    intent.putExtra("crop",mCrop.getText().toString());
                    intent.putExtra("canal",mCanal.getText().toString());

                    mContext.startActivity(intent);
                }
            });
        }

        public void bind(Sensor sensor, String key){
            mValue.setText(sensor.getValue());
            mLongitude.setText(sensor.getLongitude());
            mLatitude.setText(sensor.getLatitude());
            int x=Integer.parseInt(sensor.getValue());
            //double y=((double)x/255)*100;
            mCrop.setText(sensor.getCrop());
            mTap.setText("Tap to select crop");
            mCanal.setText(sensor.getCanal());
            /*if (sensor.getCrop().equals("Wheat"))
            {
                if (y<70)  mIrrigation.setText("Irrigation needed");
                else mIrrigation.setText("No need for irrigation");
            }
            else if (sensor.getCrop().equals("Rice"))
            {
                if (y<85)  mIrrigation.setText("Irrigation needed");
                else mIrrigation.setText("No need for irrigation");
            }
            else if (sensor.getCrop().equals("Corn"))
            {
                if (y<45)  mIrrigation.setText("Irrigation needed");
                else mIrrigation.setText("No need for irrigation");
            }
            else if (sensor.getCrop().equals("Barley"))
            {
                if (y<60)  mIrrigation.setText("Irrigation needed");
                else mIrrigation.setText("No need for irrigation");
            }
            else if (sensor.getCrop().equals("Cotton"))
            {
                if (y<50)  mIrrigation.setText("Irrigation needed");
                else mIrrigation.setText("No need for irrigation");
            }
            else mIrrigation.setText("Crop not identified");*/

            if (x==40)
            {
                if (sensor.getCanal().equals("Canal is filled"))
                {
                    mIrrigation.setText("Use Canal Water to irrigate");
                }
                else {
                    mIrrigation.setText("Irrigation needed!!!");
                }
            }
            else if (x==30)
            {
                //mIrrigation.setText("Status - "+"Irrigating....");
                if (sensor.getCrop().equals("Barley"))
                {
                    mIrrigation.setText("No need for irrigation");
                }
                else if (sensor.getCanal().equals("Canal is filled"))
                {
                    mIrrigation.setText("Use Canal Water to irrigate");
                }
                else
                {
                    mIrrigation.setText("Irrigation needed!!!");
                }
            }
            else if (x==20)
            {
                if (sensor.getCrop().equals("Barley")||sensor.getCrop().equals("Wheat")||sensor.getCrop().equals("Cotton"))
                {
                    mIrrigation.setText("No need for irrigation");
                }
                else if (sensor.getCanal().equals("Canal is filled"))
                {
                    mIrrigation.setText("Use Canal Water to irrigate");
                }
                else
                {
                    mIrrigation.setText("Irrigation needed!!!");
                }
            }
            else if (x==10)
            {
                mIrrigation.setText("No need for irrigation");
            }
            find_weather(sensor.getLongitude(),sensor.getLatitude());
            this.key=key;
        }


        public void find_weather(String longitude,String latitude)
        {
            String url="https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=b2d319a6b5b4d781998b0e3e900d029c";

            JsonObjectRequest jor=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try
                    {
                        JSONArray array=response.getJSONArray("weather");
                        JSONObject object=array.getJSONObject(0);
                        String description = object.getString("description");
                        mWeather.setText("Weather - "+ description);
                        mCity.setText("City - "+response.getString("name"));
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );
            RequestQueue queue=Volley.newRequestQueue(mContext);;
            queue.add(jor);
        }


    }
    class SensorsAdapter extends RecyclerView.Adapter<BookItemView>{
        private List<Sensor> mSensorList;
        private List<String> mKeys;

        public SensorsAdapter(List<Sensor> mSensorList, List<String> mKeys){
            this.mSensorList = mSensorList;
            this.mKeys = mKeys;
        }

        @Override
        public BookItemView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BookItemView(parent);
        }

        @Override
        public void onBindViewHolder(BookItemView holder, int position) {
            holder.bind(mSensorList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mSensorList.size();
        }
    }
}
