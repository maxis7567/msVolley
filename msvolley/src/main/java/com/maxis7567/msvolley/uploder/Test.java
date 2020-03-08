package com.maxis7567.msvolley.uploder;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

public class Test {
    public static void uploadMultipart(final Context context, Uri path) {
        try {
            String uploadId =
                    new MultipartUploadRequest(context, "http://192.168.1.33/uploader/public/api/profile")
                            .setUtf8Charset()
                            .addHeader("Accept","application/json")
                            .addFileToUpload(path.getPath(), "image")
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2)
                            .setMethod("post")
                            .setDelegate(new UploadStatusDelegate() {
                                @Override
                                public void onProgress(Context context, UploadInfo uploadInfo) {

                                }

                                @Override
                                public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

                                }

                                @Override
                                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {

                                }

                                @Override
                                public void onCancelled(Context context, UploadInfo uploadInfo) {

                                }
                            })
                            .startUpload();
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }
}
