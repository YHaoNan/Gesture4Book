package site.lilpig.gesture4book.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import site.lilpig.gesture4book.Gesture4BookApplication;
import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.handler.GestureHandler;
import site.lilpig.gesture4book.service.GestureService;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.ui.GestureHandlerSelectActivity;
import site.lilpig.gesture4book.ui.GestureHandlerSettingActivity;

public class GestureHandlerSelectListAdapter extends BaseAdapter {
    private static String[] titles = {"左滑","上滑","右滑","下滑","左滑悬停","上滑悬停","右滑悬停","下滑悬停"};
    private final String tb;
    private GestureHandler[] handlers;
    private Context context;
    private Dialog dialog;
    public GestureHandlerSelectListAdapter(String tb,Dialog dialog, Context context, GestureHandler[] handlers){
        this.tb = tb;
        this.context = context;
        this.handlers = handlers;
        this.dialog = dialog;
    }
    @Override
    public int getCount() {
        return handlers.length;
    }

    @Override
    public Object getItem(int i) {
        return handlers.length;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = View.inflate(context, R.layout.item_handler_select,null);
            holder = new ViewHolder();
            holder.direcitionAndType = view.findViewById(R.id.ihl_direction_and_type);
            holder.handlerIcon = view.findViewById(R.id.ihl_handler_icon);
            holder.handlerName = view.findViewById(R.id.ihl_handler_name);
            holder.handlerSetting = view.findViewById(R.id.ihl_handler_setting);
            holder.handlerChange = view.findViewById(R.id.ihl_handler_change);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        final GestureHandler handler = handlers[i];

        if (handler == null){
            view.setVisibility(View.GONE);
        }else {
            view.setVisibility(View.VISIBLE);
            List<GestureHandlerSetting> handlerSettings = handler.settings();
            if (handlerSettings == null)
                holder.handlerSetting.setVisibility(View.GONE);
            else{

                holder.handlerSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, GestureHandlerSettingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Gesture4BookApplication.getInstance().setCurrentSetting(handler.settings());
                        context.startActivity(intent);
                    }
                });
                holder.handlerSetting.setVisibility(View.VISIBLE);
            }
            holder.handlerChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Intent intent = new Intent(context, GestureHandlerSelectActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("dr",i);
                    intent.putExtra("tb",tb);
                    context.startActivity(intent);
                }
            });
            holder.direcitionAndType.setText(titles[i]);
            holder.handlerName.setText(handler.name());
            holder.handlerIcon.setImageResource(handler.icon());
        }
        return view;
    }
    class ViewHolder{
        public TextView direcitionAndType;
        public TextView handlerName;
        public ImageView handlerIcon;
        public Button handlerSetting;
        public Button handlerChange;

        public ViewHolder(){

        }
    }
}
