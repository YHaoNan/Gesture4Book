package site.lilpig.gesture4book.handler;

import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.Set;

public class GestureHandlerSetting implements Serializable {
    private final SharedPreferences sharedPreferences;
    private final String name;
    private final String label;
    private Class type;
    private final String description;
    private Object defaultValue;
    private Object value;


    public GestureHandlerSetting(SharedPreferences sharedPreferences,String name,String label, Class type,String description, Object defaultValue) {
        this.sharedPreferences = sharedPreferences;
        this.name = name;
        this.label = label;
        setType(type);
        setDefaultValue(defaultValue);
        this.description =description;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    private void setType(Class type){
        String clzName = type.getName();
        if (clzName.equals("java.lang.Integer") ||
            clzName.equals("java.lang.Float") ||
            clzName.equals("java.lang.Boolean") ||
            clzName.equals("java.lang.String") ||
            clzName.equals("java.lang.Long") ||
            clzName.equals("java.lang.Set")){
            this.type = type;
        } else
            throw new TypeWrongExpception("Setting must be an Integer , Float , Boolean , String ,Long or Set<String> , given "+type.getSimpleName());
    }

    public Object getValue() {
        if (value != null)return value;

        String clzName = type.getName();
        if (clzName.equals("java.lang.Integer")){
            return sharedPreferences.getInt(name, (Integer) defaultValue);
        }else if (clzName.equals("java.lang.Float"))
            return sharedPreferences.getFloat(name, (Float) defaultValue);
        else if (clzName.equals("java.lang.Boolean"))
            return sharedPreferences.getBoolean(name, (Boolean) defaultValue);
        else if (clzName.equals("java.lang.String"))
            return sharedPreferences.getString(name, (String) defaultValue);
        else if (clzName.equals("java.lang.Long"))
            return sharedPreferences.getLong(name, (Long) defaultValue);
        else if (clzName.equals("java.lang.Set"))
            return sharedPreferences.getStringSet(name, (Set<String>) defaultValue);
        else
            return null;
    }

    public void setValue(Object value) {
        if (!type.isInstance(value))
            throw new TypeWrongExpception("Setting "+name+" is "+type.getSimpleName()+" ,but receive a "+ value.getClass().getSimpleName());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof Integer)
            editor.putInt(name, (Integer) value);
        else if (value instanceof Float)
            editor.putFloat(name, (Float) value);
        else if (value instanceof Boolean)
            editor.putBoolean(name, (Boolean) value);
        else if (value instanceof String)
            editor.putString(name, (String) value);
        else if (value instanceof Long)
            editor.putLong(name, (Long) value);
        else if (value instanceof Set)
            editor.putStringSet(name, (Set<String>) value);
        else
            throw new TypeWrongExpception("Setting must be an Integer , Float , Boolean , String ,Long or Set<String> , given "+value.getClass().getSimpleName());
        this.value = value;
        editor.commit();
    }

    private void setDefaultValue(Object v){
        if (type.isInstance(v)){
            this.defaultValue = v;
        }else
            throw new TypeWrongExpception("Setting "+name+" is "+type.getSimpleName()+" ,but receive a "+ v.getClass().getSimpleName());
    }

    public String getDescription() {
        return description;
    }


    public Class getType() {
        return type;
    }
}
