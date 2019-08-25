package site.lilpig.gesture4book.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import site.lilpig.gesture4book.Gesture4BookApplication;
import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.adapter.ViewPagerAdapter;
import site.lilpig.gesture4book.handler.BuiltInHandlers;
import site.lilpig.gesture4book.handler.GestureHandler;
import site.lilpig.gesture4book.handler.OpenAppGestureHandler;
import site.lilpig.gesture4book.service.GestureService;
import site.lilpig.gesture4book.support.OnGestureHandlerSelectedListener;

public class GestureHandlerSelectActivity extends AppCompatActivity {
    private ViewPager pager;
    private TabLayout tabLayout;
    private List<Fragment> fragments;

    private String[] tabTitle = new String[]{"内建处理器","外部App"};

    private int dr;
    private String tb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_handler_select);
        dr = getIntent().getIntExtra("dr",-1);
        tb = getIntent().getStringExtra("tb");
        if (dr == -1 || tb == null){
            Toast.makeText(this, "方向信息不正确", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        bindView();
    }

    private void bindView() {
        tabLayout = findViewById(R.id.aghs_tab);
        pager = findViewById(R.id.aghs_pager);
        fragments = new ArrayList<>();
        fragments.add(new GestureHandlerFragment(new OnGestureHandlerSelectedListener() {
            @Override
            public void onSelect(Class<GestureHandler> handlerClass, String...arg) {
                boolean bol = Gesture4BookApplication.getInstance().getService().setHandler(dr,tb,handlerClass);
                if (bol)
                    Toast.makeText(GestureHandlerSelectActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        },getBuiltInGestureHandlers()));
        fragments.add(new AppListFragment(new OnGestureHandlerSelectedListener() {
            @Override
            public void onSelect(Class<GestureHandler> handlerClass, String...arg) {
                OpenAppGestureHandler handler = new OpenAppGestureHandler(tb, GestureService.DRS[dr]);
                handler.settings().get(0).setValue(arg[1]);
                boolean bol = Gesture4BookApplication.getInstance().getService().setHandler(dr,tb,handler);
                if (bol)
                    Toast.makeText(GestureHandlerSelectActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                fragments,Arrays.asList(tabTitle));
        pager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(pager);

    }

    @SuppressLint("LongLogTag")
    private List<Class<GestureHandler>> getBuiltInGestureHandlers() {
        return Arrays.asList(BuiltInHandlers.handlers);
    }
}
