package klapper.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1103304 on 2017/4/14.
 */

public class customAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInf;
    List<todolist> mItemList;

    public customAdapter(Context context, List<todolist> mItemList) {
        mLayoutInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mItemList = mItemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        todolist todo = mItemList.get(position);
        View v = mLayoutInf.inflate(R.layout.list_content,parent,false);
        v.setTag(todo);
        TextView title = (TextView)v.findViewById(R.id.title);
        TextView content = (TextView)v.findViewById(R.id.content);
        TextView datetime = (TextView)v.findViewById(R.id.date);
        Button btn = (Button)v.findViewById(R.id.button2);
        title.setText(todo.getTitle().toString());
        content.setText(todo.getContent().toString());
        datetime.setText(todo.getDatatime().toString());
        btn.setTag(todo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todolist t = (todolist) v.getTag();
                todolistActivity.deleteItem(t.getUsername(),t.getTitle(),t.getContent());
                //notifyDataSetChanged();
            }
        });
        return v;
    }

}
