package minecraft54.engine.gui;

import minecraft54.engine.audio.SoundManager;
import minecraft54.engine.graphics.SpriteBatch;
import minecraft54.engine.io.Keyboard;
import minecraft54.engine.io.Mouse;
import minecraft54.engine.io.Window;
import minecraft54.engine.math.Maths;
import minecraft54.engine.utils.Assets;

import java.util.UUID;

public abstract class LayoutElement{


    private final UUID uuid;
    private Layout layout;

    private int renderX,renderY,renderWidth,renderHeight, gravity;
    private boolean show,allocated,oldAllocated,touched,oldTouched;
    private double x,y,width,height;

    private TouchCallback touchCallback;
    private AllocateCallback allocateCallback;


    public LayoutElement(double width,double height){
        uuid=UUID.randomUUID();
        this.width=width;
        this.height=height;
        show=true;
    }


    void updateRenderValues(){
        Layout layout=getLayout();
        if(layout==null)
            return;

        int ww=(int)layout.getWidth();
        int wh=(int)layout.getHeight();

        float px=0,py=0;
        int gravity=getGravity();

        if(gravity==Gravity.RIGHT_DOWN)
            px=ww-renderWidth;
        else if(gravity==Gravity.LEFT_UP)
            py=wh-renderHeight;
        else if(gravity==Gravity.CENTER_DOWN)
            px=ww/2f-renderWidth/2f;
        else if(gravity==Gravity.LEFT_CENTER)
            py=wh/2f-renderHeight/2f;
        else if(gravity==Gravity.RIGHT_UP){
            px=ww-renderWidth;
            py=wh-renderHeight;
        }else if(gravity==Gravity.CENTER){
            px=ww/2f-renderWidth/2f;
            py=wh/2f-renderHeight/2f;
        }else if(gravity==Gravity.RIGHT_CENTER){
            px=ww-renderWidth;
            py=wh/2f-renderHeight/2f;
        }else if(gravity==Gravity.CENTER_UP){
            px=ww/2f-renderWidth/2f;
            py=wh-renderHeight;
        }

        renderX=Maths.round(x()*wh + px);
        renderY=Maths.round(y()*wh + py);
        renderWidth=Maths.round(getWidth()*wh);
        renderHeight=Maths.round(getHeight()*wh);
    }

    public void updateCallbacks(Mouse mouse,Window window){
        if(!window.isFocused())
            return;

        float tx=mouse.getX();
        float ty=window.getHeight()-mouse.getY();

        boolean cancelledTouch=false;
        if(tx>=renderX && tx<renderX+renderWidth && ty>=renderY && ty<renderY+renderHeight){
            allocated=true;
            touched=mouse.isButtonPressed(0);
        }else{
            allocated=false;
            cancelledTouch=true;
            touched=false;
        }

        TouchCallback touchCallback=getTouchCallback();
        if(touchCallback!=null){
            if(touched){
                touchCallback.touched(this);
                if(!oldTouched){
                    touchCallback.touchOn(this);
                    SoundManager.play("random_click",0.3f);
                }
            }else if(oldTouched && !cancelledTouch)
                touchCallback.touchOff(this);
        }

        AllocateCallback allocateCallback=getAllocateCallback();
        if(allocateCallback!=null){
            if(allocated){
                allocateCallback.allocated(this);
                if(!oldAllocated)
                    allocateCallback.allocateOn(this);
            }else if(oldAllocated)
                allocateCallback.allocateOff(this);
        }

        oldTouched=touched;
        oldAllocated=allocated;
    }

    public boolean isTouched(){
        return touched;
    }

    public int getRenderX(){
        return renderX;
    }
    public int getRenderY(){
        return renderY;
    }

    public int getRenderWidth(){
        return renderWidth;
    }
    public int getRenderHeight(){
        return renderHeight;
    }

    public void setTouchCallback(TouchCallback touchCallback){
        this.touchCallback=touchCallback;
    }
    public TouchCallback getTouchCallback(){
        return touchCallback;
    }

    public void setAllocateCallback(AllocateCallback allocateCallback){
        this.allocateCallback=allocateCallback;
    }
    public AllocateCallback getAllocateCallback(){
        return allocateCallback;
    }


    public void setPos(double x,double y){
        this.x=x;
        this.y=y;
    }
    public void setX(double value){
        x=value;
    }
    public void setY(double value){
        y=value;
    }

    public double x(){
        return x;
    }
    public double y(){
        return y;
    }


    public void setSize(double width,double height){
        this.width=width;
        this.height=height;
    }
    public void setWidth(double value){
        width=value;
    }
    public void setHeight(double value){
        height=value;
    }

    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }


    abstract void render(SpriteBatch batch);
    abstract void update(Mouse mouse,Keyboard keyboard,Window window);
    abstract String getType();


    public void setGravity(int gravity){
        this.gravity=gravity;
    }

    public int getGravity(){
        return gravity;
    }

    public boolean isHidden(){
        return !show;
    }
    public void show(boolean flag){
        show=flag;
    }

    public void setAllocated(boolean flag){
        allocated=flag;
    }
    public boolean isAllocated(){
        return allocated;
    }

    public void setLayout(Layout layout){
        this.layout=layout;
    }
    public Layout getLayout(){
        return layout;
    }

    public UUID getUUID(){
        return uuid;
    }


}
