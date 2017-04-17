package klapper.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1103304 on 2017/4/14.
 */

public class customAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater mLayoutInf;
    List<todolist> mItemList;
    List<todolist> originalItemList;

    public customAdapter(Context context, List<todolist> mItemList) {
        mLayoutInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mItemList = mItemList;
        Log.d("MYLOG","Item size: "+mItemList.size());
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
        title.setText(todo.getTitle().toString());
        content.setText(todo.getContent().toString());
        datetime.setText(todo.getDatatime().toString());
        return v;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString();
                FilterResults result = new FilterResults();
                if(originalItemList == null){
                    synchronized (this){
                        originalItemList = new ArrayList<>(mItemList);
                    }
                }

                if(constraint != null && constraint.toString().length()>0){
                    ArrayList<todolist> filteredItem = new ArrayList<>();
                    for(int i=0; i<originalItemList.size();i++){
                        todolist cs = originalItemList.get(i);
                        if(cs.getTitle().contains(constraint)) {
                            filteredItem.add(cs);
                        }
                        /*
                        if(cs.getTitle().contains(constraint)||cs.getContent().contains(constraint)||cs.getDatatime().contains(constraint)){
                            filteredItem.add(cs);
                        }*/
                    }
                    result.count = filteredItem.size();
                    result.values = filteredItem;
                }else{
                    synchronized (this){
                        ArrayList<todolist> list = new ArrayList<>(originalItemList);
                        result.values = list;
                        result.count = list.size();
                    }
                }
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mItemList = (ArrayList<todolist>)results.values;
                if(results.count>0){
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}
