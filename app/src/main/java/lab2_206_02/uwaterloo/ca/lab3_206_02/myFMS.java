package lab2_206_02.uwaterloo.ca.lab3_206_02;

import android.util.Log;
import android.widget.TextView;

/**
 * Created by Zman on 2017-06-01.
 */

//this class was created to determine if the reading is to the left, right, up or down

//Finite state machien
public class myFMS {


    //create a list of all the possibilities for the states of the phone
    enum FMSstates{WAIT, LEFT,  RIGHT, STABLE_LEFT, STABLE_RIGHT, DETERMINED,
        UP, DOWN, STABLE_UP, STABLE_DOWN};


    //set the finite state machine state to be private
    private FMSstates myState;

    //create a list of all the my Signals can be
    enum mySig{LEFT, RIGHT,UP, DOWN, UNDETERMINED};

    //create a new variable for the private state of mySig
    private mySig mySigniture;

    //set the values of what is the values to determine if the phone is going right
    private final float[] THRESHOLD_RIGHT = {0.7f, 0.8f, 0.2f};
    //set the values of what is the values to determine if the phone is going left
    private final float[] THRESHOLD_LEFT = {-0.7f,-0.8f, -0.2f};


    //set the values of what is the values to determine if the phone is going up
    private final float[] THRESHOLD_UP = {0.6f, 0.7f, 0.2f};
    //set the values of what is the values to determine if the phone is sgoing down
    private final float[] THRESHOLD_DOWN = {-0.5f,-0.6f, -0.2f};

    //declare a variable to store the previous reading for the x axis
    private float previousReading;

    //declare a variable to store the previous reading for the y axis
    private float previousReadingy;

    //declare a private counter variable to store the average points required until to read the readings agian
    private int counter;

    //create local variable of where to output the view on the phone that was passed in
    TextView setDirection;

    //GameLoopTask GameLooper;


    //the constructor
    public myFMS(TextView direction){
        //set all the default values
        myState = FMSstates.WAIT;
        mySigniture = mySig.UNDETERMINED;
        previousReading = 0.0f;
        previousReadingy = 0.0f;
        counter = 30;
        setDirection = direction;

    }

    //the method to reset the views in the case where the direction is undetermined
    public void resetmyFMS(){
        //set up all the defualt values
        myState = FMSstates.WAIT;
        mySigniture = mySig.UNDETERMINED;
        previousReading = 0.0f;
        previousReadingy = 0.0f;
    }

