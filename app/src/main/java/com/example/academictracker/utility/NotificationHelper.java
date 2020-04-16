package com.example.academictracker.utility;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.academictracker.R;

public class NotificationHelper extends ContextWrapper {

    public enum NotificationCategory {
        COURSE_BEGIN("Course Begin", 9999999),
        COURSE_END("Course End", 9999998),
        ASSESSMENT_DUE("Assessment Due", 9999997);

        public final String label;
        public final int id;

        private NotificationCategory(String label, int id) {
            this.label = label;
            this.id = id;
        }
    }

    public static final String EXTRA_NOTIFICATION_TITLE = "com.example.academictracker.view.EXTRA_NOTIFICATION_TITLE";
    public static final String EXTRA_NOTIFICATION_MESSAGE = "com.example.academictracker.view.EXTRA_NOTIFICATION_MESSAGE";
    public static final String EXTRA_NOTIFICATION_ID = "com.example.academictracker.view.EXTRA_NOTIFICATION_ID";

    public static final String channelID = "Academic Tracker Channel ID 1";
    public static final String channelName = "Academic Tracker Channel";
    public int notificationId;
    public String notificationTitle;
    public String notificationMessage;

    private NotificationManager notificationManager;

    public NotificationHelper(Context base, String notificationTitle, String notificationMessage, int notificationId) {
        super(base);
        this.notificationTitle = notificationTitle;
        this.notificationMessage = notificationMessage;
        this.notificationId = notificationId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setSmallIcon(R.drawable.ic_school)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
    }

    public static int generateNotificationId(NotificationCategory category, int id) {
        String idString = Integer.toString(id);
        String categoryIdString = "";

        switch (category) {
            case COURSE_BEGIN:
                categoryIdString = Integer.toString(NotificationCategory.COURSE_BEGIN.id);
                break;
            case COURSE_END:
                categoryIdString = Integer.toString(NotificationCategory.COURSE_END.id);
                break;
            case ASSESSMENT_DUE:
                categoryIdString = Integer.toString(NotificationCategory.ASSESSMENT_DUE.id);
                break;
        }

        String notificationIdString = categoryIdString + idString;

        return Integer.parseInt(notificationIdString);
    }
}
