package com.example.eventbusdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.example.eventbusdemo.MainActivity.BROAD_CAST_EVENT_BUS_DEMO;
import static com.example.eventbusdemo.MainActivity.SUCCESS_FLAG;

public class PublishDiaglogFragment extends DialogFragment {




    public PublishDiaglogFragment(){

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Publish");
        final String[] items = {"Success","Failure"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                //注意这里发送广播的时候，需要将Action的名字填写上
                intent.setAction(BROAD_CAST_EVENT_BUS_DEMO);
                switch (which){
                    case 0://success

                        intent.putExtra(SUCCESS_FLAG,true);
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                        break;

                    case 1://fail
                    {

                        intent.putExtra(SUCCESS_FLAG, false);
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    }
                        break;
                    default:
                        break;
                }
            }
        });
        return builder.create();
    }
}
