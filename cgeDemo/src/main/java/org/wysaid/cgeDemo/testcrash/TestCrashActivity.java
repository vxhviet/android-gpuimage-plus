package org.wysaid.cgeDemo.testcrash;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import org.wysaid.cgeDemo.R;
import org.wysaid.view.ImageGLSurfaceView;

import java.io.File;

/**
 * Created by Viet on 28/07/2017.
 */

public class TestCrashActivity extends AppCompatActivity {

    //change these to whatever suit your system
    final String INPUT_PATH = "/storage/emulated/0/Pictures/test/test1.jpg";
    final Uri mInput = Uri.fromFile(new File(INPUT_PATH));
    final Uri mOutput = Uri.fromFile(new File("/storage/emulated/0/Pictures/test/test1_out.jpg"));

    //final String BASIC_FILTER_CONFIG = "@adjust contrast 1 @adjust brightness 0 @adjust saturation 1 @adjust exposure 0";

    ImageGLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_crash);

        mGLSurfaceView = (ImageGLSurfaceView) findViewById(R.id.gl_surface_view);
        mGLSurfaceView.setDisplayMode(ImageGLSurfaceView.DisplayMode.DISPLAY_ASPECT_FIT);

        mGLSurfaceView.setSurfaceCreatedCallback(new ImageGLSurfaceView.OnSurfaceCreatedCallback() {
            @Override
            public void surfaceCreated() {
                //this crash in the CGEImageHandler#nativeInitWithBitmap(long holder Bitmap bmp)
                loadImage();

                //this works fine
                /*Bitmap bitmap = BitmapFactory.decodeFile(INPUT_PATH);
                mGLSurfaceView.setImageBitmap(bitmap);
                mGLSurfaceView.setFilterWithConfig(BASIC_FILTER_CONFIG);*/
            }
        });
    }

    private void loadImage(){
        int maxBitmapSize = BitmapLoadUtils.calculateMaxBitmapSize(this);

        BitmapLoadUtils.decodeBitmapInBackground(this, mInput, mOutput, maxBitmapSize, maxBitmapSize, new BitmapLoadCallback() {
            @Override
            public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo, @NonNull String imageInputPath, @Nullable String imageOutputPath) {
                mGLSurfaceView.setImageBitmap(bitmap);
                //mGLSurfaceView.setFilterWithConfig(BASIC_FILTER_CONFIG);
            }

            @Override
            public void onFailure(@NonNull Exception bitmapWorkerException) {

            }
        });
    }
}
