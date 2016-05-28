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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HeadlinesFragment.OnArticleSelectedListener {


    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.container_fragment)!=null){
            if(savedInstanceState !=null){
                return;
            }
            HeadlinesFragment firstFragment = new HeadlinesFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.container_fragment, firstFragment)
                    .commit();
        }
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
//        if(fragment == null){
//            fragmentManager = getFragmentManager();
//            fragment = fragmentManager.findFragmentById(R.id.container_fragment);
//            fragmentTransaction = fragmentManager.beginTransaction();
//            ArticleFragment articleFragment = new ArticleFragment();
//            fragmentTransaction.add(R.id.container_fragment,articleFragment);
//            fragmentTransaction.commit(); // 처음엔 뉴스피드
//        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();

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
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int id = item.getItemId();
        if (id == R.id.user) {
            MyFrag myFrag = new MyFrag();
            fragmentTransaction.replace(R.id.container_fragment, myFrag);
            // 내 상태 정보
        }
        else if (id == R.id.search) {

        } // 검색
        else if (id == R.id.like) {
            LikeFrag likeFrag = new LikeFrag();
            fragmentTransaction.replace(R.id.container_fragment, likeFrag);
        }// 좋아요 했어
        else if (id == R.id.rcm) {
            RcmFrag rcmFrag = new RcmFrag();
            fragmentTransaction.replace(R.id.container_fragment, rcmFrag);
        }// 추천해줘
        else if (id == R.id.reg) {
            RegFrag regFrag = new RegFrag();
            fragmentTransaction.replace(R.id.container_fragment, regFrag);
        }// 등록했어
        else if (id == R.id.news) {
            HeadlinesFragment firstFragment = new HeadlinesFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_fragment, firstFragment)
                    .commit();
        }//최신

        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onArticleSelected(int position) { // Headline을 선택했을 떄 Article로 넘어가는 상황 만들때!?!
        Fragment articleFrag;
        articleFrag = getFragmentManager().findFragmentByTag("article");
        //article이란 태그를 가지고 있는 fragment를 찾음!
        if (articleFrag != null) {// 이미 있으면
            ((ArticleFragment)articleFrag).updateArticleView(position);
        }
        else {
            ArticleFragment newFragment = ArticleFragment.newInstance(position);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container_fragment, newFragment, "article");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}