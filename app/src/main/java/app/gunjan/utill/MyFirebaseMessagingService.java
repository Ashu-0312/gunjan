package app.gunjan.utill;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import app.gunjan.R;
import app.gunjan.activities.CommunityHelpActivity;
import app.gunjan.activities.SplashActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onNewToken(@NonNull String refreshedToken) {
        super.onNewToken(refreshedToken);
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        NotificationChannel mChanal = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChanal = new NotificationChannel("MY_CHANNEL","channelname", NotificationManager.IMPORTANCE_HIGH);
        }
        FCSharedPreferances.getSharedPreferance(this).setNOTIFICATION_TYPE(remoteMessage.getData().get("type"));
        if (remoteMessage.getData().get("type").equals("post coin")){
            Intent intent = new Intent( this,CommunityHelpActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                    PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getData().get("body"))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.logo)
                    .setSmallIcon(R.drawable.logo)
                    .setColor(getResources().getColor(R.color.pink))
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                    .setContentIntent(resultIntent)
                    .setChannelId("MY_CHANNEL")
                    .setAutoCancel(true);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(mChanal);
            }
            notificationManager.notify(0, notificationBuilder.build());
        }else {
            Intent intent = new Intent( this , SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                    PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getData().get("body"))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.logo)
                    .setSmallIcon(R.drawable.logo)
                    .setColor(getResources().getColor(R.color.pink))
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                    .setContentIntent(resultIntent)
                    .setChannelId("MY_CHANNEL")
                    .setAutoCancel(true);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(mChanal);
            }
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }
}

