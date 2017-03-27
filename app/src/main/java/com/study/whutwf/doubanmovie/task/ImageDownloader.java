package com.study.whutwf.doubanmovie.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;

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
    //图片预缓存
    private LruCache<String, Bitmap> mBitmapLruCache;

    public interface ImageDownloadListener<T> {
        void onImageDownloaded(T target, Bitmap bitmap);
    }

    public void setImageDownloadListener(ImageDownloadListener<T> listener) {
        mImageDownloadListener = listener;
    }

    public ImageDownloader(Handler responseHandler, LruCache<String, Bitmap> bitmapLruCache) {
        super(TAG);
        mResponseHandler = responseHandler;
        mBitmapLruCache = bitmapLruCache;
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
            final String url = mImageRequestMap.get(target);
            final Bitmap bitmap;
            if (mBitmapLruCache.get(url) != null) {
                bitmap = mBitmapLruCache.get(url);
                Log.i(TAG, "==========Cache begin");
            } else {
                byte[] bitmapBytes = NetworkUtils.getUrlBytes(imageUrl);
                bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                mBitmapLruCache.put(url, bitmap);
                Log.i(TAG, "==========Cache create");

            }

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (url != imageUrl || mHasQuit) {
                        return;
                    }

                    mImageRequestMap.remove(target);
                    //注意这里是使用了匿函数定义接口，然后在post处调用更新UI
                    //其中每一个UI线程都绑定这一个handler，并伴随UI，使用的就是UI handler更新数据
                    mImageDownloadListener.onImageDownloaded(target, bitmap);
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "error downloading image", e);
            e.printStackTrace();
        }
    }
}
