package klapper.myapplication;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by c1103304 on 2017/4/14.
 */

public class userinfo extends RealmObject{
    @Required
    String username;
    @Required
    String password;
    RealmList<todolist> todolists;

    public RealmList<todolist> getTodolists() {
        return todolists;
    }

    public void setTodolists(RealmList<todolist> todolists) {
        this.todolists = todolists;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
