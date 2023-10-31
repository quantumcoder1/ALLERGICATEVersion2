package com.example.allergicateversion2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PhotoFetcher {
    private ImageView imageView;
    private String apiKey = "AIzaSyAXrKBr0Z5qfRC-F-eZH_Rxbbpb4IHuwo4";

    public PhotoFetcher(ImageView imageView) {
        this.imageView = imageView;
    }

    public void fetchAndSetPhoto(String photoReference, int width, int height) {
        // Build the URL to fetch the image
        String imageUrl = "https://maps.googleapis.com/maps/api/place/photo" +
                "?maxwidth=" + width +
                "&maxheight=" + height +
                "&photoreference=" + photoReference +
                "&key=" + apiKey;

        // Use OkHttp to download the image from the URL
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle the failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());

                    // Display the image on the main UI thread
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                } else {
                    // Handle the error (e.g., display an error message)
                }
            }
        });
    }
}
