package ku.im.dangjuhang;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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
import android.widget.Toast;

import ku.im.dangjuhang.Fragments.ArticleFragment;
import ku.im.dangjuhang.Fragments.HeadlinesFragment;
import ku.im.dangjuhang.Fragments.MyFrag;
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
        boolean login = new Client().NaverLogin(this);
        if(login)
        {
            Toast.makeText(this, Client.userdata.get("name") + "님 환영합니다.\n연령 : " + Client.userdata.get("age")+"0대", Toast.LENGTH_LONG).show();
        }

//        Hangsa hangsa = new Hangsa("Title","시작날","종료날","Time","건국대학교 새천년관",null,null,null,null,"설명",null);
//        boolean result = new Client().RegisterEvent(hangsa);
//        new Client().GetLikeNum("100");
//        new Client().GetLike(String.valueOf(77107));
//        new Client().GetAllEvent();
//        new Client().GetMyEvent();
        new Client().SearchPlace("ㅅ", null, null);

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
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);

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
            //내가 등록한 행사들 나열
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            MyFrag myFrag= new MyFrag();
            fragmentTransaction.replace(R.id.container_fragment, myFrag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (id == R.id.search) {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

        } // 검색
        else if (id == R.id.like) {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            HeadlinesFragment firstFragment = new HeadlinesFragment();
            firstFragment.setArguments(getIntent().getExtras());
            fragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, firstFragment)
                    .commit();
            //여기서는 어댑터를 바꿔줘야 한다잉
        }// 좋아요
        else if (id == R.id.rcm) {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            HeadlinesFragment firstFragment = new HeadlinesFragment();
            firstFragment.setArguments(getIntent().getExtras());
            fragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, firstFragment)
                    .commit();
            //여기서는 어댑터를 바꿔줘야 한다잉
        }// 추천
        else if (id == R.id.reg) {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            RegFrag regFrag = new RegFrag();
            fragmentTransaction.replace(R.id.container_fragment, regFrag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            //등록페이지
        }// 등록
        else if (id == R.id.news) {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            HeadlinesFragment firstFragment = new HeadlinesFragment();
            firstFragment.setArguments(getIntent().getExtras());
            fragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, firstFragment)
                    .commit();
            //여기서는 어댑터를 바꿔줘야 한다잉
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