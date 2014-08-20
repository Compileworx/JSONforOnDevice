package com.example.jsonondevice;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

public class MainActivity extends Activity{

    public static final String tag="MainTestActivity";
    GSONTester ft = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(ft == null){
            ft = new GSONTester(this);
        }

        TextView tv = (TextView)this.findViewById(R.id.JSONReturn);

        int id = item.getItemId();
        if (id == R.id.testJson) {
            tv.setText(ft.testJSON());
        }

        if(id == R.id.testEscapeChars){
            tv.setText(ft.testEscapeCharactersInPreferences());
        }

        if(id == R.id.storeJson){
            tv.setText(ft.storeJSON());
        }

        if(id == R.id.retrieveJson){
           tv.setText(ft.retrieveJSON());
        }

        if(id == R.id.saveJsonStorage){
            tv.setText(ft.saveJsonToPrivateStorage());
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
