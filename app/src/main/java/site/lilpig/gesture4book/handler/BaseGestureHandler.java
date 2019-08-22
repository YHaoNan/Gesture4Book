package site.lilpig.gesture4book.handler;

public abstract class BaseGestureHandler implements GestureHandler{
    private GestureMetaData metaData;
    public BaseGestureHandler(){

    }


    @Override
    public void onActive(GestureMetaData metaData) {
        this.metaData = metaData;
        if (metaData.gestureType == GestureType.TYPE_HOVER){
            onHover(metaData);
        }else{
            onTriggerWrapper(metaData);
        }
    }

    protected void onTriggerWrapper(GestureMetaData metaData){
        onTrigger(metaData);
        onExit();
    }

    protected abstract void onHover(GestureMetaData metaData);

    protected abstract void onTrigger(GestureMetaData metaData);

    @Override
    public void onExit() {
        this.metaData = null;
        onOver();
    }

    @Override
    public int isActive() {
        return this.metaData != null ? this.metaData.gestureType : -1;
    }

    public abstract void onOver();
}
