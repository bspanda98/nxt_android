package com.nxt;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AccountFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        populateTransactions(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    private void populateTransactions(View rootView){
        TableLayout transactions = (TableLayout) rootView.findViewById(R.id.transactions_table);
        //dummy data
        TableRow row1 = new TableRow(getActivity());
        TableRow row2 = new TableRow(getActivity());
        TableRow row3 = new TableRow(getActivity());
        TableRow row4 = new TableRow(getActivity());

        TextView account1 = new TextView(getActivity());
        account1.setText("NXT-MRCC-2YLS-8M54-3CMAJ");
        TextView account2 = new TextView(getActivity());
        account2.setText("NXT-MRCC-2YLS-8M54-3CMAJ");
        TextView account3 = new TextView(getActivity());
        account3.setText("NXT-MRCC-2YLS-8M54-3CMAJ");
        TextView account4 = new TextView(getActivity());
        account4.setText("NXT-MRCC-2YLS-8M54-3CMAJ");

        row1.addView(account1);
        row2.addView(account2);
        row3.addView(account3);
        row4.addView(account4);

        transactions.addView(row1);
        transactions.addView(row2);
        transactions.addView(row3);
        transactions.addView(row4);
    }
}