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

import static com.example.android.scarnesdice.R.id.dice;
import static com.example.android.scarnesdice.R.string.roll;


public class MainActivity extends AppCompatActivity {

    private static int user_overall_score;
    private static int user_turn_score;
    private static int comp_overall_score;
    private static int comp_turn_score;
    private static int winScore=50;
    private Handler computerTurnHandler;
  //  private boolean iscomp=false;

    private TextView userOverallScore;
    private TextView userTurnScore;
    private TextView compOverallScore;
    private TextView compTurnScore;
    private ImageView diceView;
    private TextView Winner;


    private Button rollbtn;
    private Button holdbtn;
    private Button resetbtn;

    private static int diceNo[] = {
            R.drawable.dice1,    //id for image1
            R.drawable.dice2,    // id for image2
            R.drawable.dice3,    //id for image3 and so on....
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
    };

    private Random random =new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollbtn=(Button)findViewById(R.id.btn_roll);
        holdbtn=(Button)findViewById(R.id.btn_hold);
        resetbtn=(Button)findViewById(R.id.btn_reset);

        userOverallScore=(TextView)findViewById(R.id.p_score);
        userTurnScore=(TextView)findViewById(R.id.user_turn_score);
        compOverallScore=(TextView)findViewById(R.id.c_score);
        compTurnScore=(TextView)findViewById(R.id.com_turn_score);
        Winner=(TextView)findViewById(R.id.Result);

        diceView=(ImageView)findViewById(R.id.dice);
    }
    public void onReset(View view)
    {
        diceView.setImageResource(R.drawable.dice1);
        user_overall_score = user_turn_score = comp_overall_score = comp_turn_score = 0;
        updateScore();
        rollbtn.setEnabled(true);
        holdbtn.setEnabled(true);
        Winner.setText("");
    }
    public void onRollDice(View view)
    {
        //int diceValue = 1 + (int) (Math.random() * ((6 - 1) + 1));

        int diceValue = random.nextInt(6) + 1;
        if (diceValue == 1) {
            //Log.d("Score", "You rolled 1");
            diceView.setImageResource(diceNo[diceValue-1]);
            //int temp=user_overall_score+user_turn_score;
            user_turn_score = 0;
            updateScore();
            /*if (temp >= winScore) {
                Winner.setText(R.string.user_winner);
                rollbtn.setEnabled(false);
                holdbtn.setEnabled(false);
                return;
            }*/
            rollbtn.setEnabled(false);
            cpuTurn();
        } else {
            //Log.d("Score", "You rolled " + diceValue);

           /* YoYo.with(Techniques.Shake)
                    .duration(250)
                    .playOn(ivDiceFace);*/

            diceView.setImageResource(diceNo[diceValue - 1]);
            user_turn_score += diceValue;
            updateScore();
            if (user_overall_score+user_turn_score >= winScore) {
                Winner.setText(R.string.user_winner);
                rollbtn.setEnabled(false);
                holdbtn.setEnabled(false);
                return;
            }
            //cpuTurn();
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
            return;
        }

        cpuTurn();
    }
    public void cpuTurn() {
        rollbtn.setEnabled(false);
        holdbtn.setEnabled(false);
        resetbtn.setEnabled(false);
        //int diceValue;
        //new Timer().schedule(new TimerTask() {
          //  @Override
           // public void run() {

        computerTurnHandler = new Handler();

        computerTurnHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // this code will be executed after 1 seconds



                     int diceValue = random.nextInt(6) + 1;
                    diceView.setImageResource(diceNo[diceValue - 1]);
                    if (diceValue == 1) {
                        //Log.d("Score", "CPU rolled 1");
                        comp_overall_score+=comp_turn_score;
                        comp_turn_score = 0;
                        updateScore();
                        rollbtn.setEnabled(true);
                        holdbtn.setEnabled(true);
                        resetbtn.setEnabled(true);
                        /*if (comp_overall_score >= winScore) {
                            Winner.setText(R.string.cpu_winner);
                            rollbtn.setEnabled(false);
                            holdbtn.setEnabled(false);
                            resetbtn.setEnabled(true);
                            return;
                        }*/
                    } else {
                        comp_turn_score += diceValue;
                        updateScore();
                        if (comp_overall_score+comp_turn_score >= winScore) {
                            Winner.setText(R.string.cpu_winner);
                            rollbtn.setEnabled(false);
                            holdbtn.setEnabled(false);
                            resetbtn.setEnabled(true);
                            return;
                        }
                        int temp=random.nextInt(20-15) + 15;
                        if(comp_turn_score <= temp )
                        {
                        //Log.d("Score", "CPU rolled " + diceValue);

                            cpuTurn();
                        }
                        else
                        {
                            comp_overall_score += comp_turn_score;
                            comp_turn_score = 0;
                            updateScore();
                            if (comp_overall_score >= winScore) {
                                Winner.setText(R.string.cpu_winner);
                                rollbtn.setEnabled(false);
                                holdbtn.setEnabled(false);
                                resetbtn.setEnabled(true);
                                return;
                            }
                            rollbtn.setEnabled(true);
                            holdbtn.setEnabled(true);
                            resetbtn.setEnabled(true);
                        }
                    }


            }
        }, 2000);
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
}
