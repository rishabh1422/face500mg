package com.example.face500mg;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageProcess implements ActivityCompat.OnRequestPermissionsResultCallback {
    private final int MY_CAMERA_REQUEST = 100;
    private final int PICK_FROM_GALLERY = 123;
    //    TextView img_view;
    Activity activity;

    Uri uri = null;


    static Context context;
    static ImageProcess imageProcess;

    public static ImageProcess getInstanced(Context newcontext) {
        context = newcontext;
        if (imageProcess == null) {
            imageProcess = new ImageProcess();
        }
        return imageProcess;
    }


    public void selectImage(final Activity activity /*, TextView img_view */) {

//        this.img_view = img_view;
        this.activity = activity;
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST);

                    } else {

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                        activity.startActivityForResult(cameraIntent, MY_CAMERA_REQUEST);


                    }
                } else if (options[item].equals("Choose from Gallery")) {

                    if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {

                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activity.startActivityForResult(pickPhoto, PICK_FROM_GALLERY);
                    }

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull final int[] grantResults) {
        switch (requestCode) {
            case 100: {
                RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            activity.startActivityForResult(cameraIntent, MY_CAMERA_REQUEST);

                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(context, "Permission Required", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }


            case 123: {
                RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            activity.startActivityForResult(pickPhoto, PICK_FROM_GALLERY);

                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(context, "Permission required", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
    }


    public String activityResult(int requestCode, int resultCode, Intent data) {

        String path = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == MY_CAMERA_REQUEST) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                File destination = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    // destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                uri = Uri.fromFile(destination);

                path = getRealPathFromURIPath(uri, context);

            } else if (requestCode == PICK_FROM_GALLERY) {
                if (data != null) {
                    try {


                        uri = data.getData();

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (uri != null) {
                            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                path = cursor.getString(columnIndex);
                                //  cursor.close();

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return path;
    }


    private String getRealPathFromURIPath(Uri contentURI, Context context) {
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String path = cursor.getString(idx);
            //  cursor.close();
            return path;
        }
    }



}
