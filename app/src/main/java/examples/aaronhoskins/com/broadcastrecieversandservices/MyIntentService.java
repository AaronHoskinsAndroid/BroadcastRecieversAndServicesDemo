package examples.aaronhoskins.com.broadcastrecieversandservices;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import static examples.aaronhoskins.com.broadcastrecieversandservices.MainActivity.MESSAGE_KEY;


public class MyIntentService extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_ADD
            = "examples.aaronhoskins.com.broadcastrecieversandservices.action.AddNumbers";
    private static final String ACTION_MULTIPLY
            = "examples.aaronhoskins.com.broadcastrecieversandservices.action.MultiplyNumbers";

    private static final String EXTRA_NUMBER_ONE
            = "examples.aaronhoskins.com.broadcastrecieversandservices.extra.NumberOne";
    private static final String EXTRA_NUMBER_TWO
            = "examples.aaronhoskins.com.broadcastrecieversandservices.extra.NumberTwo";

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void startActionAdd(Context context, int numOne, int numTwo) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_NUMBER_ONE, String.valueOf(numOne));
        intent.putExtra(EXTRA_NUMBER_TWO, String.valueOf(numTwo));
        context.startService(intent);
    }

    public static void startActionMul(Context context, String numOne, String numTwo) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_MULTIPLY);
        intent.putExtra(EXTRA_NUMBER_ONE, String.valueOf(numOne));
        intent.putExtra(EXTRA_NUMBER_TWO, String.valueOf(numTwo));
        context.startService(intent);
    }

    //What the service will actually run
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD.equals(action)) {
                final int param1 = Integer.parseInt(intent.getStringExtra(EXTRA_NUMBER_ONE));
                final int param2 = Integer.parseInt(intent.getStringExtra(EXTRA_NUMBER_TWO));
                handleActionAdd(param1, param2);
            } else if (ACTION_MULTIPLY.equals(action)) {
                final int param1 = Integer.parseInt(intent.getStringExtra(EXTRA_NUMBER_ONE));
                final int param2 = Integer.parseInt(intent.getStringExtra(EXTRA_NUMBER_TWO));
                handleActionMultiply(param1, param2);
            }
        }
    }

    private void handleActionAdd(int numOne, int numTwo) {
        final int total = numOne + numTwo;
        Intent intent = new Intent("send.broadcast");
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE_KEY, String.valueOf(total));
        intent.putExtras(bundle);
        getBaseContext().sendBroadcast(intent);
    }

    private void handleActionMultiply(int numOne, int numTwo) {
        final int total = numOne * numTwo;
        Intent intent = new Intent("send.broadcast");
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE_KEY, String.valueOf(total));
        intent.putExtras(bundle);
        getBaseContext().sendBroadcast(intent);
    }
}