    //the method that is called each time to pass in a new point for the x and y
    public void supplyReading(float input, float inputY){

        //first calculate the slope
        float slope = input - previousReading;
        //slope for the y axis
        float slopey = inputY - previousReadingy;
        //previousReading = slope;

        //switch statement for the current state of the phone
        //uses a finite state machine
        switch (myState){
            //the default state of when the program needs to decide what type of hand gesture was provided
            case WAIT:
                //Log.d("myFSM says ", String.format("WAITING... on %f", slope));

                //when going right
                if(slope >= THRESHOLD_RIGHT[0]){
                    //Log.d("myFSM says ", String.format("RIGHT... on %f", slope));
                    myState = FMSstates.RIGHT;
                }
                //left
                else if(slope <= THRESHOLD_LEFT[0]){
                    //Log.d("myFSM says ", String.format("LEFT... on %f", slope));
                    myState = FMSstates.LEFT;
                }
                //up
                else if(slopey >= THRESHOLD_UP[0]){
                    //Log.d("myFSM says ", String.format("UP... on %f", slope));
                    myState = FMSstates.UP;
                }
                //down
                else if(slopey <= THRESHOLD_DOWN[0]){
                    //Log.d("myFSM says ", String.format("DOWN... on %f", slope));
                    myState = FMSstates.DOWN;
                }
                //do nothing
                else{
                    //Log.d("myFSM says ", String.format("STILL WAITING... on %f", slope));
                }
                break;

            //when the sensor reads that the accelerometer is moved to RIGHT
            case RIGHT:
                //Log.d("myFSM says ", "RIGHT I am Rising...");
                //setDirection.setText("RIGHT");
                //GameLooper.setDirection(GameLoopTask.gameDirection.RIGHT);
                myState = FMSstates.RIGHT;
                //slope creassing 0 into negative values
                //..Maximum value appeared
                if(slope <= 0){
                    if(input > THRESHOLD_RIGHT[1]){
                        myState = FMSstates.STABLE_RIGHT;
                    }
                    else{
                        //Log.d("myFMS", String.format("Maxima is: %f", input));
                        //bad move
                        myState = FMSstates.DETERMINED;
                        mySigniture = mySig.UNDETERMINED;
                    }
                }

                break;

            //the case where the LEFT is still rising
            case LEFT:
                //Log.d("myFSM says ", "LEFT I am Rising...");
                //setDirection.setText("LEFT");
                //GameLooper.setDirection(GameLoopTask.gameDirection.LEFT);
                myState = FMSstates.LEFT;
                //slope creassing 0 into negative values
                //..Maximum value appeared
                if(slope >= 0){
                    if(input < THRESHOLD_LEFT[1]){
                        myState = FMSstates.STABLE_LEFT;
                    }
                    else{
                        //Log.d("myFMS", String.format("Maxima is: %f", input));
                        //bad move
                        myState = FMSstates.DETERMINED;
                        mySigniture = mySig.UNDETERMINED;
                    }
                }
                break;

            //case where it going up
            case UP:
                //Log.d("myFSM says ", "UP I am Rising...");
                //setDirection.setText("UP");
                //GameLooper.setDirection(GameLoopTask.gameDirection.UP);
                myState = FMSstates.UP;
                //slope creassing 0 into negative values
                //..Maximum value appeared
                if(slopey <= 0){
                    if(inputY > THRESHOLD_UP[1]){
                        myState = FMSstates.STABLE_UP;
                    }
                    else{
                        //Log.d("myFMS", String.format("Maxima is: %f", inputY));
                        //bad move
                        myState = FMSstates.DETERMINED;
                        mySigniture = mySig.UNDETERMINED;
                    }
                }

                break;

            //case where it is going down
            case DOWN:
                //Log.d("myFSM says ", "DOWN I am Rising...");
                //setDirection.setText("DOWN");
                //GameLooper.setDirection(GameLoopTask.gameDirection.DOWN);
                myState = FMSstates.DOWN;
                //slope creassing 0 into negative values
                //..Maximum value appeared
                if(slopey >= 0){
                    if(inputY < THRESHOLD_DOWN[1]){
                        myState = FMSstates.STABLE_DOWN;
                    }
                    else{
                        //Log.d("myFMS", String.format("Maxima is: %f", inputY));
                        //bad move
                        myState = FMSstates.DETERMINED;
                        mySigniture = mySig.UNDETERMINED;
                    }
                }
                break;

            //the case for RIGHT when the phone is set to the right
            case STABLE_RIGHT:

                //Log.d("myFMS State", "Stablizing Right");

                //start the countdown until a new point can be registared
                counter--;
                //when it hits 0,
                if (counter ==0){
                    //if the current reading is back down to nuetral territory
                    if (Math.abs(input) <= THRESHOLD_RIGHT[2]){
                        myState = FMSstates.DETERMINED;
                        mySigniture = mySig.UNDETERMINED;
                        counter =30;
                    }
                    else{
                        myState = FMSstates.WAIT;
                        counter = 30;
                    }
                }
                break;
            //case where the going up is done and now waiting for the to stabalize again
            case STABLE_UP:

                //Log.d("myFMS State", "Stablizing UP");

                //start the count down
                counter--;

                //once hit zero go to a different state
                if (counter ==0){
                    if (Math.abs(inputY) <= THRESHOLD_UP[2]){
                        myState = FMSstates.DETERMINED;
                        mySigniture = mySig.UNDETERMINED;
                        counter =30;
                    }
                    else{
                        myState = FMSstates.WAIT;
                        counter = 30;
                    }
                }
                break;

            //the case where the LEFT is stabalizing
            case STABLE_LEFT:
                //Log.d("myFMS State", "Stablizing Left");
                //start the counter
                counter--;

                //when the counter hits 0
                if (counter ==0){

                    if (Math.abs(input) <= THRESHOLD_LEFT[2]){
                        myState = FMSstates.DETERMINED;
                        counter =30;
                        mySigniture = mySig.UNDETERMINED;
                    }
                    else{
                        myState = FMSstates.WAIT;
                        counter = 30;
                    }
                }
                break;
            //case where it is stabalizing after RISING_DOWN
            case STABLE_DOWN:
                //Log.d("myFMS State", "Stablizing DOWN");
                //start the counter
                counter--;

                //once it hits 0
                if (counter ==0){

                    if (Math.abs(inputY) <= THRESHOLD_DOWN[2]){
                        myState = FMSstates.DETERMINED;
                        counter =30;
                        mySigniture = mySig.UNDETERMINED;
                    }
                    else{
                        myState = FMSstates.WAIT;
                        counter = 30;
                    }
                }
                break;
            //when the state has finsihed and is now should be waiting for additional cammands
            case DETERMINED:

                //Log.d("myFMS State:", String.format("Determined"));
                myState = FMSstates.WAIT;
                break;

            //the default of what to do when one of the the cases are met
            default:
                resetmyFMS();
        }

        //replace history record with the new input
        previousReading = input;
        //replace the history record of the y-axis with a new input
        previousReadingy = inputY;

    }

    public FMSstates getState(){
        return myState;
    }
}
