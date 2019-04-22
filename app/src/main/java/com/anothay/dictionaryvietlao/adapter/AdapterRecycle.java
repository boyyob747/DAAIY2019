package com.anothay.dictionaryvietlao.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anothay.dictionaryvietlao.R;
import com.anothay.dictionaryvietlao.model.Word;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class AdapterRecycle extends RecyclerView.Adapter<AdapterRecycle.WordHolder> {
    private RealmResults<Word> words;
    public AdapterRecycle(RealmResults<Word> words) {
        this.words = words;
    }
    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewMy = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_word, viewGroup, false);
        return new WordHolder(viewMy);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder wordHolder, int i) {
        if (words.get(i) != null) {
            if (words.get(i).getLao() != null) {
                wordHolder.textLao.setText(words.get(i).getLao());
            }
            if (words.get(i).getViet() != null) {
                wordHolder.textViet.setText(words.get(i).getViet());
            }
        }
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public static class WordHolder extends RecyclerView.ViewHolder {
        TextView textViet;
        TextView textLao;
        public WordHolder(@NonNull View itemView) {
            super(itemView);
            textViet = (TextView) itemView.findViewById(R.id.text_viet);
            textLao = (TextView) itemView.findViewById(R.id.text_lao);
        }
    }
}
