package info.mabin.android.simplechat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SetupServer extends Activity {
	Context thisContext;
	
	EditText editThisPort;
	RadioGroup groupType;
	int type;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_server);
		
		editThisPort = (EditText) findViewById(R.id.editThisPort);
		groupType = (RadioGroup) findViewById(R.id.grpServerType);
		
		NetGetMyIp getMyIp = new NetGetMyIp((TextView) findViewById(R.id.txtThisIP));
		getMyIp.execute();
		
		findViewById(R.id.btnOpen).setOnClickListener(onClickOpen);
		
	}
	
	OnClickListener onClickOpen = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(groupType.getCheckedRadioButtonId() == R.id.radioServerTCP)
				type = Net.TYPE_TCP;
			if(groupType.getCheckedRadioButtonId() == R.id.radioServerUDP)
				type = Net.TYPE_UDP;

			
			Intent i = new Intent(SetupServer.this, Chat.class);
			i.putExtra("mode", Net.MODE_SERVER);
			i.putExtra("port", Integer.parseInt(editThisPort.getText().toString()));
			i.putExtra("type", type);

			startActivity(i);
			finish();
		}
	};
}
