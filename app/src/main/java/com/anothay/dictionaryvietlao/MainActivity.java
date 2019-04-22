package com.anothay.dictionaryvietlao;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.anothay.dictionaryvietlao.adapter.AdapterRecycle;
import com.anothay.dictionaryvietlao.model.Word;
import com.anothay.dictionaryvietlao.model.WordDao;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private WordDao wordDao;
    private RealmResults<Word> words;
    @BindView(R.id.search_view_word)
    SearchView searchViewWord;
    @BindView(R.id.recycle_view_words)
    RecyclerView mRecyclerView;
    AdapterRecycle adapterRecycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initValue();
        loadData();
        initWordList();
        initSearchView();
    }

    private void initWordList() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapterRecycle = new AdapterRecycle(words);
        mRecyclerView.setAdapter(adapterRecycle);
    }

    private void searchWords(String key, String key_word) {
        if (key_word.isEmpty()) {
            words = wordDao.getAllWords();
        } else {
            words = wordDao.searchWord(key,key_word);
        }
        adapterRecycle = new AdapterRecycle(words);
        mRecyclerView.setAdapter(adapterRecycle);
        adapterRecycle.notifyDataSetChanged();
    }

    private void loadData() {
        words = wordDao.getAllWords();
    }

    void initValue() {
        wordDao = new WordDao();
    }
    void initSearchView() {
        searchViewWord.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchWords("viet", s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                searchWords("viet", s);
                return false;
            }
        });
    }
}
