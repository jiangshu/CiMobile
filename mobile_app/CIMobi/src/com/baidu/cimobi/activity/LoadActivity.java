package com.baidu.cimobi.activity;

import com.baidu.cimobi.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Intent;


public class LoadActivity extends Activity {
	private EditText IpaddrEdit ;
	private EditText UsernameEdit ;
	private Button LoginBtn ;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		
        IpaddrEdit  = (EditText)findViewById(R.id.IpaddrEdit);
    	UsernameEdit = (EditText)findViewById(R.id.UsernameEdit);
    	LoginBtn = (Button)findViewById(R.id.LoginBtn);
    	LoginBtn.setOnClickListener(new RegisterClickListener());
	}
	
	class RegisterClickListener implements OnClickListener{
		
		public void onClick(View v){
			String ip = IpaddrEdit.getText().toString();
			String user = UsernameEdit.getText().toString();
			Intent infoIntent = new Intent();
			infoIntent.putExtra("serverIp", ip);
			infoIntent.putExtra("user", user);
			infoIntent.setClass(LoadActivity.this,InfoActivity.class);
			LoadActivity.this.startActivity(infoIntent);
//	        Intent intent = new Intent();
//	        intent.setAction("android.intent.action.VIEW");    
//	        Uri content_url = Uri.parse("http://www.baidu.com");   
//	        intent.setData(content_url);           
//	        intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");  
//	        startActivity(intent);
		}	
	}

}



