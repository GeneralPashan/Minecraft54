package engine54.maths;

import engine54.maths.vectors.Vector3d;
import engine54.maths.vectors.Vector3f;

public class EulerAngle{

    private double pitch,yaw,roll;

    public EulerAngle(){}

    public EulerAngle(double pitch,double yaw){
        this.pitch=pitch;
        this.yaw=yaw;
    }

    public EulerAngle(double yaw,double pitch,double roll){
        this.pitch=pitch;
        this.yaw=yaw;
        this.roll=roll;
    }

    public double yaw(){
        return yaw;
    }
    public double pitch(){
        return pitch;
    }
    public double roll(){
        return roll;
    }

    public void constrain(){
        if(pitch>90)
            pitch=90;
        else if(pitch<-90)
            pitch=-90;

        if(yaw>=360)
            yaw-=360;
        else if(yaw<=-360)
            yaw+=360;
    }

    public Vector3f direction(){
        double yaw=Math.toRadians(this.yaw);
        double pitch=Math.toRadians(this.pitch);

        return new Vector3f(
                Math.cos(pitch)*Math.cos(yaw),
                Math.sin(pitch),
                Math.cos(pitch)*Math.sin(yaw)
        );
    }

    public void setDirection(double x,double y,double z){
        yaw=-Math.atan2(x,z)*Maths.toDegrees+90;
        pitch=Math.atan2(y,Math.sqrt(x*x+z*z))*Maths.toDegrees;
    }

    public void setDirection(Vector3d dir){
        setDirection(dir.x,dir.y,dir.z);
    }
    public void setDirection(Vector3f dir){
        setDirection(dir.x,dir.y,dir.z);
    }

    public void set(EulerAngle eulerAngle){
        yaw=eulerAngle.yaw;
        pitch=eulerAngle.pitch;
        roll=eulerAngle.roll;
    }

    public void set(double yaw,double pitch,double roll){
        this.yaw=yaw;
        this.pitch=pitch;
        this.roll=roll;
    }

    public void set(double yaw,double pitch){
        this.yaw=yaw;
        this.pitch=pitch;
    }

    public void setPitch(double pitch){
        this.pitch=pitch;
    }
    public void setYaw(double yaw){
        this.yaw=yaw;
    }
    public void setRoll(double roll){
        this.roll=roll;
    }

    public void rotate(EulerAngle eulerAngle){
        yaw+=eulerAngle.yaw;
        pitch+=eulerAngle.pitch;
        roll+=eulerAngle.roll;
    }

    public void rotate(double yaw,double pitch,double roll){
        this.yaw+=yaw;
        this.pitch+=pitch;
        this.roll+=roll;
    }

    public void rotate(double yaw,double pitch){
        this.yaw+=yaw;
        this.pitch+=pitch;
    }

    public void rotatePitch(double pitch){
        this.pitch+=pitch;
    }
    public void rotateYaw(double yaw){
        this.yaw+=yaw;
    }
    public void rotateRoll(double roll){
        this.roll+=roll;
    }

}
