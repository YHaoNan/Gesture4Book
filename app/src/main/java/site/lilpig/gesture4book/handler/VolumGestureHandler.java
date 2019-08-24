package site.lilpig.gesture4book.handler;

import android.content.Context;
import android.media.AudioManager;

import java.util.List;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.support.GestureDirection;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;
import site.lilpig.gesture4book.support.GestureType;

public class VolumGestureHandler extends BaseGestureHandler {
    private AudioManager audioManager;

    public VolumGestureHandler(String tb, String dr) {
        super(tb, dr);
    }


    private void changeVolum(GestureMetaData metaData){
        if (audioManager == null) {
            audioManager = (AudioManager) metaData.context.getSystemService(Context.AUDIO_SERVICE);
        }

        int uiFlag = ((Boolean)getSetting("showUi")) ? AudioManager.FLAG_SHOW_UI : 0;

        if (metaData.direction == GestureDirection.DIRE_LEFT || metaData.direction == GestureDirection.DIRE_TOP){
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,uiFlag);
        }else {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,uiFlag);
        }
    }

    @Override
    public void onHover(final GestureMetaData metaData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isActive()== GestureType.TYPE_HOVER){
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
        ).add(
                "showUi",
                "显示UI",
                Boolean.class,
                "触发时是否显示系统修改音量的栏",
                true
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
