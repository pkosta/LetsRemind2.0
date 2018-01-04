package palash.watermelon.letsremind.userinterface;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import de.hdodenhof.circleimageview.CircleImageView;
import palash.watermelon.letsremind.R;
import palash.watermelon.letsremind.databinding.ActivityHomeBinding;
import palash.watermelon.letsremind.datamodel.AppUser;
import palash.watermelon.letsremind.datamodel.User;
import palash.watermelon.letsremind.login.IProfileFetchResult;
import palash.watermelon.letsremind.login.LoginManager;
import palash.watermelon.letsremind.userinterface.login.LoginActivity;
import palash.watermelon.letsremind.userinterface.login.WelcomeActivity;
import palash.watermelon.letsremind.userinterface.navigationfragments.WorkListFragment;

public class HomeActivity
        extends
        AppCompatActivity
        implements
        IProfileFetchResult,
        NavigationView.OnNavigationItemSelectedListener,
        WorkListFragment.OnFragmentInteractionListener{

    private ActivityHomeBinding mBindingLayout;

    @Inject
    LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mBindingLayout = DataBindingUtil.setContentView(this, R.layout.activity_home);

        setUpToolbar();

        setUpNavigationView();

        /*TextView tvSample = (TextView) findViewById(R.id.sample);*/

        mLoginManager.registerToProfileFetchListener(this);
        mLoginManager.getLoggedInUser();

    }


    private void setUpToolbar() {
        mBindingLayout.tbHomeActivity.setTitle("Work List");
        setSupportActionBar(mBindingLayout.tbHomeActivity);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mBindingLayout.mainDrawerLayout,
                mBindingLayout.tbHomeActivity,
                R.string.open,
                R.string.close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        mBindingLayout.mainDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

    }

    private TextView tvUserName;
    private TextView tvEmail;
    private CircleImageView circleImageView;

    private void setUpNavigationView() {
        mBindingLayout.navigationView.setNavigationItemSelectedListener(this);
        View header = mBindingLayout.navigationView.getHeaderView(0);
        tvUserName = (TextView) header.findViewById(R.id.username);
        tvEmail = (TextView) header.findViewById(R.id.email);
        circleImageView = (CircleImageView) header.findViewById(R.id.profile_image);

    }

    @Override
    public void onProfileFetchSuccessful(User aUser) {

        if (!TextUtils.isEmpty(aUser.getPhotoUrl())) {
            Picasso.with(this)
                    .load(aUser.getPhotoUrl())
                    .into(circleImageView);
        }

        tvUserName.setText(aUser.getUsername());
        tvEmail.setText(aUser.getEmail());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                mLoginManager.signOut();
                mBindingLayout.mainDrawerLayout.closeDrawers();
                Intent welcomeActivityIntent = new Intent(HomeActivity.this,
                        WelcomeActivity.class);
                startActivity(welcomeActivityIntent);
                finish();
                return true;
            case R.id.worklist:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_layout_navigation_pages, new WorkListFragment())
                        .commit();
                mBindingLayout.mainDrawerLayout.closeDrawers();
                return true;
        }

        return false;
    }

    @Override
    public void onFragmentInteraction(@NotNull Uri uri) {

    }
}
