package com.example.pacrat_good_empty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pacrat_good_empty.databinding.ActivityUploadNewItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class upload_new_item extends AppCompatActivity {
    private ActivityUploadNewItemBinding binding;
    private int CAMERA_REQUEST = 1001;
    private TextRecognizer recognize ;
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

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("hello", "onActivityResult: ACT");
        if (requestCode == CAMERA_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            Log.d("hello", "onActivityResult: INSIDE PERMISSIONS");
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