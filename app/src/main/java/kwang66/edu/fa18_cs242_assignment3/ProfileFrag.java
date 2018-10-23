package kwang66.edu.fa18_cs242_assignment3;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;


import adapter.GridViewAdapter;
import network.EndPoint;
import network.RetrofitClientInstance;
import network.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFrag extends Fragment {
    private UserInfo user;

    public ProfileFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EndPoint service = RetrofitClientInstance.getRetrofitInstance().create(EndPoint.class);
        Call<UserInfo> call = service.getUser("octocat");
        final Context context= this.getContext();
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                user = response.body();
                ImageView avatar= (ImageView) getView().findViewById(R.id.avatar);
                Picasso.get().load(user.getAvatarUrl()).into(avatar);
                TextView login = (TextView) getView().findViewById(R.id.login);
                login.setText(user.getLogin());
                TextView create = (TextView) getView().findViewById(R.id.name);
                String name = user.getName();
                if (name==null){
                    create.setText("Created at "+user.getCreatedAt().substring(0,10));
                }
                else{
                    create.setText("Created by "+user.getName()+" at "+user.getCreatedAt().substring(0,10));
                }
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
                GridView gridView = (GridView)getView().findViewById(R.id.grid);
                GridViewAdapter adapter = new GridViewAdapter(sourceName, sourceValue,context);
                gridView.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d("STATE","Fail");
            }
        });
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
