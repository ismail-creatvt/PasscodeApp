package com.creatvt.ismail.androidpasscodetask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PasscodeFragment extends Fragment implements View.OnClickListener,PasscodeDeleteListener {

    private View mView;
    private RecyclerView mPasscodeList;
    private CardView mAddNewPasscodeButton;
    private OnPasscodeFragmentInteractionListener mListener;
    private PasscodeListAdapter adapter;
    private PasscodeDatabaseHelper db;
    public PasscodeFragment() {
        // Required empty public constructor
    }

    public PasscodeFragment setListener(OnPasscodeFragmentInteractionListener listener) {
        mListener = listener;
        //Return the current object reference to create method chain like obj.setFoo().setBar();
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_passcode, container, false);

        mAddNewPasscodeButton = mView.findViewById(R.id.add_new_passcode_button);
        mPasscodeList = mView.findViewById(R.id.passcode_list);
        db = new PasscodeDatabaseHelper(getActivity());
        adapter = new PasscodeListAdapter(db.getPasscodes());
        adapter.setListener(this);
        mPasscodeList.setAdapter(adapter);
        mPasscodeList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAddNewPasscodeButton.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_new_passcode_button:
                mListener.onAddNewPasscodeButtonClicked();
                break;
        }
    }

    @Override
    public void passcodeDeleted() {
        adapter.setPasscodeList(db.getPasscodes());
        adapter.notifyDataSetChanged();
    }

    public interface OnPasscodeFragmentInteractionListener{
        void onAddNewPasscodeButtonClicked();
    }
}
