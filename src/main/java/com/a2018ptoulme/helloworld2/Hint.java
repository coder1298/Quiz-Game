package com.a2018ptoulme.helloworld2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Hint extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.a2018ptoulme.helloworld2.answer_is_true";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    public boolean used= false;


    public static Intent newIntent(Context packagecontext, boolean answerIsTrue) {
        Intent i = new Intent(packagecontext, Hint.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
         mAnswerIsTrue = getIntent().getExtras().getBoolean("b");
        mAnswerTextView = (TextView) findViewById(R.id.answerText);

        mShowAnswer = (Button) findViewById(R.id.hbutton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mAnswerIsTrue) {
                    mAnswerTextView.setText("Answer: Yes");
                    Intent returnIntent = new Intent();
                    used = true;
                    returnIntent.putExtra("result",used);
                    setResult(Hint.RESULT_OK,returnIntent);
                } else {
                    mAnswerTextView.setText("Answer: No");
                    Intent returnIntent = new Intent();
                    used = true;
                    returnIntent.putExtra("result",used);
                    setResult(Hint.RESULT_OK,returnIntent);
                }
            }
        });
    }
}
