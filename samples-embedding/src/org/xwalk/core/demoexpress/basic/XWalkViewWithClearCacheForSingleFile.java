// Copyright (c) 2014 Intel Corporation. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.xwalk.core.demoexpress.basic;


import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.demoexpress.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebResourceResponse;
import android.widget.Button;
import android.widget.TextView;

public class XWalkViewWithClearCacheForSingleFile extends XWalkActivity {
    private static final String TAG = "ClearCacheForSingleFile";
    private static final String XWALK_CORE = "/app_xwalkcore/Default/Cache";
    private String xwalkCachePath = "";

    private XWalkView mXWalkView;
    private Button clearBaiduButton;
    private Button loadBaiduButton;
    private TextView cacheSizeTextView;

    private List<String> imageUrls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xwalk_view_with_clearcache_forsinglefile);
        mXWalkView = (XWalkView) findViewById(R.id.xwalkview);
        xwalkCachePath = this.getCacheDir().getParent() + XWALK_CORE;
    }

    @Override
    protected void onXWalkReady() {
        mXWalkView.setResourceClient(new ResourceClient(mXWalkView));
        mXWalkView.setUIClient(new UIClient(mXWalkView));
        mXWalkView.clearCache(true);

        cacheSizeTextView = (TextView) findViewById(R.id.text1);

        clearBaiduButton = (Button) findViewById(R.id.clear_baidu_btn);
        clearBaiduButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mXWalkView != null) {
                    for (String image : imageUrls) {
                        mXWalkView.clearCacheForSingleFile(image);
                    }
                    cacheSizeTextView.setText("Cache Size: "+countCacheSize(xwalkCachePath));
                }
            }
        });

        loadBaiduButton = (Button) findViewById(R.id.baidu_btn);
        loadBaiduButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mXWalkView != null) {
                    mXWalkView.load("http://image.baidu.com/", null);
                }
            }
        });
    }

    class UIClient extends XWalkUIClient{

        public UIClient(XWalkView view) {
            super(view);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onPageLoadStopped(XWalkView view, String url,
                LoadStatus status) {
            // TODO Auto-generated method stub
            super.onPageLoadStopped(view, url, status);
            cacheSizeTextView.setText("Cache Size: "+countCacheSize(xwalkCachePath));
        }
    }

    class ResourceClient extends XWalkResourceClient {

        public ResourceClient(XWalkView xwalkView) {
            super(xwalkView);
        }

        public WebResourceResponse shouldInterceptLoadRequest(XWalkView view, String url) {
            Log.d(TAG, "Intercept load request: " + url);
            imageUrls.add(url);
            return super.shouldInterceptLoadRequest(view, url);
        }
    }

    private static String countCacheSize(String path){
        long blockSize = 0;
        try {
            File cacheDir = new File(path);
            if (cacheDir != null) {
                if (cacheDir.isDirectory()) {
                    blockSize = getFileSizes(cacheDir);
                } else {
                    blockSize = getFileSize(cacheDir);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.d(TAG, "count Cache Size error!");
        }
        return readableFileSize(blockSize);
    }

    private static long getFileSize(File f) throws Exception{
        // TODO Auto-generated method stub
        long size = 0;
        if (f.exists() && f.isFile()) {
             size = f.length();
        }
        return size;
    }

    private static long getFileSizes(File f) throws Exception{
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++){
            if (flist[i].isDirectory()){
                size = size + getFileSizes(flist[i]);
            }
            else{
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    private static String readableFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
