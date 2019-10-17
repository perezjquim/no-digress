package com.perezjquim.zerodigress;

import java.util.*;
import android.app.*;
import android.app.ActivityManager.*;
import android.content.*;

public class AppBlocker extends Thread
{
    private static final int SAMPLING_RATE_MS = 1000;
    private static final int BLOCKING_TIME_DEFAULT_MS = 10 * 1000;
    private HashMap<String,Integer> _blockList;
    private Context _context;

    public AppBlocker(Context context)
    {
        _context = context;
        _blockList = new HashMap<>();
    }

    @Override
    public void run()
    {
        while(true)
        {
            this._handleAppBlock();

            try
            {
                Thread.sleep(SAMPLING_RATE_MS);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean has(String packageName)
    {
        return _blockList.containsKey(packageName);
    }

    public void add(String packageName)
    {
        _blockList.put(packageName, BLOCKING_TIME_DEFAULT_MS);
    }

    public void remove(String packageName)
    {
        _blockList.remove(packageName);
    }

    private void _handleAppBlock()
    {
        Set<Map.Entry<String, Integer>> apps = _blockList.entrySet();

        for(Map.Entry<String,Integer> a : apps)
        {
            String packageName = a.getKey();
            int timeLeft = a.getValue();

            if(timeLeft > 0)
            {
                _blockList.replace(packageName, timeLeft - SAMPLING_RATE_MS);
            }

//            System.out.println(packageName + ":" + timeLeft);
        }

        this._closeBlockedApp();
    }

    private void _closeBlockedApp()
    {
        ActivityManager am = (ActivityManager) _context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> processes = am.getRunningAppProcesses();

        for (RunningAppProcessInfo p : processes )
        {
            String packageName = p.processName;
            System.out.println(packageName);

            if(p.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND && _blockList.containsKey(packageName))
            {
                int timeLeft = _blockList.get(packageName);
                if(timeLeft < 0)
                {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    _context.startActivity(startMain);
                }
            }
        }
    }
}
