package djankcraft.minecraft54.events;

import djankcraft.engine.graphics.PerspectiveCamera;
import djankcraft.engine.math.Intersector;
import djankcraft.engine.math.Maths;
import djankcraft.engine.math.vectors.Vector3;
import djankcraft.minecraft54.Block;
import djankcraft.minecraft54.World;

import java.util.ArrayList;
import java.util.List;

public class EventProcessor{


    public static int rayLength=10;
    public static List<EventsListener> listeners=new ArrayList<>();

    public static List<Integer> rayIgnoreIds=new ArrayList<>();
    static{
        rayIgnoreIds.add(Block.AIR.id);
    }
    public static List<Integer> rayIgnoreIds2=new ArrayList<>();
    static{
        rayIgnoreIds2.add(Block.WATER.id);
        rayIgnoreIds2.add(Block.STILL_WATER.id);
    }


    public static void update(World world,PerspectiveCamera cam){
        Vector3 dir=cam.getDirection();
        for(float i=0.75f; i<rayLength; i+=0.001f){
            Vector3 pos=new Vector3(dir).mul(i).add(cam.getPosition());
            int x=Maths.floor(pos.x);
            int y=Maths.floor(pos.y);
            int z=Maths.floor(pos.z);
            int id=world.getBlock(x,y,z);
            if(!rayIgnoreIds.contains(id) && !rayIgnoreIds2.contains(id)){
                Vector3 line=dir.clone().mul(rayLength).add(cam.getPosition());

                Vector3 px=Intersector.lineQuad(cam.getPosition().clone(),line, x+1,y+1,z+1,  x+1,y  ,z+1,  x+1,y  ,z  ,  x+1,y+1,z   );
                Vector3 mx=Intersector.lineQuad(cam.getPosition().clone(),line, x  ,y+1,z+1,  x  ,y  ,z+1,  x  ,y  ,z  ,  x  ,y+1,z   );
                Vector3 py=Intersector.lineQuad(cam.getPosition().clone(),line, x+1,y+1,z+1,  x+1,y+1,z  ,  x  ,y+1,z  ,  x  ,y+1,z+1 );
                Vector3 my=Intersector.lineQuad(cam.getPosition().clone(),line, x+1,y  ,z+1,  x+1,y  ,z  ,  x  ,y  ,z  ,  x  ,y  ,z+1 );
                Vector3 pz=Intersector.lineQuad(cam.getPosition().clone(),line, x  ,y+1,z+1,  x  ,y  ,z+1,  x+1,y  ,z+1,  x+1,y+1,z+1 );
                Vector3 mz=Intersector.lineQuad(cam.getPosition().clone(),line, x  ,y+1,z  ,  x  ,y  ,z  ,  x+1,y  ,z  ,  x+1,y+1,z   );

                Vector3[] points={
                        new Vector3(x+1,y+0.5,z+0.5),new Vector3(x,y+0.5,z+0.5),new Vector3(x+0.5,y+1,z+0.5),new Vector3(x+0.5,y,z+0.5),new Vector3(x+0.5,y+0.5,z+1),new Vector3(x+0.5,y+0.5,z)
                };

                boolean[] sides={
                        px!=null,mx!=null,py!=null,my!=null,pz!=null,mz!=null
                };

                int n=0;
                for(boolean s:sides)
                    if(s)
                        n++;

                int side=0;
                if(n==1){
                    for(int j=0; j<6; j++){
                        if(sides[j]){
                            side=j;
                            break;
                        }
                    }
                }else{
                    for(int q=0; q<6; q++){
                        for(int t=0; t<6; t++){
                            if(sides[q] && sides[t] && q!=t){
                                float dq=Vector3.dst(points[q],cam.getPosition());
                                float dt=Vector3.dst(points[t],cam.getPosition());
                                if(dq<dt)
                                    side=q;
                                else
                                    side=t;

                                q=6;
                                break;
                            }
                        }
                    }
                }

                for(EventsListener l:listeners)
                    l.selectedBlock(x,y,z,side);

               break;
            }
        }
    }


}