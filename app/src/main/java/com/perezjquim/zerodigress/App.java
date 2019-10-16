package com.perezjquim.zerodigress;

import android.graphics.drawable.Drawable;

public class App
{
    private String _label;
    private Drawable _icon;
    private String _packageName;

    public App(String label, Drawable icon, String packageName)
    {
        _label = label;
        _icon = icon;
        _packageName = packageName;
    }

    public String getLabel()
    {
        return _label;
    }

    public String getPackageName()
    {
        return _packageName;
    }

    public Drawable getIcon()
    {
        return _icon;
    }
}
