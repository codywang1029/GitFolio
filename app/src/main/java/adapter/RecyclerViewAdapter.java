package adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kwang66.edu.fa18_cs242_assignment3.R;
import network.Repo;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Repo> repo;
    Context mContext;

    public static class MyViewHolder extends ViewHolder {
        private final TextView name;
        private final TextView owner;
        private final TextView description;
        //get each view from layout
        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.text_view);
            owner = v.findViewById(R.id.user);
            description = v.findViewById(R.id.description);
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

