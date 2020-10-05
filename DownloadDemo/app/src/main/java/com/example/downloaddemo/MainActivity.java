package com.example.downloaddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private ProgressBar mProgressBar;
    private TextView mDisplayTextView;
    public static final String APK_URL = "http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk";
    public static final String APK_NAME = "imooc.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = this.findViewById(R.id.button);
        mProgressBar = this.findViewById(R.id.progressBar);
        mDisplayTextView = this.findViewById(R.id.textView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[]permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                boolean readPermission= ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                boolean writePermission = ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                //获取到读写权限的时候
                if(readPermission && writePermission){
                    new DownLoadTask().execute(APK_URL);
                }else {
                    ActivityCompat.requestPermissions(MainActivity.this, permissions,1);
                }
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:{
                if(grantResults.length > 0 && grantResults.length >= 2){
                    if(grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"没有授权下载失败",Toast.LENGTH_LONG).show();
                    }else{
                        new DownLoadTask().execute(APK_URL);
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    class DownLoadTask extends AsyncTask<String,Integer,Boolean>{

        private String mMPathFile;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mButton.setText("开始下载");
            mProgressBar.setProgress(0);
            mDisplayTextView.setText("准备开始下载");
        }


        @Override
        protected Boolean doInBackground(String... params) {
            if(params == null || params.length == 0){
                return false;
            }
            String downLoadUrl = params[0];
            //构建url
            try {
                URL url = new URL(downLoadUrl);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();

                //获取下载的总长度
                int totalDownLoadSize = urlConnection.getContentLength();

                //定义下载的位置
                mMPathFile = Environment.getExternalStorageDirectory()+ File.separator+APK_NAME;
                File file = new File(mMPathFile);
                if(file.exists()){
                   boolean deleteResult = file.delete();
                   //删除失败的时候
                   if(!deleteResult){
                       return false;
                   }
                }
                byte[]bytes = new byte[1024];
                int length = 0;
                int downSize = 0;
                FileOutputStream outputStream = new FileOutputStream(file);
                while((length = inputStream.read(bytes)) != -1){
                    outputStream.write(bytes,0,length);
                    downSize += length;
                    publishProgress(downSize*100/totalDownLoadSize);
                }
                if(inputStream != null){
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }

            }  catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                mDisplayTextView.setText("下载成功了"+"位置在"+mMPathFile);
                mButton.setText("下载完成了");
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values != null && values.length > 0) {
                mProgressBar.setProgress(values[0]);
            }
        }
    }
}