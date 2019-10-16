package com.perezjquim.zerodigress;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AppArrayAdapter extends ArrayAdapter<ApplicationInfo>
{
    private Context _context;
    private PackageManager _pm;
    private ArrayList<App> _apps;

    public AppArrayAdapter(Context context, ArrayList<ApplicationInfo> apps)
    {
        super(context, 0, apps);
        _context = context;
        _pm = _context.getPackageManager();
        _apps = this._parseApps(apps);
    }

    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent)
    {
        if(view == null)
        {
            view = LayoutInflater.from(_context).inflate(R.layout.template_app,parent,false);
        }

        App a = _apps.get(position);

        ImageView icon = (ImageView)view.findViewById(R.id.a_icon);
        icon.setImageDrawable(a.getIcon());

        TextView label = (TextView) view.findViewById(R.id.a_label);
        label.setText(a.getLabel());

        TextView packageName = (TextView) view.findViewById(R.id.a_package);
        packageName.setText(a.getPackageName());

        return view;
    }

    private ArrayList<App> _parseApps(ArrayList<ApplicationInfo> apps_info)
    {
        ArrayList<App> apps = new ArrayList<>();
        for(ApplicationInfo a : apps_info)
        {
            String label = this._getAppLabel(a);
            Drawable icon = this._getAppIcon(a);
            String packageName = a.packageName;
            apps.add(new App(label, icon, packageName));
        }
        return apps;
    }

    private String _getAppLabel(ApplicationInfo a)
    {
        return _pm.getApplicationLabel(a).toString();
    }

    private Drawable _getAppIcon(ApplicationInfo a)
    {
        return _pm.getApplicationIcon(a);
    }
}
