package com.example.charanpuli.fish_baller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class TellUrFriendsActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tell_ur_friends);
//    }
private String downloadimageurl="https://firebasestorage.googleapis.com/v0/b/hungry-fish-1c545.appspot.com/o/User%20Images%2Fimage%3A115482Apr%2001%2C201920%3A54%3A43%20PM.jpg?alt=media&token=ed9bf1e2-489d-4efe-8818-819b6738e5b6";
    private Button shareBtn;
    private EditText inputName,inputPlace;
    private CircleImageView userImage;
    private static final int gallerypick=1;
    private Uri imageuri;
    private String name,age,place,savecurrentdate,savecurrenttime,productrandomkey;
    private int score;
    private StorageReference UserPicRef;
    private DatabaseReference Userref;
    ProgressDialog Loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_ur_friends);


        UserPicRef=FirebaseStorage.getInstance().getReference().child("User Images");

        score=Integer.parseInt(getIntent().getStringExtra("score"));
        shareBtn=(Button)findViewById(R.id.sharebtn);
        inputName=(EditText)findViewById(R.id.settings_name);
        inputPlace=(EditText)findViewById(R.id.settings_place);
        userImage=(CircleImageView)findViewById(R.id.settings_profile_image);
        Userref=FirebaseDatabase.getInstance().getReference().child("Users");
        Loadingbar=new ProgressDialog(this);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });



    }


    private void openGallery() {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,gallerypick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==gallerypick && resultCode==RESULT_OK && data!=null)
        {
            imageuri=data.getData();
            userImage.setImageURI(imageuri);
        }
    }
    private void ValidateProductData() {
        name=inputName.getText().toString();
        place=inputPlace.getText().toString();
        if(imageuri==null){
//            Toast.makeText(this, "Provide Your image..", Toast.LENGTH_SHORT).show();
            if(TextUtils.isEmpty(name)){
                Toast.makeText(this, "Provide Your name..", Toast.LENGTH_SHORT).show();
            }
            else{
                Loadingbar.setTitle("Sharing with friends");
                Loadingbar.setMessage("Make Sure You Enabled Data Connection...");
                Loadingbar.setCanceledOnTouchOutside(false);
                Loadingbar.show();
                Calendar calender=Calendar.getInstance();
                SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyyy");
                savecurrentdate=currentdate.format(calender.getTime());
                SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
                savecurrenttime=currenttime.format(calender.getTime());
                productrandomkey=savecurrentdate+savecurrenttime;
                saveproductInfotoDatabase();
            }

        }

        else if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Provide Your name..", Toast.LENGTH_SHORT).show();
        }


        else
        {
            StoreProductInformation();
        }



    }



    private void StoreProductInformation() {
        Loadingbar.setTitle("Sharing with friends");
        Loadingbar.setMessage("Make Sure You Enabled Data Connection...");
        Loadingbar.setCanceledOnTouchOutside(false);
        Loadingbar.show();
        Calendar calender=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=currentdate.format(calender.getTime());
        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currenttime.format(calender.getTime());
        productrandomkey=savecurrentdate+savecurrenttime;

        final StorageReference filepath;
        ///////////////////////////////////////////////////
        filepath = UserPicRef.child(imageuri.getLastPathSegment() + productrandomkey + ".jpg");

        final UploadTask uploadTask=filepath.putFile(imageuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg=e.toString();
                Toast.makeText(TellUrFriendsActivity.this, "Error : "+msg, Toast.LENGTH_SHORT).show();
                Loadingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(TellUrFriendsActivity.this, "Uploading.....", Toast.LENGTH_SHORT).show();
                Task<Uri> urltask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadimageurl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {   downloadimageurl=task.getResult().toString();
                            saveproductInfotoDatabase();
                        }

                    }
                });

            }

        });

    }

    private void saveproductInfotoDatabase() {
        HashMap<String,Object> productmap=new HashMap<>();

        productmap.put("image",downloadimageurl);
        productmap.put("place",place);
        productmap.put("name",name);
        productmap.put("score",score);
        productmap.put("pid",productrandomkey);
        Userref.child(productrandomkey).updateChildren(productmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {    Intent intent=new Intent(TellUrFriendsActivity.this,StartActivity.class);
                            startActivity(intent);
                            Loadingbar.dismiss();
                            Toast.makeText(TellUrFriendsActivity.this, "Shared successfully...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {    Loadingbar.dismiss();
                            String message=task.getException().toString();
                            Toast.makeText(TellUrFriendsActivity.this, "Error : "+message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });



    }


}
