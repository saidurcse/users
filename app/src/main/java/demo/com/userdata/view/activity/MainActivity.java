package demo.com.userdata.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import demo.com.userdata.R;
import demo.com.userdata.contact.MainContact;
import demo.com.userdata.presenter.MainPresenter;
import demo.com.userdata.retrofit.model.User;
import demo.com.userdata.util.MyPreferences;
import demo.com.userdata.view.adapter.userAdapter;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MainContact.View{

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainContact.UserActionListener userActionListener;
    private Button userData;
    private RecyclerView recycler;
    private ProgressBar progress;
    private Realm realm;
    private MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userActionListener = new MainPresenter(this);

        realm = Realm.getDefaultInstance();
        myPreferences = MyPreferences.getPreferences(this);

        progress = findViewById(R.id.progress);
        userData = findViewById(R.id.userData);
        userData.setOnClickListener( v->{

            if(myPreferences.getFirstTimeSyncStatus() == false) {

                myPreferences.setSyncStatus(true);
                userActionListener.getUserData();

            }else{
                removeUserButton(false);
                forwardToShowDataFromRealm();
            }
        });
    }

    @Override
    public void forwardToShowDataFromRealm() {

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        recycler.setAdapter(new userAdapter(realm.where(User.class).findAll()));
    }

    @Override
    public void showProgressIndicator(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void removeUserButton(boolean show) {
        userData.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showToastMsg(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}
