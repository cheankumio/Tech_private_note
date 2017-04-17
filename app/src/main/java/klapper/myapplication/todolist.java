package klapper.myapplication;

import io.realm.RealmObject;

/**
 * Created by c1103304 on 2017/4/14.
 */

public class todolist extends RealmObject {
    String username;
    String title;
    String content;
    String datatime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }
}
