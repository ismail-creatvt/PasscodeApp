package com.creatvt.ismail.androidpasscodetask;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

public class PasscodeActivity extends AppCompatActivity implements PasscodeFragment.OnPasscodeFragmentInteractionListener,AddPasscodeFragment.OnAddPasscodeFragmentInteractionListener{

    private static final int SCREEN_1 = 10011;
    private static final int SCREEN_2 = 10012;
    private int currentScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.passcode);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setCustomView(R.layout.passcode_appbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_icon);

        changeFragment(new PasscodeFragment().setListener(this));
        currentScreen = SCREEN_1;
    }

    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.parent,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onAddNewPasscodeButtonClicked() {
        changeFragment(new AddPasscodeFragment().setListener(this));
        currentScreen = SCREEN_2;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.homeAsUp){
            if(currentScreen == SCREEN_2){
                goToScreen1();
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(currentScreen == SCREEN_2){
            goToScreen1();
        }
        else{
            finish();
        }
    }

    public void goToScreen1(){
            changeFragment(new PasscodeFragment().setListener(this));
            currentScreen = SCREEN_1;
    }

    @Override
    public void onSaved() {
        goToScreen1();
    }
}
