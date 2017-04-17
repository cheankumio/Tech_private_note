package klapper.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;

/**
 * Created by c1103304 on 2017/4/14.
 */

public class todolistActivity extends AppCompatActivity{
    ListView mListView;
    customAdapter mListAdapter;
    String username,userlevel;
    SearchView searchView;
    List<todolist> user_todo_list;
    Realm realm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist_layout);
        realm = MainActivity.realm;
        setUpIfNeeded();
        listViewLoad();
    }

    private void setUpIfNeeded() {
        Intent getintent = getIntent();
        username = getintent.getStringExtra("username");
        mListView = (ListView)findViewById(R.id.listview);
        //mListView.setOnItemClickListener(clickListener);

        searchView = (SearchView)findViewById(R.id.searchView);
        // 設置SearchView 直接展開搜尋輸入欄位
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(false);
        int id = searchView.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(Color.DKGRAY);

        searchdata();
    }

    private void searchdata(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.d("MYLOG","QuertyText: "+newText);
                mListAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void listViewLoad() {
        Log.d("MYLOG","username: "+username);
        user_todo_list = realm.where(todolist.class).equalTo("username",username).findAll();
        for(todolist us:user_todo_list){
            Log.d("MYLOG",us.getTitle());
        }
        mListAdapter = new customAdapter(this,user_todo_list);
        mListView.setAdapter(mListAdapter);
        mListView.setTextFilterEnabled(true);
    }


    public void add(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("新增一則事項");
        LinearLayout ln = new LinearLayout(this);
        ln.setOrientation(LinearLayout.VERTICAL);
        ln.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        final EditText titleText = new EditText(this);
        final EditText contentText = new EditText(this);
        ln.addView(titleText);
        ln.addView(contentText);
        alert.setView(ln);
        alert.setPositiveButton("完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                realm.beginTransaction();
                todolist info = realm.createObject(todolist.class);
                info.setUsername(username);
                info.setTitle(titleText.getText().toString());
                info.setContent(contentText.getText().toString());
                info.setDatatime("9999/99/99");
                realm.commitTransaction();
            }
        });
        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
