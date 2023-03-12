package com.example.pacrat_good_empty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pacrat_good_empty.databinding.ActivityMainBinding;
import com.google.mlkit.vision.text.TextRecognizer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int CAMERA_REQUEST = 10001;
    private ImageView imageView;
    private TextRecognizer recognize ;
    private CollectionDatabase collectionDatabase;
    private String TABLENAME = "";
    private String arr [];
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        collectionDatabase = new CollectionDatabase(this );
        arr = collectionDatabase.getTableNames();

        ArrayAdapter adapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arr);
        adapt.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.tableNameSpinner.setAdapter(adapt);

        binding.tableNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TABLENAME = arr[i];
                collectionDatabase = new CollectionDatabase(view.getContext() );
                collectionDatabase.TABLE_NAME = arr[i];

                ArrayList <individual_collection_items> list = new ArrayList<individual_collection_items>();
                if(!collectionDatabase.TABLE_NAME.equals("")) {

                    list = collectionDatabase.readFromDB();
                }
                Log.d("hello", "onCreate: ++++++++++++++ " + list.size());

                populate_listView(list);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                TABLENAME = arr[0];
                collectionDatabase = new CollectionDatabase(view.getContext() );
                collectionDatabase.TABLE_NAME = arr[0];


                ArrayList <individual_collection_items> list = new ArrayList<individual_collection_items>();
                if(!collectionDatabase.TABLE_NAME.equals("")) {

                    list = collectionDatabase.readFromDB();
                }
                Log.d("hello", "onCreate: ++++++++++++++ " + list.size());

                populate_listView(list);
            }
        });











        binding.ocrStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("hello", "onClick: camera ");
                Intent intent = new Intent(view.getContext() , upload_new_item.class);
                startActivity(intent);


            }
        });

        binding.createCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manage = getSupportFragmentManager();
                FragmentTransaction trans = manage.beginTransaction();
                if(binding.fragmentContainer.getVisibility() == View.VISIBLE) {
                    binding.fragmentContainer.setVisibility(View.GONE);
                }
                else {
                    binding.fragmentContainer.setVisibility(View.VISIBLE);
                }
                trans.replace(R.id.fragmentContainer , fragment_add.class , null).commit();

            }
        });

    }

    private void populate_listView(ArrayList<individual_collection_items> list){

        ListViewAdapter adapt = new ListViewAdapter(this , R.layout.list_view_layout, list);
        binding.feed.setAdapter(adapt);



//            listview = findViewById(R.id.feed);
//            ArrayAdapter<Object> arrayAdapter = new ArrayAdapter<Object>(getApplicationContext(), R.layout.list_view_layout, collect);
//            listview.setAdapter(arrayAdapter);







        }



    }


