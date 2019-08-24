package site.lilpig.gesture4book.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.adapter.TouchBarHandlerSelectAdapter;
import site.lilpig.gesture4book.handler.GestureHandler;

public class TouchBarHandlerSettingDialog extends BaseDialog {
    private ListView handlerList;
    private TouchBarHandlerSelectAdapter adapter;

    public TouchBarHandlerSettingDialog(Context context, GestureHandler[] handlers) {
        super(context);
        this.adapter = new TouchBarHandlerSelectAdapter(this,context,handlers);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        handlerList = new ListView(getContext());
        handlerList.setPadding(20,20,20,20);
        LinearLayout.LayoutParams paramsOfList = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addContentView(handlerList,paramsOfList);
        handlerList.setAdapter(adapter);
    }

}
