package site.lilpig.gesture4book.util;

import android.content.SharedPreferences;

import site.lilpig.gesture4book.Gesture4BookApplication;
import site.lilpig.gesture4book.support.DefaultConfig;

public class ConfigUtil {

    private static final String ALPHA = "alpha";
    private static final String WIDTH = "width";
    private static final String LENGTH = "length";
    private static final String EDGE_PADDING = "edgePadding";

    private static SharedPreferences preferences = Gesture4BookApplication.getInstance().getAppConfig();

    public static void setString(String name,String value){
        preferences.edit().putString(name,value).commit();
    }
    public static void setInteger(String name,Integer value){
        preferences.edit().putInt(name,value).commit();
    }

    public static String getString(String name,String defaultValue){
        return preferences.getString(name,defaultValue);
    }

    public static Integer getInteger(String name,Integer defaultValue){
        return preferences.getInt(name,defaultValue);
    }

    public static void setAlpha(Integer value){
        setInteger(ALPHA,value);
    }
    public static void setWidth(Integer value){
        setInteger(WIDTH,value);
    }
    public static void setLength(Integer value){
        setInteger(LENGTH,value);
    }
    public static void setEdgePadding(Integer value){
        setInteger(EDGE_PADDING,value);
    }

    public static Integer getAlpha(){
        return getInteger(ALPHA, DefaultConfig.ALPHA);
    }
    public static Integer getWidth(){
        return getInteger(WIDTH,DefaultConfig.WIDTH);
    }
    public static Integer getLength(){
        return getInteger(LENGTH,DefaultConfig.LENGTH);
    }
    public static Integer getEdgePadding(){
        return getInteger(EDGE_PADDING,DefaultConfig.EDGE_PADDING);
    }





    public static void setLeftTop(String handlerName){
        setString("leftTop",handlerName);
    }
    public static String getLeftTop(){
        return getString("leftTop",DefaultConfig.LEFT_TOP);
    }
    public static void setLeftTopHover(String handlerName){
        setString("leftTopHover",handlerName);
    }
    public static String getLeftTopHover(){
        return getString("leftTopHover",DefaultConfig.LEFT_TOP_HOVER);
    }
    public static void setLeftRight(String handlerName){
        setString("leftRight",handlerName);
    }
    public static String getLeftRight(){
        return getString("leftRight",DefaultConfig.LEFT_RIGHT);
    }
    public static void setLeftRightHover(String handlerName){
        setString("leftRightHover",handlerName);
    }
    public static String getLeftRightHover(){
        return getString("leftRightHover",DefaultConfig.LEFT_RIGHT_HOVER);
    }
    public static void setLeftBottom(String handlerName){
        setString("leftBottom",handlerName);
    }
    public static String getLeftBottom(){
        return getString("leftBottom",DefaultConfig.LEFT_BOTTOM);
    }
    public static void setLeftBottomHover(String handlerName){
        setString("leftBottomHover",handlerName);
    }
    public static String getLeftBottomHover(){
        return getString("leftBottomHover",DefaultConfig.LEFT_BOTTOM_HOVER);
    }


    public static void setRightLeft(String handlerName){
        setString("rightLeft",handlerName);
    }
    public static String getRightLeft(){
        return getString("rightLeft",DefaultConfig.RIGHT_LEFT);
    }
    public static void setRightLeftHover(String handlerName){
        setString("rightLeftHover",handlerName);
    }
    public static String getRightLeftHover(){
        return getString("rightLeftHover",DefaultConfig.RIGHT_LEFT_HOVER);
    }
    public static void setRightTop(String handlerName){
        setString("rightTop",handlerName);
    }
    public static String getRightTop(){
        return getString("rightTop",DefaultConfig.RIGHT_TOP);
    }
    public static void setRightTopHover(String handlerName){
        setString("rightTopHover",handlerName);
    }
    public static String getRightTopHover(){
        return getString("rightTopHover",DefaultConfig.RIGHT_TOP_HOVER);
    }
    public static void setRightBottom(String handlerName){
        setString("rightBottom",handlerName);
    }
    public static String getRightBottom(){
        return getString("rightBottom",DefaultConfig.RIGHT_BOTTOM);
    }
    public static void setRightBottomHover(String handlerName){
        setString("rightBottomHover",handlerName);
    }
    public static String getRightBottomHover(){
        return getString("rightBottomHover",DefaultConfig.RIGHT_BOTTOM_HOVER);
    }


    public static void setBottomLeft(String handlerName){
        setString("bottomLeft",handlerName);
    }
    public static String getBottomLeft(){
        return getString("bottomLeft",DefaultConfig.BOTTOM_LEFT);
    }
    public static void setBottomLeftHover(String handlerName){
        setString("bottomLeftHover",handlerName);
    }
    public static String getBottomLeftHover(){
        return getString("bottomLeftHover",DefaultConfig.BOTTOM_LEFT_HOVER);
    }
    public static void setBottomTop(String handlerName){
        setString("bottomTop",handlerName);
    }
    public static String getBottomTop(){
        return getString("bottomTop",DefaultConfig.BOTTOM_TOP);
    }

    public static void setBottomTopHover(String handlerName){
        setString("bottomTopHover",handlerName);
    }
    public static String getBottomTopHover(){
        return getString("bottomTopHover",DefaultConfig.BOTTOM_TOP_HOVER);
    }
    public static void setBottomRight(String handlerName){
        setString("bottomRight",handlerName);
    }
    public static String getBottomRight(){
        return getString("bottomRight",DefaultConfig.BOTTOM_RIGHT);
    }
    public static void setBottomRightHover(String handlerName){
        setString("bottomRightHover",handlerName);
    }
    public static String getBottomRightHover(){
        return getString("bottomRightHover",DefaultConfig.BOTTOM_RIGHT_HOVER);
    }
}
