package com.itshareplus.placesnearme.Acitivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itshareplus.placesnearme.Model.GlobalVars;
import com.itshareplus.placesnearme.Module.PostStatus;
import com.itshareplus.placesnearme.Module.PostStatusListener;
import com.itshareplus.placesnearme.Module.RequestToServer;
import com.itshareplus.placesnearme.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TUAN TU on 6/19/2016.
 */
public class StatusActivity extends AppCompatActivity implements View.OnClickListener, PostStatusListener {
    private static final int SELECT_IMAGE = 1099;
    EditText etStatus;
    ImageView btnBack;
    ImageView ivAvatar;
    RelativeLayout btnPhoto;
    Button btnPost;
    GridLayout preview;
    String path;
    ArrayList<Bitmap> bmps = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        btnBack = (ImageView)findViewById(R.id.btnBack);
        btnPhoto = (RelativeLayout)findViewById(R.id.btnPhoto);
        preview = (GridLayout)findViewById(R.id.preview);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
            }
        });

        btnPost = (Button) findViewById(R.id.post);
        btnPost.setOnClickListener(this);

        etStatus = (EditText) findViewById(R.id.etStatus);

        ivAvatar = (ImageView) findViewById(R.id.avatar);
        Picasso.with(this).load(GlobalVars.getAvatar()).placeholder(R.drawable.no_avatar).into(ivAvatar);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == this.RESULT_OK) {
                if (data != null) {
                    try {
                        preview.removeAllViews();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        //path = getRealPathFromURI(this, data.getData());
                        Log.d("Path", data.getData().toString());
                        //MediaStore.Images.
                        ImageView image = new ImageView(this);
                        //if (bitmap.getWidth() * bitmap.getHeight() > 1080 * 1920)
                        //image.setImageBitmap(bitmap.createScaledBitmap(bitmap, (int) bitmap.getWidth() / (1080*1920), (int) bitmap.getHeight() / (1080*1920), true));
                        image.setImageBitmap(bitmap);
                        //image.setId(100);
                        RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        image.setLayoutParams(param3);

                        RelativeLayout container = new RelativeLayout(this);
                        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        container.setLayoutParams(param1);
                        container.addView(image);

                        ImageView x = new ImageView(this);
                        RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(dpToPx(20), dpToPx(20));
                        /*param2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        param2.addRule(RelativeLayout.ALIGN_PARENT_END);*/
                        param2.addRule(RelativeLayout.ALIGN_RIGHT, image.getId());
                        param2.addRule(RelativeLayout.ALIGN_TOP, image.getId());
                        x.setLayoutParams(param2);
                        x.setImageResource(R.drawable.close);
                        x.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                preview.removeAllViews();
                            }
                        });

                        container.addView(x);

                        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                        //param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                        //param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                        //param.setGravity(Gravity.CENTER);
                        param.rowSpec = GridLayout.spec(0);
                        param.columnSpec = GridLayout.spec(0);
                        container.setLayoutParams(param);
                        preview.addView(container);


                       /* BitmapFactory.Options options;
                        bitmapMain = BitmapFactory.decodeFile(picturePath);
                        if (bitmapMain.getWidth() * bitmapMain.getHeight() > 2560 * 1440) {
                            options = new BitmapFactory.Options();
                            options.inSampleSize = (bitmapMain.getWidth() * bitmapMain.getHeight()) / (2560 * 1440);
                            bitmapMain = BitmapFactory.decodeFile(picturePath, options);

                        }*/
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == this.RESULT_CANCELED) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void uploadPhoto() {
        //List photo path files
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post:
                postStatus();
                break;
        }
    }

    private void postStatus() {
        if (!IsLogined()) {
            Toast.makeText(this, "You have to login to post status", Toast.LENGTH_SHORT).show();
            return;
        }

        new PostStatus(this, GlobalVars.getUserId(),GlobalVars.currentPlace.getStandardPlaceId(), etStatus.getText().toString(), null).execute();
    }

    private boolean IsLogined() {
        return GlobalVars.getUserId() != 0;
    }

    @Override
    public void OnPostStatusStart() {

    }

    @Override
    public void OnPostStatusSuccess() {
        Toast.makeText(this, "Post status success!", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void OnPostStatusFailed(String error_message) {
        Toast.makeText(this, error_message, Toast.LENGTH_SHORT).show();
    }
}
