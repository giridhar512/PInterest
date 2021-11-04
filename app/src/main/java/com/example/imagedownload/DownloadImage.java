package com.example.imagedownload;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class DownloadImage {
    private static final int NO_OF_MAX_TRIES = 5;
    private static final long RETRY_INTERVAL_5000_MS = 5000;
    private final File mDownloadRoot;
    private final ICache mCache;

    public DownloadImage(Context context, ICache cache) {
        mDownloadRoot = context.getCacheDir();
        mCache = cache;
    }

    public String downloadImage(String httpUrl) throws InterruptedException {
        StringBuilder cachePath = new StringBuilder();
        Thread downloadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    cachePath.append(download(httpUrl));
                } catch (Exception e) {
                    // Do nothing
                }
            }
        });

        downloadThread.start();
        downloadThread.join();
        return cachePath.toString();
    }

    private String download(String imageUrl) {
        String cachePath = mCache.get(imageUrl);
        if (cachePath != null) return cachePath;
        int tryCount = 0;
        File destination = null;

        while (tryCount < NO_OF_MAX_TRIES) {
            try {
                URL url = new URL(imageUrl);
                destination = File.createTempFile("image-", ".tmp", mDownloadRoot);
                destination.deleteOnExit();

                InputStream is = url.openStream();
                OutputStream os = new FileOutputStream(destination);

                byte[] b = new byte[2048];
                int length;
                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }
                is.close();
                os.close();
                break;
            } catch (Exception ex) {
                Log.e("DownloadImage", "Download image failed due to " + ex);
                tryCount++;
            }
        }
        if (destination != null) {
            mCache.store(imageUrl, destination.getAbsolutePath());
            return destination.getAbsolutePath();
        }
        return null;
    }
}
