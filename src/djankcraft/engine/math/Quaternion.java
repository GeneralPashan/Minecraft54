package djankcraft.engine.math;

public class Quaternion{


    public float x;
    public float y;
    public float z;
    public float w;


    public Quaternion(){
        this.w=1.0f;
    }

    public Quaternion(float x,float y,float z,float w){
        this.x=x;
        this.y=y;
        this.z=z;
        this.w=w;
    }


    public float x(){
        return this.x;
    }
    public float y(){
        return this.y;
    }
    public float z(){
        return this.z;
    }
    public float w(){
        return this.w;
    }


    public static float len(float x,float y,float z,float w){
        return (float)Math.sqrt(x*x+y*y+z*z+w*w);
    }

    public float len(){
        return (float)Math.sqrt(x*x+y*y+z*z+w*w);
    }

    @Override
    public String toString(){
        return "["+x+"|"+y+"|"+z+"|"+w+"]";
    }

    public Quaternion setEulerAngles(float yaw,float pitch,float roll){
        return setEulerAnglesRad(yaw*Maths.toRadians,pitch*Maths.toRadians,roll*Maths.toRadians);
    }

    public Quaternion setEulerAnglesRad(float yaw,float pitch,float roll){
        final float hr=roll*0.5f;
        final float shr=(float)Math.sin(hr);
        final float chr=(float)Math.cos(hr);
        final float hp=pitch*0.5f;
        final float shp=(float)Math.sin(hp);
        final float chp=(float)Math.cos(hp);
        final float hy=yaw*0.5f;
        final float shy=(float)Math.sin(hy);
        final float chy=(float)Math.cos(hy);
        final float chy_shp=chy*shp;
        final float shy_chp=shy*chp;
        final float chy_chp=chy*chp;
        final float shy_shp=shy*shp;

        x=(chy_shp*chr)+(shy_chp*shr); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) * cos(pitch/2) * sin(roll/2)
        y=(shy_chp*chr)-(chy_shp*shr); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) * sin(pitch/2) * sin(roll/2)
        z=(chy_chp*shr)-(shy_shp*chr); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) * sin(pitch/2) * cos(roll/2)
        w=(chy_chp*chr)+(shy_shp*shr); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) * sin(pitch/2) * sin(roll/2)
        return this;
    }

    public int getGimbalPole(){
        final float t=y*x+z*w;
        return t>0.499f ? 1:(t<-0.499f ? -1:0);
    }

    public float getRollRad(){
        final int pole=getGimbalPole();
        return pole==0 ? (float)Math.atan2(2f*(w*z+y*x),1f-2f*(x*x+z*z)):(float)pole*2f*(float)Math.atan2(y,w);
    }

    public float getRoll(){
        return getRollRad()*Maths.toDegrees;
    }

    public float getPitchRad(){
        final int pole=getGimbalPole();
        return pole==0 ? (float)Math.asin(Maths.clamp(2f*(w*x-z*y),-1f,1f)):(float)pole*Maths.PI*0.5f;
    }

    public float getPitch(){
        return getPitchRad()*Maths.toDegrees;
    }

    public float getYawRad(){
        return getGimbalPole()==0 ? (float)Math.atan2(2f*(y*w+x*z),1f-2f*(y*y+x*x)):0f;
    }

    public float getYaw(){
        return getYawRad()*Maths.toDegrees;
    }

    public final static float len2(final float x,final float y,final float z,final float w){
        return x*x+y*y+z*z+w*w;
    }

    public float len2(){
        return x*x+y*y+z*z+w*w;
    }

    public Quaternion nor(){
        float len=len2();
        if(len!=0.f && !Maths.isEqual(len,1f)){
            len=(float)Math.sqrt(len);
            w/=len;
            x/=len;
            y/=len;
            z/=len;
        }
        return this;
    }

    public Quaternion conjugate(){
        x=-x;
        y=-y;
        z=-z;
        return this;
    }

    public Quaternion mul(final Quaternion other){
        final float newX=this.w*other.x+this.x*other.w+this.y*other.z-this.z*other.y;
        final float newY=this.w*other.y+this.y*other.w+this.z*other.x-this.x*other.z;
        final float newZ=this.w*other.z+this.z*other.w+this.x*other.y-this.y*other.x;
        final float newW=this.w*other.w-this.x*other.x-this.y*other.y-this.z*other.z;
        this.x=newX;
        this.y=newY;
        this.z=newZ;
        this.w=newW;
        return this;
    }

    public Quaternion mul(final float x,final float y,final float z,final float w){
        final float newX=this.w*x+this.x*w+this.y*z-this.z*y;
        final float newY=this.w*y+this.y*w+this.z*x-this.x*z;
        final float newZ=this.w*z+this.z*w+this.x*y-this.y*x;
        final float newW=this.w*w-this.x*x-this.y*y-this.z*z;
        this.x=newX;
        this.y=newY;
        this.z=newZ;
        this.w=newW;
        return this;
    }

    public Quaternion add(Quaternion quaternion){
        this.x+=quaternion.x;
        this.y+=quaternion.y;
        this.z+=quaternion.z;
        this.w+=quaternion.w;
        return this;
    }

    public Quaternion add(float qx,float qy,float qz,float qw){
        this.x+=qx;
        this.y+=qy;
        this.z+=qz;
        this.w+=qw;
        return this;
    }

    public Matrix4 toMatrix(){
        final float xx=x*x;
        final float xy=x*y;
        final float xz=x*z;
        final float xw=x*w;
        final float yy=y*y;
        final float yz=y*z;
        final float yw=y*w;
        final float zz=z*z;
        final float zw=z*w;

        Matrix4 result=new Matrix4();

        result.val[Matrix4.m00]=1-2*(yy+zz);
        result.val[Matrix4.m01]=2*(xy-zw);
        result.val[Matrix4.m02]=2*(xz+yw);
        result.val[Matrix4.m03]=0;
        result.val[Matrix4.m10]=2*(xy+zw);
        result.val[Matrix4.m11]=1-2*(xx+zz);
        result.val[Matrix4.m12]=2*(yz-xw);
        result.val[Matrix4.m13]=0;
        result.val[Matrix4.m20]=2*(xz-yw);
        result.val[Matrix4.m21]=2*(yz+xw);
        result.val[Matrix4.m22]=1-2*(xx+yy);
        result.val[Matrix4.m23]=0;
        result.val[Matrix4.m30]=0;
        result.val[Matrix4.m31]=0;
        result.val[Matrix4.m32]=0;
        result.val[Matrix4.m33]=1;

        return result;
    }

}