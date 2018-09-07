package com.creatvt.ismail.androidpasscodetask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PasscodeListAdapter extends RecyclerView.Adapter<PasscodeViewHolder> implements PasscodeDeleteListener{

    private List<Passcode> mPasscodeList;
    private PasscodeDeleteListener mListener;
    public PasscodeListAdapter(List<Passcode> passcodes){
        mPasscodeList = passcodes;
    }

    public void setPasscodeList(List<Passcode> passcodeList) {
        mPasscodeList = passcodeList;
    }

    public void setListener(PasscodeDeleteListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public PasscodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passcode_item,parent,false);
        return new PasscodeViewHolder(view).setListener(this);
    }

    @Override
    public void onBindViewHolder(@NonNull PasscodeViewHolder holder, int position) {
        Passcode passcode = mPasscodeList.get(position);

        holder.passcodeName.setText(passcode.getPasscodeName());
        holder.passcodeType.setText(passcode.getPasscodeType());
        if(passcode.getPasscodeType().toLowerCase().equals("time bound")){
            holder.passcodeDay.setText(passcode.getPasscodeDay());
            holder.passcodeTime.setText(getTimeString(passcode.getStartTime()) + "-" + getTimeString(passcode.getEndTime()));
        }
    }

    public String getTimeString(long timestamp){
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        return formatter.format(new Date(timestamp));

    }

    @Override
    public int getItemCount() {
        return mPasscodeList == null ? 0 : mPasscodeList.size();
    }

    @Override
    public void passcodeDeleted() {
        mListener.passcodeDeleted();
    }

}
