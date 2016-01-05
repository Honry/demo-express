// Copyright (c) 2014 Intel Corporation. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.xwalk.core.demoexpress.misc;


import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkCookieManager;
import org.xwalk.core.XWalkView;
import org.xwalk.core.demoexpress.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class XWalkViewWithAcceptFileSchemeCookies extends XWalkActivity {
    private static final String TAG = XWalkViewWithAcceptFileSchemeCookies.class.getSimpleName();
    private String url;
    private XWalkView mXWalkView;
    private XWalkCookieManager mCookieManager;
    private Button getCookie;
    private Button setAcceptFileSchemeCookiesTrue;
    private Button setAcceptFileSchemeCookiesFalse;
    private TextView CookieString;
    private String allCookies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xwalk_view_with_accept_file_scheme_cookies);
        getCookie = (Button)findViewById(R.id.remove_session_cookie);
        setAcceptFileSchemeCookiesTrue = (Button)findViewById(R.id.remove_expired_cookie);
        setAcceptFileSchemeCookiesFalse = (Button)findViewById(R.id.remove_all_cookie);
        CookieString = (TextView)findViewById(R.id.getcookie);

        mCookieManager = new XWalkCookieManager();
        mXWalkView = (XWalkView) findViewById(R.id.xwalkview);
    }

    @Override
    protected void onXWalkReady() {

        mCookieManager.setAcceptCookie(true);
        Log.d(TAG, "mCookieManager.acceptCookie()="+mCookieManager.acceptCookie());
        mCookieManager.removeAllCookie();
        mCookieManager.flushCookieStore();
        Log.d(TAG, "mCookieManager.hasCookies()="+mCookieManager.hasCookies());

        String value = "test123";
        url = "file:///android_asset/cookie_test.html?value=" + value;

        getCookie.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mXWalkView.load(url, null);
                allCookies = mCookieManager.getCookie(url);
                CookieString.setText(allCookies);
                Log.d(TAG, "mCookieManager allowFileSchemeCookies="+mCookieManager.allowFileSchemeCookies());
            }
        });

        setAcceptFileSchemeCookiesTrue.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mCookieManager.setAcceptFileSchemeCookies(true);
            }
        });

        setAcceptFileSchemeCookiesFalse.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mCookieManager.setAcceptFileSchemeCookies(false);
            }
        });
    }
}
