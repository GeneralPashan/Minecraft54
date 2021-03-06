package engine54.graphics;

import engine54.graphics.vertices.Mesh;
import engine54.maths.Matrix4f;
import engine54.maths.vectors.Vector3f;

public class Model{


    private final Vector3f position,rotation,scale;

    public Mesh mesh;
    public Material material;

    private Matrix4f modelView;

    public boolean isReqCalcMat;


    public Model(Mesh mesh,Material material){
        this.mesh=mesh;
        this.material=material;

        position=new Vector3f();
        rotation=new Vector3f();
        scale=new Vector3f(1);

        modelView=new Matrix4f();
        isReqCalcMat=false;
    }

    public Model(Model model){
        this.mesh=new Mesh(model.mesh);
        this.material=new Material(model.material);

        position=new Vector3f(model.position);
        rotation=new Vector3f(model.rotation);
        scale=new Vector3f(model.scale);

        modelView=new Matrix4f(model.modelView);
        isReqCalcMat=model.isReqCalcMat;
    }


    public void calculateMatrix(){
        Matrix4f translation=Matrix4f.translated(position);
        Matrix4f scaling=Matrix4f.scaled(scale);

        Matrix4f rotXMatrix=Matrix4f.rotatedX(rotation.x);
        Matrix4f rotYMatrix=Matrix4f.rotatedY(rotation.y);
        Matrix4f rotZMatrix=Matrix4f.rotatedZ(rotation.z);
        Matrix4f rotate=Matrix4f.mul(rotXMatrix,Matrix4f.mul(rotYMatrix,rotZMatrix));

        modelView=Matrix4f.mul(Matrix4f.mul(scaling,translation),rotate);
        isReqCalcMat=false;
    }


    public void setPos(Vector3f pos){
        position.set(pos);
        isReqCalcMat=true;
    }
    public void setPos(float x,float y,float z){
        position.set(x,y,z);
        isReqCalcMat=true;
    }
    public void translate(Vector3f tran){
        position.add(tran);
        isReqCalcMat=true;
    }
    public void translate(float x,float y,float z){
        position.add(x,y,z);
        isReqCalcMat=true;
    }

    public void setRotation(Vector3f rot){
        rotation.set(rot);
        isReqCalcMat=true;
    }
    public void setRotation(float x,float y,float z){
        rotation.set(x,y,z);
        isReqCalcMat=true;
    }
    public void rotate(Vector3f rot){
        rotation.add(rot);
        isReqCalcMat=true;
    }
    public void rotate(float x,float y,float z){
        rotation.add(x,y,z);
        isReqCalcMat=true;
    }

    public void setScale(Vector3f scl){
        scale.set(scl);
        isReqCalcMat=true;
    }
    public void setScale(float x,float y,float z){
        scale.set(x,y,z);
        isReqCalcMat=true;
    }
    public void scale(Vector3f scl){
        scale.add(scl);
        isReqCalcMat=true;
    }
    public void scale(float x,float y,float z){
        scale.add(x,y,z);
        isReqCalcMat=true;
    }
    public void scale(float xyz){
        scale.add(xyz);
        isReqCalcMat=true;
    }


    public Vector3f getPos(){
        return position;
    }

    public Vector3f getRotation(){
        return rotation;
    }

    public Vector3f getScale(){
        return scale;
    }

    public Matrix4f getModelMatrix(){
        return modelView;
    }


}
