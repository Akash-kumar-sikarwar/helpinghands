package com.akashkumar.helpinghands;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author dipenp
 *
 *         This activity will add Navigation Drawer for our application and all
 *         the code related to navigation drawer. We are going to extend all our
 *         other activites from this BaseActivity so that every activity will
 *         have Navigation Drawer in it. This activity layout contain one frame
 *         layout in which we will add our child activity layout.
 */
public class BaseActivity extends Activity {

	/**
	 * Frame layout: Which is going to be used as parent layout for child
	 * activity layout. This layout is protected so that child activity can
	 * access this
	 */
	protected FrameLayout frameLayout;

	/**
	 * ListView to add navigation drawer item in it. We have made it protected
	 * to access it in child class. We will just use it in child class to make
	 * item selected according to activity opened.
	 */

	protected ListView mDrawerList;

	/**
	 * List item array for navigation drawer items.
	 */
	int ICONS[] = { R.drawable.ic_home_black_24dp, R.drawable.contacts, R.drawable.enail, R.drawable.speech,
			R.drawable.password, R.drawable.details, R.drawable.help_symbol };
	protected String[] listArray = { "Home", "Contacts", "Email Contacts", "Word activation", "Change password",
			"Change details","Change Pattern Lock", "Help" };

	/**
	 * Static variable for selected item position. Which can be used in child
	 * activity to know which item is selected from the list.
	 */
	protected static int position;

	/**
	 * This flag is used just to check that launcher activity is called first
	 * time so that we can open appropriate Activity on launch and make list
	 * item position selected accordingly.
	 */
	private static boolean isLaunch = true;

	/**
	 * Base layout node of this Activity.
	 */
	private DrawerLayout mDrawerLayout;

	/**
	 * Drawer listner class for drawer open, close etc.
	 */
	private ActionBarDrawerToggle actionBarDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer_base_layout);

		frameLayout = (FrameLayout) findViewById(R.id.content_frame);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		// GravityCompat.START);

		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, listArray));
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				openActivity(position);
			}
		});

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		actionBarDrawerToggle = new ActionBarDrawerToggle(
				this, /* host Activity */
				mDrawerLayout, /* DrawerLayout object */
				R.drawable.apptheme_ic_navigation_drawer, /*
															 * nav drawer image to
															 * replace 'Up'
															 * caret
															 */
				R.string.open_drawer) /*
										 * "close drawer" description for
										 * accessibility
										 */
		{
			@Override
			public void onDrawerClosed(View drawerView) {
				getActionBar().setTitle(listArray[position]);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(getString(R.string.app_name));
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				super.onDrawerStateChanged(newState);
			}
		};
		mDrawerLayout.post(new Runnable() {
			public void run() {
				actionBarDrawerToggle.syncState();
			}
		});
		mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

		/**
		 * As we are calling BaseActivity from manifest file and this base
		 * activity is intended just to add navigation drawer in our app. We
		 * have to open some activity with layout on launch. So we are checking
		 * if this BaseActivity is called first time then we are opening our
		 * first activity.
		 */
		if (isLaunch) {
			/**
			 * Setting this flag false so that next time it will not open our
			 * first activity. We have to use this flag because we are using
			 * this BaseActivity as parent activity to our other activity. In
			 * this case this base activity will always be call when any child
			 * activity will launch.
			 */
			isLaunch = false;
			openActivity(0);
		}
	}

	/**
	 * @param position
	 * 
	 *            Launching activity when any list item is clicked.
	 */
	protected void openActivity(int position) {

		/**
		 * We can set title & itemChecked here but as this BaseActivity is
		 * parent for other activity, So whenever any activity is going to
		 * launch this BaseActivity is also going to be called and it will reset
		 * this value because of initialization in onCreate method. So that we
		 * are setting this in child activity.
		 */
		// mDrawerList.setItemChecked(position, true);
		// setTitle(listArray[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
		BaseActivity.position = position; // Setting currently selected position
											// in this field so that it will be
											// available in our child
											// activities.
		String itemPressed = (String) listArray[position];
		switch (position) {
		case 0:
			startActivity(new Intent(this, CameraView.class)); // Call for Home
			break;
		case 1:
			startActivity(new Intent(this, Contacts.class)); // Call for
																// Contacts
			break;

		case 2:
			Database database = new Database(this); // Call for Email Contacts
			if (!database.hasGmail()) {
				createDialog();
			} else {
				Intent email = new Intent(this, EmailContacts.class);
				startActivity(email);
			}
			break;
		case 3:
			Intent subMenuView = new Intent(this, SubMenuViews.class);
			subMenuView.putExtra("buttonPressed", "Word activation");
			startActivity(subMenuView); // Call for Word Activation
			break;
		case 4:
			Intent subMenuView2 = new Intent(this, SubMenuViews.class);
			subMenuView2.putExtra("buttonPressed", "Change password");
			startActivity(subMenuView2); // Call for
																	// Change
																	// password
			break;
		case 5:
			Intent subMenuView3 = new Intent(this, SubMenuViews.class);
			subMenuView3.putExtra("buttonPressed", "Change details");
			startActivity(subMenuView3); // Call for
																	// Change
																	// details
			break;
		case 6:
			Intent l = new Intent(this, ChangeLock.class);
			l.putExtra("FROM_ACTIVITY","BaseActivity");
			startActivity(l); // Call for
																	// Change Lock
			break;
		case 7:
			startActivity(new Intent(this, Instructions.class)); // Call for
																	// Help
			break;

		default:
			break;
		}

		//Toast.makeText(this, "Selected Item Position::" + position, Toast.LENGTH_LONG).show();
	}

	public void createDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		TextView view = new TextView(this);
		view.setText(
				R.string.for_you_to_be_able_to_send_emails_you_must_supply_your_gmail_and_password_for_emergency_send_);
		view.setTextSize(20);
		alert.setTitle(Html.fromHtml("<font color='#CC0000'>Warning!</font>"));

		alert.setView(view);
		alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Intent emailForm = new Intent(getApplicationContext(), EmailForm.class);
				BaseActivity.this.finish();
				startActivity(emailForm);
				return;
			}
		});

		alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		alert.create();
		alert.show();
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * 
	 * getMenuInflater().inflate(R.menu.main, menu); return
	 * super.onCreateOptionsMenu(menu); }
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* Called whenever we call invalidateOptionsMenu() */
	/*
	 * @Override public boolean onPrepareOptionsMenu(Menu menu) { // If the nav
	 * drawer is open, hide action items related to the content view boolean
	 * drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	 * menu.findItem(R.id.action_settings).setVisible(!drawerOpen); return
	 * super.onPrepareOptionsMenu(menu); }
	 */
	/* We can override onBackPressed method to toggle navigation drawer */
	@Override
	public void onBackPressed() {
		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			mDrawerLayout.openDrawer(mDrawerList);
		}
	}
}
