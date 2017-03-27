package com.study.whutwf.doubanmovie.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.study.whutwf.doubanmovie.utils.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by whutwf on 17-3-23.
 */

public class ImageDownloader<T> extends HandlerThread {

    private static final String TAG = "ImageDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;

    private Boolean mHasQuit = false;
    private Handler mImageRequestHandler;
    private ConcurrentMap<T, String> mImageRequestMap = new ConcurrentHashMap<>();
    //接收UI线程的Handler
    private Handler mResponseHandler;
    private ImageDownloadListener<T> mImageDownloadListener;

    public interface ImageDownloadListener<T> {
        void onImageDownloaded(T target, Bitmap bitmap);
    }

    public void setImageDownloadListener(ImageDownloadListener<T> listener) {
        mImageDownloadListener = listener;
    }

    public ImageDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    public void queueTargetImage(T target, String url) {

        if (url == null) {
            mImageRequestMap.remove(target);
        } else {
            mImageRequestMap.put(target, url);
            mImageRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                    .sendToTarget();
        }
    }

    @Override
    public boolean quit() {
        mHasQuit = true;
        return super.quit();
    }

    @Override
    protected void onLooperPrepared() {
        mImageRequestHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T target = (T) msg.obj;
                    handleRequest(target);
                }
            }
        };
    }

    public void clearQueue() {
        mImageRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
    }

    private void handleRequest(final T target) {
        final String imageUrl = mImageRequestMap.get(target);

        if (imageUrl == null) {
            return;
        }

        try {
            byte[] bitmapBytes = NetworkUtils.getUrlBytes(imageUrl);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mImageRequestMap.get(target) != imageUrl || mHasQuit) {
                        return;
                    }

                    mImageRequestMap.remove(target);
                    mImageDownloadListener.onImageDownloaded(target, bitmap);
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "error downloading image", e);
            e.printStackTrace();
        }
    }
}
