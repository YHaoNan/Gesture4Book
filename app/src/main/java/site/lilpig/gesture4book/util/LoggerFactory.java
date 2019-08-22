package site.lilpig.gesture4book.util;

import android.util.Log;

import java.util.logging.Logger;

public class LoggerFactory {
    private final String tag;

    public LoggerFactory(String tag){
        this.tag = tag;
    }
    public class LoggerMessage{
        private final String tag;
        private StringBuffer sb;
        public LoggerMessage(String tag,String msg){
            sb = new StringBuffer(msg);
            this.tag = tag;
        }

        public LoggerMessage and(String msg){
            sb.append(msg);
            return this;
        }
        public LoggerMessage and(String msg,String prefix){
            sb.append(prefix+msg);
            return this;
        }
        public void i(){
            Log.i(tag,sb.toString());
        }
        public void v(){
            Log.v(tag,sb.toString());
        }
    }

    public LoggerMessage create(){
        return create("");
    }
    public LoggerMessage create(String msg){
        return new LoggerMessage(tag,msg);
    }

}
