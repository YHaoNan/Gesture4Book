package site.lilpig.gesture4book.util;

import android.widget.SeekBar;

public abstract class MyProgressChangeListener implements SeekBar.OnSeekBarChangeListener {
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        onProgressChanged(i,b);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public abstract void onProgressChanged(int i,boolean b);
}
