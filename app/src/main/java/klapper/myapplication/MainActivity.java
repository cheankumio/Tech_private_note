package klapper.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.annotations.RealmModule;


public class MainActivity extends AppCompatActivity {
    // 宣告元件
    EditText username;
    EditText password;
    public static Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Realm.init(this);
        setUpElement();

    }

    private void setUpElement() {
        /**
         *   程式碼與視窗介面(元件)的連結必須先註冊元件
         *
         *   將程式碼中的username、password
         *   綁定到 activity_main.xml 的 editText3、editText5
         */
        username = (EditText)findViewById(R.id.editText3);
        password = (EditText)findViewById(R.id.editText5);


        realm = Realm.getDefaultInstance();

        Shimmer shimmer = new Shimmer();
        shimmer.start((ShimmerTextView)findViewById(R.id.shimmer_tv));
    }

    public void login(View v){
        // 在login方法下，註冊兩個String 字串，用來暫存使用者輸入資料
        String inputName = username.getText().toString();
        String inputPassword = password.getText().toString();

        /*  使用if判斷式進行判斷
        *
        *   意思是：
        *   如果( inputName.內容與("1234")是否相符
        *           而且 inputPassword.內容與("1234")是否也相符 )
        *
        *   如果都相符，就會執行裡面的Toast彈出式提式窗
        *
        *   如果不相符，就會執行else{ } 中的彈出式提式窗
         */
        RealmResults<userinfo> userlogin = realm.where(userinfo.class).equalTo("username",inputName).findAll();
        if(userlogin.size()>0) {
            if (userlogin.get(0).getPassword().equals(inputPassword)) {
                Toast.makeText(this, "使用者 " + inputName + " 你好.", Toast.LENGTH_SHORT).show();
                Intent in = new Intent();
                in.putExtra("username",inputName);
                in.setClass(this,todolistActivity.class);
                startActivity(in);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            } else {
                Toast.makeText(this, "密碼錯誤.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "找不到使用者, 請先註冊", Toast.LENGTH_SHORT).show();
        }
    }

    public void regist(View v){
        String inputName = username.getText().toString();
        String inputPassword = password.getText().toString();
        if(inputName.length()>1 && inputPassword.length()>1) {

            realm.beginTransaction();
            userinfo user = realm.createObject(userinfo.class);
            user.setUsername(inputName);
            user.setPassword(inputPassword);
            realm.commitTransaction();
            Toast.makeText(this, inputName+" 註冊成功.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "註冊失敗.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
