package minecraft54.main.client.controls;

import engine54.E54;
import engine54.audio.SoundListener;
import engine54.graphics.camera.PerspectiveCamera;
import engine54.io.Mouse;
import engine54.io.Window;
import engine54.maths.Frustum;
import engine54.maths.Matrix4f;
import engine54.maths.vectors.Vector3d;
import engine54.maths.vectors.Vector3f;
import minecraft54.main.Options;

public class Controls{


    public static PerspectiveCamera CAMERA;
    public static Frustum frustum;
    private static boolean ignoreRotation;
    private static final Vector3d position=new Vector3d();
    public static float fov=Options.FOV;
    private static float ifov=fov;
    public static float i=0.5f;
    private static boolean interpolation;


    static{
        CAMERA=new PerspectiveCamera(E54.getWinWidth()/2,E54.getWinHeight()/2,0.05f,1000f,Options.FOV);
        frustum=new Frustum(CAMERA.getView().val,CAMERA.getProjection().val);
    }


    public static void interpolateFov(float fov){
        if(!interpolation){
            interpolation=true;
            ifov=fov;
        }
    }


    public static void ignoreRotation(){
        ignoreRotation=true;
    }


    public static void rotateCamera(Window window,Mouse mouse){
        CAMERA.getPosition().set(0,(float)position.y,0);

        SoundListener.setPosition(position);
        SoundListener.setOrientation(CAMERA.getRotation().direction().mul(-1),new Vector3f(0,1,0));

        if(interpolation){
            if(fov>ifov+i)
                fov-=i;
            else if(fov<ifov-i)
                fov+=i;
            else{
                fov=ifov;
                interpolation=false;
            }
        }

        CAMERA.setFov(fov);

        if(window.isFocused()){
            int x=mouse.getX();
            int y=mouse.getY();
            int halfW=Math.round(window.getWidth()/2f);
            int halfH=Math.round(window.getHeight()/2f);

            float dx=halfW-x;
            float dy=halfH-y;
            float rotX=dx/window.getWidth()*Options.SENSITIVITY;
            float rotY=dy/window.getHeight()*Options.SENSITIVITY;

            if(mouse.isVisible()){
                mouse.show(false);
                ignoreRotation=true;
            }

            if(ignoreRotation){
                ignoreRotation=false;
                rotX=0;
                rotY=0;
            }

            mouse.setPos(
                    rotX==0?x:halfW,
                    rotY==0?y:halfH
            );

            CAMERA.getRotation().rotate(rotX,rotY,0);
            CAMERA.getRotation().constrain();

        }else if(!mouse.isVisible())
            mouse.show(true);


        frustum.setFrustum(Matrix4f.lookAt(new Vector3f(position),CAMERA.getRotation().direction()).val,CAMERA.getProjection().val);
    }



    public static void setPosition(Vector3d pos){
        position.set(pos);
    }

    public static Vector3d getPosition(){
        return position;
    }


}
