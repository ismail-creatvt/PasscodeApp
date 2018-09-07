package com.creatvt.ismail.androidpasscodetask;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class PasscodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,PopUpListener {
    public TextView passcodeName,passcodeTime,passcodeDay,passcodeType;
    private CardView passcodeItem;
    private PopupWindow mPopupWindow;
    private PasscodeDeleteListener mListener;
    public PasscodeViewHolder(View view) {
        super(view);

        passcodeItem = view.findViewById(R.id.passcode_item);
        passcodeName = view.findViewById(R.id.passcode_name);
        passcodeType = view.findViewById(R.id.passcode_type);
        passcodeDay = view.findViewById(R.id.passcode_day);
        passcodeTime = view.findViewById(R.id.passcode_time);

        passcodeItem.setOnClickListener(this);
    }

    public PasscodeViewHolder setListener(PasscodeDeleteListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.passcode_item){
            View view = LayoutInflater.from(v.getContext()).inflate(R.layout.passcode_popup,null);

            TextView edit = view.findViewById(R.id.edit);
            TextView delete = view.findViewById(R.id.delete);
            TextView share = view.findViewById(R.id.share);

            mPopupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            mPopupWindow.showAtLocation(v,Gravity.CENTER,0,0);
            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
            share.setOnClickListener(this);

        }
        else if(v.getId() == R.id.edit){

        }
        else if(v.getId() == R.id.delete){
            PasscodeDatabaseHelper db = new PasscodeDatabaseHelper(v.getContext());
            if(db.deletePasscode(passcodeName.getText().toString())){
                Toast.makeText(v.getContext(),"Passcode Deleted",Toast.LENGTH_SHORT).show();
                mListener.passcodeDeleted();
                mPopupWindow.dismiss();
            }
            else{
                Toast.makeText(v.getContext(),"Passcode Not Deleted",Toast.LENGTH_SHORT).show();
            }
        }
        else if (v.getId() == R.id.share){
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            String data = "Passcode Name : " + passcodeName.getText().toString() + "\n"
                    + "Passcode Type : " + passcodeType.getText().toString() + "\n";
            if(passcodeType.getText().toString().toLowerCase().equals("time bound")){
                data += "Passcode Time : " + passcodeTime.getText().toString() + "\n"
                        + "Passcode Day : " + passcodeDay.getText().toString();
            }

            share.putExtra(Intent.EXTRA_SUBJECT,  "Passcode Data");
            share.putExtra(Intent.EXTRA_TEXT,data);
            v.getContext().startActivity(Intent.createChooser(share, "Share Passcode"));
            mPopupWindow.dismiss();
        }

    }

    @Override
    public boolean onPopUpClose() {
        if(mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
            return true;
        }
        return false;
    }
}
