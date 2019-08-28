package examples.aaronhoskins.com.broadcastrecieversandservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String MESSAGE_KEY = "value";
    @BindView(R.id.etUserInput)
    EditText etUserInputForBroadcast;

    @BindView(R.id.tvMessageReceived)
    TextView tvMessageRecieved;

    @BindView(R.id.etNumOne)
    EditText etNumOne;

    @BindView(R.id.etNumTwo)
    EditText etNumTwo;

    //Instance of receiver
    MyBroadcastReceiver myBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //instantiate the receiver
        myBroadcastReceiver = new MyBroadcastReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Register no restrictions
        registerReceiver(myBroadcastReceiver, new IntentFilter("send.broadcast"));
        //Register Locally
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(myBroadcastReceiver, new IntentFilter("send.broadcast"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Unregister no restrictions
        unregisterReceiver(myBroadcastReceiver);
        //Unregister Locally;
        //LocalBroadcastManager.getInstance(this)
        //        .unregisterReceiver(myBroadcastReceiver);
    }

    public void sendBroadcast(View view)/*Just on click, not the method that sends*/ {
        switch (view.getId()) {
            case R.id.btnSendBroadcast:
                final String userInput = etUserInputForBroadcast.getText().toString();
                Intent intent = new Intent("send.broadcast");
                Bundle bundle = new Bundle();
                bundle.putString(MESSAGE_KEY, userInput);
                intent.putExtras(bundle);
                //Send broadcast - no restrictions
                //sendBroadcast(intent);
                //Send Broadcast locally to the app;
                //LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                //Send Broadcast - requires permission to receive
                sendBroadcast(intent, "broadcast.permission");
                break;
        }
    }

    public void onStartIntentSeviceClicked(View view) {
        final String numOneInput = etNumOne.getText().toString();
        final String numTwoInput = etNumTwo.getText().toString();

        switch (view.getId()) {
            case R.id.btnStartIntentAdd:
                MyIntentService.startActionAdd(this, Integer.parseInt(numOneInput), Integer.parseInt(numTwoInput));
                break;
            case R.id.btnStartIntentMul:
                MyIntentService.startActionMul(this, numOneInput, numTwoInput);
                break;
        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            final String message = bundle.getString(MESSAGE_KEY);
            tvMessageRecieved.setText(message);
        }
    }
}
