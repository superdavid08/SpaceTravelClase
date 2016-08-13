package elsuper.david.com.spacetravel;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsuper.david.com.spacetravel.data.ApodService;
import elsuper.david.com.spacetravel.data.Data;
import elsuper.david.com.spacetravel.fragment.FragmentApod;
import elsuper.david.com.spacetravel.fragment.FragmentListing;
import elsuper.david.com.spacetravel.model.MarsRovertResponse;
import elsuper.david.com.spacetravel.model.Photo;
import elsuper.david.com.spacetravel.ui.view.apod.list.adapter.NasaApodAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListActivity extends AppCompatActivity {

    //@BindView(R.id.mars_rover_listing) RecyclerView marsRoverListingRecycler;
    @BindView(R.id.listing_toolbar)
    Toolbar toolbar;
    @BindView(R.id.listing_navigation_view)
    NavigationView navigationView;
    @BindView(R.id.listing_navigation_drawer)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);
        setContentView(R.layout.listing_navigation_activity);
        ButterKnife.bind(this);


        try {
                PackageInfo info = getPackageManager().getPackageInfo(
                        "elsuper.david.com.spacetravel", PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        }
        catch (PackageManager.NameNotFoundException e) { }
        catch (NoSuchAlgorithmException e) { }




        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                drawerLayout.closeDrawers();

                switch (item.getItemId()){
                    case R.id.today_apod_item:
                        getFragmentManager().beginTransaction().replace(R.id.FragmentFolder, new FragmentApod()).commit();
                        //Snackbar.make(findViewById(android.R.id.content), "Today Apod Item", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case R.id.mars_rover_item:
                        getFragmentManager().beginTransaction().replace(R.id.FragmentFolder, new FragmentListing()).commit();
                        //Snackbar.make(findViewById(android.R.id.content), "Mars Rover", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case R.id.favorite_item:
                        Snackbar.make(findViewById(android.R.id.content), "Favorities", Snackbar.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getFBUserInfo();

    }

    private void getFBUserInfo() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try{
                    SimpleDraweeView userImage = (SimpleDraweeView) findViewById(R.id.user_image);
                    userImage.setImageURI("http://graph.facebook.com/" + object.getString("id") + "/picture?type=large");
                    TextView userName = (TextView) findViewById(R.id.user_name);
                    userName.setText(object.getString("name"));

                    Log.d("nameFB",object.getString("name"));
                    Log.d("idFB",object.getString("id"));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        request.executeAsync();
    }

}

