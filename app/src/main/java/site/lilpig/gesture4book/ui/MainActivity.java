package site.lilpig.gesture4book.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.service.GestureService;
import site.lilpig.gesture4book.support.DefaultConfig;
import site.lilpig.gesture4book.util.ConfigUtil;
import site.lilpig.gesture4book.util.MyProgressChangeListener;
import site.lilpig.gesture4book.util.PermissionUtil;

public class MainActivity extends AppCompatActivity {
    private SeekBar alphaSeek;
    private SeekBar edgePaddingSeek;
    private SeekBar lengthSeek;
    private SeekBar widthSeek;
    private ImageView permissionImage;
    private Button acceptPermissionBtn;
    private Button leftSettingBtn;
    private Button bottomSettingBtn;
    private Button rightSettingBtn;
    private TextView permissionTip;
    private GestureService.GestureServiceBinder service = null;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = (GestureService.GestureServiceBinder) iBinder;
            service.bindLeftListener(leftSettingBtn);
            service.bindRightListener(rightSettingBtn);
            service.bindBottomListener(bottomSettingBtn);
            widthSeek.setProgress(ConfigUtil.getWidth());
            lengthSeek.setProgress(ConfigUtil.getLength());
            edgePaddingSeek.setProgress(ConfigUtil.getEdgePadding());
            alphaSeek.setProgress(ConfigUtil.getAlpha());
            Log.i("MainActivity","Service binded!");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConfigUtil.setAlpha(alphaSeek.getProgress());
        ConfigUtil.setEdgePadding(edgePaddingSeek.getProgress());
        ConfigUtil.setWidth(widthSeek.getProgress());
        ConfigUtil.setLength(lengthSeek.getProgress());
        Log.i("MainActivity","Destory...");
        unbindService(connection);
        service = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PermissionUtil.isAccessibilitySettingsOn(this)){
            permissionImage.setImageResource(R.drawable.ic_cry);
            permissionTip.setText("没有无障碍权限，我没法模拟操作啊 QAQ~~");
            acceptPermissionBtn.setVisibility(View.VISIBLE);
            acceptPermissionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PermissionUtil.openAccessbilitySettingUI(MainActivity.this);
                }
            });
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)){
            permissionImage.setImageResource(R.drawable.ic_cry);
            permissionTip.setText("没有悬浮窗权限，我接收不到手势 QAQ~~");
            acceptPermissionBtn.setVisibility(View.VISIBLE);
            acceptPermissionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PermissionUtil.openShowOnOtherAppsUI(MainActivity.this);
                }
            });
        }else {
            acceptPermissionBtn.setVisibility(View.GONE);
            permissionImage.setImageResource(R.drawable.ic_smile);
            permissionTip.setText("我开始工作喽");

            if (service==null){
                Intent intent = new Intent(MainActivity.this, GestureService.class);
                bindService(intent,connection,BIND_AUTO_CREATE);
            }
        }
    }


    private void bindView() {
        alphaSeek = findViewById(R.id.am_alpha_seek);
        alphaSeek.setOnSeekBarChangeListener(new MyProgressChangeListener() {
            @Override
            public void onProgressChanged(int i, boolean b) {
                int alpha = i * 20;
                service.setBarAlpha(alpha);
            }
        });

        edgePaddingSeek = findViewById(R.id.am_edge_padding_seek);
        edgePaddingSeek.setOnSeekBarChangeListener(new MyProgressChangeListener() {
            @Override
            public void onProgressChanged(int i, boolean b) {
                int padding = i * 2;
                service.setEdgePadding(padding);
            }
        });

        lengthSeek = findViewById(R.id.am_length_seek);
        lengthSeek.setOnSeekBarChangeListener(new MyProgressChangeListener() {
            @Override
            public void onProgressChanged(int i, boolean b) {
                int sidePadding = (12-i) * 20;
                service.setBarSidePadding(sidePadding);
            }
        });

        widthSeek = findViewById(R.id.am_width_seek);
        widthSeek.setOnSeekBarChangeListener(new MyProgressChangeListener() {
            @Override
            public void onProgressChanged(int i, boolean b) {
                int width = i * 6;
                service.setBarWidth(width);
            }
        });

        permissionImage = findViewById(R.id.am_permission_img);
        permissionTip = findViewById(R.id.am_permission_tip);
        acceptPermissionBtn = findViewById(R.id.am_accept_permission);

        leftSettingBtn = findViewById(R.id.am_left_btn);
        rightSettingBtn = findViewById(R.id.am_right_btn);
        bottomSettingBtn = findViewById(R.id.am_bottom_btn);

    }
}
