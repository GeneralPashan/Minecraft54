package minecraft54.main;

import engine54.E54;
import engine54.app.AppListener;
import engine54.graphics.ShaderProgram;
import engine54.graphics.TrueTypeFont;
import engine54.graphics.texture.Pixmap;
import engine54.graphics.texture.Texture;
import engine54.graphics.texture.Texture3D;
import engine54.particles.ParticleBehavior;
import engine54.util.ArrayUtils;
import engine54.util.Assets;
import engine54.util.Utils;
import minecraft54.main.client.audio.SoundPack;
import minecraft54.main.client.screens.*;
import minecraft54.main.client.world.BlockData;
import minecraft54.main.client.world.BlockManager;
import minecraft54.main.client.world.Block;
import minecraft54.main.util.Direction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Minecraft54 implements AppListener{


    public static String GAME_FOLDER=".minecraft54";
    public static String HOME_PATH=System.getProperty("user.home");
    public static String OPTIONS_PATH=HOME_PATH+"/"+GAME_FOLDER+"/options.json";


    //public static Server server;
    //public static TCPClient client;

    public static List<int[]> setBlockStack=new ArrayList<>();

    public static Block AIR,STONE,GRASS_BLOCK,DIRT,COBBLESTONE,PLANKS,BEDROCK,WATER,WATER_STILL,SAND,LOG,LEAVES,GLASS,GRASS,FLOWER;

    public static ParticleBehavior particleBehavior1;


    @Override
    public void create(){
        { // Create particle behavior

            particleBehavior1=current->{
                current.getPosition().add(current.getVelocity().get()).y+=current.gravityVelocity;
                current.getVelocity().reduce(0.005);
                current.gravityVelocity-=0.005;
            };

        }

        { // Create Files
            try{

                File saves=new File(HOME_PATH+"/"+GAME_FOLDER+"/saves");
                if(!saves.exists())
                    saves.mkdirs();

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        { // Load Textures
            Assets.loadTexture(new Texture(new Pixmap(1,1).setPixel(0,0,1,1,1,1)),"white");
            Assets.loadTexture("textures/ui/crosshair.png","crosshair");

            Assets.loadTexture("textures/ui/button1.png","button1_n");
            Assets.loadTexture("textures/ui/button1_selected.png","button1_a");
            Assets.loadTexture("textures/ui/button1_pressed.png","button1_p");

            Assets.loadTexture("textures/ui/slider1.png","slider1_n");
            Assets.loadTexture("textures/ui/slider1_selected.png","slider1_a");
            Assets.loadTexture("textures/ui/slider1_pressed.png","slider1_p");

            Assets.loadTexture("textures/ui/background.png","background");

            Assets.loadTexture("textures/block/dirt.png","dirt");

            String blocks="textures/block/";
            Assets.loadTexture3D(new Texture3D(16,16,false,
                    blocks+"grass_block_side.png", // 0
                    blocks+"grass_block_top.png",
                    blocks+"dirt.png",
                    blocks+"stone.png",
                    blocks+"sand.png",
                    blocks+"oak_log.png",
                    blocks+"oak_log_top.png",
                    blocks+"oak_leaves.png",
                    blocks+"birch_log.png",
                    blocks+"birch_log_top.png",
                    blocks+"glass.png",            // 10
                    blocks+"grass.png",
                    blocks+"cobblestone.png",
                    blocks+"water_overlay.png",
                    blocks+"oak_planks.png",
                    blocks+"grass_block_side_overlay.png",
                    blocks+"poppy.png",
                    blocks+"dandelion.png",
                    blocks+"bedrock.png"/*,
                    blocks+".png"/*,
                    blocks+".png"/*,                // 20
                    blocks+".png"/*,
                    blocks+".png"/*,
                    blocks+".png"/*,
                    blocks+".png"/*,
                    blocks+".png"*/
            ),"blocks");
        }

        { // Load Sounds
            // step
            Assets.loadSound("sounds/step/gravel1.ogg","step_gravel1");
            Assets.loadSound("sounds/step/gravel2.ogg","step_gravel2");
            Assets.loadSound("sounds/step/gravel3.ogg","step_gravel3");
            Assets.loadSound("sounds/step/gravel4.ogg","step_gravel4");

            Assets.loadSound("sounds/step/grass1.ogg","step_grass1");
            Assets.loadSound("sounds/step/grass2.ogg","step_grass2");
            Assets.loadSound("sounds/step/grass3.ogg","step_grass3");
            Assets.loadSound("sounds/step/grass4.ogg","step_grass4");
            Assets.loadSound("sounds/step/grass5.ogg","step_grass5");
            Assets.loadSound("sounds/step/grass6.ogg","step_grass6");

            Assets.loadSound("sounds/step/stone1.ogg","step_stone1");
            Assets.loadSound("sounds/step/stone2.ogg","step_stone2");
            Assets.loadSound("sounds/step/stone3.ogg","step_stone3");
            Assets.loadSound("sounds/step/stone4.ogg","step_stone4");
            Assets.loadSound("sounds/step/stone5.ogg","step_stone5");
            Assets.loadSound("sounds/step/stone6.ogg","step_stone6");

            Assets.loadSound("sounds/step/sand1.ogg","step_sand1");
            Assets.loadSound("sounds/step/sand2.ogg","step_sand2");
            Assets.loadSound("sounds/step/sand3.ogg","step_sand3");
            Assets.loadSound("sounds/step/sand4.ogg","step_sand4");
            Assets.loadSound("sounds/step/sand5.ogg","step_sand5");

            Assets.loadSound("sounds/step/wood1.ogg","step_wood1");
            Assets.loadSound("sounds/step/wood2.ogg","step_wood2");
            Assets.loadSound("sounds/step/wood3.ogg","step_wood3");
            Assets.loadSound("sounds/step/wood4.ogg","step_wood4");
            Assets.loadSound("sounds/step/wood5.ogg","step_wood5");
            Assets.loadSound("sounds/step/wood6.ogg","step_wood6");

            // dig
            Assets.loadSound("sounds/dig/gravel1.ogg","dig_gravel1");
            Assets.loadSound("sounds/dig/gravel2.ogg","dig_gravel2");
            Assets.loadSound("sounds/dig/gravel3.ogg","dig_gravel3");
            Assets.loadSound("sounds/dig/gravel4.ogg","dig_gravel4");

            Assets.loadSound("sounds/dig/grass1.ogg","dig_grass1");
            Assets.loadSound("sounds/dig/grass2.ogg","dig_grass2");
            Assets.loadSound("sounds/dig/grass3.ogg","dig_grass3");
            Assets.loadSound("sounds/dig/grass4.ogg","dig_grass4");

            Assets.loadSound("sounds/dig/stone1.ogg","dig_stone1");
            Assets.loadSound("sounds/dig/stone2.ogg","dig_stone2");
            Assets.loadSound("sounds/dig/stone3.ogg","dig_stone3");
            Assets.loadSound("sounds/dig/stone4.ogg","dig_stone4");

            Assets.loadSound("sounds/dig/sand1.ogg","dig_sand1");
            Assets.loadSound("sounds/dig/sand2.ogg","dig_sand2");
            Assets.loadSound("sounds/dig/sand3.ogg","dig_sand3");
            Assets.loadSound("sounds/dig/sand4.ogg","dig_sand4");

            Assets.loadSound("sounds/dig/wood1.ogg","dig_wood1");
            Assets.loadSound("sounds/dig/wood2.ogg","dig_wood2");
            Assets.loadSound("sounds/dig/wood3.ogg","dig_wood3");
            Assets.loadSound("sounds/dig/wood4.ogg","dig_wood4");

            // random
            Assets.loadSound("sounds/random/glass1.ogg","random_glass1");
            Assets.loadSound("sounds/random/glass2.ogg","random_glass2");
            Assets.loadSound("sounds/random/glass3.ogg","random_glass3");

            Assets.loadSound("sounds/random/click.ogg","random_click");

            // damage
            Assets.loadSound("sounds/damage/fallbig.ogg","damage_fallbig");
            Assets.loadSound("sounds/damage/fallsmall.ogg","damage_fallsmall");
        }

        { // Load TTFs
            int size=64;

            TrueTypeFont ttf1=new TrueTypeFont("fonts/andy_bold.ttf",size);
            Assets.loadTTF(ttf1,"font1");
            TrueTypeFont ttf2=new TrueTypeFont("fonts/digit.ttf",size);
            Assets.loadTTF(ttf2,"font2");
            TrueTypeFont ttf3=new TrueTypeFont("fonts/minecraft.ttf",25);
            Assets.loadTTF(ttf3,"font3");
            TrueTypeFont ttf4=new TrueTypeFont("fonts/trashco.ttf",32);
            Assets.loadTTF(ttf4,"font4");
            TrueTypeFont ttf5=new TrueTypeFont("fonts/unreal_tournament.ttf",32);
            Assets.loadTTF(ttf5,"font5");
            TrueTypeFont ttf6=new TrueTypeFont("fonts/minecraft.ttf",16);
            Assets.loadTTF(ttf6,"font6");
        }

        { // Load Shaders
            ShaderProgram chunkShader=new ShaderProgram(Utils.readFile("shaders/Chunk.v"),Utils.readFile("shaders/Chunk.f"),Utils.readFile("shaders/Chunk.g"));
            chunkShader.addUniforms("u_texture","u_world","u_proj","u_model","underWater","u_camPos");
            Assets.loadShader(chunkShader,"chunk");
        }

        { // Configure Screens
            E54.context().defineScreen(new MenuScreen()       ,"menu"       );
            E54.context().defineScreen(new WorldListScreen()  ,"world list" );
            E54.context().defineScreen(new GameScreen()       ,"game"       );
            E54.context().defineScreen(new ServerListScreen() ,"serverList" );
            E54.context().defineScreen(new WorldCreateScreen(),"worldCreate");
            E54.context().defineScreen(new SettingsScreen()   ,"settings"   );

            E54.context().setScreen("menu");
        }

        { // Load Settings Options
            Options.load();
        }

        { // Defining Blocks
            short id=0;
            BlockData blockData; // AIR
            SoundPack soundPack;

            //0.2 0.7 0.1 ; 0.4 0.6 0.3 ; 0.4 0.7 0.2
            float grassBlockR=0.45f,grassBlockG=0.75f,grassBlockB=0.35f;
            float grassR=0.45f,grassG=0.75f,grassB=0.35f;
            float leavesR=0.3f,leavesG=0.7f,leavesB=0.15f;
            float waterR=0.1f,waterG=0.4f,waterB=0.8f;


            AIR=new Block(id);
            AIR.addData(0,new BlockData(0,0,false,true,true));


            id++;
            STONE=new Block(id);
            blockData=BlockManager.blockDataSolid(id,0,3);
            soundPack=new SoundPack();
            soundPack.addPlace(0.75f,"dig_stone1","dig_stone2","dig_stone3","dig_stone4");
            soundPack.addDestroy(0.75f,"dig_stone1","dig_stone2","dig_stone3","dig_stone4");
            soundPack.addStep("step_stone1","step_stone2","step_stone3","step_stone4","step_stone5","step_stone6");
            blockData.setSounds(soundPack);
            STONE.addData(0,blockData); // STONE


            id++;
            GRASS_BLOCK=new Block(id);
            blockData=new BlockData(id,0,true,false,false);
            blockData.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,0,ArrayUtils.add(BlockManager.quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),BlockManager.quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0)),new float[]{0,15},ArrayUtils.add(BlockManager.quad_uvs(),BlockManager.quad_uvs()),new float[]{1,1,1,1, grassBlockR,grassBlockG,grassBlockB,1});
            blockData.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,0,ArrayUtils.add(BlockManager.quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),BlockManager.quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1)),new float[]{0,15},ArrayUtils.add(BlockManager.quad_uvs(),BlockManager.quad_uvs()),new float[]{1,1,1,1, grassBlockR,grassBlockG,grassBlockB,1});
            blockData.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,0,ArrayUtils.add(BlockManager.quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),BlockManager.quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1)),new float[]{0,15},ArrayUtils.add(BlockManager.quad_uvs(),BlockManager.quad_uvs()),new float[]{1,1,1,1, grassBlockR,grassBlockG,grassBlockB,1});
            blockData.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,0,ArrayUtils.add(BlockManager.quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),BlockManager.quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0)),new float[]{0,15},ArrayUtils.add(BlockManager.quad_uvs(),BlockManager.quad_uvs()),new float[]{1,1,1,1, grassBlockR,grassBlockG,grassBlockB,1});
            blockData.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,0,          BlockManager.quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),                                                     new float[]{1},             BlockManager.quad_uvs(),                         new float[]{         grassBlockR,grassBlockG,grassBlockB,1});
            blockData.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,0,          BlockManager.quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),                                                     new float[]{2},             BlockManager.quad_uvs(),                         new float[]{1,1,1,1});
            soundPack=new SoundPack();
            soundPack.addPlace(0.75f,"dig_grass1","dig_grass2","dig_grass3","dig_grass4");
            soundPack.addDestroy(0.75f,"dig_grass1","dig_grass2","dig_grass3","dig_grass4");
            soundPack.addStep("step_grass1","step_grass2","step_grass3","step_grass4","step_grass5","step_grass6");
            blockData.setSounds(soundPack);
            GRASS_BLOCK.addData(0,blockData); // GRASS BLOCK


            id++;
            DIRT=new Block(id);
            blockData=BlockManager.blockDataSolid(id,0,2);
            soundPack=new SoundPack();
            soundPack.addPlace(0.75f,"dig_gravel1","dig_gravel2","dig_gravel3","dig_gravel4");
            soundPack.addDestroy(0.75f,"dig_gravel1","dig_gravel2","dig_gravel3","dig_gravel4");
            soundPack.addStep("step_gravel1","step_gravel2","step_gravel3","step_gravel4");
            blockData.setSounds(soundPack);
            DIRT.addData(0,blockData); // DIRT


            id++;
            COBBLESTONE=new Block(id);
            blockData=BlockManager.blockDataSolid(id,0,12);
            soundPack=new SoundPack();
            soundPack.addPlace(0.75f,"dig_stone1","dig_stone2","dig_stone3","dig_stone4");
            soundPack.addDestroy(0.75f,"dig_stone1","dig_stone2","dig_stone3","dig_stone4");
            soundPack.addStep("step_stone1","step_stone2","step_stone3","step_stone4","step_stone5","step_stone6");
            blockData.setSounds(soundPack);
            COBBLESTONE.addData(0,blockData); // COBBLESTONE


            id++;
            PLANKS=new Block(id);
            blockData=BlockManager.blockDataSolid(id,0,14);
            soundPack=new SoundPack();
            soundPack.addPlace("dig_wood1","dig_wood2","dig_wood3","dig_wood4");
            soundPack.addDestroy(0.75f,"dig_wood1","dig_wood2","dig_wood3","dig_wood4");
            soundPack.addStep("step_wood1","step_wood2","step_wood3","step_wood4","step_wood5","step_wood6");
            blockData.setSounds(soundPack);
            PLANKS.addData(0,blockData); // OAK PLANKS


            id++;
            BEDROCK=new Block(id);
            blockData=BlockManager.blockDataSolid(id,0,18);
            soundPack=new SoundPack();
            soundPack.addPlace(0.75f,"dig_stone1","dig_stone2","dig_stone3","dig_stone4");
            soundPack.addDestroy(0.75f,"dig_stone1","dig_stone2","dig_stone3","dig_stone4");
            soundPack.addStep("step_stone1","step_stone2","step_stone3","step_stone4","step_stone5","step_stone6");
            blockData.setSounds(soundPack);
            BEDROCK.addData(0,blockData); // OAK PLANKS


            id++;
            WATER=new Block(id);
            blockData=new BlockData(id,0,true,false,true);
            blockData.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,3,BlockManager.quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id+1));
            blockData.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,3,BlockManager.quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id+1));
            blockData.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,3,BlockManager.quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id+1));
            blockData.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,3,BlockManager.quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id+1));
            blockData.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,2,BlockManager.quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id+1));
            blockData.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,2,BlockManager.quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id+1));
            WATER.addData(0,blockData); // WATER


            id++;
            WATER_STILL=new Block(id);
            blockData=new BlockData(id,0,false,false,true);
            blockData.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,3,BlockManager.quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id-1));
            blockData.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,3,BlockManager.quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id-1));
            blockData.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,3,BlockManager.quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id-1));
            blockData.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,3,BlockManager.quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id-1));
            blockData.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,2,BlockManager.quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id-1));
            blockData.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,2,BlockManager.quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{13},BlockManager.quad_uvs(),new float[]{waterR,waterG,waterB,1}).addInterruptNeighborBlocksIds(id,(short)(id-1));
            WATER_STILL.addData(0,blockData); // WATER STILL


            id++;
            SAND=new Block(id);
            blockData=BlockManager.blockDataSolid(id,0,4);
            soundPack=new SoundPack();
            soundPack.addPlace(0.8f,"dig_sand1","dig_sand2","dig_sand3","dig_sand4");
            soundPack.addDestroy(0.8f,"dig_sand1","dig_sand2","dig_sand3","dig_sand4");
            soundPack.addStep("step_sand1","step_sand2","step_sand3","step_sand4","step_sand5");
            blockData.setSounds(soundPack);
            SAND.addData(0,blockData); // SAND


            id++;
            LOG=new Block(id);
            blockData=BlockManager.blockDataSolid(id,0,5,6);
            soundPack=new SoundPack();
            soundPack.addPlace(0.75f,"dig_wood1","dig_wood2","dig_wood3","dig_wood4");
            soundPack.addDestroy(0.75f,"dig_wood1","dig_wood2","dig_wood3","dig_wood4");
            soundPack.addStep("step_wood1","step_wood2","step_wood3","step_wood4","step_wood5","step_wood6");
            blockData.setSounds(soundPack);
            LOG.addData(0,blockData); //   OAK LOG
            blockData=BlockManager.blockDataSolid(id,1,8,9);
            soundPack=new SoundPack();
            soundPack.addPlace(0.75f,"dig_wood1","dig_wood2","dig_wood3","dig_wood4");
            soundPack.addDestroy(0.75f,"dig_wood1","dig_wood2","dig_wood3","dig_wood4");
            soundPack.addStep("step_wood1","step_wood2","step_wood3","step_wood4","step_wood5","step_wood6");
            blockData.setSounds(soundPack);
            LOG.addData(1,blockData); // BIRCH LOG


            id++;
            LEAVES=new Block(id);
            blockData=new BlockData(id,0,false,false,true);
            blockData.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,0,BlockManager.quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{7},BlockManager.quad_uvs(),new float[]{leavesR, leavesG, leavesB,1});
            blockData.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,0,BlockManager.quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{7},BlockManager.quad_uvs(),new float[]{leavesR, leavesG, leavesB,1});
            blockData.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,0,BlockManager.quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{7},BlockManager.quad_uvs(),new float[]{leavesR, leavesG, leavesB,1});
            blockData.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,0,BlockManager.quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{7},BlockManager.quad_uvs(),new float[]{leavesR, leavesG, leavesB,1});
            blockData.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,0,BlockManager.quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{7},BlockManager.quad_uvs(),new float[]{leavesR, leavesG, leavesB,1});
            blockData.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,0,BlockManager.quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{7},BlockManager.quad_uvs(),new float[]{leavesR, leavesG, leavesB,1});
            soundPack=new SoundPack();
            soundPack.addPlace(0.75f,"dig_grass1","dig_grass2","dig_grass3","dig_grass4");
            soundPack.addDestroy(0.75f,"dig_grass1","dig_grass2","dig_grass3","dig_grass4");
            soundPack.addStep("step_grass1","step_grass2","step_grass3","step_grass4","step_grass5","step_grass6");
            blockData.setSounds(soundPack);
            LEAVES.addData(0,blockData); // OAK LEAVES


            id++;
            GLASS=new Block(id);
            blockData=new BlockData(id,0,true,true,false);
            blockData.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,0,BlockManager.quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{10},BlockManager.quad_uvs(),new float[]{1,1,1,1}).addInterruptNeighborBlocksIds(id);
            blockData.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,0,BlockManager.quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{10},BlockManager.quad_uvs(),new float[]{1,1,1,1}).addInterruptNeighborBlocksIds(id);
            blockData.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,0,BlockManager.quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{10},BlockManager.quad_uvs(),new float[]{1,1,1,1}).addInterruptNeighborBlocksIds(id);
            blockData.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,0,BlockManager.quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{10},BlockManager.quad_uvs(),new float[]{1,1,1,1}).addInterruptNeighborBlocksIds(id);
            blockData.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,0,BlockManager.quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{10},BlockManager.quad_uvs(),new float[]{1,1,1,1}).addInterruptNeighborBlocksIds(id);
            blockData.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,0,BlockManager.quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{10},BlockManager.quad_uvs(),new float[]{1,1,1,1}).addInterruptNeighborBlocksIds(id);
            soundPack=new SoundPack();
            soundPack.addPlace(0.8f,"dig_stone1","dig_stone2","dig_stone3","dig_stone4");
            soundPack.addDestroy(0.8f,"random_glass1","random_glass2","random_glass3");
            blockData.setSounds(soundPack);
            GLASS.addData(0,blockData); // GLASS


            id++;
            GRASS=new Block(id);
            blockData=new BlockData(id,0,false,false,false);
            blockData.sides[6]=new Block.BlockSide(Direction.UNKNOWN,id,1,
                    ArrayUtils.add( BlockManager.quad_vertices(1,1,1,1,0,1,0,0,0,0,1,0), BlockManager.quad_vertices(0,1,1,0,0,1,1,0,0,1,1,0) ),
                    new float[]{11,11},
                    ArrayUtils.add( BlockManager.quad_uvs(), BlockManager.quad_uvs() ),
            new float[]{grassR,grassG,grassB,1, grassR,grassG,grassB,1});
            soundPack=new SoundPack();
            soundPack.addPlace(0.75f,"dig_grass1","dig_grass2","dig_grass3","dig_grass4");
            soundPack.addDestroy(0.75f,"dig_grass1","dig_grass2","dig_grass3","dig_grass4");
            soundPack.addStep("step_grass1","step_grass2","step_grass3","step_grass4","step_grass5","step_grass6");
            blockData.setSounds(soundPack);
            GRASS.addData(0,blockData); // GRASS


            id++;
            FLOWER=new Block(id);
            blockData=new BlockData(id,0,false,false,false);
            blockData.sides[6]=new Block.BlockSide(Direction.UNKNOWN,id,1,
                    ArrayUtils.add( BlockManager.quad_vertices(1,1,1,1,0,1,0,0,0,0,1,0), BlockManager.quad_vertices(0,1,1,0,0,1,1,0,0,1,1,0) ),
                    new float[]{16,16},
                    ArrayUtils.add( BlockManager.quad_uvs(), BlockManager.quad_uvs() ),
                    new float[]{1,1,1,1, 1,1,1,1});
            soundPack=new SoundPack();
            soundPack.addPlace(0.75f,"dig_grass1","dig_grass2","dig_grass3","dig_grass4");
            soundPack.addDestroy(0.75f,"dig_grass1","dig_grass2","dig_grass3","dig_grass4");
            blockData.setSounds(soundPack);
            FLOWER.addData(0,blockData); // POPPY
            blockData=new BlockData(id,1,false,false,false);
            blockData.sides[6]=new Block.BlockSide(Direction.UNKNOWN,id,1,
                    ArrayUtils.add( BlockManager.quad_vertices(1,1,1,1,0,1,0,0,0,0,1,0), BlockManager.quad_vertices(0,1,1,0,0,1,1,0,0,1,1,0) ),
                    new float[]{17,17},
                    ArrayUtils.add( BlockManager.quad_uvs(), BlockManager.quad_uvs() ),
                    new float[]{1,1,1,1, 1,1,1,1});
            soundPack=new SoundPack();
            soundPack.addPlace(0.75f,"dig_grass1","dig_grass2","dig_grass3","dig_grass4");
            soundPack.addDestroy(0.75f,"dig_grass1","dig_grass2","dig_grass3","dig_grass4");
            blockData.setSounds(soundPack);
            FLOWER.addData(1,blockData); // DANDELION


            BlockManager.addBlocks(AIR,STONE,GRASS_BLOCK,DIRT,COBBLESTONE,PLANKS,BEDROCK,WATER,WATER_STILL,SAND,LOG,LEAVES,GLASS,GRASS,FLOWER);
        }

        /*
        { // Server init
            server=new Server();
            server.setMaxPlayers(54);
            server.setMotd("A Simple Minecraft54 Server");
            server.open();
        }

        { // Client init
            client=new TCPClient();
            client.addListener(new TCPClientListener(){
                public void receive(TCPClient thisClient,Packet packet){
                    switch(packet.getClass().getSimpleName()){
                        case "PacketStatusOutPong" -> {
                            PacketStatusOutPong p=(PacketStatusOutPong)packet;
                            ((Text)((ServerListScreen)Main.cfg.getScreen("serverList")).layout.getElement("serverInfo")).setText(p.m+" ("+p.a+"/"+p.b+")");
                        }
                    }
                }
                public void connected(TCPClient thisClient){
                    //System.out.println("connected.");
                }
                public void disconnected(TCPClient thisClient,String reason){

                }
                public void error(TCPClient thisClient,Exception e){
                    e.printStackTrace();
                }
            });
            client.connect("localhost",server.getPort());
        }
        */
    }


    @Override
    public void update(){
        if(E54.keyboard().isKeyReleased(GLFW_KEY_V))
            E54.window().setVSync(!E54.window().isVSync());
        //if(Main.keyboard.isKeyReleased(GLFW_KEY_L))
        //    client.disconnect("leave");
    }


    @Override
    public void dispose(){
        Assets.dispose();
        GameScreen.stopThreads();
        //client.disconnect("exit");
        //server.close();
    }


}
