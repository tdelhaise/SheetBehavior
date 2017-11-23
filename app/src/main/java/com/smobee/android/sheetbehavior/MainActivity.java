package com.smobee.android.sheetbehavior;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.Slide;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.transition.Slide;
import android.support.transition.Transition;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements WestFragment.OnWestFragmentInteractionListener, SouthFragment.OnSouthFragmentInteractionListener, NorthFragment.OnNorthFragmentInteractionListener, EastFragment.OnEastFragmentInteractionListener, CenterFragment.OnCenterFragmentInteractionListener
{
    
    private static final long MOVE_DEFAULT_TIME = 300;
    private static final long FADE_DEFAULT_TIME = 300;
    
    public static final String TAG = "Main";
    
    private FloatingActionButton floatingActionButton;
    
    private CoordinatorLayout rootCoordinator;
    
    private FrameLayout anchorPoint;
    
    private CenterFragment centerFragment;
    
    private SouthFragment southFragment;
    
    private NorthFragment northFragment;
    
    private EastFragment eastFragment;
    
    private WestFragment westFragment;
    
    private Fragment currentFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        this.floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        this.floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        
        this.anchorPoint = (FrameLayout) findViewById(R.id.anchor_point);
        
        this.rootCoordinator = (CoordinatorLayout) findViewById(R.id.rootCoordinator);
    
    
        final GestureDetector swipeGestureDetector = new GestureDetector(new SwipePermissionGestureListener());
        this.rootCoordinator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                return swipeGestureDetector.onTouchEvent(event);
            }
        });
    
    
        this.centerFragment = CenterFragment.newInstance("Hello","Center");
        this.northFragment = NorthFragment.newInstance("Hello","North");
        this.southFragment = SouthFragment.newInstance("Hello","South");
        this.westFragment = WestFragment.newInstance("Hello","West");
        this.eastFragment = EastFragment.newInstance("Hello","East");
        
        
        this.presentCenter();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    public void presentNorth()
    {
        Slide slideExit = new Slide(Gravity.BOTTOM);
        slideExit.setDuration(MOVE_DEFAULT_TIME);
        Slide slideEnter = new Slide(Gravity.TOP);
        slideEnter.setDuration(MOVE_DEFAULT_TIME);
    
        this.currentFragment.setExitTransition(slideExit);
        this.northFragment.setEnterTransition(slideEnter);
        
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.anchor_point,this.northFragment,"NORTH");
        fragmentTransaction.commit();
        this.currentFragment = this.northFragment;
    }
    
    public void presentSouth()
    {
        Slide slideExit = new Slide(Gravity.TOP);
        slideExit.setDuration(MOVE_DEFAULT_TIME);
        Slide slideEnter = new Slide(Gravity.BOTTOM);
        slideEnter.setDuration(MOVE_DEFAULT_TIME);
    
        this.currentFragment.setExitTransition(slideExit);
        this.southFragment.setEnterTransition(slideEnter);
        
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.anchor_point,this.southFragment,"SOUTH");
        fragmentTransaction.commit();
        this.currentFragment = this.southFragment;
    }
    
    public void presentWest()
    {
        Slide slideExit = new Slide(Gravity.RIGHT);
        slideExit.setDuration(MOVE_DEFAULT_TIME);
        Slide slideEnter = new Slide(Gravity.LEFT);
        slideEnter.setDuration(MOVE_DEFAULT_TIME);
    
        this.currentFragment.setExitTransition(slideExit);
        this.westFragment.setEnterTransition(slideEnter);
        
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.anchor_point,this.westFragment,"WEST");
        fragmentTransaction.commit();
        
        this.currentFragment = this.westFragment;
    }
    
    public void presentEast()
    {
        Slide slideExit = new Slide(Gravity.LEFT);
        slideExit.setDuration(MOVE_DEFAULT_TIME);
        Slide slideEnter = new Slide(Gravity.RIGHT);
        slideEnter.setDuration(MOVE_DEFAULT_TIME);
    
        this.currentFragment.setExitTransition(slideExit);
        this.eastFragment.setEnterTransition(slideEnter);
        
        
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.anchor_point,this.eastFragment,"EAST");
        fragmentTransaction.commit();
        
        this.currentFragment = this.eastFragment;
        
    }
    
    public void presentCenter()
    {
        Slide slideEnter = null;
        Slide slideExit = null;
        
        if(this.currentFragment != null)
        {
            if (this.currentFragment.equals(this.eastFragment))
            {
                // East fragement s'en va par la droite.
                slideExit = new Slide(Gravity.RIGHT);
                slideExit.setDuration(MOVE_DEFAULT_TIME);
                slideEnter = new Slide(Gravity.LEFT);
                slideEnter.setDuration(MOVE_DEFAULT_TIME);
            }
            else if (this.currentFragment.equals(this.westFragment))
            {
                slideExit = new Slide(Gravity.LEFT);
                slideExit.setDuration(MOVE_DEFAULT_TIME);
                slideEnter = new Slide(Gravity.RIGHT);
                slideEnter.setDuration(MOVE_DEFAULT_TIME);
            }
            else if (this.currentFragment.equals(this.northFragment))
            {
                slideExit = new Slide(Gravity.TOP);
                slideExit.setDuration(MOVE_DEFAULT_TIME);
                slideEnter = new Slide(Gravity.BOTTOM);
                slideEnter.setDuration(MOVE_DEFAULT_TIME);
            }
            else if (this.currentFragment.equals(this.southFragment))
            {
                slideExit = new Slide(Gravity.BOTTOM);
                slideExit.setDuration(MOVE_DEFAULT_TIME);
                slideEnter = new Slide(Gravity.TOP);
                slideEnter.setDuration(MOVE_DEFAULT_TIME);
            }
    
            this.currentFragment.setExitTransition(slideExit);
            this.centerFragment.setEnterTransition(slideEnter);
        }
        
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.anchor_point,this.centerFragment,"CENTER");
        fragmentTransaction.commit();
        
        this.currentFragment = this.centerFragment;
    }
    
    public void moveToLeft()
    {
        Log.d("###","moveToLeft");
        if(this.currentFragment.equals(this.centerFragment))
        {
            Log.d("###","presentWest");
            this.presentWest();
        }
        else if(this.currentFragment.equals(this.eastFragment))
        {
            Log.d("###","presentCenter");
            this.presentCenter();
        }
        else
        {
            // DO NOTHING.
        }
    }
    
    public void moveToRight()
    {
        Log.d("###","moveToRight");
        if(this.currentFragment.equals(this.centerFragment))
        {
            Log.d("###","presentEast");
            this.presentEast();
        }
        else if(this.currentFragment.equals(this.westFragment))
        {
            Log.d("###","presentCenter");
            this.presentCenter();
        }
        else
        {
            // DO NOTHING.
        }
    }
    
    public void moveToTop()
    {
        Log.d("###","moveToTop");
        if(this.currentFragment.equals(this.centerFragment))
        {
            Log.d("###","presentNorth");
            this.presentNorth();
        }
        else if(this.currentFragment.equals(this.southFragment))
        {
            Log.d("###","presentCenter");
            this.presentCenter();
        }
        else
        {
            // DO NOTHING.
        }
    }
    
    public void moveToBottom()
    {
        Log.d("###","moveToBottom");
        if(this.currentFragment.equals(this.centerFragment))
        {
            Log.d("###","presentSouth");
            this.presentSouth();
        }
        else if(this.currentFragment.equals(this.northFragment))
        {
            Log.d("###","presentCenter");
            this.presentCenter();
        }
        else
        {
            // DO NOTHING.
        }
    }
    
    @Override
    public void onWestFragmentInteraction(Uri uri)
    {
        Log.d("###","onWestFragmentInteraction [" + uri + "]");
    }
    
    @Override
    public void onEastFragmentInteraction(Uri uri)
    {
        Log.d("###","onEastFragmentInteraction [" + uri + "]");
    }
    
    @Override
    public void onNorthFragmentInteraction(Uri uri)
    {
        Log.d("###","onNorthFragmentInteraction [" + uri + "]");
    }
    
    @Override
    public void onSouthFragmentInteraction(Uri uri)
    {
        Log.d("###","onSouthFragmentInteraction [" + uri + "]");
    }
    
    @Override
    public void onCenterFragmentInteraction(Uri uri)
    {
        Log.d("###","onCenterFragmentInteraction [" + uri + "]");
    }
    
    
    class SwipePermissionGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String TAG = "CHAT_SWIPE_GESTURE";
        
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;
        
        @Override
        public boolean onDown(MotionEvent event) {
            
            return true;
        }
        
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean managed = false;
            
            
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // *******************
                // Swipe Right to left
                // We launch Video Chat
                // *******************
                Log.d("###","Swipe RIGHT to LEFT");
                // on veut donc voir la vue de droite ...
                MainActivity.this.moveToRight();
            }
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // *******************
                // Swipe Left to Right
                // We launch Friends
                // *******************
                Log.d("###","Swipe LEFT to RIGHT");
                // on veut donc voir la vue de gauche ...
                MainActivity.this.moveToLeft();
            }
            else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // *******************
                // Swipe Top to Bottom
                // We launch MAP
                // *******************
                Log.d("###","Swipe BOTTOM to TOP");
                // on veut donc voir la vue du bas ...
                MainActivity.this.moveToBottom();
            }
            else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // *******************
                // Swipe Bottom to Top
                // We launch Profile
                // *******************
                Log.d("###","Swipe TOP to BOTTOM");
                // on veut donc voir la vue du haut ...
                MainActivity.this.moveToTop();
            }
            
            return managed;
        }
    }
    
}
