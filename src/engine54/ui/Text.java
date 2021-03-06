package engine54.ui;

import engine54.graphics.SpriteBatch;
import engine54.graphics.TrueTypeFont;
import engine54.io.Keyboard;
import engine54.io.Mouse;
import engine54.io.Window;
import engine54.util.Assets;
import engine54.util.Color;

public class Text extends LayoutElement{

    private String text;
    private TrueTypeFont font;
    private final Color color;

    public Text(double width,double height,String fontID,String text){
        super(width,height);
        setText(text);
        setFont(fontID);
        color=new Color();
    }

    public void setColor(Color color){
        this.color.set(color);
    }
    public void setColor(float r,float g,float b,float a){
        this.color.set(r,g,b,a);
    }
    public Color getColor(){
        return color;
    }

    public void setText(String text){
        this.text=text;
    }
    public String getText(){
        return text;
    }

    public void setFont(String fontID){
        this.font=Assets.getTTF(fontID);
    }
    public TrueTypeFont getFont(){
        return font;
    }

    public void render(SpriteBatch batch){
        int rw=getRenderWidth();
        int rh=getRenderHeight();
        float tw=font.getTextWidth(text);
        float th=font.getTextHeight(text);
        Color currentColor=batch.getColor().clone();
        batch.setColor(color);
        batch.drawText(font,text,
                Math.round((rw-tw)/2+getRenderX()),
                Math.round((rh+th)/2+getRenderY()-font.getCScale()*font.getDescent())
        );
        batch.setColor(currentColor);
    }

    public void update(Mouse mouse,Keyboard keyboard,Window window){
        updateRenderValues(window);
        updateCallbacks(mouse,window);
    }

    public String getType(){
        return "Text";
    }

}