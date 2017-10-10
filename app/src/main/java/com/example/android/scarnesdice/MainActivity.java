package com.example.android.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import android.os.Handler;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.y;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.android.scarnesdice.R.id.dice;
import static com.example.android.scarnesdice.R.string.hold;
import static com.example.android.scarnesdice.R.string.reset;
import static com.example.android.scarnesdice.R.string.roll;


public class MainActivity extends AppCompatActivity {

    private static int user_overall_score;
    private static int user_turn_score;
    private static int comp_overall_score;
    private static int comp_turn_score;
    private static int winScore=100;
   // private Handler computerTurnHandler;
   // private boolean iscomp;
    private boolean isToast;

    private TextView userOverallScore;
    private TextView userTurnScore;
    private TextView compOverallScore;
    private TextView compTurnScore;
    private ImageView diceView;
    private TextView Winner;
    private TextView userTurnScoreLabel;
    private TextView compTurnScoreLabel;


    private Button rollbtn;
    private Button holdbtn;
    private Button resetbtn;

    /** declaring all id's of dice image as an array so that it can be efficiently used while changing dice randomly**/
    private static int diceNo[] = {
            R.drawable.one,    //id for image1
            R.drawable.two,    // id for image2
            R.drawable.three,    //id for image3 and so on....
            R.drawable.four,
            R.drawable.five,
            R.drawable.six
    };

    private Random random =new Random();

    Handler diceRoll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting all views necessary  from layout
        rollbtn=(Button)findViewById(R.id.btn_roll);
        holdbtn=(Button)findViewById(R.id.btn_hold);
        resetbtn=(Button)findViewById(R.id.btn_reset);

        userOverallScore=(TextView)findViewById(R.id.p_score);
        compOverallScore=(TextView)findViewById(R.id.c_score);
        Winner=(TextView)findViewById(R.id.Result);

        diceView=(ImageView)findViewById(R.id.dice);

        userTurnScoreLabel=(TextView)findViewById(R.id.user_turn_score_label);
        compTurnScoreLabel=(TextView)findViewById(R.id.com_turn_score_label);
        userTurnScore=(TextView)findViewById(R.id.user_turn_score);
        compTurnScore=(TextView)findViewById(R.id.com_turn_score);

