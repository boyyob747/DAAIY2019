package com.anothay.dictionaryvietlao;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.anothay.dictionaryvietlao.adapter.AdapterRecycle;
import com.anothay.dictionaryvietlao.model.LocalDAO;
import com.anothay.dictionaryvietlao.model.Word;
import com.anothay.dictionaryvietlao.model.WordDao;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class FavoriteActivity extends AppCompatActivity {
    private LocalDAO wordDao;
    private RealmResults<Word> words;
    @BindView(R.id.search_view_word)
    SearchView searchViewWord;
    @BindView(R.id.recycle_view_words)
    RecyclerView mRecyclerView;
    AdapterRecycle adapterRecycle;
    private boolean isVietLao = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (extras != null) {
            this.isVietLao = extras.getBoolean("isVietLao");
        }
        initValue();
        loadData();
        initWordList();
        initSearchView();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    private void initWordList() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapterRecycle = new AdapterRecycle(words,isVietLao,this);
        mRecyclerView.setAdapter(adapterRecycle);
    }
    private void searchWords(String key, String key_word) {
        if (key_word.isEmpty()) {
            words = isVietLao ? wordDao.getAllWords() : wordDao.getLaoViet();
        } else {
            words = wordDao.searchWord(key,key_word);
        }
        adapterRecycle = new AdapterRecycle(words,isVietLao,this);
        mRecyclerView.setAdapter(adapterRecycle);
        adapterRecycle.notifyDataSetChanged();
    }

    private void loadData() {
        words = wordDao.getAllWords();
    }

    void initValue() {
        wordDao = new LocalDAO(getApplicationContext(), true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        adapterRecycle.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }

    void initSearchView() {
        searchViewWord.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchWords(isVietLao ? "viet" : "lao", s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                searchWords(isVietLao ? "viet" : "lao", s);
                return false;
            }
        });
    }
}
