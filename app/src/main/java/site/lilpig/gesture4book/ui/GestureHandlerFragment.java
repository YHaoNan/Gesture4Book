package site.lilpig.gesture4book.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.handler.GestureHandler;
import site.lilpig.gesture4book.support.OnGestureHandlerSelectedListener;

public class GestureHandlerFragment extends Fragment {
    private final List<Class<GestureHandler>> handlers;
    private final OnGestureHandlerSelectedListener listener;
    private ListView handlerList;

    public GestureHandlerFragment(OnGestureHandlerSelectedListener listener,List<Class<GestureHandler>> handlers){
        this.listener = listener;
        this.handlers = handlers;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_icon_and_text,null);
        handlerList = root.findViewById(R.id.fiat_list);
        List<Map<String,Object>> datas = new ArrayList<>();
        for (Class<GestureHandler> handler:handlers){
            try {
                String name = (String) handler.getField("name").get(handler);
                int icon = handler.getField("icon").getInt(handler);
                Map<String,Object> data = new HashMap<>();
                data.put("icon",icon);
                data.put("text",name);
                datas.add(data);
            } catch (NoSuchFieldException e) {
                Log.e("GestureHandlerSelectActivity","Init handler faild, please make sure your handler class have name and icon properties(static and public )");
                continue;
            } catch (IllegalAccessException e) {
                Log.e("GestureHandlerSelectActivity","Init handler faild, please make sure your handler class have name and icon properties(static and public)");
            }
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(),datas,R.layout.item_icon_and_text,
                new String[]{"icon","text"},new int[]{R.id.iiat_icon,R.id.iiat_text});
        handlerList.setAdapter(adapter);
        handlerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onSelect(handlers.get(i),"");
            }
        });
        return root;
    }
}
