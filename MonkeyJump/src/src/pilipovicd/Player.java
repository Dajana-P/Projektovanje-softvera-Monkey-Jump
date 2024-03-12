package src.pilipovicd;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {

	int xCoordinate;
	int yCoordinate;
	int Width=0;
	int Height=0;
	BufferedImage slika;
	int animProgress;
	Color[][] matricaBoja;
	
	
    
	boolean gledaDesno;
	boolean daLiSeKrece;
	boolean mozeDaSkace;
	
	int brzina;
	int visinaSkoka;
	int jacinaSkoka;
	int kretanje;
	
	public Player(String nazivSlike,int x,int y,int Brzina) throws IOException
	{
		UcitajSlikuKaraktera(nazivSlike);
		getHeight();
		getWidth();
		matricaBoja=GameMethods.UcitajMatricuBoja(slika);
		xCoordinate=x;
		yCoordinate=y; 
		gledaDesno=true;
		daLiSeKrece=false;
		this.brzina=Brzina;
		kretanje=0;
		getHeight();
		getWidth();
		mozeDaSkace=false;
		jacinaSkoka=0;
		visinaSkoka=60;
	}
	public void getHeight()
	{
		Height=slika.getHeight();
	}
	public void getWidth()
	{
		Width=slika.getWidth();
	}
	
	
	public void UcitajSlikuKaraktera(String nazivSlike) throws IOException
	{
		File pathToFile = new File("C:\\Users\\PD\\Desktop\\Projektovanje softvera\\Slike\\"+ nazivSlike +".png");
	    slika = ImageIO.read(pathToFile);
	    
	}
	
	public void OkreniKaraktera()
	{
		if(kretanje>0)
		{
			if(gledaDesno==false)
			{
				GameMethods.RotirajIgraca(this);
				gledaDesno=true;
			}
			
		}
		else if(kretanje<0)
		{
			if(gledaDesno==true)
			{
				GameMethods.RotirajIgraca(this);
				gledaDesno=false;
			}
		}
	}
}