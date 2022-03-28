package minecraft54.main.client.audio;

import minecraft54.engine.audio.SoundBuffer;
import minecraft54.engine.audio.SoundManager;
import minecraft54.engine.audio.SoundSource;
import minecraft54.engine.math.vectors.Vector3f;
import minecraft54.engine.utils.Assets;

public class Sound extends SoundBuffer{



    private Float volume,pitch,pan;
    private Boolean loop,relative;
    private Vector3f speed,position;


    public Sound(SoundBuffer soundBuffer){
        super(soundBuffer);
    }

    public Sound(String assetsId){
        super(Assets.getSound(assetsId));
    }


    public void play(){
        SoundSource soundSource=new SoundSource().setBuffer(getBufferId());
        if(volume!=null) soundSource.setVolume(volume);
        if(speed!=null) soundSource.setSpeed(speed);
        if(pitch!=null) soundSource.setPitch(pitch);
        if(position!=null) soundSource.setPosition(position);
        if(loop!=null) soundSource.setLoop(loop);
        if(relative!=null) soundSource.setRelative(relative);
        if(pan!=null) soundSource.setPan(pan);
        SoundManager.play(soundSource);
    }


    public Sound setVolume(float volume){
        this.volume=volume;
        return this;
    }

    public Sound setPitch(float pitch){
        this.pitch=pitch;
        return this;
    }

    public Sound setPan(float pan){
        this.pan=pan;
        return this;
    }

    public Sound setLoop(boolean loop){
        this.loop=loop;
        return this;
    }

    public Sound setRelative(boolean relative){
        this.relative=relative;
        return this;
    }

    public Sound setSpeed(Vector3f speed){
        if(this.speed==null)
            this.speed=speed;
        else
            this.speed.set(speed);
        return this;
    }

    public Sound setPosition(Vector3f position){
        if(this.position==null)
            this.position=position;
        else
            this.position.set(position);
        return this;
    }

    public Sound setSpeed(float speed){
        if(this.speed==null)
            this.speed=new Vector3f(speed);
        else
            this.speed.set(speed);
        return this;
    }

    public Sound setPosition(float position){
        if(this.position==null)
            this.position=new Vector3f(position);
        else
            this.position.set(position);
        return this;
    }


}
