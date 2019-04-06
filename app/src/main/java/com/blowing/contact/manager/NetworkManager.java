package com.blowing.contact.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.blowing.contact.activity.MainActivity;
import com.blowing.contact.model.AppInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wujie
 * on 2019/4/4/004.
 * 网络数据
 */
public class NetworkManager {

    private Context context;
    public static ArrayList<AppInfo> list = new ArrayList<>();
    public NetworkManager(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public ArrayList<AppInfo> getNetwork() throws RemoteException {
        if(list.size() > 0) {
            return list;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        NetworkStatsManager networkStatsManager = (NetworkStatsManager) context.getSystemService(Activity.NETWORK_STATS_SERVICE);

        // 获取subscriberId
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String subId = tm.getSubscriberId();

        NetworkStats summaryStats;
        long summaryRx = 0;
        long summaryTx = 0;
        NetworkStats.Bucket summaryBucket = new NetworkStats.Bucket();
        int summaryTotal = 0;

        summaryStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_MOBILE, subId, getTimesMonthMorning(), System.currentTimeMillis());
        do {
            summaryStats.getNextBucket(summaryBucket);
            int summaryUid = summaryBucket.getUid();
//            if (uid == summaryUid) {
//                summaryRx += summaryBucket.getRxBytes();
//                summaryTx += summaryBucket.getTxBytes();
//            }
            Log.i(MainActivity.class.getSimpleName(), "uid:" + summaryBucket.getUid() + " rx:" + summaryBucket.getRxBytes() +
                    " tx:" + summaryBucket.getTxBytes());
            summaryTotal += summaryBucket.getRxBytes() + summaryBucket.getTxBytes();
            if (map.containsKey(summaryUid)) {
                summaryTotal = summaryTotal + map.get(summaryUid);
            }
            map.put(summaryUid, summaryTotal);
            summaryTotal = 0;
        } while (summaryStats.hasNextBucket());

        PackageManager pm = context.getPackageManager();
        //获取系统中安装到所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : installedPackages) {
            //获取包名
            String packageName = packageInfo.packageName;
            //获取版本号
            String versionName = packageInfo.versionName;
            //获取application
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;

            int uid = applicationInfo.uid;
            //获取应用程序的图标
            Drawable icon = applicationInfo.loadIcon(pm);
            //获取应用程序的名称
            String name = applicationInfo.loadLabel(pm).toString();
            //是否是用户程序
            //获取应用程序中相关信息,是否是系统程序和是否安装到SD卡


            //添加到bean中
            AppInfo appInfo = new AppInfo();
            appInfo.icon = icon;
            appInfo.name = name;
            appInfo.uid = (int)uid;


            try {
                if (uid >= 10000) { //系统级应用就不管了

                    appInfo.data = map.get(uid);
                    list.add(appInfo);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            //将bean存放到list集合

        }
        return list;

    }


    public  long getTimesMonthMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis();
    }


//    private boolean hasPermissionToReadNetworkStats() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
//        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
//                android.os.Process.myUid(), getPackageName());
//        if (mode == AppOpsManager.MODE_ALLOWED) {
//            return true;
//        }
//
//        requestReadNetworkStats();
//        return false;
//    }
//    // 打开“有权查看使用情况的应用”页面
//    private void requestReadNetworkStats() {
//        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
//        startActivity(intent);
//    }



    public  List<AppInfo> getAppInfos(){
//        List<AppInfo> list = new ArrayList<AppInfo>();
        //获取应用程序信息
        //包的管理者
        PackageManager pm = context.getPackageManager();
        //获取系统中安装到所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : installedPackages) {
            //获取包名
            String packageName = packageInfo.packageName;
            //获取版本号
            String versionName = packageInfo.versionName;
            //获取application
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            int uid = applicationInfo.uid;
            //获取应用程序的图标
            Drawable icon = applicationInfo.loadIcon(pm);
            //获取应用程序的名称
            String name = applicationInfo.loadLabel(pm).toString();
            //是否是用户程序
            //获取应用程序中相关信息,是否是系统程序和是否安装到SD卡


            //添加到bean中
            AppInfo appInfo = new AppInfo();
            appInfo.icon = icon;
            appInfo.name = name;
            appInfo.uid = uid;
            //将bean存放到list集合
            list.add(appInfo);
        }
        return list;
    }





}
