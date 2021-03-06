package minecraft54.main.client.entity;

import engine54.E54;
import engine54.io.Keyboard;
import engine54.maths.vectors.Vector3d;
import engine54.maths.vectors.Vector3f;
import engine54.util.Timer;
import minecraft54.main.Options;
import minecraft54.main.client.controls.Controls;
import minecraft54.main.Main;
import minecraft54.main.client.screens.GameScreen;
import minecraft54.main.client.world.Block;
import minecraft54.main.client.world.BlockData;
import minecraft54.main.client.world.BlockManager;
import minecraft54.main.util.GameMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;

public class Player extends Entity{


    private final String name;
    private boolean canFly;
    private boolean flying;
    private GameMode gameMode;
    private final Timer stepTimer;


    public Player(String name){
        super(new Vector3f(-0.3,0,-0.3),new Vector3f(0.3,1.8,0.3),new Vector3f(0,1.62,0));
        this.name=name;
        setSpeed(4.3);
        stepTimer=new Timer().start().setSeconds(1);
    }


    public void controls(Keyboard keyboard){
        float cam_speed=(float)getVelocity().max()/10;

        Vector3f controlMoveVel=new Vector3f();
        Vector3f dirXZ=Controls.CAMERA.getRotation().direction();
        dirXZ.y=0;
        if(keyboard.isKeyPressed(GLFW_KEY_W))
            controlMoveVel.add(dirXZ.mul(cam_speed));
        if(keyboard.isKeyPressed(GLFW_KEY_S))
            controlMoveVel.add(dirXZ.mul(-cam_speed));
        if(flying && keyboard.isKeyPressed(GLFW_KEY_SPACE))
            controlMoveVel.y+=cam_speed;
        if(flying && keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
            controlMoveVel.y-=cam_speed;
        if(keyboard.isKeyPressed(GLFW_KEY_D))
            controlMoveVel.add(Vector3f.crs(new Vector3f(0,1,0),dirXZ).mul(cam_speed));
        if(keyboard.isKeyPressed(GLFW_KEY_A))
            controlMoveVel.add(Vector3f.crs(new Vector3f(0,1,0),dirXZ).mul(-cam_speed));
        controlMoveVel.nor().mul(cam_speed);


        if(isOnGround() && gameMode!=GameMode.SPECTATOR){
            if(keyboard.isKeyPressed(GLFW_KEY_SPACE)){
                jump();
                stepTimer.setSeconds(1);
            }

            if(flying)
                flying=false;

            if(E54.keyboard().isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
                setSpeed(1.3);
                getEye().y=getEye().y>1.3f?getEye().y-0.04f:1.3f; //1.32
                getHitbox().getB().set(0.3,1.5,0.3); //1.3

                if(!isOnGround(controlMoveVel.x,0,0)){
                    controlMoveVel.x=0;
                    getVelocity().get().x=0;
                }
                if(!isOnGround(0,0,controlMoveVel.z)){
                    controlMoveVel.z=0;
                    getVelocity().get().z=0;
                }

            }else if(E54.keyboard().isKeyPressed(GLFW_KEY_LEFT_CONTROL)){
                setSpeed(5.612);
                getEye().y=getEye().y<1.62f?getEye().y+0.04f:1.62f;
                getHitbox().getB().set(0.3,1.8,0.3);
            }else{
                setSpeed(4.316);
                getEye().y=getEye().y<1.62f?getEye().y+0.04f:1.62f;
                getHitbox().getB().set(0.3,1.8,0.3);
            }

            // Step Sounds
            if(getVelocity().get().len()!=0){
                Vector3d pos=getHitbox().getPosition();
                Block floorBlock=BlockManager.getBlockFromId(GameScreen.world.getBlockId(pos.xf(),pos.yf()-1,pos.zf()));
                if(floorBlock!=null){
                    BlockData floorBlockData=floorBlock.getBlockData(GameScreen.world.getBlockData(pos.xf(),pos.yf()-1,pos.zf()));
                    if(floorBlockData!=null && floorBlockData.sounds!=null && stepTimer.getMillis()>1600*(1/getVelocity().get().len())){
                        floorBlockData.sounds.playStep(new Vector3f(getHitbox().getPosition()).add(0.5,0,0.5));
                        stepTimer.reset();
                    }
                }
            }
        }

        //getEye().y=0.1f;
        //getHitbox().getA().set(-0.05,0,-0.05);
        //getHitbox().getB().set(0.05,0.2,0.05);

        if(isJumping())
            controlMoveVel.div(2f);

        if(flying){
            setNoGravity(true);
            if(E54.keyboard().isKeyPressed(GLFW_KEY_LEFT_CONTROL))
                setSpeed(22);
            else
                setSpeed(4.316);
        }else{
            setNoGravity(false);
            if(keyboard.isKeyDown(GLFW_KEY_F) && isCanFly())
                flying=true;
        }

        getVelocity().get().add(controlMoveVel);
        getHitbox().getVelocity().zero();

        if(!E54.keyboard().isKeyPressed(GLFW_KEY_C)){
            if(E54.keyboard().isKeyDown(GLFW_KEY_LEFT_CONTROL) && getVelocity().get().x!=0 && getVelocity().get().z!=0)
                Controls.interpolateFov(Options.FOV+10);
            else if(E54.keyboard().isKeyReleased(GLFW_KEY_LEFT_CONTROL))
                Controls.interpolateFov(Options.FOV);
        }
        if(E54.keyboard().isKeyDown(GLFW_KEY_C))
            Controls.fov=Options.FOV/4;
        else if(E54.keyboard().isKeyReleased(GLFW_KEY_C))
            Controls.fov=Options.FOV;

        ((GameScreen)E54.context().getScreen("game")).playerMoved(this);

        Controls.setPosition(getHitbox().getPosition().clone().add(getEye()));
    }


    public void setSpeed(double speed){
        getVelocity().setMax(speed);
    }

    public void setGameMode(GameMode gameMode){
        if(this.gameMode==gameMode)
            return;
        this.gameMode=gameMode;

        if(gameMode==GameMode.SPECTATOR){
            setNoClip(true);
            setFlying(true);
            setCanFly(true);
        }else{
            setNoClip(false);
            if(gameMode==GameMode.CREATIVE){
                setCanFly(true);
            }else if(gameMode==GameMode.SURVIVAL || gameMode==GameMode.ADVENTURE){
                setCanFly(false);
            }
        }
    }

    public GameMode gameMode(){
        return gameMode;
    }


    public boolean isCanFly(){
        return canFly;
    }
    public void setCanFly(boolean canFly){
        this.canFly=canFly;
        if(!canFly && flying)
            flying=false;
    }

    public boolean isFlying(){
        return flying;
    }
    public void setFlying(boolean flying){
        this.flying=flying;
        setNoGravity(flying);
    }

    public String getName(){
        return name;
    }


}
