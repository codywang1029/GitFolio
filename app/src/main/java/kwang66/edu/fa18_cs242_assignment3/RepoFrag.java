package kwang66.edu.fa18_cs242_assignment3;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import adapter.RecyclerViewAdapter;
import network.EndPoint;
import network.Repo;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        EndPoint service = RetrofitClientInstance.getRetrofitInstance().create(EndPoint.class);
        Call<List<Repo>> call = service.getRepo("octocat");
        final Context context= this.getContext();
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repo = response.body();

                RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_list);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                mRecyclerView.setHasFixedSize(true);


                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                LayoutManagerType mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                mRecyclerView.setLayoutManager(mLayoutManager);

                // specify an adapter (see also next example)
                RecyclerView.Adapter mAdapter = new RecyclerViewAdapter(repo,context);
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.d("STATE","Fail");
            }
        });
        return inflater.inflate(R.layout.fragment_repo, container, false);
    }

}
