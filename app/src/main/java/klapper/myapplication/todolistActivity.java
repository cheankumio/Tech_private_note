package klapper.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by c1103304 on 2017/4/14.
 */

public class todolistActivity extends AppCompatActivity{
    ListView mListView;
    public static customAdapter mListAdapter;
    String username,userlevel;
    EditText searchView;
    public List<todolist> user_todo_list;
    List<todolist2> user_list;
    public static Realm realm;
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

        searchView = (EditText)findViewById(R.id.editText);


    }

    public void searchdata(View v){
        user_todo_list = realm.where(todolist.class).equalTo("username",username).contains("title",searchView.getText().toString())
                .or().equalTo("username",username).contains("content",searchView.getText().toString())
                .or().equalTo("username",username).contains("datatime",searchView.getText().toString()).findAll();
        mListAdapter = new customAdapter(this,user_todo_list);
        mListView.setAdapter(mListAdapter);
    }

    private void listViewLoad() {
        user_list = new ArrayList<>();
        user_todo_list = realm.where(todolist.class).equalTo("username",username).findAll();
        mListAdapter = new customAdapter(this,user_todo_list);
        mListView.setAdapter(mListAdapter);
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
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                info.setDatatime(sDateFormat.format(new java.util.Date()));
                realm.commitTransaction();
                mListAdapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    public static void deleteItem(String username,String title,String content){
        realm.beginTransaction();
        RealmQuery<todolist> query = realm.where(todolist.class);
        query.equalTo("username",username).equalTo("title",title).equalTo("content",content).findFirst().deleteFromRealm();
        realm.commitTransaction();
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
