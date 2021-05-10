package com.example.notification;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    public static MethodChannel methodChannel;
    public final String channelName = "notification";
    NotificationManagerCompat notificationManager;
    public final String NOTIFICATION_ID = "Channel_ID";

    @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         notificationManager = NotificationManagerCompat.from(this);
     }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        methodChannel = new MethodChannel(
                flutterEngine.getDartExecutor().getBinaryMessenger(),
                channelName);
        methodChannel.setMethodCallHandler((call, result) -> {
            if (call.method.equals("create")) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    this,
                        NOTIFICATION_ID)
                  .setContentTitle("Title")
                  .setContentText("Subtitle")
                  .setSmallIcon(R.mipmap.ic_launcher)
                  .setOngoing(false)
                  .addAction(R.mipmap.ic_launcher, "Increase", getIntent(true))
                  .addAction(R.mipmap.ic_launcher, "Decrease", getIntent(false));

                notificationManager.notify(0, builder.build());
                result.success(null);
            }
            if (call.method.equals("destroy")) {
                notificationManager.cancel(0);
                result.success(null);
            }
        });
    }
    private PendingIntent getIntent(boolean isIncrease) {
        Intent intent = new Intent(this, Notification.class);
        if (isIncrease) {
            intent.setAction("increase");
        } else {
            intent.setAction("decrease");
        }
        return PendingIntent.getBroadcast(this, 0, intent, 0);
    }
}