package com.example.pacrat_good_empty;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<individual_collection_items> {
    private ArrayList<individual_collection_items> mylist;
    private int resourcelayout;
    private Context mContext;
    public ListViewAdapter(@NonNull Context context , int resource , ArrayList<individual_collection_items> list){

        super(context , resource , list);
        this.resourcelayout = resource;

        this.mContext = context;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // convertView which is recyclable view
        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);

            v = vi.inflate(resourcelayout , null);
        }
        // of the recyclable view is null then inflate the custom layout for the same


        // get the position of the view from the ArrayAdapter

        individual_collection_items item = getItem(position);

        if(item !=null) {

            // then according to the position of the view assign the desired image for the same
            ImageView numbersImage = v.findViewById(R.id.image_feed);
            TextView textView1 = v.findViewById(R.id.released_feed);
            TextView purch = v.findViewById(R.id.date_purchased_feed);
            TextView dex = v.findViewById(R.id.description_feed);
            TextView name = v.findViewById(R.id.name_feed);
//            byte[] decodedString =  mylist.get(i).get("photo").toString().getBytes();

            if(numbersImage!=null) {

                numbersImage.setImageBitmap(item.item_photo);
            }


            // then according to the position of the view assign the desired TextView 1 for the same
            if(textView1!=null) {
                textView1.setText( "RELEASE DATE: "+ item.item_released);
                Log.d("list_view", "getView: ++++++++ " + item.item_released );
            }

            // then according to the position of the view assign the desired TextView 2 for the same
            if(purch!=null) {
                purch.setText("PURCHASE DATE: "+ item.item_purchased);
            }

            if(dex!=null) {
                dex.setText((String) "DESCRIPTION: "+item.item_description);
            }

           if(name!=null) {
               name.setText((String) "NAME: "+item.item_name);
           }


        }


        // then return the recyclable view
        Log.d("hello", "getView: " + v.toString());
        return v;
    }

//    public Bitmap textToBitmap(String text) {
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setTextSize(30);
//        paint.setColor(Color.BLACK);
//        paint.setTextAlign(Paint.Align.LEFT);
//        float baseline = -paint.ascent();
//        int width = (int) (paint.measureText(text) + 0.5f);
//        int height = (int) (baseline + paint.descent() + 0.5f);
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        canvas.drawText(text, 0, baseline, paint);
//        return bitmap;
//    }


//    public int getCameraPhotoOrientation(Context context, Uri imageUri,
//                                         String imagePath) {
//        int rotate = 0;
//        try {
//            context.getContentResolver().notifyChange(imageUri, null);
//            File imageFile = new File(imagePath);
//            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
//            int orientation = exif.getAttributeInt(
//                    ExifInterface.TAG_ORIENTATION,
//                    ExifInterface.ORIENTATION_NORMAL);
//
//            switch (orientation) {
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    rotate = 270;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    rotate = 180;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    rotate = 90;
//                    break;
//            }
//
//            Log.i("RotateImage", "Exif orientation: " + orientation);
//            Log.i("RotateImage", "Rotate value: " + rotate);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rotate;
//    }

    }



