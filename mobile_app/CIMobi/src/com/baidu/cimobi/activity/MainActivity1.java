package com.baidu.cimobi.activity;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.net.Uri;
import android.content.Intent;


public class MainActivity1 extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listData();
	}
	
	private void listData(){
		String[] names = new String[]
				{"Native","Chrome","QQ","Opera","UC"};
		this.setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names));
	}
	
	protected void onListItemClick(ListView l,View v,int position, long id){
		super.onListItemClick(l,v,position,id);
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		Toast.makeText(this, "ä¯ÀÀÆ÷Îª£º"+keyword, Toast.LENGTH_LONG).show();
		openBrowser();
	}
	
	private void openBrowser(){
//		Uri uri = Uri.parse("http://wap.baidu.com");
//        Intent it = new Intent(Intent.ACTION_VIEW, uri); 
//        startActivity(it);
        
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");    
        Uri content_url = Uri.parse("http://www.baidu.com");   
        intent.setData(content_url);           
        intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");  
//        intent.setClassName("com.uc.browser","com.uc.browser.ActivityUpdate");  
        startActivity(intent);
	}
	
	private String getBrowser(){
		
//		¡¡¡¡ucä¯ÀÀÆ÷"£º"com.uc.browser", "com.uc.browser.ActivityUpdate¡°
//		¡¡¡¡opera£º"com.opera.mini.android", "com.opera.mini.android.Browser"
//		¡¡¡¡qqä¯ÀÀÆ÷£º"com.tencent.mtt", "com.tencent.mtt.MainActivity"
		
		return "";
	}
	
	
	
}




















//public class MainActivity extends Activity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//}
