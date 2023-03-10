package com.example.pacrat_good_empty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pacrat_good_empty.databinding.ActivityUploadNewItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.io.FileOutputStream;

public class upload_new_item extends AppCompatActivity {
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 100010;
    private ActivityUploadNewItemBinding binding;
    private int CAMERA_REQUEST = 1001;
    private TextRecognizer recognize ;
    private Bitmap image;
    private CollectionDatabase collectionDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_new_item);

        binding = ActivityUploadNewItemBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }
        }


        //READING AND WRITING PERMISIONS

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(upload_new_item.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(upload_new_item.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(upload_new_item.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }




        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent , CAMERA_REQUEST);
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionDatabase = new CollectionDatabase(getApplicationContext());
                String name = "";
                String release = "";
                String purchased = "";
                String desc = "";
                String img = "";
                name = binding.name.getText().toString();
                release = binding.date.getText().toString();
                purchased = binding.purchase.getText().toString();
                desc = binding.description.getText().toString();



                try {
                    //String name ,String released , String purchased , String description , String map
                    Log.d("hello", "onClick: NAME" + name + "\nRELEASE" + release + "\nPURCHASED" + purchased + "\nDESC" + desc + "\nIMAGE" + image );
                    //writing file to sdcard
                    String external = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
                    File file = new File(external,  name+".png");

                    FileOutputStream outputStream = new FileOutputStream(file);
                    image.compress(Bitmap.CompressFormat.PNG,100,outputStream);

                    outputStream.flush();

                    outputStream.close();

                    collectionDatabase.addNewItem(name, release, purchased, desc, image);
                    Toast.makeText(getApplicationContext(), "Course has been added.", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    Log.d("hello", "onClick: ERROR " + e.getMessage());
                    return;
                }




                binding.name.setText("");
                binding.date.setText("");
                binding.purchase.setText("");
                binding.description.setText("");
                binding.imageView2.setImageBitmap(null);
                Intent back_toMain = new Intent(view.getContext() , MainActivity.class);
                startActivity(back_toMain);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("hello", "onActivityResult: ACT");
        if (requestCode == CAMERA_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            Log.d("hello", "onActivityResult: INSIDE PERMISSIONS");
            this.image = image;
            getData(image);
        }

    }

    private void getData(Bitmap image){
        recognize = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        if(image != null) {

            InputImage inputImage = InputImage.fromBitmap(image , 0 );

            Task<Text> result = recognize.process(inputImage).addOnSuccessListener(new OnSuccessListener<Text>() {
                @Override
                public void onSuccess(Text text) {
                    Log.d("hello", "onSuccess: success");
                    String result = text.getText();
                    binding.imageView2.setImageBitmap(image);
                    binding.name.setText(result);


                }
            });
            Task <Text> fail = recognize.process(inputImage).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {
                    binding.imageView2.setImageBitmap(image);
                    Log.d("hello", "onFailure: failed");
                    Toast toast = Toast.makeText(getApplicationContext(), "Failure to find text within image", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }


    }
}