package kwang66.edu.fa18_cs242_assignment3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.FollowerViewAdapter;
import adapter.RecyclerViewAdapter;
import network.Follower;
import network.Repo;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerFrag extends Fragment {

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    public FollowerFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DatabaseReference mFollowerReference = FirebaseDatabase.getInstance().getReference().child("followers");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Follower> followers = new ArrayList<>();
                for (DataSnapshot followerSnapshot : dataSnapshot.getChildren()) {
                    try {
                        Follower follower = followerSnapshot.getValue(Follower.class);
                        followers.add(follower);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        break;
                    }
                }

                RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.follower_recycler_list);
                mRecyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                FollowerFrag.LayoutManagerType mCurrentLayoutManagerType = FollowerFrag.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                mRecyclerView.setLayoutManager(mLayoutManager);
                // connect data set with view
                RecyclerView.Adapter mAdapter = new FollowerViewAdapter(followers,getContext());
                mRecyclerView.setAdapter(mAdapter);
                int resId = R.anim.layout_animation_fall_down;
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
                mRecyclerView.setLayoutAnimation(animation);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

            }
        };
        mFollowerReference.addValueEventListener(postListener);
        return inflater.inflate(R.layout.fragment_follower, container, false);
    }

}
