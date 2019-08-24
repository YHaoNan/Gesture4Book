package site.lilpig.gesture4book.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.support.GestureHandlerSetting;

public class GestureHandlerSettingAdapter extends BaseAdapter {
    private List<GestureHandlerSetting> settings;
    private Context context;

    private String[] values;
    public GestureHandlerSettingAdapter(Context context,List<GestureHandlerSetting> settings){
        this.context = context;
        this.settings = settings;
        values = new String[settings.size()];
        for (int i=0;i<settings.size();i++){
            values[i] = String.valueOf(settings.get(i).getValue());
        }
    }
    @Override
    public int getCount() {
        return settings.size();
    }

    @Override
    public Object getItem(int i) {
        return settings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = View.inflate(context,R.layout.item_handler_setting,null);
            holder = new ViewHolder();
            holder.label = view.findViewById(R.id.ihs_label);
            holder.description = view.findViewById(R.id.ihs_description);
            holder.edit = view.findViewById(R.id.ihs_edit);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        final GestureHandlerSetting setting = settings.get(i);
        holder.label.setText(setting.getLabel());
        holder.description.setText(setting.getDescription());
        holder.edit.setText(values[i]);
        final ViewHolder finalHolder = holder;
        holder.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                values[i] = finalHolder.edit.getText().toString();
            }
        });
        holder.edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                EditText editText = (EditText) view;
                if (b){
                    editText.selectAll();
                }else {
                }
            }
        });
        return view;
    }

    public boolean saveModify() {
        int i;
        for (i = 0; i < settings.size(); i++) {
            GestureHandlerSetting setting = settings.get(i);
            Class clz = setting.getType();
            try {
                if (clz == Integer.class) {
                    setting.setValue(Integer.valueOf(values[i]));
                } else if (clz == Float.class) {
                    setting.setValue(Float.valueOf(values[i]));
                } else if (clz == String.class) {
                    setting.setValue(values[i]);
                } else if (clz == Boolean.class) {
                    setting.setValue(Boolean.valueOf(values[i]));
                } else if (clz == Long.class) {
                    setting.setValue(Long.parseLong(values[i]));
                } else if (clz == Set.class) {
                    Set<String> set = new HashSet<String>(Arrays.asList(values[i].split(",")));
                    setting.setValue(set);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(context, setting.getLabel() + "必须是" + clz.getSimpleName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        Toast.makeText(context, "saved!", Toast.LENGTH_SHORT).show();
        return true;
    }

    class ViewHolder{
        public TextView label;
        public TextView description;
        public EditText edit;
    }
}
