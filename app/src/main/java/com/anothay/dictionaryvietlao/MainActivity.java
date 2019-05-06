package com.anothay.dictionaryvietlao;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    @BindView(R.id.btn_swap)
    Button mBtnSwap;
    AdapterRecycle adapterRecycle;
    private boolean isVietLao = true;
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
        adapterRecycle = new AdapterRecycle(words,isVietLao,this);
        mRecyclerView.setAdapter(adapterRecycle);
        mBtnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVietLao = !isVietLao;
                if (isVietLao) {
                    mBtnSwap.setText("VIET-LAO");
                } else {
                    mBtnSwap.setText("LAO-VIET");
                }
                swap();
            }
        });
    }
    private void swap () {
        words = isVietLao ? wordDao.getAllWords() : wordDao.getLaoViet();
        adapterRecycle = new AdapterRecycle(words, isVietLao, this);
        mRecyclerView.setAdapter(adapterRecycle);
        adapterRecycle.notifyDataSetChanged();
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
        wordDao = new WordDao();
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
