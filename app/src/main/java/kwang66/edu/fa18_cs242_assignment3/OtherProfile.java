package kwang66.edu.fa18_cs242_assignment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import adapter.GridViewAdapter;
import adapter.RecyclerViewAdapter;
import network.EndPoint;
import network.RetrofitClientInstance;
import network.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        EndPoint service = RetrofitClientInstance.getRetrofitInstance().create(EndPoint.class);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        Call<UserInfo> call = service.getUser(user);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                UserInfo user = response.body();
                ImageView avatar= (ImageView) findViewById(R.id.avatar);
                Picasso.get().load(user.getAvatarUrl()).into(avatar);
                TextView login = (TextView) findViewById(R.id.login);
                login.setText(user.getLogin());
                TextView create = (TextView) findViewById(R.id.name);
                String name = user.getName();
                if (name==null){
                    create.setText("Created at "+user.getCreatedAt().substring(0,10));
                }
                else{
                    create.setText("Created by "+user.getName()+" at "+user.getCreatedAt().substring(0,10));
                }
                //initialize data set
                List<String> sourceName=new ArrayList<>();
                sourceName.add("Repo Count");
                sourceName.add("Follower");
                sourceName.add("Following");
                sourceName.add(user.getHtmlUrl());
                List<Integer> sourceValue = new ArrayList<>();
                sourceValue.add(user.getPublicRepos());
                sourceValue.add(user.getFollowers());
                sourceValue.add(user.getFollowing());
                sourceValue.add(-1);
                //connect data set with view
                GridView gridView = (GridView)findViewById(R.id.grid);
                GridViewAdapter adapter = new GridViewAdapter(sourceName, sourceValue, getBaseContext());
                gridView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d("STATE","Fail");
            }
        });
    }
}
