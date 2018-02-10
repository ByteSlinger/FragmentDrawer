package net.byteslinger.fragmentdrawer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Activity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	/**
	 * Load the passed fragment into the content frame.  This is the "secret sauce" for
	 * using 1 Activity and navigating thru multiple Fragments.
	 *
	 * @param fragmentClass - A fragment class name.
	 *
	 * 20180210 ByteSlinger - Original.
	 */
	public void loadFragment(Class fragmentClass) {
		Fragment fragment = null;

		try {
			fragment = (Fragment) fragmentClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		FragmentManager fragmentManager = getSupportFragmentManager();

		// load the new fragment into the "content" frame.  See layout/content.xml.
		fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		if (savedInstanceState == null) {
			loadFragment(Fragment1.class);
		}

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				loadFragment(Button.class);
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.drawer);
		navigationView.setNavigationItemSelectedListener(this);
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
		getMenuInflater().inflate(R.menu.top_right, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// this comes from the top right menu.  See menu/top_right.xml.
		if (id == R.id.action_preferences) {
			loadFragment(Preferences.class);        // the secret sauce!

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		Class fragmentClass = null;

		// Set the fragment class depending on which menu item was clicked. See menu/drawer.xml.

		if (id == R.id.nav1) {
			fragmentClass = Fragment1.class;
		} else if (id == R.id.nav2) {
			fragmentClass = Fragment2.class;
		} else if (id == R.id.nav3) {
			fragmentClass = Fragment3.class;
		}

		if (fragmentClass == null) {
			return false;
		}

		loadFragment(fragmentClass);    // the secret sauce!

		// make sure the drawer is closed when the new fragment loads
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);

		return true;
	}
}


