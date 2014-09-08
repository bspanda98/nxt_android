package org.nxt.client.net.model;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by rgreen on 9/7/14.
 */
public class NxtAccount {

    public class NxtAssetBalance {
        public String asset;
        public BigDecimal balance;
    }

    public String publicKey;
    public ArrayList<NxtAssetBalance> assetBalances;
    public BigDecimal balanceQNT;
    public String accountRS;
    public ArrayList<NxtAssetBalance> unconfirmedAssetBalances;
    public String account;
    public BigDecimal effectiveBalanceNXT;
    public BigDecimal unconfirmedBalanceNQT;
    public BigDecimal forgedBalanceNQT;
}
