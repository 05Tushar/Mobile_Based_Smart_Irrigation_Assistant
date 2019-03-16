package com.example.books;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceSensors;
    private List<Sensor> sensors=new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Sensor> sensors, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceSensors= mDatabase.getReference("books");
    }

    public void readSensors(final DataStatus dataStatus){
        mReferenceSensors.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                sensors.clear();
                List<String> keys=new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Sensor sensor= keyNode.getValue(Sensor.class);
                    sensors.add(sensor);
                }
                dataStatus.DataIsLoaded(sensors,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }
        );
    }

    public void addSensor(Sensor sensor, final DataStatus dataStatus){
        String key=mReferenceSensors.push().getKey();
        mReferenceSensors.child(key).setValue(sensor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void updateSensor(String key, Sensor sensor, final DataStatus dataStatus){
        mReferenceSensors.child(key).setValue(sensor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteSensor(String key, final DataStatus dataStatus){
        mReferenceSensors.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();;
            }
        });
    }
}
