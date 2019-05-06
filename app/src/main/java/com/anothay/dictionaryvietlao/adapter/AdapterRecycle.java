package com.anothay.dictionaryvietlao.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anothay.dictionaryvietlao.DetailActivity;
import com.anothay.dictionaryvietlao.R;
import com.anothay.dictionaryvietlao.model.Word;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class AdapterRecycle extends RecyclerView.Adapter<AdapterRecycle.WordHolder>  {
    private RealmResults<Word> words;
    private boolean isVietLao;
    private Activity activity;

    public AdapterRecycle(RealmResults<Word> words,boolean isVietLao,Activity activity) {
        this.words = words;
        this.isVietLao = isVietLao;
        this.activity = activity;
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
                String lao = words.get(i).getLao();
                if (isVietLao) {
                    wordHolder.textLao.setText(lao);
                } else {
                    wordHolder.textViet.setText(lao);
                }
            }
            if (words.get(i).getViet() != null) {
                if (isVietLao) {
                    wordHolder.textViet.setText(words.get(i).getViet());
                } else {
                    wordHolder.textLao.setText(words.get(i).getViet());
                }
            }
        }
        wordHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                String word =  isVietLao ? words.get(position).getViet() : words.get(position).getLao();
                String wordDetail = isVietLao ? words.get(position).getLao() : words.get(position).getViet();
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra("word",word);
                intent.putExtra("wordDetail",wordDetail);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }


    public static class WordHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViet;
        TextView textLao;
        private ItemClickListener itemClickListener;
        public WordHolder(@NonNull View itemView) {
            super(itemView);
            textViet = (TextView) itemView.findViewById(R.id.text_viet);
            textLao = (TextView) itemView.findViewById(R.id.text_lao);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }
    }
}
