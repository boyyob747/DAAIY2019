package com.aiy.dictionaryvietlao.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiy.dictionaryvietlao.DetailActivity;
import com.aiy.dictionaryvietlao.R;
import com.aiy.dictionaryvietlao.model.LocalDAO;
import com.aiy.dictionaryvietlao.model.Word;

import io.realm.RealmResults;

public class AdapterRecycle extends RecyclerView.Adapter<AdapterRecycle.WordHolder>  {
    private RealmResults<Word> words;
    private boolean isVietLao;
    private Activity activity;
    LocalDAO localDAO;
    public AdapterRecycle(RealmResults<Word> words,boolean isVietLao,Activity activity) {
        this.words = words;
        this.isVietLao = isVietLao;
        this.activity = activity;
        this.localDAO = new LocalDAO(activity.getApplicationContext(), isVietLao);
    }
    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewMy = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_word, viewGroup, false);
        return new WordHolder(viewMy);
    }

    @Override
    public void onBindViewHolder(@NonNull final WordHolder wordHolder, final int i) {
        if (words.get(i) != null) {
            if (localDAO.checkIsFavorite(words.get(i))) {
                wordHolder.imageFavorite.setVisibility(View.VISIBLE);
            } else {
                wordHolder.imageFavorite.setVisibility(View.GONE);
            }
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
                if (isLongClick) {
                    if (localDAO.checkIsFavorite(words.get(position))) {
                        wordHolder.imageFavorite.setVisibility(View.GONE);
                        localDAO.deleteFavorite(words.get(position));
                    } else {
                        wordHolder.imageFavorite.setVisibility(View.VISIBLE);
                        localDAO.setFavorite(words.get(position));
                    }
                } else {
                    String word =  isVietLao ? words.get(position).getViet() : words.get(position).getLao();
                    String wordDetail = isVietLao ? words.get(position).getLao() : words.get(position).getViet();
                    Intent intent = new Intent(activity, DetailActivity.class);
                    intent.putExtra("word",word);
                    intent.putExtra("wordDetail",wordDetail);
                    intent.putExtra("isVietLao",isVietLao);
                    activity.startActivityForResult(intent, 999);
                }

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
        ImageView imageFavorite;
        private ItemClickListener itemClickListener;
        public WordHolder(@NonNull View itemView) {
            super(itemView);
            textViet = (TextView) itemView.findViewById(R.id.text_viet);
            textLao = (TextView) itemView.findViewById(R.id.text_lao);
            imageFavorite = (ImageView) itemView.findViewById(R.id.image_favorite);
            imageFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(v,getAdapterPosition(),true);
                }
            });
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
