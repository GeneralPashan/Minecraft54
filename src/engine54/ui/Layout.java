package engine54.ui;

import engine54.graphics.SpriteBatch;
import engine54.io.Keyboard;
import engine54.io.Mouse;
import engine54.io.Window;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Layout extends LayoutElement{


    public HashMap<String,LayoutElement> elements;
    public List<String> ids=new ArrayList<>();


    public Layout(){
        super(1,1);
        elements=new HashMap<>();
    }

    public Layout(double width,double height){
        super(width,height);
        elements=new HashMap<>();
    }


    public void addElement(String id,LayoutElement element){
        element.setParent(this);
        elements.put(id,element);
        ids.add(id);
    }

    public void removeElement(String id){
        elements.remove(id);
        ids.remove(id);
    }

    public LayoutElement getElement(String id){
        return elements.get(id);
    }


    public void render(SpriteBatch batch){
        for(String k:ids){
            LayoutElement e=elements.get(k);
            if(e!=null && !e.isHidden())
                e.render(batch);
        }
    }

    public void update(Mouse mouse,Keyboard keyboard,Window window){
        updateWindowGravity();
        for(String k:ids){
            LayoutElement e=elements.get(k);
            if(e!=null)
                e.update(mouse,keyboard,window);
        }
    }


    public String getType(){
        return "Layout";
    }


    public void load(String guiFile){
        try{
            JSONArray jsonElements=new JSONArray(Files.readString(new File(guiFile).toPath()));
            for(int i=0; i<jsonElements.length(); i++){
                JSONObject jsonElement=jsonElements.getJSONObject(i);

                JSONArray jsonSize=jsonElement.getJSONArray("size");
                String id=jsonElement.getString("id");

                switch(jsonElement.getString("type")){
                    case "Image"->{
                        JSONArray jsonPos=jsonElement.getJSONArray("pos");
                        Image text=new Image(jsonSize.getDouble(0),jsonSize.getDouble(1),jsonElement.getString("textureID"));
                        text.setPos(jsonPos.getDouble(0),jsonPos.getDouble(1));
                        text.setGravity(Gravity.parseGravity(jsonElement.getString("gravity")));
                        addElement(id,text);
                    }
                    case "Button"->{
                        JSONArray jsonPos=jsonElement.getJSONArray("pos");

                        Button button=new Button(jsonSize.getDouble(0),jsonSize.getDouble(1),jsonElement.getString("texture1ID"),jsonElement.getString("texture2ID"),jsonElement.getString("texture3ID"));
                        button.setPos(jsonPos.getDouble(0),jsonPos.getDouble(1));
                        button.setGravity(Gravity.parseGravity(jsonElement.getString("gravity")));
                        addElement(id,button);
                    }
                    case "Text"->{
                        JSONArray jsonPos=jsonElement.getJSONArray("pos");
                        JSONArray jsonColor=jsonElement.getJSONArray("color");

                        Text text=new Text(jsonSize.getDouble(0),jsonSize.getDouble(1),jsonElement.getString("fontID"),jsonElement.getString("text"));
                        text.setPos(jsonPos.getDouble(0),jsonPos.getDouble(1));
                        text.setGravity(Gravity.parseGravity(jsonElement.getString("gravity")));
                        text.setColor((float)jsonColor.getDouble(0),(float)jsonColor.getDouble(1),(float)jsonColor.getDouble(2),(float)jsonColor.getDouble(3));
                        addElement(id,text);
                    }
                    case "TextField"->{
                        JSONArray jsonPos=jsonElement.getJSONArray("pos");
                        JSONArray jsonColor=jsonElement.getJSONArray("color");

                        TextField textField=new TextField(jsonSize.getDouble(0),jsonSize.getDouble(1),jsonElement.getString("fontID"),jsonElement.getString("text"),jsonElement.getInt("maxSymbols"));
                        textField.setPos(jsonPos.getDouble(0),jsonPos.getDouble(1));
                        textField.setGravity(Gravity.parseGravity(jsonElement.getString("gravity")));
                        textField.setColor((float)jsonColor.getDouble(0),(float)jsonColor.getDouble(1),(float)jsonColor.getDouble(2),(float)jsonColor.getDouble(3));
                        addElement(id,textField);
                    }
                    case "ElementList"->{
                        JSONArray jsonPos=jsonElement.getJSONArray("pos");

                        ElementList textField=new ElementList(jsonSize.getDouble(0),jsonSize.getDouble(1),jsonElement.getString("textureID"));
                        textField.setPos(jsonPos.getDouble(0),jsonPos.getDouble(1));
                        textField.setGravity(Gravity.parseGravity(jsonElement.getString("gravity")));
                        addElement(id,textField);
                    }
                    case "Slider"->{
                        JSONArray jsonPos=jsonElement.getJSONArray("pos");
                        JSONArray jsonSliderSize=jsonElement.getJSONArray("sliderSize");

                        Slider button=new Slider(jsonSize.getDouble(0),jsonSize.getDouble(1),jsonSliderSize.getDouble(0),jsonSliderSize.getDouble(1),jsonElement.getString("texture1ID"),jsonElement.getString("texture2ID"),jsonElement.getString("texture3ID"));
                        button.setPos(jsonPos.getDouble(0),jsonPos.getDouble(1));
                        button.setGravity(Gravity.parseGravity(jsonElement.getString("gravity")));
                        addElement(id,button);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
