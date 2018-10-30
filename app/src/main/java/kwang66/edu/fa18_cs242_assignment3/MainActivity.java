package kwang66.edu.fa18_cs242_assignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerViewAdapter;
import network.EndPoint;
import network.Follower;
import network.OAuthClientInstance;
import network.Repo;
import network.RetrofitClientInstance;
import network.StarredRepo;
import network.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;


public class MainActivity extends AppCompatActivity {



    private FrameLayout frameLayout;
    private BottomNavigationView navView;
    private ProfileFrag profileFrag;
    private RepoFrag repoFrag;
    private FollowerFrag followerFrag;
    private FollowingFrag followingFrag;

    private final String login = "kwang66";

    //Navigation functionality with fragment transaction
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.profile:
                    setFragment(profileFrag);
                    return true;
                case R.id.repo:
                    setFragment(repoFrag);
                    return true;
                case R.id.follower:
                    setFragment(followerFrag);
                    return true;
                case R.id.following:
                    setFragment(followingFrag);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        EndPoint service = RetrofitClientInstance.getRetrofitInstance().create(EndPoint.class);
        Call<UserInfo> call = service.getUser(login);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                UserInfo user = response.body();
                database.child("profile").setValue(user);
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d("STATE","Fail");
            }
        });
        Call<List<Repo>> repoCall = service.getRepo(login);
        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                List<Repo> repos = response.body();
                database.child("repos").removeValue();
                for (Repo repo : repos){
                    database.child("repos").child(String.valueOf(repo.getId())).setValue(repo);
                }
            }
            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.d("STATE","Fail");
            }
        });

        Call<List<Follower>> followerCall = service.getFollower(login);
        followerCall.enqueue(new Callback<List<Follower>>() {
            @Override
            public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                List<Follower> followers = response.body();
                database.child("followers").removeValue();
                for (Follower follower : followers){
                    database.child("followers").child(String.valueOf(follower.getLogin())).setValue(follower);
                }
            }
            @Override
            public void onFailure(Call<List<Follower>> call, Throwable t) {
                Log.d("STATE","Fail");
            }
        });

        Call<List<Follower>> followingCall = service.getFollowing(login);
        followingCall.enqueue(new Callback<List<Follower>>() {
            @Override
            public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                List<Follower> followings = response.body();
                database.child("following").removeValue();
                for (Follower following : followings){
                    database.child("following").child(String.valueOf(following.getLogin())).setValue(following);
                }
            }
            @Override
            public void onFailure(Call<List<Follower>> call, Throwable t) {
                Log.d("STATE","Fail");
            }
        });

        Call<List<StarredRepo>> starCall = service.getStarred();
        starCall.enqueue(new Callback<List<StarredRepo>>() {
            @Override
            public void onResponse(Call<List<StarredRepo>> call, Response<List<StarredRepo>> response) {
                final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                List<StarredRepo> starredRepos = response.body();
                List<String> namesContainer = new ArrayList<>();
                for (StarredRepo repo:starredRepos){
                    namesContainer.add(repo.getName());
                }
                final List<String> starredReposNames = namesContainer;
                database.child("starred").removeValue();
                database.child("repo").removeValue();
                DatabaseReference mRepoReference = FirebaseDatabase.getInstance().getReference().child("repos");
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot repoSnapshot : dataSnapshot.getChildren()) {
                            try {
                                Repo repo1 = repoSnapshot.getValue(Repo.class);
                                if (starredReposNames.contains(repo1.getName())){
                                    repo1.setStarred("true");
                                }
                                database.child("repos").child(String.valueOf(repo1.getId())).setValue(repo1);
                            }
                            catch(Exception e){
                                e.printStackTrace();
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
                };
                mRepoReference.addValueEventListener(postListener);
            }
            @Override
            public void onFailure(Call<List<StarredRepo>> call, Throwable t) {
                Log.d("STATE","Fail");
            }
        });


        navView = (BottomNavigationView) findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        frameLayout = (FrameLayout) findViewById(R.id.main_frame);
        profileFrag = new ProfileFrag();
        repoFrag = new RepoFrag();
        followerFrag = new FollowerFrag();
        followingFrag = new FollowingFrag();
        setFragment(profileFrag);

    }

    /**
     * Do fragment transaction
     * @param fragment the fragment to be displayed
     */
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }


}
