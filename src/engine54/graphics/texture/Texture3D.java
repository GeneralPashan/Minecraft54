package engine54.graphics.texture;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL42C.glTexStorage3D;

public class Texture3D{

    private final int id;
    private final List<Pixmap> pixmapList;

    public Texture3D(int width,int height,Pixmap... textureData){
        pixmapList=new ArrayList<>();
        id=glGenTextures();
        glBindTexture(GL_TEXTURE_2D_ARRAY,id);
        //glTexStorage3D(GL_TEXTURE_2D_ARRAY,1,GL_RGBA8,width,height,textureData.length);

        for(int z=0; z<textureData.length; z++){
            Pixmap td=textureData[z];
            pixmapList.add(td);

            glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MIN_FILTER,GL_NEAREST_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
            glTexSubImage3D(GL_TEXTURE_2D_ARRAY,0,0,0,z,td.getWidth(),td.getHeight(),1,GL_RGBA,GL_UNSIGNED_BYTE,td.getPixels());
            glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
        }
        glBindTexture(GL_TEXTURE_2D_ARRAY,0);
    }

    public Texture3D(int width,int height,BufferedImage... bufferedImage){
        pixmapList=new ArrayList<>();
        id=glGenTextures();
        glBindTexture(GL_TEXTURE_2D_ARRAY,id);
        //glTexStorage3D(GL_TEXTURE_2D_ARRAY,1,GL_RGBA8,width,height,bufferedImage.length);

        for(int z=0; z<bufferedImage.length; z++){
            Pixmap td=new Pixmap(bufferedImage[z]);
            pixmapList.add(td);

            glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MIN_FILTER,GL_NEAREST_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
            glTexSubImage3D(GL_TEXTURE_2D_ARRAY,0,0,0,z,td.getWidth(),td.getHeight(),1,GL_RGBA,GL_UNSIGNED_BYTE,td.getPixels());
            glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
        }
        glBindTexture(GL_TEXTURE_2D_ARRAY,0);
    }

    public Texture3D(int width,int height,Texture... texture){
        pixmapList=new ArrayList<>();
        id=glGenTextures();
        glBindTexture(GL_TEXTURE_2D_ARRAY,id);
        //glTexStorage3D(GL_TEXTURE_2D_ARRAY,1,GL_RGBA8,width,height,texture.length);

        for(int z=0; z<texture.length; z++){
            Pixmap td=texture[z].getPixmap().clone();
            pixmapList.add(td);

            glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MIN_FILTER,GL_NEAREST_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
            glTexSubImage3D(GL_TEXTURE_2D_ARRAY,0,0,0,z,td.getWidth(),td.getHeight(),1,GL_RGBA,GL_UNSIGNED_BYTE,td.getPixels());
            glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
        }
        glBindTexture(GL_TEXTURE_2D_ARRAY,0);
    }

    public Texture3D(int width,int height,String... file){
        this(width,height,false,file);
    }

    public Texture3D(int width,int height,boolean invY,String... file){
        pixmapList=new ArrayList<>();
        id=glGenTextures();
        glBindTexture(GL_TEXTURE_2D_ARRAY,id);
        glTexStorage3D(GL_TEXTURE_2D_ARRAY,1,GL_RGBA8,width,height,file.length);

        //glTexParameterf(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MAX_LEVEL,1);

        glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MIN_FILTER,GL_NEAREST_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_WRAP_S,GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_WRAP_T,GL_CLAMP_TO_EDGE);

        //float amount=Math.min(4f,glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
        //glTexParameterf(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MAX_ANISOTROPY,amount);

        for(int z=0; z<file.length; z++){
            String f=file[z];
            Pixmap td=new Pixmap(f,invY);
            pixmapList.add(td);

            glTexImage3D(GL_TEXTURE_2D,0,GL_RGBA8,width,height,z,0,GL_RGBA,GL_UNSIGNED_BYTE,td.getPixels());
            glTexSubImage3D(GL_TEXTURE_2D_ARRAY,0,0,0,z,width,height,1,GL_RGBA,GL_UNSIGNED_BYTE,td.getPixels());
        }
        glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
    }

    public void bind(int unit){
        glActiveTexture(GL_TEXTURE0+unit);
        glBindTexture(GL_TEXTURE_2D_ARRAY,id);
    }

    public static void unbind(){
        glBindTexture(GL_TEXTURE_2D_ARRAY,0);
    }

    public void dispose(){
        glDeleteTextures(id);
    }

    public List<Pixmap> getPixmapList(){
        return pixmapList;
    }

    public int getWidth(){
        if(pixmapList.size()>0)
            return pixmapList.get(0).getWidth();
        else return -1;
    }

    public int getHeight(){
        if(pixmapList.size()>0)
            return pixmapList.get(0).getHeight();
        else return -1;
    }

    public int getId(){
        return id;
    }

}
