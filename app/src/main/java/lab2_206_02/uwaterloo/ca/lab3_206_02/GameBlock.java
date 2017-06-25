package lab2_206_02.uwaterloo.ca.lab3_206_02;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Zman on 2017-06-24.
 */

public class GameBlock extends android.support.v7.widget.AppCompatImageView {

    private float IMAGE_SCALE;
    private int myCoordX = 0;
    private int myCorrdY = 0;
    private int Xgoal =0;
    private int Ygoal = 0;
    private int velocity = 20;
    public RelativeLayout relativelay;
    ImageView image;
    //public GameLoopTask Loop;


    public GameBlock(Context myContext, int CoordX, int CoordY, RelativeLayout rl){
        super(myContext);
        image = new ImageView(myContext);
        relativelay = rl;
        image.setImageResource(R.drawable.gameblock);
        image.setScaleX(1.0f);
        image.setScaleY(1.0f);
        image.setX(myCoordX);
        image.setY(myCorrdY);
        rl.addView(image);


        //Loop = new GameLoopTask();
        //this.addView(myImage);

//        ImageView myImage = new ImageView(getApplicationContext());
//        myImage.setImageResource(R.drawable.gameblock);
//        myImage.setX(0);
//        myImage.setY(0);
//        myImage.setScaleX(1.0f);  //Scale the image accordingly
//        myImage.setScaleY(1.0f);
//        rl.addView(myImage);
    }

    public void move(GameLoopTask.gameDirection directioninput){
        switch (directioninput){
            case UP:
                if (myCorrdY > 0){
                    Ygoal -=180;
                }
                break;
            case DOWN:
                if (myCorrdY< 540){
                    Ygoal += 180;
                }
                break;
            case RIGHT:
                if(myCoordX < 540){
                    Xgoal += 180;
                }
                break;
            case LEFT:
                if(myCoordX > 0){
                    Xgoal -= 180;
                }
                break;
            default:
                //do nothing
        }

        //get to the desired location of the square
        if(myCorrdY != Ygoal){
            if(myCorrdY < Ygoal){
                myCorrdY += velocity;
                image.setY(myCorrdY);
                image.setX(myCoordX);
            }
            else if(myCorrdY > Ygoal){
                myCorrdY -= velocity;
                image.setY(myCorrdY);
                image.setX(myCoordX);
            }
        }

        //get to the desired location of the square
        if(myCoordX != Xgoal){
            if(myCoordX < Xgoal){
                myCoordX += velocity;
                image.setY(myCorrdY);
                image.setX(myCoordX);
            }
            else if(myCoordX > Xgoal){
                myCoordX -= velocity;
                image.setY(myCorrdY);
                image.setX(myCoordX);
            }
        }

    }

}
