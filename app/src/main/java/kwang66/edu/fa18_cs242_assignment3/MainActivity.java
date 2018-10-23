package kwang66.edu.fa18_cs242_assignment3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import network.EndPoint;
import network.RetrofitClientInstance;
import network.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView navView;
    private ProfileFrag profileFrag;
    private RepoFrag repoFrag;
    private FollowerFrag followerFrag;
    private FollowingFrag followingFrag;

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
