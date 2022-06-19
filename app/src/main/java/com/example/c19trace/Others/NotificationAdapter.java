package com.example.c19trace.Others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c19trace.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationVH> {

    private Context context;
    private List<NotificationClass> notificationList;
    private FirebaseAuth firebaseAuth;

    public NotificationAdapter(Context context, List<NotificationClass> faqList){
        this.context = context;
        this.notificationList = notificationList;

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_setup, parent, false);
        return new NotificationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationVH holder, int position) {
        NotificationClass notificationClass = notificationList.get(position);


        holder.title.setText(notificationClass.getNotificationTitle());
        holder.info.setText(notificationClass.getNotificationInfo());
        boolean isExpandable = notificationList.get(position).isExpandable();
        holder.relativeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {

        return notificationList.size();
    }

    public class NotificationVH extends RecyclerView.ViewHolder{
        TextView title, info;
        LinearLayout linearLayout;
        RelativeLayout relativeLayout;

        public NotificationVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_notificationTitle);
            info = itemView.findViewById((R.id.tv_notificationInfo));
            linearLayout = itemView.findViewById(R.id.linear);
            relativeLayout = itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NotificationClass notificationClass = notificationList.get(getAdapterPosition());
                    notificationClass.setExpandable(!notificationClass.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
