package info.mabin.android.simplechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;


public class SetupClient extends Activity {
	EditText editServerIPaddr, editServerPort;
	RadioGroup groupType;
	int type;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_client);
		
		editServerIPaddr = (EditText) findViewById(R.id.editServerIP);
		editServerPort = (EditText) findViewById(R.id.editServerPort);
		groupType = (RadioGroup) findViewById(R.id.grpClientType);
		
		findViewById(R.id.btnConnect).setOnClickListener(onClickConnect);
	}
	
	OnClickListener onClickConnect = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(groupType.getCheckedRadioButtonId() == R.id.radioClientTCP)
				type = Net.TYPE_TCP;
			if(groupType.getCheckedRadioButtonId() == R.id.radioClientUDP)
				type = Net.TYPE_UDP;

			
			Intent i = new Intent(SetupClient.this, Chat.class);
			i.putExtra("mode", Net.MODE_CLIENT);
			i.putExtra("ipaddr", editServerIPaddr.getText().toString());
			i.putExtra("port", Integer.parseInt(editServerPort.getText().toString()));			
			i.putExtra("type", type);
			startActivity(i);
			
			finish();
		}
	};
}
