package adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kwang66.edu.fa18_cs242_assignment3.OtherProfile;
import kwang66.edu.fa18_cs242_assignment3.R;
import network.Follower;
import network.Repo;

import static android.support.v4.content.ContextCompat.startActivity;

public class FollowerViewAdapter extends RecyclerView.Adapter<FollowerViewAdapter.FollowerViewHolder> {

    private List<Follower> followers;
    Context mContext;

    public static class FollowerViewHolder extends RecyclerView.ViewHolder {
        private final TextView login;
        private final ImageView avatar;
        //get each view from layout
        public FollowerViewHolder(View v) {
            super(v);
            login = v.findViewById(R.id.follower_login);
            avatar = v.findViewById(R.id.follower_avatar);

        }
    }


    public FollowerViewAdapter(List<Follower> followers, Context context) {
        this.followers = followers;
        this.mContext=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FollowerViewAdapter.FollowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.follower_list_item, parent, false);
        FollowerViewAdapter.FollowerViewHolder vh = new FollowerViewAdapter.FollowerViewHolder(v);
        return vh;
    }

    //replace content of a view
    @Override
    public void onBindViewHolder(FollowerViewAdapter.FollowerViewHolder holder, final int position) {
        holder.login.setText(followers.get(position).getLogin());
        Picasso.get().load(followers.get(position).getAvatarUrl()).into(holder.avatar);
        holder.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OtherProfile.class);
                intent.putExtra("user", followers.get(position).getLogin());
                mContext.startActivity(intent);
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return followers.size();
    }
}


