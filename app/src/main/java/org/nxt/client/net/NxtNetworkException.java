package org.nxt.client.net;

/**
 * Created by rgreen on 9/7/14.
 */
public class NxtNetworkException extends Exception {

    public NxtNetworkException() {
        super();
    }

    public NxtNetworkException(String detailMessage) {
        super(detailMessage);
    }

    public NxtNetworkException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NxtNetworkException(Throwable throwable) {
        super(throwable);
    }
}
