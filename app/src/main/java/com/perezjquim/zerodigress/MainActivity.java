package com.perezjquim.zerodigress;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.*;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        this._listApps();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private ArrayList<ApplicationInfo> _getApps()
    {
        PackageManager pm = this.getPackageManager();
        ArrayList<ApplicationInfo> apps = (ArrayList<ApplicationInfo>) pm.getInstalledApplications(PackageManager.GET_META_DATA);
        Collections.sort(apps, (ApplicationInfo a1, ApplicationInfo a2) ->
        {
            String sa1 = pm.getApplicationLabel(a1).toString().toUpperCase();
            String sa2 = pm.getApplicationLabel(a2).toString().toUpperCase();
            return sa1.compareTo(sa2);
        });
        return apps;
    }

    private void _listApps()
    {
        ArrayList<ApplicationInfo> apps_list = this._getApps();
        AppArrayAdapter a = new AppArrayAdapter(this,apps_list);
        ListView list = findViewById(R.id.apps_list);
        list.setAdapter(a);
    }
}
