package adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import java.util.List;

import kwang66.edu.fa18_cs242_assignment3.R;

public class GridViewAdapter extends BaseAdapter {
    List<String> sourceName;
    List<Integer> sourceValue;
    Context mContext;

    public GridViewAdapter(List<String> sourceName, List<Integer> sourceValue, Context mContext) {
        this.sourceName = sourceName;
        this.sourceValue = sourceValue;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return sourceName.size();
    }

    @Override
    public Object getItem(int position) {
        return sourceName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * customize view based on different content to be displayed
     * @param position
     * @param convertView
     * @param parent
     * @return the view created
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = new View(mContext);
            gridView = inflater.inflate(R.layout.profile_item,null);
            Button button = (Button) gridView.findViewById(R.id.button);
            if (position==3){
                button.setText("Website");
                button.setBackgroundColor(0xFF009688);
            }
            if (position==0){
                button.setText(sourceName.get(position)+"\n"+sourceValue.get(position));
                button.setBackgroundColor(0xFFFF5722);
            }
            if (position==2){
                button.setText(sourceName.get(position)+"\n"+sourceValue.get(position));
                button.setBackgroundColor(0xFF2F4056);
            }
            if (position==1){
                button.setText(sourceName.get(position)+"\n"+sourceValue.get(position));
                button.setBackgroundColor(0xFFFFB800);
            }
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(position == 3) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceName.get(position)));
                        mContext.startActivity(browserIntent);
                    }
                }
            });

        }
        else {
            gridView = (View) convertView;
        }

        return gridView;


    }
}
