package com.liveline.chalgyacodeapna;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageSwitcher imagesIs;
    private Button previousBtn, nextBtn, pickImagesBtn;

    //store image uris in this array list
    private ArrayList<Uri> imageUris;

    //request code to pick images
    File imageFile;
    private static final int PICK_IMAGES_CODE = 0;

    //positoion of selected image
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        //init UI Views
        imagesIs = findViewById(R.id.imagesIs);
        previousBtn = findViewById(R.id.previousBtn);
        nextBtn = findViewById(R.id.nextBtn);
        pickImagesBtn = findViewById(R.id.pickImagesBtn);

        //init list
        imageUris = new ArrayList<>();

        //setup image switcher
        imagesIs.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                return imageView;
            }
        });

        //click handle, pick images
        pickImagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
              //  pickImagesIntent();
            }
        });

        //click handle, show previous image
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0){
                    position--;
                    imagesIs.setImageURI(imageUris.get(position));
                }
                else {
                    Toast.makeText(MainActivity.this, "No Previous images...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //click handle, show next image
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < imageUris.size() - 1){
                    position++;
                    imagesIs.setImageURI(imageUris.get(position));
                }
                else {
                    Toast.makeText(MainActivity.this, "No More images...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void pickImagesIntent(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGES_CODE){
//
//            if (resultCode == Activity.RESULT_OK){
//
//                if (data.getClipData() != null){
//                    //picked multiple images
//
//                    int cout = data.getClipData().getItemCount(); //number of picked images
//                    for (int i=0; i<cout; i++){
//                        //get image uri at specific index
//                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                        imageUris.add(imageUri); //add to list
//
//                    }
//
//                    //set first image to our image switcher
//                    imagesIs.setImageURI(imageUris.get(0));
//                    position = 0;
//                }
//                else {
//                    //picked single image
//                    Uri imageUri = data.getData();
//                    imageUris.add(imageUri);
//                    //set image to our image switcher
//                    imagesIs.setImageURI(imageUris.get(0));
//                    position = 0;
//                }
//
//            }
//
//        }
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==Activity.RESULT_OK)
        {
            Uri uri=data.getData();
            Context context=MainActivity.this;
            String path=RealPathUtil.getRealPath(context,uri);
            Toast.makeText(context, ""+path, Toast.LENGTH_SHORT).show();
            Log.d("FinalHopex", "onActivityResult: "+path);
        }
    }
}
