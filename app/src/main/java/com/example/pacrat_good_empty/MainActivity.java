package com.example.pacrat_good_empty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pacrat_good_empty.databinding.ActivityMainBinding;
import com.google.mlkit.vision.text.TextRecognizer;

import java.util.ArrayList;
import java.util.Dictionary;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int CAMERA_REQUEST = 10001;
    private ImageView imageView;
    private TextRecognizer recognize ;
    private CollectionDatabase collectionDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        collectionDatabase = new CollectionDatabase(view.getContext());
        ArrayList <Dictionary> list = new ArrayList<Dictionary>();

        list = collectionDatabase.readFromDB();
        Log.d("hello", "onCreate: " + list.size());

        populate_listView(list);





        binding.ocrStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("hello", "onClick: camera ");
                Intent intent = new Intent(view.getContext() , upload_new_item.class);
                startActivity(intent);


            }
        });

    }

    private void populate_listView(ArrayList<Dictionary> list){
        for(int i = 0 ; i<list.size() ; i++) {
            Log.d("hello", "populate_listView: "+ list.get(i));
        }

    }


}