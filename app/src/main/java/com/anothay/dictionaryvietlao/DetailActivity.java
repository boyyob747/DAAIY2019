package com.anothay.dictionaryvietlao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.word)
    TextView mTxtWord;
    @BindView(R.id.word_detail)
    TextView mTxtWordDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (extras != null) {
            String word = extras.getString("word");
            String wordDetail = extras.getString("wordDetail");
            mTxtWord.setText(word);
            mTxtWordDetail.setText(wordDetail);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
