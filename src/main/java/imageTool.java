import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

 class imagetool {
    private imagetool(){};
    public static Image getImage(String path)
    {
        BufferedImage bi=null;
        try{
            URL u=imagetool.class.getClassLoader().getResource(path);
            bi=ImageIO.read(u);
        }catch(IOException e){
            e.printStackTrace();
        }
        return bi;
    }

}