package site.lilpig.gesture4book.handler;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import java.util.List;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.util.DisplayUtil;

public class VolumGestureHandler extends BaseGestureHandler {
    private AudioManager audioManager;

    public VolumGestureHandler(String tb, String dr) {
        super(tb, dr);
    }


    private void changeVolum(GestureMetaData metaData){
        if (audioManager == null) {
            audioManager = (AudioManager) metaData.context.getSystemService(Context.AUDIO_SERVICE);
        }
        if (metaData.direction == GestureDirection.DIRE_LEFT || metaData.direction == GestureDirection.DIRE_TOP){
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
        }else {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
        }
    }

    @Override
    public void onHover(final GestureMetaData metaData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isActive()==GestureType.TYPE_HOVER){
                    changeVolum(metaData);
                    try {
                        Thread.sleep((Integer) getSetting("triggerInterval"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onTrigger(GestureMetaData metaData) {
        changeVolum(metaData);
        onExit();
    }

    @Override
    public void onOver() {
        audioManager = null;
    }

    @Override
    public List<GestureHandlerSetting> settings() {
        return createSettings().add(
                "triggerInterval",
                "触发间隔",
                Integer.class,
                "在悬停状态下每隔多少 ·毫秒· 触发一次",
                500
        ).get();
    }

    @Override
    public String name() {
        return "音量加减";
    }

    @Override
    public int icon() {
        return R.drawable.ic_volume_up_black_24dp;
    }

}
