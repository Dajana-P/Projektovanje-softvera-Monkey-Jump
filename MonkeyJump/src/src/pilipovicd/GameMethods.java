package src.pilipovicd;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JLabel;

public class GameMethods {

	
		public static Color[][] UcitajMatricuBoja(BufferedImage slika) throws IOException {
		    int visina = slika.getHeight();
	        int  sirina = slika.getWidth();
	        Color[][] colors = new Color[visina][sirina];
	        for (int x = 0; x < visina; x++) {
	            for (int y = 0; y < sirina; y++) {
	                colors[x][y] = new Color(slika.getRGB(y, x), true);
	            }
	        }

	        return colors;
	    }
	
		public static void RotirajIgraca(Player igrac) 
		{
				for (int i = 0; i < igrac.Height; i++) 
				{
			        for (int k = igrac.Width-1, j=0; k>=igrac.Width/2; k--,j++) 
			        {
			        	Color boja;
			        	boja=igrac.matricaBoja[i][j];
			        	igrac.matricaBoja[i][j]=igrac.matricaBoja[i][k];
			        	igrac.matricaBoja[i][k]=boja;
			        }
			    }
				for(int i=0;i<igrac.Height;i++)
				{
					for(int j=0;j<igrac.Width;j++)
					{
						Color boja;
			        	boja=igrac.matricaBoja[i][j];
			        	igrac.slika.setRGB(j, i, boja.getRGB());
					}
				}
			
		}
}