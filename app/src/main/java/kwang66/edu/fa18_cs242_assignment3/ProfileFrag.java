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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFrag extends Fragment {



    public ProfileFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DatabaseReference mProfileReference = FirebaseDatabase.getInstance().getReference()
                .child("profile");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo user = dataSnapshot.getValue(UserInfo.class);
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
                GridView gridView = (GridView)getView().findViewById(R.id.grid);
                GridViewAdapter adapter = new GridViewAdapter(sourceName, sourceValue, getContext());
                gridView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

            }
        };
        mProfileReference.addValueEventListener(postListener);


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
