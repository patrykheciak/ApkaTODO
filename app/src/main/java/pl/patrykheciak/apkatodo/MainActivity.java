package pl.patrykheciak.apkatodo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import pl.patrykheciak.apkatodo.db.AppDatabase;
import pl.patrykheciak.apkatodo.db.TaskList;
import pl.patrykheciak.apkatodo.db.TaskSublist;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TOOLBAR_TITLE = "TOOLBAR_TITLE";
    private NavigationView navigationView;
    DemoCollectionPagerAdapter viewPagerAdapter;
    ViewPager mViewPager;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private TabLayout tabLayout;

    private int visibleListId = -1;
    private String VISIBLE_LIST_ID = "VISIBLE_LIST_ID";

    private int visibleSublistId = -1;
    private String VISIBLE_SUBLIST_ID = "VISIBLE_SUBLIST_ID";

    private int visibleSublistPosition = -1;
    private String VISIBLE_SUBLIST_POSITION = "VISIBLE_SUBLIST_POSITION";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(VISIBLE_LIST_ID, visibleListId);
        outState.putInt(VISIBLE_SUBLIST_ID, visibleSublistId);
        outState.putInt(VISIBLE_SUBLIST_POSITION, visibleSublistPosition);
        outState.putString(TOOLBAR_TITLE, toolbar.getTitle().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNewTaskActivity();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        viewPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.d("PAGER", "onPageScrolled " + position);
                visibleSublistPosition = position;
                visibleSublistId = (int) viewPagerAdapter.sublists.get(position).getId_tasksublist();
            }
        });

        tabLayout.setupWithViewPager(mViewPager);

        if (savedInstanceState != null) {
            visibleListId = savedInstanceState.getInt(VISIBLE_LIST_ID);
            visibleSublistId = savedInstanceState.getInt(VISIBLE_SUBLIST_ID);
            visibleSublistPosition = savedInstanceState.getInt(VISIBLE_SUBLIST_POSITION);
            toolbar.setTitle(savedInstanceState.getString(TOOLBAR_TITLE));
        }
//        startActivity(new Intent(this, IntroActivity.class));


    }

    @Override
    protected void onResume() {
        super.onResume();
        fillMenuWithLists();

        AppDatabase dbInstance = ((TodoApplication) getApplication()).getDbInstance();
        List<TaskList> all = dbInstance.taskListDao().getAll();

        if (visibleListId == -1)
            if (!all.isEmpty())
                visibleListId = (int) all.get(0).getId_tasklist();

        if (visibleListId > 0) {
            updateUiForList(visibleListId);

            if (visibleSublistPosition >= 0) {
                mViewPager.setCurrentItem(visibleSublistPosition);
            }
        }
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mViewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);
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
        int id = item.getItemId();


        if (id == R.id.nav_new_list) {
            launchNewListActivity();
        } else {
            updateUiForList(id);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void launchNewListActivity() {
        Intent i = new Intent(this, NewListActivity.class);
        startActivityForResult(i, 1);
    }

    private void launchNewTaskActivity() {
        Intent i = new Intent(this, NewTaskActivity.class);
        i.putExtra(
                NewTaskActivity.EXTRA_SUBLIST_ID,
                visibleSublistId);
        startActivityForResult(i, 2);
    }

    private void updateUiForList(int id) {
        AppDatabase dbInstance = ((TodoApplication) getApplication()).getDbInstance();

        List<TaskSublist> taskSublists = dbInstance.taskSublistDao().tasksublistsOfTasklist(id);
        viewPagerAdapter.setSublists(taskSublists);

        TaskList list = dbInstance.taskListDao().findById(id);
//        if (toolbar.getTitle().toString().equals("ApkaTODO"))
        toolbar.setTitle(list.getName());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String name = data.getStringExtra("name");
                String color = data.getStringExtra("color");

                // create new list, sublist
                // put list in menu
                TaskList list = new TaskList();
                list.setName(name);
                list.setColor(color);

                AppDatabase dbInstance = ((TodoApplication) getApplication()).getDbInstance();
                long listId = dbInstance.taskListDao().insert(list);

                TaskSublist sublist = new TaskSublist();
                sublist.setId_tasklist(listId);
                dbInstance.taskSublistDao().insert(sublist);

                fillMenuWithLists();
                updateUiForList((int) listId);

                mViewPager.setCurrentItem(viewPagerAdapter.getCount() - 1);
            }
        }
    }

    private void fillMenuWithLists() {
        final Menu menu = navigationView.getMenu();
        menu.removeGroup(R.id.task_group);

        AppDatabase dbInstance = ((TodoApplication) getApplication()).getDbInstance();
        for (TaskList tl : dbInstance.taskListDao().getAll()) {
            int img = 0;
            switch (tl.getColor()) {
                case "czerwony":
                    img = R.mipmap.ic_red;
                    break;
                case "zielony":
                    img = R.mipmap.ic_green;
                    break;
                case "niebieski":
                    img = R.mipmap.ic_blue;
                    break;
                case "żółty":
                    img = R.mipmap.ic_yellow;
                    break;
            }
            menu.add(R.id.task_group, (int) tl.getId_tasklist(), Menu.NONE, tl.getName());
            menu.findItem((int) tl.getId_tasklist()).setIcon(img);

//            menu.findItem((int) tl.getId_tasklist()).setIconTintMode(new ColorStateList());
        }
//        menu.findItem(0).setTitle("du").setIcon(R.drawable.ic_done_white);
    }
}
