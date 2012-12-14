package info.mabin.android.simplechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ModeSelect extends Activity{
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modeselect);
		
		findViewById(R.id.btnServer).setOnClickListener(onClickServerMode);
		findViewById(R.id.btnClient).setOnClickListener(onClickClientMode);
	}

	OnClickListener onClickServerMode = new OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent i = new Intent(ModeSelect.this, SetupServer.class);
			startActivity(i);
			finish();
		}
	};

	OnClickListener onClickClientMode = new OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent i = new Intent(ModeSelect.this, SetupClient.class);
			startActivity(i);
			finish();
		}
	};
}
