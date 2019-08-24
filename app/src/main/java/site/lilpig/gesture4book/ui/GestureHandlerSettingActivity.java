package site.lilpig.gesture4book.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import site.lilpig.gesture4book.Gesture4BookApplication;
import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.adapter.GestureHandlerSettingAdapter;
import site.lilpig.gesture4book.support.GestureHandlerSetting;

public class GestureHandlerSettingActivity extends AppCompatActivity {
    private List<GestureHandlerSetting> settings;
    private GestureHandlerSettingAdapter adapter;
    private ListView settingListView;
    private Button saveModify;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_handler);
        settings = Gesture4BookApplication.getInstance().getCurrentSetting();
        if (settings == null){
            Toast.makeText(this, "读取设置项失败", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        adapter = new GestureHandlerSettingAdapter(this,settings);
        settingListView = findViewById(R.id.agh_settings);
        settingListView.setAdapter(adapter);
        saveModify = findViewById(R.id.agh_save_modify);

        saveModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bol = adapter.saveModify();
                if (bol)
                    finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Gesture4BookApplication.getInstance().setCurrentSetting(null);
    }
}
