package com.soderstrand.martin.inlinestracker.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.soderstrand.martin.inlinestracker.R;
import com.soderstrand.martin.inlinestracker.controller.FrontPageActivity;
import com.soderstrand.martin.inlinestracker.controller.MapActivity;
import com.soderstrand.martin.inlinestracker.controller.TracksPageActivity;

/**
 * @author Martin Söderstrand
 * @version 9.0
 * Date: 2016-03-05
 *
 * A class to register gestures in the application such as Swipe.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener{

    Context context;
    Position position;  //What activity you are on.
    public GestureListener(Context context, Position position){
        this.context=context;
        this.position= position;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        switch (position){
            //if you are att tacks activity and swipe right.
            case TRACKS: {
                if(e1.getX() > e2.getX()){
                    Intent intent = new Intent(context, FrontPageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
                    result = true;
                }
                break;
            }
            //if you are att tacks activity and swipe right or left.
            case FRONTPAGE:{
                if (e2.getX() > e1.getX()) {
                    Intent intent = new Intent(context, TracksPageActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
                    result = true;
                }
                if (e1.getX() > e2.getX()) {
                    Intent intent = new Intent(context, MapActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
                    result = true;
                }

                break;
            }
        }
        return result;
    }

}
