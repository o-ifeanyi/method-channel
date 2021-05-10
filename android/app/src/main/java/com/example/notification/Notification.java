package com.example.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.notification.MainActivity.methodChannel;

public class Notification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action == null) {
            return;
        }
        switch (action) {
            case "increase":
                methodChannel.invokeMethod("increase", "");
                break;
            case "decrease":
                methodChannel.invokeMethod("decrease", "");
                break;
        }
    }
}
