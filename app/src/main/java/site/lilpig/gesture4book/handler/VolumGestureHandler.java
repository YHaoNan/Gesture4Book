package site.lilpig.gesture4book.handler;

import android.content.Context;
import android.media.AudioManager;

import site.lilpig.gesture4book.util.DisplayUtil;

public class VolumGestureHandler implements GestureHandler{
    private GestureMetaData metaData;
    private AudioManager audioManager;
    private float lastDist = 0;
    @Override
    public void onActive(GestureMetaData metaData) {
        this.metaData = metaData;
        if (audioManager == null) {
            audioManager = (AudioManager) metaData.context.getSystemService(Context.AUDIO_SERVICE);
        }

        if (Math.abs(lastDist - metaData.distance)> DisplayUtil.px2dip(metaData.context,20) || metaData.gestureType == GestureType.TYPE_NORMAL){
            if (metaData.direction == GestureDirection.DIRE_TOP || metaData.direction == GestureDirection.DIRE_LEFT){
                audioManager.adjustStreamVolume (AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            }else {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            }
            lastDist = metaData.distance;
            if (metaData.gestureType==GestureType.TYPE_NORMAL)
                onExit();
        }
    }

    @Override
    public void onExit() {
        metaData = null;
        audioManager = null;
        lastDist = 0;
    }



    @Override
    public int isActive() {
        return metaData!=null ? metaData.gestureType : -1;
    }
}
