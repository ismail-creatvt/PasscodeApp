package com.creatvt.ismail.androidpasscodetask;

public class Passcode {
    public String passcodeName;
    public String passcodeType;
    public long startTime;
    public long endTime;
    public String passcodeDay;
    public String passcode;

    public Passcode(String passcodeName, String passcodeType, long startTime, long endTime, String passcodeDay, String passcode) {
        this.passcodeName = passcodeName;
        this.passcodeType = passcodeType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.passcodeDay = passcodeDay;
        this.passcode = passcode;
    }

    public String getPasscodeName() {
        return passcodeName;
    }

    public void setPasscodeName(String passcodeName) {
        this.passcodeName = passcodeName;
    }

    public String getPasscodeType() {
        return passcodeType;
    }

    public void setPasscodeType(String passcodeType) {
        this.passcodeType = passcodeType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getPasscodeDay() {
        return passcodeDay;
    }

    public void setPasscodeDay(String passcodeDay) {
        this.passcodeDay = passcodeDay;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

}
