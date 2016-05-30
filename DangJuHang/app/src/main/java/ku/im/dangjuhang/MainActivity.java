package ku.im.dangjuhang;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ku.im.dangjuhang.Fragments.ArticleFragment;
import ku.im.dangjuhang.Fragments.HeadlinesFragment;
import ku.im.dangjuhang.Fragments.LikeFrag;
import ku.im.dangjuhang.Fragments.MapFrag;
import ku.im.dangjuhang.Fragments.MyFrag;
import ku.im.dangjuhang.Fragments.RcmFrag;
import ku.im.dangjuhang.Fragments.RegFrag;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HeadlinesFragment.OnArticleSelectedListener {

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
        new Client().NaverLogin(this);
    }

    void init(Bundle savedInstanceState){
        if(findViewById(R.id.container_fragment)!=null){
            if(savedInstanceState !=null){
                return;
            }
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            HeadlinesFragment firstFragment = new HeadlinesFragment();
            firstFragment.setArguments(getIntent().getExtras());
            fragmentManager.beginTransaction()
                    .add(R.id.container_fragment, firstFragment)
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                MapFrag mapFrag = new MapFrag();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, mapFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                //mapFrag.Set(getApplicationContext());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();

        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
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

        int id = item.getItemId();
        if (id == R.id.user) {
            MyFrag myFrag = new MyFrag();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, myFrag);
            fragmentTransaction.commit();
            // 내 상태 정보
        }
        else if (id == R.id.search) {

        } // 검색
        else if (id == R.id.like) {
            LikeFrag likeFrag = new LikeFrag();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, likeFrag);
            fragmentTransaction.commit();

        }// 좋아요 했어
        else if (id == R.id.rcm) {
            RcmFrag rcmFrag = new RcmFrag();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, rcmFrag);
            fragmentTransaction.commit();

        }// 추천해줘
        else if (id == R.id.reg) {
            RegFrag regFrag = new RegFrag();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, regFrag);
            fragmentTransaction.commit();

        }// 등록했어
        else if (id == R.id.news) {
            HeadlinesFragment firstFragment = new HeadlinesFragment();
            firstFragment.setArguments(getIntent().getExtras());
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, firstFragment);
            fragmentTransaction.commit();
        }//최신

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onArticleSelected(int position) { // Headline을 선택했을 떄 Article로 넘어가는 상황 만들때!?!
        Fragment articleFrag;
        articleFrag = fragmentManager.findFragmentByTag("article");
        //article이란 태그를 가지고 있는 fragment를 찾음!
        if (articleFrag != null) {// 이미 있으면
            ((ArticleFragment)articleFrag).updateArticleView(position);
        }
        else {
            ArticleFragment newFragment = ArticleFragment.newInstance(position);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, newFragment, "article");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}