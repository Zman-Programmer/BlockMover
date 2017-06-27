package lab2_206_02.uwaterloo.ca.lab3_206_02;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Zman on 2017-06-24.
 */

public class GameBlock extends android.support.v7.widget.AppCompatImageView {
    //keeps track of the current block location for x
    private int myCoordX = 0;
    //tracks the current y location
    private int myCorrdY = 0;
    //the location the block should be at i x
    private int Xgoal =0;
    //location where the block should be at in y
    private int Ygoal = 0;
    //the speed in which the block should move
    private final int initialvelocity = 1;
    //velocity
    private int velocity = 1;
    //acceleration
    private int acceleration = 6;
    //the local copy of the relative layout
    public RelativeLayout relativelay;
    //the square imageview
    ImageView image;

    //the constructor
    public GameBlock(Context myContext, int CoordX, int CoordY, RelativeLayout rl){
        //set the context to be super (use from the parent class)
        super(myContext);
        //create the new ImageView
        image = new ImageView(myContext);
        //local copy of the relative layout
        relativelay = rl;
        //set the image in the imageview
        image.setImageResource(R.drawable.gameblock);
        //set the scale to be 1 to make sure the image is flush
        image.setScaleX(1.0f);
        //for both x and y
        image.setScaleY(1.0f);
        //set the initial location to be at the origin
        image.setX(myCoordX);
        //for both x and y
        image.setY(myCorrdY);
        // add the view to the relative layout
        rl.addView(image);
    }

    //method to move the square image view
    public void move(GameLoopTask.gameDirection directioninput){
        //create a switch case on what to do for all the cases
        switch (directioninput){
            //when up
            case UP:
                //if the square is not on the first row then
                if (myCorrdY > 0){
                    //set the desired location to be one up
                    Ygoal =0;
                }
                break;
            //when down
            case DOWN:
                //is the square is not on the last row then
                if (myCorrdY< 540){
                    //set the desired location to be one down
                    Ygoal = 540;
                }
                break;
            // when right
            case RIGHT:
                //if the square is not at the last colom then
                if(myCoordX < 540){
                    //set the desired location to be one to square to the right
                    Xgoal = 540;
                }
                break;
            //when left
            case LEFT:
                //if the square is not on the the first colom
                if(myCoordX > 0){
                    //then set the disired location to be one colom to the left
                    Xgoal = 0;
                }
                break;
            default:
                //do nothing
        }

        //get to the desired location of the square in the y axis
        //if the current y location is not the same as the disired y location
        if(myCorrdY != Ygoal){
            //is the current y location is less then the desired y location
            //DOWN
            if(myCorrdY + velocity + acceleration < Ygoal){
                velocity += acceleration;
                //keep sending the square down at the speed of velocity
                myCorrdY += velocity;

                //update the location of the image view
                image.setY(myCorrdY);
                //in the x and y
                image.setX(myCoordX);
                //velocity += acceleration;
            }

            //UP
            //case where the current location is greater than desired location
            else if(myCorrdY - velocity - acceleration > Ygoal){
                //keep sending the current y direction up to disired location
                myCorrdY -= velocity;
                velocity += acceleration;
                //update location
                image.setY(myCorrdY);
                image.setX(myCoordX);
                //velocity += acceleration;
            }
            else{
                //update the location of the image view
                image.setY(Ygoal);
                //in the x and y
                image.setX(myCoordX);
                velocity = initialvelocity;
                myCorrdY = Ygoal;
            }
        }


        //get to the desired location of the square in the x axis
        //same as above but for y
        if(myCoordX != Xgoal){
            //RIGHT
            if(myCoordX + velocity + acceleration < Xgoal){
                velocity += acceleration;
                myCoordX += velocity;
                //image.setY(myCorrdY);
                image.setX(myCoordX);
                //velocity+= acceleration;
            }
            //the second casex
            else if(myCoordX - velocity - acceleration > Xgoal){
                velocity += acceleration;
                myCoordX -= velocity;
                //image.setY(myCorrdY);
                image.setX(myCoordX);
                //velocity += acceleration;
            }
            else{
                //update the location of the image view
                //image.setY(myCorrdY);
                //in the x and y
                image.setX(Xgoal);
                velocity = initialvelocity;
                myCoordX = Xgoal;
            }
        }
    }
}
