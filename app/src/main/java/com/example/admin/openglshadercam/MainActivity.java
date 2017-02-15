package com.example.admin.openglshadercam;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.graphics.PixelFormat;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLView;

    protected Context myContext;

    protected String cameraId;
    protected CameraDevice myCameraDevice;
    protected CameraManager myCamManager;

    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(CameraDevice camera) {
            System.out.println("Camera open");
            myCameraDevice = camera;
        }

        @Override
        public void onDisconnected(CameraDevice camera) {

        }

        @Override
        public void onError(CameraDevice camera, int error) {

        }
    };




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myCamManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        String[] res;
        System.out.println("----------------------------");
        try{
            res = myCamManager.getCameraIdList();
            System.out.println(res.length);
        }
        catch (Exception e) {
            System.out.print(e.toString());
        }


        System.out.println("----------------------------");

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);

        mGLView.setZOrderOnTop(true);    // necessary
        SurfaceHolder sfhTrackHolder = mGLView.getHolder();
        sfhTrackHolder.setFormat(PixelFormat.TRANSLUCENT);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }

    /** Check if this device has a camera */
    protected boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