        //setting label of computer turn score to be disable as it not required at beginning...
        compTurnScoreLabel.setVisibility(View.GONE);
        compTurnScore.setVisibility(View.GONE);
        Toast.makeText(this,"Your Turn",Toast.LENGTH_SHORT).show();
    }
    public void onReset(View view)
    {
        diceView.setImageResource(R.drawable.dice3droll);
        user_overall_score = user_turn_score = comp_overall_score = comp_turn_score = 0;
        updateScore();

        //setting label of computer turn score to be disable as it not required at beginning...
       /** compTurnScoreLabel.setVisibility(View.GONE);
        compTurnScore.setVisibility(View.GONE);
        rollbtn.setEnabled(true);
        holdbtn.setEnabled(true);
       **/
       // iscomp=false;
       // Toast.makeText(this,"Your Turn",Toast.LENGTH_SHORT).show();
        Winner.setText("");
        yourTurn();
    }
    public void onRollDice(View view)
    {
        //int diceValue = 1 + (int) (Math.random() * ((6 - 1) + 1));
        rollbtn.setEnabled(false);
        holdbtn.setEnabled(false);
        resetbtn.setEnabled(false);
        /** getting random dice value on user turn of roll**/
        final int diceValue = random.nextInt(6) + 1;

        //extra
        diceRoll=new Handler();
        diceView.setImageResource(R.drawable.dice3d);
        diceRoll.postDelayed(new Runnable() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                diceView.setImageResource(R.drawable.dice3droll);
            }
        }, 600);
        diceRoll.postDelayed(new Runnable() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
            }
        }, 1000);
        diceView.setImageResource(R.drawable.dice3d);





      diceRoll.postDelayed(new Runnable() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                  setSelectedDice(diceValue);
            }
        }, 1000);



        //over extra

    }

    public void setSelectedDice(int diceValue)
    {
        diceView.setImageResource(diceNo[diceValue-1]);

        /** if dicevalue is 1 then switch to computer turn**/
        if (diceValue == 1) {
            //Log.d("Score", "You rolled 1");

            //int temp=user_overall_score+user_turn_score;
            user_turn_score = 0;
            updateScore();
            createToast("You Encounter 1",1000);
            /*if (temp >= winScore) {
                Winner.setText(R.string.user_winner);
                rollbtn.setEnabled(false);
                holdbtn.setEnabled(false);
                return;
            }*/
            //rollbtn.setEnabled(false);
            //  iscomp=true;
            cpuTurn();
            // isToast=false;

        } else {
            /**dice value betn 2 to 6 there are 2 option**/
            //Log.d("Score", "You rolled " + diceValue);

           /* YoYo.with(Techniques.Shake)
                    .duration(250)
                    .playOn(ivDiceFace);*/


            user_turn_score += diceValue;
            updateScore();
            if (user_overall_score+user_turn_score >= winScore) {
                user_overall_score+=user_turn_score;
                user_turn_score=0;
                user_turn_score = 0;
                updateScore();
                Winner.setText(R.string.user_winner);
                rollbtn.setEnabled(false);
                holdbtn.setEnabled(false);
                createToast("You Wins! Keep Playing....!!!",2000);
                isToast=true;
                return;
            }
            //cpuTurn();
            // if(isToast==false)
            //Toast.makeText(this,"Your Turn",Toast.LENGTH_SHORT).show();
            rollbtn.setEnabled(true);
            holdbtn.setEnabled(true);
            resetbtn.setEnabled(true);
        }

    }
    public  void onHold(View view) {
        user_overall_score += user_turn_score;
        user_turn_score = 0;
        updateScore();


        if (user_overall_score >= winScore) {
            Winner.setText(R.string.user_winner);
            rollbtn.setEnabled(false);
            holdbtn.setEnabled(false);
            isToast=true;
            return;
        }
       // iscomp=true;
        cpuTurn();

    }
    public void cpuTurn() {
        createToast("Computer Turn",2000);
        comp_turn_score = 0;
        rollbtn.setEnabled(false);
        holdbtn.setEnabled(false);
        resetbtn.setEnabled(false);
        userTurnScoreLabel.setVisibility(View.GONE);
        userTurnScore.setVisibility(View.GONE);
        compTurnScore.setVisibility(View.VISIBLE);
        compTurnScoreLabel.setVisibility(View.VISIBLE);
        //Toast.makeText(this,"Computer Turn",Toast.LENGTH_SHORT).show();
        //isToast=false;

        //int diceValue;
        //new Timer().schedule(new TimerTask() {
          //  @Override
           // public void run() {

        Handler computerTurnHandler = new Handler();

        computerTurnHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds





                        compPlays();


            }
        }, 3000);
            //if(iscomp)
              //  break;
        //} while (comp_turn_score < 20);

       /* comp_overall_score += comp_turn_score;
        //Log.d("Score", "CPU Overall Score: " + cpuOverallScore);

        comp_turn_score = 0;
        updateScore();

        if (comp_overall_score >= winScore) {
            Winner.setText(R.string.cpu_winner);
            rollbtn.setEnabled(false);
            holdbtn.setEnabled(false);
            return;
        }

        rollbtn.setEnabled(true);
        holdbtn.setEnabled(true);*/


    }
    public void updateScore()
    {
        userTurnScore.setText(""+user_turn_score);
        userOverallScore.setText(""+user_overall_score);
        compTurnScore.setText(""+comp_turn_score);
        compOverallScore.setText(""+comp_overall_score);
    }
    private void createToast(String msg,int time){
        final Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        },time);
    }

    private void yourTurn() {
        createToast("Your Turn",2000);
        user_turn_score = 0;
        updateScore();
        userTurnScoreLabel.setVisibility(View.VISIBLE);
        userTurnScore.setVisibility(View.VISIBLE);
        compTurnScoreLabel.setVisibility(View.INVISIBLE);
        compTurnScore.setVisibility(View.INVISIBLE);

        rollbtn.setEnabled(true);
        holdbtn.setEnabled(true);
        resetbtn.setEnabled(true);
    }
    public void compPlays() {
        final int diceValue = random.nextInt(6) + 1;

        //extra
        diceRoll=new Handler();
        diceView.setImageResource(R.drawable.dice3d);
        diceRoll.postDelayed(new Runnable() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                diceView.setImageResource(R.drawable.dice3droll);
            }
        }, 600);
        diceRoll.postDelayed(new Runnable() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
            }
        }, 1000);
        diceView.setImageResource(R.drawable.dice3d);

        // extra over

        diceRoll.postDelayed(new Runnable() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                compPlaySetDice(diceValue);
            }
        }, 1000);


    }

    public void compPlaySetDice(int diceValue)
    {
        diceView.setImageResource(diceNo[diceValue - 1]);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 2000);
        /** if dice value is 1 then switch to user turn**/
        if (diceValue == 1) {

            comp_turn_score = 0;
            updateScore();
            /**rollbtn.setEnabled(true);
             holdbtn.setEnabled(true);
             resetbtn.setEnabled(true);**/
            createToast("Computer Encounter 1", 1600);
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    yourTurn();
                }
            },2000);


            //necessary requirements before chance given to user
            /** compTurnScore.setVisibility(View.GONE);
             compTurnScoreLabel.setVisibility(View.GONE);
             userTurnScore.setVisibility(View.VISIBLE);
             userTurnScoreLabel.setVisibility(View.VISIBLE);**/
            //  iscomp=false;

            // Toast.makeText(this,"Your Turn",Toast.LENGTH_SHORT).show();
                        /*if (comp_overall_score >= winScore) {
                            Winner.setText(R.string.cpu_winner);
                            rollbtn.setEnabled(false);
                            holdbtn.setEnabled(false);
                            resetbtn.setEnabled(true);
                            return;
                        }*/
        } else {
            /** if dice value is betn 2 to 6 then update it**/
            comp_turn_score += diceValue;
            updateScore();
            if (comp_overall_score + comp_turn_score >= winScore) {
                comp_overall_score += comp_turn_score;
                comp_turn_score=0;
                updateScore();
                Winner.setText(R.string.cpu_winner);
                rollbtn.setEnabled(false);
                holdbtn.setEnabled(false);
                resetbtn.setEnabled(true);
                createToast("CPU Wins! Keep Playing....!!!",2000);
                isToast = true;
                return;
            }
            int temp = random.nextInt(19 - 5) + 5;
            if (comp_turn_score > temp) {
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 3000);
                compHold();
            } else {
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        compPlays();
                    }
                }, 2000);
            }
        }
    }

    public void compHold()
    {

        createToast("Computer Holds",1600);
        comp_overall_score += comp_turn_score;
        comp_turn_score=0;
        updateScore();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                yourTurn();
            }
        },2000);
    }
}
