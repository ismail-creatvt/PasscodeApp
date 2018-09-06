package com.creatvt.ismail.androidpasscodetask;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddPasscodeFragment extends Fragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener, TimePicker.OnTimeChangedListener {

    private OnAddPasscodeFragmentInteractionListener mListener;
    private View mView;
    private Spinner mTypeSpinner;
    private CardView mTimeCard;
    private TextView mStartTime,mEndTime;
    private EditText mPasscodeNameField;
    private EditText mPasscodeField;
    private EditText mReEnterPasscodeField;
    private AppCompatCheckBox mEveryDayCheckbox;
    private AppCompatCheckBox mSunCheckbox;
    private AppCompatCheckBox mMonCheckbox;
    private AppCompatCheckBox mTueCheckbox;
    private AppCompatCheckBox mWedCheckbox;
    private AppCompatCheckBox mThuCheckbox;
    private AppCompatCheckBox mFriCheckbox;
    private AppCompatCheckBox mSatCheckbox;
    private AlertDialog mTimeDialog;
    private TimePicker mTimePicker;
    private CardView mOkButton;
    private CardView mCancelButton;
    private CardView mAlertOkButton;
    private CardView mSaveButton;
    private AlertDialog mAlertDialog;
    private HorizontalScrollView mWeekDaySelector;
    private static final int START_TIME_DIALOG = 100011;
    private static final int END_TIME_DIALOG = 100012;
    private int currentDialog;
    private String mStartTimeString;
    private String mEndTimeString;
    private long mStartTimeStamp;
    private long mEndTimeStamp;
    private boolean isDayChecked=false;

    public AddPasscodeFragment() {

    }


    public AddPasscodeFragment setListener(OnAddPasscodeFragmentInteractionListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add_passcode, container, false);

        String typeList[] = {
                "Time Bound","Permanent"
        };

        initializeViewElements();
        mStartTime.setOnClickListener(this);
        mEndTime.setOnClickListener(this);

        ArrayAdapter typeAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mTypeSpinner.setAdapter(typeAdapter);
        mTypeSpinner.setOnItemSelectedListener(this);

        mEveryDayCheckbox.setOnCheckedChangeListener(this);
        mSaveButton.setOnClickListener(this);

        return mView;
    }


    private void initializeViewElements() {
        mTypeSpinner = mView.findViewById(R.id.type_spinner);
        mSaveButton = mView.findViewById(R.id.save_passcode_button);
        mTimeCard = mView.findViewById(R.id.time_card);
        mStartTime = mView.findViewById(R.id.start_time);
        mEndTime = mView.findViewById(R.id.end_time);
        mPasscodeNameField = mView.findViewById(R.id.name_field);
        mPasscodeField = mView.findViewById(R.id.passcode_field);
        mReEnterPasscodeField = mView.findViewById(R.id.confirm_passcode_field);
        mEveryDayCheckbox = mView.findViewById(R.id.everyday_checkbox);
        mSunCheckbox = mView.findViewById(R.id.sun_checkbox);
        mMonCheckbox = mView.findViewById(R.id.mon_checkbox);
        mTueCheckbox = mView.findViewById(R.id.tue_checkbox);
        mWedCheckbox = mView.findViewById(R.id.wed_checkbox);
        mThuCheckbox = mView.findViewById(R.id.thu_checkbox);
        mFriCheckbox = mView.findViewById(R.id.fri_checkbox);
        mSatCheckbox = mView.findViewById(R.id.sat_checkbox);
        mWeekDaySelector = mView.findViewById(R.id.week_day_selector);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            //Time Bound is Selected
            mTimeCard.setVisibility(View.VISIBLE);
        }
        else{
            mTimeCard.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            mWeekDaySelector.setVisibility(View.GONE);
        }
        else{
            mWeekDaySelector.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_time:
                currentDialog = START_TIME_DIALOG;
                showTimeDialog();
                break;
            case R.id.end_time:
                currentDialog = END_TIME_DIALOG;
                showTimeDialog();
                break;
            case R.id.save_passcode_button:
                saveData();
                break;
            case R.id.ok_button:
                saveTime();
                break;
            case R.id.cancel_button:
                mTimeDialog.dismiss();
                break;
            case R.id.alert_ok_button:
                mAlertDialog.dismiss();
                break;
        }
    }

    private void saveTime() {
        if(currentDialog == START_TIME_DIALOG){
            mStartTime.setText(mStartTimeString);
        }
        else {
            mEndTime.setText(mEndTimeString);
        }
        mTimeDialog.dismiss();
    }

    private void saveData() {

        String name  = mPasscodeNameField.getText().toString();
        String passcode  = mPasscodeField.getText().toString();
        String repasscode  = mReEnterPasscodeField.getText().toString();
        String passcodeType = mTypeSpinner.getSelectedItem().toString().toUpperCase();
        StringBuilder passcodeDay = new StringBuilder();
        if(mEveryDayCheckbox.isChecked()){
            passcodeDay.append("Everyday");
            isDayChecked = true;
        }
        else{
            if(mSunCheckbox.isChecked()){
                passcodeDay.append("SUN, ");
                isDayChecked = true;
            }

            if(mMonCheckbox.isChecked()){
                passcodeDay.append("MON, ");
                isDayChecked = true;
            }

            if(mTueCheckbox.isChecked()){
                passcodeDay.append("TUE, ");
                isDayChecked = true;
            }

            if(mWedCheckbox.isChecked()){
                passcodeDay.append("WED, ");
                isDayChecked = true;
            }

            if(mThuCheckbox.isChecked()){
                passcodeDay.append("THU, ");
                isDayChecked = true;
            }

            if(mFriCheckbox.isChecked()){
                passcodeDay.append("FRI, ");
                isDayChecked = true;
            }

            if(mSatCheckbox.isChecked()){
                passcodeDay.append("SAT, ");
                isDayChecked = true;
            }
//            passcodeDay.deleteCharAt(passcodeDay.length()); //Remove space
//            passcodeDay.deleteCharAt(passcodeDay.length()); //Remove comma

        }
        if(TextUtils.isEmpty(name)){
            mPasscodeNameField.setError(getString(R.string.required));
            mPasscodeNameField.requestFocus();
            return;
        }

        if(!isValidName(name)){
            mPasscodeNameField.setError(getString(R.string.invalid_name));
            mPasscodeNameField.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(passcode)){
            mPasscodeField.setError(getString(R.string.required));
            mPasscodeField.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(repasscode)){
            mReEnterPasscodeField.setError(getString(R.string.required));
            mReEnterPasscodeField.requestFocus();
            return;
        }

        if(!isDayChecked){
            showErrorDialog(R.string.select_day);
            return;
        }

        if(!passcode.equals(repasscode)){
            mReEnterPasscodeField.setError("Passcode doesn't match");
            mReEnterPasscodeField.requestFocus();
            return;
        }

        Passcode passcodeData = new Passcode(name,passcodeType,mStartTimeStamp,mEndTimeStamp,passcodeDay.toString(),passcode);

        PasscodeDatabaseHelper db = new PasscodeDatabaseHelper(getActivity());
        if(db.addPasscode(passcodeData)){
            mListener.onSaved();
        }
        else {
            showErrorDialog(R.string.something_went_wrong);
        }

    }

    private void showErrorDialog(int resId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_dialog,null);
        TextView message = view.findViewById(R.id.message);
        message.setText(resId);
        mAlertOkButton = view.findViewById(R.id.alert_ok_button);
        mAlertOkButton.setOnClickListener(this);
        builder.setView(view);
        mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mAlertDialog.show();
    }

    public static boolean isValidName(String name){
        Pattern pattern;
        Matcher matcher;

        final String NAME_PATTERN = "^[A-Za-z ]{2,}$";

        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);

        return matcher.matches();
    }


    public void showTimeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.time_picker_dialog,null);
        mOkButton = view.findViewById(R.id.ok_button);
        mCancelButton = view.findViewById(R.id.cancel_button);
        mTimePicker = view.findViewById(R.id.timePicker);
        mTimePicker.setOnTimeChangedListener(this);
        Calendar calendar = Calendar.getInstance();
        setTime(calendar);
        mOkButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        builder.setView(view);
        mTimeDialog = builder.create();
        mTimeDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mTimeDialog.show();
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        setTime(calendar);
    }

    public void setTime(Calendar calendar){
        String am_pm = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        String hrs = calendar.get(Calendar.HOUR) == 0 ? "12" : Integer.toString(calendar.get(Calendar.HOUR));

        String timeString = hrs + ":" + calendar.get(Calendar.MINUTE) + " " + am_pm;
        long timeStamp = calendar.getTime().getTime();

        if(currentDialog == START_TIME_DIALOG){
            mStartTimeString = timeString;
            mStartTimeStamp = timeStamp;
        }
        else{
            mEndTimeString = timeString;
            mEndTimeStamp = timeStamp;
        }
    }


    public interface OnAddPasscodeFragmentInteractionListener{
        void onSaved();
    }
}
