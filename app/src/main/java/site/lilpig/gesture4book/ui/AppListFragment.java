package site.lilpig.gesture4book.ui;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.handler.GestureHandler;
import site.lilpig.gesture4book.handler.OpenAppGestureHandler;
import site.lilpig.gesture4book.support.OnGestureHandlerSelectedListener;

public class AppListFragment extends Fragment {
    private static final int SUCCESS = 1, FAILD = 2;
    private final OnGestureHandlerSelectedListener listener;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Object[] arr = (Object[]) msg.obj;
            AppListFragment.Callback callback = (AppListFragment.Callback) arr[0];
            if (msg.what == SUCCESS){
                callback.done(null, (List<Map<String, Object>>) arr[1]);
            }else
                callback.done((Throwable) arr[1],null);
        }
    };

    public
    interface Callback{
        void done(Throwable err, List<Map<String,Object>> datas);
    }

    public AppListFragment(OnGestureHandlerSelectedListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_icon_and_text,null);
        final ListView appList = root.findViewById(R.id.fiat_list);
        final BaseDialog dialog = new BaseDialog(getContext());
        dialog.setTitle("正在加载应用列表");
        dialog.show();
        dialog.setCancelable(false);
        getAppList(new Callback() {
            @Override
            public void done(Throwable err, final List<Map<String, Object>> datas) {
                if (err!=null){
                    Toast.makeText(getContext(), err.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleAdapter adapter = new SimpleAdapter(getContext(),datas,R.layout.item_icon_and_text,
                        new String[]{
                                "name","icon"
                        },new int[]{R.id.iiat_text,R.id.iiat_icon});
                adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object o, String s) {
                        if (view instanceof ImageView && o instanceof Drawable){
                            ((ImageView) view).setImageDrawable((Drawable) o);
                            return true;
                        }
                        return false;
                    }
                });
                appList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String packageName = (String) datas.get(i).get("packageName");
                        listener.onSelect(null, (String) datas.get(i).get("name"),packageName);
                    }
                });
                appList.setAdapter(adapter);
                dialog.dismiss();
            }
        });
        return root;
    }


    private void getAppList(final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<Map<String,Object>> result = new ArrayList<>();
                    PackageManager pm = getContext().getPackageManager();
                    List<PackageInfo> packages = pm.getInstalledPackages(0);
                    for (PackageInfo packageInfo : packages) {
                        //排除系统应用
                        if ((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)!=0)continue;
                        Map<String,Object> data = new HashMap<>();
                        data.put("name",packageInfo.applicationInfo.loadLabel(getContext().getPackageManager()));
                        data.put("packageName",packageInfo.packageName);
                        data.put("icon",packageInfo.applicationInfo.loadIcon(getContext().getPackageManager()).getCurrent());
                        result.add(data);
                    }
                    Message message = handler.obtainMessage();
                    message.obj = new Object[]{callback,result};
                    message.what = SUCCESS;
                    handler.sendMessage(message);
                }catch (Throwable e){
                    Message message = handler.obtainMessage();
                    message.obj = new Object[]{callback,e};
                    message.what = FAILD;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
}
