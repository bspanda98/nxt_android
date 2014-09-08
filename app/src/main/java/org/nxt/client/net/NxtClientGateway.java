package org.nxt.client.net;

import android.content.Context;
import android.os.Build;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.nxt.client.Constants;
import org.nxt.client.util.NxtLog;

import java.util.HashMap;
import java.util.Map;

public class NxtClientGateway {

    private static final String TAG = "RaveServerGateway";

    private static final String USER_AGENT_PATTERN = "NXTClient/%s;Android/%s;Device/%s;Manufacturer/%s;";

    private static final int NETWORK_DISK_CACHE_SIZE = 1024 * 1024 * 5; // 5MB

    //private Context context;
    private String userAgent;
    private RequestQueue queue;

    /**
     * The callback when a server response is received
     */
    public interface NxtClientJSONResponseHandler {
        /**
         * The response callback
         *
         * If there is an error, there won't a response and vice versa. They are mutually exclusive.
         *
         * @param data The response when a successful response is received
         * @param error The error if there was an error
         */
        public void onResponse(JSONObject data, NxtNetworkException error);
    }

    public NxtClientGateway(Context context) {
        //this.context = context;
        userAgent = buildUserAgent(context);
        // Instantiate the cache
        Cache cache = new DiskBasedCache(context.getCacheDir(), NETWORK_DISK_CACHE_SIZE);
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        queue = new RequestQueue(cache, network);
        // Start the queue
        queue.start();
    }

    /**
     * Queues a request for execution
     *
     * @param request The request
     */
    public <T> void queueRequest(Request<T> request) {
        queue.add(request);
    }

    public JsonObjectRequest createJSONGETRequest(final String servicePath, JSONObject requestJson, final NxtClientJSONResponseHandler responseHandler) {
        return createJSONRequest(Request.Method.GET, servicePath, requestJson, responseHandler);
    }

    public JsonObjectRequest createJSONPOSTRequest(final String servicePath, JSONObject requestJson, final NxtClientJSONResponseHandler responseHandler) {
        return createJSONRequest(Request.Method.POST, servicePath, requestJson, responseHandler);
    }

    public JsonObjectRequest createJSONPUTRequest(final String servicePath, JSONObject requestJson, final NxtClientJSONResponseHandler responseHandler) {
        return createJSONRequest(Request.Method.PUT, servicePath, requestJson, responseHandler);
    }

    public JsonObjectRequest createJSONDELETERequest(final String servicePath, JSONObject requestJson, final NxtClientJSONResponseHandler responseHandler) {
        return createJSONRequest(Request.Method.DELETE, servicePath, requestJson, responseHandler);
    }

    /**
     * Creates a JSON request
     *
     * @param method GET, POST, PUT or DELETE (see Request.Method)
     * @param servicePath The relative path plus querystring for the service
     * @param requestJson The POST body JSON or null
     * @param responseHandler An optional handler to handle the response
     * @return
     */
    public JsonObjectRequest createJSONRequest(final int method, final String servicePath, JSONObject requestJson, final NxtClientJSONResponseHandler responseHandler) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(method, createFullURL(servicePath), requestJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("errorCode")) {
                    try {
                        String reason = "NXT Error Code " + response.getString("errorCode");
                        NxtNetworkException exception = null;
                        if (response.has("errorDescription")) {
                            reason = reason + " " + response.get("errorDescription");
                        }
                        exception = new NxtNetworkException(reason);
                        safelyCallback(responseHandler, response, exception, servicePath);
                        return;
                    } catch (JSONException e) {
                        safelyCallback(responseHandler, response, new NxtNetworkException("Error Parsing Error JSON", e), servicePath);
                        return;
                    }
                }
                safelyCallback(responseHandler, response, null, servicePath);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NxtLog.e(TAG, "Error sending request: " + error.getLocalizedMessage());
                if (responseHandler != null) {
                    responseHandler.onResponse(null, new NxtNetworkException(error));
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("User-Agent", userAgent);
                // Put any custom headers here
                return params;
            }
        };
        return jsonRequest;
    }

    /**
     * Creates the full URL to request to from a given servicePath
     *
     * Eg:  If the servicePath is /test, the full URL will look like https://peer/test (a hypothetical example)
     *
     * @param servicePath The relative service path
     * @return A complete URL for an HTTP request
     */
    private String createFullURL(String servicePath) {
        String sep = "";
        if (!servicePath.startsWith("/")) {
            sep = "/";
        }
        return Constants.PEER_URL + sep + servicePath;
    }

    private String buildUserAgent(Context context) {
        String userAgent = String.format(USER_AGENT_PATTERN, Constants.VERSION,
                Build.VERSION.RELEASE,
                Build.MODEL, Build.MANUFACTURER);
        return userAgent;
    }


    /**
     * A wrapper to callback to the requester and catch any runtime exceptions thrown
     *
     * @param responseHandler
     * @param responseData
     * @param exception
     * @param servicePath
     */
    private void safelyCallback(NxtClientJSONResponseHandler responseHandler, JSONObject responseData, NxtNetworkException exception, String servicePath) {
        if (responseHandler != null) {
            try {
                responseHandler.onResponse(responseData, exception);
            } catch (Throwable t) {
                NxtLog.e(TAG, "An error was caught sending response back from server for call to [" + servicePath + "]", t);
            }
        }

    }

}

