package com.example.c19trace.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c19trace.R;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.FAQVH> {

    List<FaqClass> faqList;

    public FAQAdapter(List<FaqClass> faqList){
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public FAQAdapter.FAQVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_setup, parent, false);
        return new FAQVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQAdapter.FAQVH holder, int position) {
        FaqClass faq = faqList.get(position);
        holder.question.setText(faq.getQuestion());
        holder.answer.setText(faq.getAnswer());
        boolean isExpandable = faqList.get(position).isExpandable();
        holder.relativeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {

        return faqList.size();
    }

    public class FAQVH extends RecyclerView.ViewHolder{
        TextView question, answer;
        LinearLayout linearLayout;
        RelativeLayout relativeLayout;

        public FAQVH(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.questions);
            answer = itemView.findViewById((R.id.ans1));
            linearLayout = itemView.findViewById(R.id.linear);
            relativeLayout = itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FaqClass faq = faqList.get(getAdapterPosition());
                    faq.setExpandable(!faq.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
