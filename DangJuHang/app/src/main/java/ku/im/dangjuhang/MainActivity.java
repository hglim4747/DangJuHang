package ku.im.dangjuhang;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new Client().NaverLogin(this);

        init();
    }
    void init(){
        if(fragment == null){
            fragmentManager = getFragmentManager();
            fragment = fragmentManager.findFragmentById(R.id.frame);
            fragmentTransaction = fragmentManager.beginTransaction();
            NewsFrag newsFrag = new NewsFrag();
            fragmentTransaction.add(R.id.frame,newsFrag);
            fragmentTransaction.commit(); // 처음엔 뉴스피드
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int id = item.getItemId();
        if (id == R.id.user) {
                MyFrag myFrag = new MyFrag();
                fragmentTransaction.replace(R.id.frame, myFrag);
            // 내 상태 정보
        }
        else if (id == R.id.search) {

        } // 검색
        else if (id == R.id.like) {
            LikeFrag likeFrag = new LikeFrag();
            fragmentTransaction.replace(R.id.frame, likeFrag);
        }// 좋아요 했어
        else if (id == R.id.rcm) {
            RcmFrag rcmFrag = new RcmFrag();
            fragmentTransaction.replace(R.id.frame, rcmFrag);
        }// 추천해줘
        else if (id == R.id.reg) {
            RegFrag regFrag = new RegFrag();
            fragmentTransaction.replace(R.id.frame, regFrag);
        }// 등록했어
        else if (id == R.id.news) {
            NewsFrag newsFrag = new NewsFrag();
            fragmentTransaction.replace(R.id.frame,newsFrag);
        }//최신

        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
