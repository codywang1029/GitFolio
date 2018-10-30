package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import kwang66.edu.fa18_cs242_assignment3.R;
import network.EndPoint;
import network.Repo;
import network.RetrofitClientInstance;
import network.StarredRepo;
import network.UserInfo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Repo> repo;
    Context mContext;

    public static class MyViewHolder extends ViewHolder {
        private final TextView name;
        private final TextView owner;
        private final TextView description;
        private final ImageView starred;
        //get each view from layout
        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.text_view);
            owner = v.findViewById(R.id.user);
            description = v.findViewById(R.id.description);
            starred = v.findViewById(R.id.star);
        }
    }


    public RecyclerViewAdapter(List<Repo> repo,Context context) {
        this.repo = repo;
        this.mContext=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //replace content of a view
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String starred = repo.get(position).getStarred();
        if (starred.equals("true")){
            holder.starred.setImageResource(R.drawable.star_gold);
        }
        holder.starred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.child("repos").removeValue();
                if (repo.get(position).getStarred().equals("true")){
                    ImageView imageView= (ImageView) v.findViewById(R.id.star);
                    imageView.setImageResource(R.drawable.star_gray);
                    repo.get(position).setStarred("false");
                    EndPoint service = RetrofitClientInstance.getRetrofitInstance().create(EndPoint.class);
                    Call<ResponseBody> call = service.unstarRepo("kwang66",repo.get(position).getName());
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
                }
                else{
                    ImageView imageView= (ImageView) v.findViewById(R.id.star);
                    imageView.setImageResource(R.drawable.star_gold);
                    repo.get(position).setStarred("true");
                    EndPoint service = RetrofitClientInstance.getRetrofitInstance().create(EndPoint.class);
                    Call<ResponseBody> call = service.starRepo("kwang66",repo.get(position).getName());
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

                }
                for (Repo repo1 : repo){
                    database.child("repos").child(String.valueOf(repo1.getId())).setValue(repo1);
                }
            }
        });
        holder.name.setText(repo.get(position).getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repo.get(position).getHtmlUrl()));
                mContext.startActivity(browserIntent);
            }
        });
        holder.owner.setText("Created by "+repo.get(position).getOwner().getLogin());
        String des = repo.get(position).getDescription();
        if (des==null){
            des="Empty description";
        }
        holder.description.setText(des);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return repo.size();
    }
}

