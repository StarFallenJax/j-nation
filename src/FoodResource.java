import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class FoodResource {

    private Image defaultState;
    private AffineTransform tx;

    //Attributes of a FoodResource object
    int dir = 0;                                        //0 - forward, 1 - backward, 2 - left, 3 - right
    int width, height;                                  //Used for hitbox, AKA collision detection code
    int x, y;                                           //Position of the object
    int vx, vy;                                         //Movement variables
    double scaleWidth = 0.05;                           //Change to scale image
    double scaleHeight = 0.05;                          //Change to scale image


    public FoodResource(){

        defaultState = getImage("/imgs/"+"image.png");  //Load the image

        width = 40;                                     //hitbox
        height = 50;                                    //hitbox

        //top left location of image
        x = Frame.width/2 - width/2;
        y = Frame.height - height*2;
        vx = 0;
        vy = 0;

        tx = AffineTransform.getTranslateInstance(0,0);

        init(x, y);        //initialize the location of the image
                           //use your variables




    }

    public void paint(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        x+=vx;
        y+=vy;

        init(x,y);


        switch(dir){
            case 0:
                g2.drawImage(defaultState,tx,null);
                break;
            case 1:
                g2.drawImage(defaultState,tx,null);
                break;
        }

        if(x > Frame.width){

            x = -width;

        }



    }

    public FoodResource(int x, int y){
        //Set this object's attributes
        this(); //Invokes the default constructor

        //this.x specifies the x attribute of this class, NOT param of this constructor
        this.x = x;
        this.y = y;


    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setDir(int dir){
        this.dir = dir;
    }
    public void setPost(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setVx(int vx){
        this.vx = vx;
    }





    public void init(double a, double b){
        tx.setToTranslation(a,b);
        tx.scale(scaleWidth,scaleHeight);
    }

    private Image getImage(String path){
        Image tempImage = null;
        try {
            URL imageURL = FoodResource.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }
}
