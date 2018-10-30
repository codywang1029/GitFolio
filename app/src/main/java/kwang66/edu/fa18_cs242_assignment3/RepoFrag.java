package kwang66.edu.fa18_cs242_assignment3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
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

import adapter.RecyclerViewAdapter;
import network.Repo;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class RepoFrag extends Fragment {


    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    public RepoFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseReference mRepoReference = FirebaseDatabase.getInstance().getReference().child("repos");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Repo> repo = new ArrayList<>();
                for (DataSnapshot repoSnapshot : dataSnapshot.getChildren()) {
                    try {
                        Repo repo1 = repoSnapshot.getValue(Repo.class);
                        repo.add(repo1);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        break;
                    }
                }
                RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_list);
                mRecyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                LayoutManagerType mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                mRecyclerView.setLayoutManager(mLayoutManager);
                // connect data set with view
                RecyclerView.Adapter mAdapter = new RecyclerViewAdapter(repo,getContext());
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
        mRepoReference.addValueEventListener(postListener);

        return inflater.inflate(R.layout.fragment_repo, container, false);
    }

}
