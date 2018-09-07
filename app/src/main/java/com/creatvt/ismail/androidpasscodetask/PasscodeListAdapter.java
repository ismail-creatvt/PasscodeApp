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

public class PasscodeListAdapter extends RecyclerView.Adapter<PasscodeViewHolder> implements PasscodeDeleteListener,PopUpListener{

    private List<Passcode> mPasscodeList;
    private PasscodeDeleteListener mListener;
    private List<PopUpListener> mPopUpListener;
    public PasscodeListAdapter(List<Passcode> passcodes){
        mPasscodeList = passcodes;
        mPopUpListener = new ArrayList<>();
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
        PasscodeViewHolder passcodeViewHolder = new PasscodeViewHolder(view).setListener(this);
        mPopUpListener.add(passcodeViewHolder);
        return passcodeViewHolder;
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

    @Override
    public boolean onPopUpClose() {
        for(int i=0;i<mPopUpListener.size();i++){
            if(mPopUpListener.get(i).onPopUpClose()){
                return true;
            }
        }
        return false;
    }
}
