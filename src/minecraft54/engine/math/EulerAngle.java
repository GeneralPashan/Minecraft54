package minecraft54.engine.math;

import minecraft54.engine.math.vectors.Vector3f;

public class EulerAngle{

    private float pitch,yaw,roll;

    public EulerAngle(){
        this(0,0,0);
    }
    public EulerAngle(float pitch,float yaw){
        this(pitch,yaw,0);
    }
    public EulerAngle(float pitch,float yaw,float roll){
        this.pitch=pitch;
        this.yaw=yaw;
        this.roll=roll;
    }

    public Vector3f toVector(){
        Vector3f result=new Vector3f();

        float yawTemp=yaw*Maths.toRadians;
        float pitchTemp=pitch*Maths.toRadians;

        result.set(
                (float)(Math.sin(yawTemp)*Math.cos(pitchTemp)),
                (float)(Math.sin(pitchTemp)),
                (float)(Math.cos(pitchTemp)*-Math.cos(yawTemp))
        );
        return result;
    }

    public void toAngles(Vector3f direction){
        Vector3f dir=new Vector3f(direction);
        dir.nor();

        float yawTemp=(float)Math.atan2(dir.x,dir.z);
        float pitchTemp=(float)Math.atan2(dir.y,Math.sqrt(dir.x*dir.x+dir.z*dir.z));

        pitchTemp=pitchTemp*Maths.toDegrees;
        yawTemp=yawTemp*Maths.toDegrees;

        this.pitch=-pitchTemp;
        this.yaw=-yawTemp;
    }

    public void constrain(){
        if(yaw>=90)
            yaw=90;
        else if(yaw<=-90)
            yaw=-90;

        if(pitch>360)
            pitch-=360;
        else if(pitch<-360)
            pitch+=360;
    }

    public float getPitch(){
        return pitch;
    }
    public void setPitch(float pitch){
        this.pitch=pitch;
    }

    public float getYaw(){
        return yaw;
    }
    public void setYaw(float yaw){
        this.yaw=yaw;
    }

    public float getRoll(){
        return roll;
    }
    public void setRoll(float roll){
        this.roll=roll;
    }

    public void rotateAngles(float yaw,float pitch,float roll){
        this.yaw+=yaw;
        this.pitch+=pitch;
        this.roll+=roll;
    }

    public void setAngles(float pitch,float yaw){
        setAngles(pitch,yaw,0);
    }
    public void setAngles(float pitch,float yaw,float roll){
        this.pitch=pitch;
        this.yaw=yaw;
        this.roll=roll;
    }

}
