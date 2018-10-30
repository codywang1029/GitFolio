package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import kwang66.edu.fa18_cs242_assignment3.OtherProfile;
import kwang66.edu.fa18_cs242_assignment3.R;
import network.EndPoint;
import network.Follower;
import network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingViewAdapter extends RecyclerView.Adapter<FollowingViewAdapter.FollowingViewHolder> {

    private List<Follower> followings;
    Context mContext;

    public static class FollowingViewHolder extends RecyclerView.ViewHolder {
        private final TextView login;
        private final ImageView avatar;
        private final Button unfollow;
        //get each view from layout
        public FollowingViewHolder(View v) {
            super(v);
            login = v.findViewById(R.id.following_login);
            avatar = v.findViewById(R.id.following_avatar);
            unfollow = v.findViewById(R.id.unfollow);
        }
    }


    public FollowingViewAdapter(List<Follower> followings, Context context) {
        this.followings = followings;
        this.mContext=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FollowingViewAdapter.FollowingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.following_list_item, parent, false);
        FollowingViewAdapter.FollowingViewHolder vh = new FollowingViewAdapter.FollowingViewHolder(v);
        return vh;
    }

    //replace content of a view
    @Override
    public void onBindViewHolder(FollowingViewAdapter.FollowingViewHolder holder, final int position) {
        holder.login.setText(followings.get(position).getLogin());
        holder.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OtherProfile.class);
                intent.putExtra("user", followings.get(position).getLogin());
                mContext.startActivity(intent);
            }
        });
        Picasso.get().load(followings.get(position).getAvatarUrl()).into(holder.avatar);
        holder.unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.child("following").child(followings.get(position).getLogin()).removeValue();
                EndPoint service = RetrofitClientInstance.getRetrofitInstance().create(EndPoint.class);
                Call<ResponseBody> call = service.unfollow(followings.get(position).getLogin());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("STATE", response.message());
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("STATE","Fail");
                    }
                });
                followings.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());


            }
        });
    }

    @Override
    public int getItemCount() {
        return followings.size();
    }
}


