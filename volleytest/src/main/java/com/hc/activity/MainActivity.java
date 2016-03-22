package com.hc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.hc.activity.adapter.MListAdapter;
import com.hc.activity.bean.Info;

import java.util.ArrayList;


public class MainActivity extends Activity {
	private ListView mListView;
	private ArrayList<Info> infos;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView  = (ListView) findViewById(R.id.listView);
        initData();
        MListAdapter adapter = new MListAdapter(this,infos);
        mListView.setAdapter(adapter);
    }


    private void initData() {
    	infos = new ArrayList<Info>();
		Info info1 = new Info("http://imgstatic.baidu.com/img/image/shouye/fanbingbing.jpg");
		Info info2 = new Info("http://imgstatic.baidu.com/img/image/shouye/liuyifei.jpg");
		Info info3 = new Info("http://imgstatic.baidu.com/img/image/shouye/wanglihong.jpg");
		Info info4 = new Info("http://imgstatic.baidu.com/img/image/shouye/gaoyuanyuan.jpg");
		Info info5 = new Info("http://imgstatic.baidu.com/img/image/shouye/yaodi.jpg");
		Info info6 = new Info("http://imgstatic.baidu.com/img/image/shouye/zhonghanliang.jpg");
		Info info7 = new Info("http://imgstatic.baidu.com/img/image/shouye/xiezhen.jpg");
		Info info8 = new Info("http://imgstatic.baidu.com/img/image/shouye/yiping3.jpg");
		Info info9 = new Info("http://imgstatic.baidu.com/img/image/shouye/erping4.jpg");
		Info info10 = new Info("http://imgstatic.baidu.com/img/image/shouye/hangeng.jpg");
		Info info11 = new Info("http://imgstatic.baidu.com/img/image/shouye/liuyan1.jpg");
		Info info12 = new Info("http://imgstatic.baidu.com/img/image/shouye/liushishi1.jpg");
		Info info13 = new Info("http://imgstatic.baidu.com/img/image/shouye/sunli1.jpg");
		Info info14 = new Info("http://imgstatic.baidu.com/img/image/shouye/tangyan1.jpg");
		Info info15 = new Info("http://imgstatic.baidu.com/img/image/shouye/zhanggenshuo1.jpg");
		Info info16 = new Info("http://imgstatic.baidu.com/img/image/shouye/xiaohua0605.jpg");
		infos.add(info1);
		infos.add(info2);
		infos.add(info3);
		infos.add(info4);
		infos.add(info5);
		infos.add(info6);
		infos.add(info7);
		infos.add(info8);
		infos.add(info9);
		infos.add(info10);
		infos.add(info11);
		infos.add(info12);
		infos.add(info13);
		infos.add(info14);
		infos.add(info15);
		infos.add(info16);
	}

	
    
}
