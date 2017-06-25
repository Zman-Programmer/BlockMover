package lab2_206_02.uwaterloo.ca.lab3_206_02;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.TimerTask;

/**
 * Created by Zman on 2017-06-24.
 */

class GameLoopTask extends TimerTask {
    private Activity myActivity;
    private Context myContext;
    private RelativeLayout myRL;
    private int myCoordX = 0;
    private int myCorrdY = 0;
    private int iterations = 8;

    enum gameDirection{UP, DOWN, LEFT, RIGHT, NO_MOVEMENT};
    gameDirection currentGameDirection = gameDirection.NO_MOVEMENT;
    gameDirection previousGameDirection = gameDirection.NO_MOVEMENT;

    public GameBlock newBlock;

    private void createBlock(){
        newBlock = new GameBlock(myContext, 0, 0, myRL); //Or any (x,y) of your choice
        myRL.addView(newBlock);
    }

    public void setDirection(gameDirection newDirection){
        String temp = String.valueOf(newDirection);
        Log.d("Direction", temp);
        switch (temp){
            case "UP":
                currentGameDirection = gameDirection.UP;
                break;
            case "DOWN":
                currentGameDirection = gameDirection.DOWN;
                break;
            case "RIGHT":
                currentGameDirection = gameDirection.RIGHT;
                break;
            case "LEFT":
                currentGameDirection = gameDirection.LEFT;
                break;
            default:
                currentGameDirection = gameDirection.NO_MOVEMENT;
        }
    }


    public GameLoopTask(Activity myAct, Context myCon, RelativeLayout myRel){
        myActivity = myAct;
        myContext = myCon;
        myRL = myRel;
        createBlock();
    }
    public void run(){
        myActivity.runOnUiThread(
                new Runnable(){
                    public void run(){
                        if(previousGameDirection == currentGameDirection){
                            newBlock.move(gameDirection.NO_MOVEMENT);
                        }
                        else{
                            newBlock.move(currentGameDirection);
                            previousGameDirection = currentGameDirection;
                        }
                        //check to see if the direction is the same as the previous one for 8 itterations

                    }
                }
        );
    }



}
