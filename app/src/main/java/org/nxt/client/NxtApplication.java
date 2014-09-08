package org.nxt.client;

import android.app.Application;

import org.nxt.client.net.NxtClientAPI;
import org.nxt.client.net.NxtNetworkException;
import org.nxt.client.net.model.NxtAccount;
import org.nxt.client.util.NxtLog;

/**
 * Created by rgreen on 9/7/14.
 */
public class NxtApplication extends Application {

    private static final String TAG = "NxtApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        SystemManager mgr = SystemManager.getInstance();
        mgr.initialize(this);
        NxtLog.i(TAG, "Calling getAccount 7898870630272254992");
        mgr.getClientAPI().getAccount("7898870630272254992", new NxtClientAPI.NxtAccountListener() {
            @Override
            public void onCompletion(NxtAccount account, NxtNetworkException exception) {
                if (exception != null) {
                    NxtLog.i(TAG, exception.getMessage());
                } else {
                    NxtLog.i(TAG, "Account: " + account.account + " " + account.effectiveBalanceNXT);
                }
            }
        });
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        SystemManager.getInstance().onLowMemory();
    }
}
