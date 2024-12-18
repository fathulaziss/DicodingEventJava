package com.example.dicodingeventjava.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.dicodingeventjava.R;
import com.example.dicodingeventjava.data.local.entity.Event;
import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.data.server.dto.EventDto;
import com.example.dicodingeventjava.data.server.response.EventResponse;
import com.example.dicodingeventjava.data.server.retrofit.ApiConfig;
import com.example.dicodingeventjava.data.server.retrofit.ApiService;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWorker extends Worker {
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private static final String TAG = MyWorker.class.getSimpleName();
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "channel_01";
    private static final String CHANNEL_NAME = "dicoding channel";
    private Result resultStatus;

    @NonNull
    @Override
    public Result doWork() {
        return getEvent();
    }

    private Result getEvent() {
        ApiService apiService = ApiConfig.getApiService();
        Call<EventResponse> client = apiService.getEvent(-1,1);
        client.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        EventDto eventDto = response.body().getListEvents().get(0);
                        showNotification(eventDto.getName(), eventDto.getBeginTime());
                        resultStatus = Result.success();
                    }
                } else {
                    showNotification("Get Event Not Success", response.message());
                    resultStatus = Result.failure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
                showNotification("Get Event Not Success", t.getLocalizedMessage());
                resultStatus = Result.failure();
            }
        });
        return resultStatus != null ? resultStatus : Result.failure();
    }

    private Result getCurrentWeather() {
        Log.d(TAG, "getCurrentWeather: Mulai.....");
        Looper.prepare();
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://event-api.dicoding.dev/events?active=-1&limit=1";
        Log.d(TAG, "getCurrentWeather: " + url);

        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray eventArray = responseObject.getJSONArray("listEvents");
                    JSONObject eventObject = eventArray.getJSONObject(0);
                    String titleEvent = eventObject.getString("name");
                    String dateEvent = eventObject.getString("beginTime");
                    showNotification(titleEvent, dateEvent);
                    Log.d(TAG, "onSuccess: Selesai.....");
                    resultStatus = Result.success();
                } catch (JSONException e) {
                    showNotification("Get Event Not Success", e.getMessage());
                    Log.d(TAG, "onSuccess: Gagal.....");
                    resultStatus = Result.failure();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "onFailure: Gagal.....");
                showNotification("Get Event Failed", error.getMessage());
                resultStatus = Result.failure();
            }
        });

        return resultStatus != null ? resultStatus : Result.failure();
    }

    private void showNotification(String title, String description) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notification.setChannelId(CHANNEL_ID);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID, notification.build());
    }
}
