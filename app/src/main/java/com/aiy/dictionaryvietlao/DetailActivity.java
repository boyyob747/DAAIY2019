package com.aiy.dictionaryvietlao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiy.dictionaryvietlao.model.LocalDAO;
import com.aiy.dictionaryvietlao.model.Word;
import com.aiy.dictionaryvietlao.model.WordDao;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.word)
    TextView mTxtWord;
    @BindView(R.id.word_detail)
    TextView mTxtWordDetail;
    @BindView(R.id.img_favorite)
    ImageView mImgFavorite;
    LocalDAO localDAO;
    Word word;
    boolean isVietLao = false;
    WordDao wordDao;
    boolean isFavorite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        wordDao = new WordDao();
        localDAO = new LocalDAO(getApplicationContext(),true);
        if (extras != null) {
            String word = extras.getString("word");
            String wordDetail = extras.getString("wordDetail");
            this.isVietLao = extras.getBoolean("isVietLao");
            this.word = wordDao.findWord(isVietLao ? "viet" : "lao", word);
            this.isFavorite = localDAO.checkIsFavorite(this.word);
            if (this.isFavorite) {
                this.mImgFavorite.setImageResource(R.drawable.ic_star_yellow_800_24dp);
            } else {
                this.mImgFavorite.setImageResource(R.drawable.ic_star_border_yellow_800_24dp);
            }
            mTxtWord.setText(word);
            mTxtWordDetail.setText(wordDetail);
        }
        initOnClik();
    }

    private void initOnClik() {
        mImgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    isFavorite = false;
                    localDAO.deleteFavorite(word);
                    mImgFavorite.setImageResource(R.drawable.ic_star_border_yellow_800_24dp);
                } else {
                    isFavorite = true;
                    localDAO.setFavorite(word);
                    mImgFavorite.setImageResource(R.drawable.ic_star_yellow_800_24dp);
                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
