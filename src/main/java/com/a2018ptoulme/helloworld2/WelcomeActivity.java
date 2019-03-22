package com.a2018ptoulme.helloworld2;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class WelcomeActivity extends AppCompatActivity {
    private Button mYesButton;
    public int question;
    private Button mNoButton;
    private Switch mySwitch;
    private Animation myAnimation;
    private Button mNextButton;
    private Button mHButton;
    private static final int REQUEST_CODEX_CHEAT =0;
    public TextView mQuestionTextView;
    public TextView mscoreId;
    public TextView mHeading;
    public TextView highScore;
    public SharedPreferences.Editor editor;
    public int mCurrentIndex = 0;
    public SharedPreferences prefs;
    public int mcur = 0;
    public int qnum = 1;
    public int hiScore;
    public boolean g = false;
    public int last = -1;
    public int score = 0;
    public boolean u;
    public Question[] mQuestionBank = new Question[]
            {
                    new Question(R.string.Question1, true),
                    new Question(R.string.Question2, true),
                    new Question(R.string.Question3, false),
                    new Question(R.string.Question4, true),
                    new Question(R.string.Question5, false),

            };

    public void checkAnswer(boolean userPressed) {
        boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
      if(userPressed == answer && u == true){
          messageResId = R.string.cheat;
          last++;
          mcur++;
          mYesButton.setEnabled((false));
          mNoButton.setEnabled((false));
          mHButton.setEnabled((false));
          u=false;
          g = true;
      }
       else if (userPressed == answer) {
            messageResId = R.string.positive;
            score++;
            last++;
            mcur++;
            mYesButton.setEnabled((false));
            mNoButton.setEnabled((false));
          mHButton.setEnabled((false));
        } else {
            messageResId = R.string.negative;
            last++;
            mcur++;
            mYesButton.setEnabled((false));
            mNoButton.setEnabled((false));
            mHButton.setEnabled((false));
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        String current = "Score:";
        String s = "" + score;

        mscoreId.setText(current + s);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Hint.RESULT_OK){
                 u = data.getBooleanExtra("result",false);
            }
            if (resultCode == Hint.RESULT_CANCELED) {
                 u = false;
            }
        }
    }//onActivityResult
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        highScore =(TextView)(findViewById(R.id.high));
        mHButton = (Button) findViewById(R.id.hbutton);
        mQuestionTextView = (TextView) (findViewById(R.id.question_text_view));
        mHeading = (TextView) (findViewById(R.id.Heading));
        mscoreId = (TextView) (findViewById(R.id.scoreId));
        question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mySwitch = (Switch)findViewById(R.id.musicS);
        mYesButton = (Button) findViewById(R.id.YesButton);
        mNoButton = (Button) findViewById(R.id.NoButton);
        mNextButton = (Button) findViewById(R.id.NextButton);

        myAnimation = AnimationUtils.loadAnimation(this, R.anim.myanimation);

      prefs = getSharedPreferences("key", Context.MODE_PRIVATE);

        editor = prefs.edit();
         if(prefs.getInt("key",0) != 0) {

             hiScore = prefs.getInt("key", 0);
         }
        else
         {
             editor.putInt("key" , score);
             editor.commit();
         }
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mp.isPlaying()){
                    mp.pause();
                }
                else
                {
                    mp.start();
                }

            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex == 4) {
                    String x = "" + score;
                    String f = "Congratulations! Score:" + score + "/5";
                    mHeading.setText(f);
                    mYesButton.setEnabled(false);
                    mNoButton.setEnabled(false);
                    mNextButton.setEnabled(false);
                    mHButton.setEnabled(false);
                    mQuestionTextView.setText("");
                    mscoreId.setText("");
                    if(score>hiScore)
                    {
                        editor.putInt("key", score);
                        editor.commit();
                    }
                    String y = "High Score:" + prefs.getInt("key",0) + "/5";
                    highScore.setText(y);
                    return;
                } else {
                    qnum++;
                }
                mCurrentIndex++;
                question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question+"");

                String cur = "" + qnum;
                String question = "Question:";
                mHeading.setText(question + cur);
                mYesButton.setEnabled(true);
                mNoButton.setEnabled(true);
                mHButton.setEnabled(true);


            }
        });


        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        mHButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
            Intent i = Hint.newIntent(WelcomeActivity.this, answerIsTrue);
                i.putExtra("b", answerIsTrue);
                startActivityForResult(i, 1);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
    }
}

