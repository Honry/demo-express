package org.xwalk.core.demoexpress.client;


import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.TextView;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.demoexpress.R;

public class XWalkViewWithClientReceivedSSLError extends XWalkActivity {

    private XWalkView mXWalkView;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xwalk_view_with_client_received_sslerror);
    }

    @Override
    protected void onXWalkReady() {
        tv = (TextView)findViewById(R.id.client_sslerror_tip);
        tv.setTextColor(Color.GREEN);

        mXWalkView = (XWalkView) findViewById(R.id.client_sslerror_xwalk_view);
        mXWalkView.setResourceClient(new XWalkResourceClient(mXWalkView) {
            @Override
            public void onReceivedSslError(XWalkView view, ValueCallback<Boolean> callback, SslError error) {
                tv.setText("XWalkResourceClient.onReceivedSslError is invoked. Event: " + error.toString());
                //super.onReceivedSslError(view, callback, error);
            }
        });

        Button bt = (Button)findViewById(R.id.refresh_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXWalkView.load("https://kyfw.12306.cn/otn/regist/init", null);
            }
        });

        mXWalkView.load("https://kyfw.12306.cn/otn/regist/init", null);
    }
}