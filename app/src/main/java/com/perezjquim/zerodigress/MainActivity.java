package com.perezjquim.zerodigress;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.*;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
{
    private AppBlocker _blocker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        this._listApps();
        _blocker = new AppBlocker(this);
        _blocker.start();
    }

    public void onToggleAppBlock(View v)
    {
        LinearLayout app = (LinearLayout) v;

        String packageName = ((TextView)(((LinearLayout)(app.getChildAt(1))).getChildAt(1))).getText().toString();

        if(!_blocker.has(packageName))
        {
            app.setBackgroundColor(Color.RED);
            _blocker.add(packageName);
        }
        else
        {
            app.setBackgroundColor(Color.WHITE);
            _blocker.remove(packageName);
        }
    }

    private void _listApps()
    {
        ArrayList<ApplicationInfo> apps_list = this._getApps();
        AppArrayAdapter a = new AppArrayAdapter(this,apps_list);
        ListView list = findViewById(R.id.apps_list);
        list.setAdapter(a);
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
}
