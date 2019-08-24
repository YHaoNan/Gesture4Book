package site.lilpig.gesture4book.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import site.lilpig.gesture4book.R;

public class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        super(context, R.style.baseDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.base_dialog_background);
    }
}
