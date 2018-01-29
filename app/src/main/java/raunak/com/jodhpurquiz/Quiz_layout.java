package raunak.com.jodhpurquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static raunak.com.jodhpurquiz.R.id.score;

public class Quiz_layout extends AppCompatActivity {
TextView quest, Score;
Button b1,b2,b3,b4,playagain,quit;
int correctans=0;

String sc;
int count=0;
    int scorint=0;
DBAdapter obj;
RelativeLayout unfocus, scorelayout;
ArrayList<Suitcase> arr= new ArrayList<>();
    ArrayList<Suitcasemedium> arrmed= new ArrayList<>();
    ArrayList<Suitcaseexpert> arrexp= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_layout);

        quest=(TextView)findViewById(R.id.ques);
        Score=(TextView)findViewById(score);
        b1=(Button)findViewById(R.id.opt1);
        b2=(Button)findViewById(R.id.opt2);
        b3=(Button)findViewById(R.id.opt3);
        b4=(Button)findViewById(R.id.opt4);

        unfocus = (RelativeLayout)findViewById(R.id.unfocus_layout);
        scorelayout = (RelativeLayout)findViewById(R.id.score_layout);
        playagain = (Button)findViewById(R.id.play_again);
        quit = (Button)findViewById(R.id.quit);

        ////////////////////Database Connectivity/////////////
        obj= DBAdapter.getDBAdapter(getApplicationContext());

        if(obj.checkDatabase()==false)
            obj.createDatabase(getApplicationContext());

        obj.openDatabase();
        arr=obj.getData();
        arrmed=obj.getDatamed();
        arrexp=obj.getDataexp();

        ///////////////////////////////////////////////////
        Display();


    }

    public void Display()
    {
        quest.setText(arr.get(count).getQuest());
        b1.setText(arr.get(count).getO1());
        b2.setText(arr.get(count).getO2());
        b3.setText(arr.get(count).getO3());
        b4.setText(arr.get(count).getO4());
        correctans=arr.get(count).getAns();
        scorint=arr.get(count).getScore();

    }

    public void Displaymed()
    {
        quest.setText(arrmed.get(count).getQuest());
        b1.setText(arrmed.get(count).getO1());
        b2.setText(arrmed.get(count).getO2());
        b3.setText(arrmed.get(count).getO3());
        b4.setText(arrmed.get(count).getO4());
        correctans=arrmed.get(count).getAns();
        scorint=arrmed.get(count).getScore();

    }

    public void Displayexp()
    {
        quest.setText(arrexp.get(count).getQuest());
        b1.setText(arrexp.get(count).getO1());
        b2.setText(arrexp.get(count).getO2());
        b3.setText(arrexp.get(count).getO3());
        b4.setText(arrexp.get(count).getO4());
        correctans=arrexp.get(count).getAns();
        scorint=arrexp.get(count).getScore();

    }

    public void Check(View a)
    {
        if      (
                a.getId()==R.id.opt1 && correctans==1
                        ||
                        a.getId()==R.id.opt2 && correctans==2
                        ||
                        a.getId()==R.id.opt3 && correctans==3
                        ||
                        a.getId()==R.id.opt4 && correctans==4

                )
        {
            if (count < 4) {
                Toast.makeText(getApplicationContext(), "Bingo!! you nailed it", Toast.LENGTH_LONG).show();
                count++;
                Display();
            } else if(count >= 4 && count < 8){
                //count++;
               // Displaymed();

                    /*if (
                                a.getId()==R.id.opt1 && correctans==1
                                ||
                                a.getId()==R.id.opt2 && correctans==2
                                ||
                                a.getId()==R.id.opt3 && correctans==3
                                ||
                                a.getId()==R.id.opt4 && correctans==4

                        )*/


                            Toast.makeText(getApplicationContext(), "Bingo!! you nailed it", Toast.LENGTH_LONG).show();
                            count++;
                            Displaymed();

                         } else if(count >= 8 && count < 12){
                             //count++;
                             //Displayexp();
                                /*if    (
                                            a.getId()==R.id.opt1 && correctans==1
                                            ||
                                            a.getId()==R.id.opt2 && correctans==2
                                            ||
                                            a.getId()==R.id.opt3 && correctans==3
                                            ||
                                            a.getId()==R.id.opt4 && correctans==4

                                    ) */


                                    Toast.makeText(getApplicationContext(), "Bingo!! you nailed it", Toast.LENGTH_LONG).show();
                                    count++;
                                    Displayexp();

                                } else if (count >= 12) {
                                    sc = Integer.toString(scorint);
                                    Score.setText(sc);

                                    Toast.makeText(getApplicationContext(), "Wow!! Winner your score:" + sc, Toast.LENGTH_LONG).show();
                                    unfocus.setVisibility(View.VISIBLE);
                                    scorelayout.setVisibility(View.VISIBLE);


                                    // Score.setText(scorint);


                                    playagain.setOnClickListener(new View.OnClickListener() {

                                        public void onClick(View v) {

                                            unfocus.setVisibility(View.INVISIBLE);
                                            scorelayout.setVisibility(View.INVISIBLE);

                                            count = 0;
                                            Display();

                                        }
                                    });

                                    quit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finish();
                                        }
                                    });

                                }
                            }

        else if(count >= 4 && count < 8)
        {
          count=0;
          Display();
        }
        else if (count >= 8 && count <= 12)
        {
            count=4;
            Displaymed();
        } else {

            scorint = scorint-10;
            sc = Integer.toString(scorint);
            Score.setText(sc);
            Toast.makeText(getApplicationContext(),"Oops!! you Lost with score of :" + sc ,Toast.LENGTH_LONG).show();
            unfocus.setVisibility(View.VISIBLE);
            scorelayout.setVisibility(View.VISIBLE);


            playagain.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   unfocus.setVisibility(View.INVISIBLE);
                   scorelayout.setVisibility(View.INVISIBLE);
                    count=0;
                    Display();
              }
            });

            quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  finish();
               }
           });

        }
    }
}


