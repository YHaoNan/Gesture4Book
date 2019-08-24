package site.lilpig.gesture4book.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import site.lilpig.gesture4book.adapter.GestureHandlerSelectListAdapter;
import site.lilpig.gesture4book.handler.GestureHandler;
import site.lilpig.gesture4book.service.GestureService;

public class GestureHandlerSelectDialog extends BaseDialog {
    private final Context context;
    private ListView handlerList;
    private GestureHandlerSelectListAdapter adapter;

    public GestureHandlerSelectDialog(String tb,Context context, GestureHandler[] handlers) {
        super(context);
        this.context = context;
        this.adapter = new GestureHandlerSelectListAdapter(tb,this,context,handlers);
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
