package org.nxt.client.net;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.nxt.client.net.model.NxtAccount;

import java.math.BigDecimal;

/**
 * Created by rgreen on 9/7/14.
 */
public class NxtClientAPI {

    private NxtClientGateway gw;

    public NxtClientAPI(Context context) {
        gw = new NxtClientGateway(context);
    }

    public interface NxtAccountListener {
        public void onCompletion(NxtAccount account, NxtNetworkException exception);
    }

    public void getAccount(String accountNum, final NxtAccountListener listener) {
        JsonObjectRequest req = gw.createJSONGETRequest("nxt?requestType=getAccount&account=" + accountNum, null, new NxtClientGateway.NxtClientJSONResponseHandler() {
            @Override
            public void onResponse(JSONObject data, NxtNetworkException error) {
                if (error != null) {
                    if (listener != null) {
                        listener.onCompletion(null, error);
                    }
                    return;
                }
                if (listener != null) {
                    NxtAccount account = new NxtAccount();
                    try {
                        if (data.has("publicKey")) {
                            account.publicKey = data.getString("publicKey");
                        }
                        if (data.has("assetBalances")) {
                            // TODO
                        }
                        if (data.has("balanceQNT")) {
                            account.balanceQNT = new BigDecimal(data.getString("balanceQNT"));
                        }
                        if (data.has("accountRS")) {
                            account.accountRS = data.getString("accountRS");
                        }
                        if (data.has("unconfirmedAssetBalances")) {
                            // TODO
                        }
                        if (data.has("account")) {
                            account.account = data.getString("account");
                        }
                        if (data.has("effectiveBalanceNXT")) {
                            account.effectiveBalanceNXT = new BigDecimal(data.getString("effectiveBalanceNXT"));
                        }
                        if (data.has("unconfirmedBalanceNQT")) {
                            account.unconfirmedBalanceNQT = new BigDecimal(data.getString("unconfirmedBalanceNQT"));
                        }
                        if (data.has("forgedBalanceNQT")) {
                            account.unconfirmedBalanceNQT = new BigDecimal(data.getString("forgedBalanceNQT"));
                        }
                    } catch (JSONException exception) {
                        listener.onCompletion(null, new NxtNetworkException("Error parsing JSON", exception));
                        return;
                    }
                    listener.onCompletion(account, null);
                }
            }
        });
        gw.queueRequest(req);
    }
}
