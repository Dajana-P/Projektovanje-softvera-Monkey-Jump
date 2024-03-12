package src.pilipovicd;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Banana {

    
    
    public int xCoordinate;
    public int yCoordinate;
    public BufferedImage slika;
    public boolean Pokupljena;
    public int Width;
    public int Height;
    
    public Banana(int x,int y) throws IOException
    {
        xCoordinate=x;
        yCoordinate=y;
        UcitajSlikuBanane();
        Width=slika.getWidth();
        Height=slika.getHeight();
        Pokupljena=false;
        
    }
    
    public void UcitajSlikuBanane() throws IOException {
        File pathToFile = new File("C:\\Users\\PD\\Desktop\\Projektovanje softvera\\Slike\\Banana.png"); //Promeniti putanju
        slika = ImageIO.read(pathToFile);    
    }
}