package lab2_206_02.uwaterloo.ca.lab3_206_02;

import android.graphics.Color;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //declare the textview for the direction
    TextView direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declare the linear layout
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout);
        //set it to be a square with the width of the screen
        rl.getLayoutParams().width = 720; //gameboard size
        rl.getLayoutParams().height = 720;
        //set the gameboard picture to be the packground of the phone
        rl.setBackgroundResource(R.drawable.gameboard);


        //start of the textview of the dirextion the phone is currently in
        direction = new TextView(getApplicationContext());
        direction.setText("DIRECTION");
        direction.setTextColor(Color.parseColor("#000000"));
        direction.setTextSize(20);
        rl.addView(direction);

        //Set up an animation timer of 16ms (60 frames per second)
        Timer myTimer = new Timer();
        //create a new Game loop task (look at game loop task class)
        GameLoopTask gameLoop = new GameLoopTask(this, getApplicationContext(), rl);
        //create a timer scheduale to run ever 50ms starting at 50ms
        myTimer.schedule(gameLoop, 50, 50);

        //declare your sensor manager for the accelerometer
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        //now call the accelerometer class and pass in a lot of parameters such as graphs
        SensorEventListener a = new AccelerometerSensorEventListener(direction, gameLoop);
        //set the Sensor Event Listener to AcceleromterSensorEventListener to get values
        final AccelerometerSensorEventListener y = (AccelerometerSensorEventListener) a;
        //register the sensor
        //set the SENSOR DELAY to GAME to get optimal performance
        sensorManager.registerListener(a, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    }
}


//Beginning of the ACCELEROTMER CLASS =============================================================
class AccelerometerSensorEventListener implements SensorEventListener{
    //the filter reading for x
    float filterReadingx=0;
    //the filter reading for y
    float filterReadingy = 0;
    //the filter reading for z
    float filterReadingz =0;
    //the local textview for the current direction of the phone
    TextView setDirection;
    //the Finite state machine that is create locally
    private myFMS FMS1;
    //the game loop
    private GameLoopTask gameLoop;

    //class contructor
    public AccelerometerSensorEventListener(TextView direction, GameLoopTask gameLoopin){
        //set the direction to be displayed
        setDirection = direction;
        //create the new finite state machine and pass in the textview as a parameter
        FMS1 = new myFMS(setDirection);
        //set up the game loop
        gameLoop = gameLoopin;
    }


    //sets the sensor reading accuracy, set as default
    public void onAccuracyChanged (Sensor s, int i){ }

    //method for updating the values on the graph, array, and textview
    public void onSensorChanged (SensorEvent se) {
        //filter the reading for x to make it smooth
        filterReadingx += (se.values[0] - filterReadingx)/6;
        //filter the reading for y to make it smooth
        filterReadingy += (se.values[1] - filterReadingy)/6;
        //filter the reading for z to make it smooth
        filterReadingz += (se.values[2] - filterReadingz)/6;
        //create a float array to store the 3 adjusted readings temp
        float [] currentpoint = new float[3];
        //store each of the points into the new array for x
        currentpoint[0]= filterReadingx;
        //store each of the points into the new array for y
        currentpoint[1]= filterReadingy;
        //store each of the points into the new array for z
        currentpoint[2]= filterReadingz;

        //if the sensor is of type accelerometer then continuie
        if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            //pass in the readings of the x and y smoothed out readings
            FMS1.supplyReading(currentpoint[0], currentpoint[1]);
            //for storing temporarly the state of the state
            String temp = String.valueOf(FMS1.getState());

            //switch case on what to do in each of the diff scenarios
            switch (temp){
                //when it is up
                case "UP":
                    //set the diretion to be up
                    gameLoop.setDirection(GameLoopTask.gameDirection.UP);
                    //display it on the textview
                    setDirection.setText("UP");
                    break;
                //when it is down
                case "DOWN":
                    
                    gameLoop.setDirection(GameLoopTask.gameDirection.DOWN);
                    setDirection.setText("DOWN");
                    break;
                case "LEFT":
                    gameLoop.setDirection(GameLoopTask.gameDirection.LEFT);
                    setDirection.setText("LEFT");
                    break;
                case "RIGHT":
                    gameLoop.setDirection(GameLoopTask.gameDirection.RIGHT);
                    setDirection.setText("RIGHT");
                    break;
                default:
                    gameLoop.setDirection(GameLoopTask.gameDirection.NO_MOVEMENT);
            }
        }
    }
}
//End of ACCELEROMETER CLASS ======================================================================
