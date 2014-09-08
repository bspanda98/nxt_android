package org.nxt.client;

import android.content.Context;

import org.nxt.client.net.NxtClientAPI;
import org.nxt.client.util.NxtLog;

/**
 * Created by rgreen on 9/7/14.
 */
public class SystemManager {
    private static final String TAG = "SystemManager";

    private static SystemManager instance;

    private boolean initialized;
    private NxtClientAPI clientApi;

    private SystemManager() {
    }

    public static SystemManager getInstance() {
        if (instance == null) {
            instance = new SystemManager();
        }
        return instance;
    }

    public void initialize(Context context) {
        NxtLog.setLogLevel(NxtLog.LogLevel.DEBUG);
        clientApi = new NxtClientAPI(context);
        NxtLog.i(TAG, "NXT SystemManager Initialized");
        initialized = true;
    }

    public void onLowMemory() {

    }

    /**
     * Gets the NXT Client API
     *
     * @return The NXTClientAPI - network interface
     */
    public NxtClientAPI getClientAPI() {
        assertInitialized();
        return clientApi;
    }

    private void assertInitialized() {
        if (!initialized) {
            throw new RuntimeException("Must initialize before calling this method!");
        }
    }
}
