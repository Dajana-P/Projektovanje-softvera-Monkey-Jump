package src.pilipovicd;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;

public class GameWindowFrame extends JFrame implements KeyListener {

	GlavniMeni glavniMeni;
	String ime;
	String karakter;
	
	int SekundeOdPocetka=0;
	int MiliSekunde=0;
	JLabel lblTime;
	
	//Prozor
	private JPanel contentPane;
	BufferedImage img;
	BufferedImage imgObjekti;
	BufferedImage pozadina;
	JLabel lbl;
	int SirinaPozadine=1280;
	int VisinaPozadine=720;
	int[][] matricaIgre = new int[720][1280];
	
	//Banane
	Banana[] banane;
	int brojBanana=0;
	JLabel[] lbBanane;
	int BananaRadius=12;
	int brojPokupljenihBanana=0;
	
	//Vrednosti za matricu igre
	int Platforma=3;
	int Banana=2;
	int Igrac=1;
	int Nista=0;
	
	//Gravitacija
	int BaseGravitacija=3;
	int Gravitacija=3;
	
	//Igrac
	Player igrac;
	JLabel lbSlikaIgraca;
	

	private JLabel lblBananaCountIcon;
	JLabel lblBananaCount;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	
	Timer timerVreme=new Timer();
	TimerTask vreme=new TimerTask() {
		public void run() {
			    int minute=SekundeOdPocetka/60;
			    String sMinute=String.valueOf(minute);
			    if(minute<10)
			    {
			    	sMinute="0"+sMinute;
			    }
			    int seconds=SekundeOdPocetka%60;
			    String sSeconds=String.valueOf(seconds);
			    if(seconds<10)
			    {
			    	sSeconds="0"+sSeconds;
			    }
			    
			    
			    lblTime.setText(sMinute+":"+sSeconds);
				System.out.println("Proslo je "+ SekundeOdPocetka++ +" s");
		}
	};

	
	
	Timer timer=new Timer();
	TimerTask updateFrame=new TimerTask(){
		public void run() {
		//ovde ide kod za updatovanje frame, poziv pomeranja
		 //   GameMethods.NacrtajIgraca(img, igrac);
		    
			
		    PomeriIgraca();
		    VertikalnoPomeranjeKaraktera();
		    ProveriDaLiSamNaBanani();
		//    igrac.OkreniKaraktera();
		    lbSlikaIgraca.setIcon(new ImageIcon(igrac.slika));
		    lbSlikaIgraca.setLocation(igrac.xCoordinate, igrac.yCoordinate);
		   
		//	System.out.print(igrac.kretanje);
			
		}
		
	};

	
	public void Start() {
		timer.schedule(updateFrame, 0, 16);
        timerVreme.schedule(vreme, 0, 1000);
	}
	
	
	public GameWindowFrame(String Ime,String Karakter,GlavniMeni meni) throws IOException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameWindowFrame.class.getResource("/Slike/Banana.png")));
		setTitle("Monkey Jump");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0,1296, 759);
		contentPane = new JPanel();
		this.addKeyListener(this);
        this.setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		 lblTime = new JLabel("15:20");
		 lblTime.setFont(new Font("Tahoma", Font.PLAIN, 43));
		 lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		 lblTime.setForeground(Color.WHITE);
		 lblTime.setBounds(566, 39, 138, 44);
		 contentPane.add(lblTime);
		 ime=Ime;
		 karakter=Karakter;
		 this.glavniMeni = meni;
		
		//Igrac
		 igrac=new Player(Karakter,600,600,5);
		 ImageIcon igracSlika= new ImageIcon(igrac.slika);
		 
		 lblBananaCount = new JLabel("0/7");
		 lblBananaCount.setHorizontalAlignment(SwingConstants.CENTER);
		 lblBananaCount.setForeground(Color.YELLOW);
		 lblBananaCount.setFont(new Font("Tahoma", Font.PLAIN, 36));
		 lblBananaCount.setBounds(850, 39, 80, 44);
		 contentPane.add(lblBananaCount);
		 
		 lblBananaCountIcon = new JLabel();
		 lblBananaCountIcon.setHorizontalAlignment(SwingConstants.CENTER);
		 lblBananaCountIcon.setForeground(Color.YELLOW);
		 lblBananaCountIcon.setFont(new Font("Tahoma", Font.PLAIN, 36));
		 lblBananaCountIcon.setBounds(900, 20, 90, 90);
		 File pathToFile = new File("C:\\Users\\PD\\Desktop\\Projektovanje softvera\\Slike\\Banana.png");
		 BufferedImage slikaBanane;
		 slikaBanane = ImageIO.read(pathToFile);
		 ImageIcon bananaIcon=new ImageIcon(slikaBanane);
		 lblBananaCountIcon.setIcon(bananaIcon);
		 contentPane.add(lblBananaCountIcon);
		 
		
		
		 lbSlikaIgraca=new JLabel(igracSlika,JLabel.CENTER);
		 lbSlikaIgraca.setBounds(igrac.xCoordinate, igrac.yCoordinate, igrac.Height+10, igrac.Width);
		 
		
		 
		// lbSlikaIgraca.setIcon(igracSlika);
		 contentPane.add(lbSlikaIgraca);
		 UcitajSlikuPozadine("Background");
		 NapraviPozadinu();
		 GenerisiMatricuIgre("MatrixBackground");
		 //Banane
		 DobiBrojBanana();
		 banane=new Banana[brojBanana];
		 GenerisiBanane();
		 
		 lbBanane=new JLabel[brojBanana];
		 for(int i=0;i<brojBanana;i++)
		 {
			 lbBanane[i]=new JLabel();
			 ImageIcon icon=new ImageIcon(banane[i].slika);
			 lbBanane[i].setIcon(icon);
			 lbBanane[i].setBounds(banane[i].xCoordinate, banane[i].yCoordinate, banane[i].Width, banane[i].Height);
			 contentPane.add(lbBanane[i]);
		 }
		 
		 
		//Pozadina
		lbl=new JLabel();
		lbl.setBounds(0, 0, SirinaPozadine, VisinaPozadine);
		contentPane.add(lbl);
	//	UcitajSlikuPozadine("Background");
		
	    PostaviPozadinu();
		GenerisiOkvirPozadine(5);

        Start();
	}
	public void ProveriDaLiSamNaBanani() {
		int aktivnaBananaIndex=-1;
		for(int i=0;i<igrac.Height;i++)
		{
			for(int j=0;j<igrac.Width;j++)
			{
				if(matricaIgre[igrac.yCoordinate+i][igrac.xCoordinate+j]==Banana)
				{
					for(int k=0;k<brojBanana;k++)
					{
						if(Math.abs(banane[k].xCoordinate-igrac.xCoordinate)<2*BananaRadius && Math.abs(banane[k].yCoordinate-igrac.yCoordinate)<2*BananaRadius)
						{
							aktivnaBananaIndex=k;
						}
					}
					
				}
			}
		}
		if(aktivnaBananaIndex!=-1)
			try {
				PokupiBananu(aktivnaBananaIndex);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
	public void PokupiBananu(int IndexBanane) throws IOException
	{
		brojPokupljenihBanana++;
		lblBananaCount.setText(brojPokupljenihBanana+"/"+brojBanana);
		lbBanane[IndexBanane].setVisible(false);
		for(int i=0;i<banane[IndexBanane].Height+2*BananaRadius;i++)
		{
			for(int j=0;j<banane[IndexBanane].Width+2*BananaRadius;j++) 
			{
				if(matricaIgre[banane[IndexBanane].yCoordinate-BananaRadius+i][banane[IndexBanane].xCoordinate-BananaRadius+j]==Banana) {
					matricaIgre[banane[IndexBanane].yCoordinate-BananaRadius+i][banane[IndexBanane].xCoordinate-BananaRadius+j]=Nista;
					
			}
		
				
			}
		}
		banane[IndexBanane].Pokupljena=true;
		if(brojPokupljenihBanana==brojBanana)
		{
			timerVreme.cancel();
			timerVreme.purge();
			try {
				MeniPosleIgre meni=new MeniPosleIgre(ime,lblTime.getText(),karakter,glavniMeni);
				meni.show();
				this.dispose();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	
	public void UcitajSlikuPozadine(String nazivSlike) throws IOException {
		
			File pathToFile = new File("C:\\Users\\PD\\Desktop\\Projektovanje softvera\\Slike\\"+ nazivSlike +".png");
		    pozadina = ImageIO.read(pathToFile);	
		
			
	}

	public void NapraviPozadinu()
	{
		
	    img = new BufferedImage(1280, 720,BufferedImage.TYPE_INT_RGB);
	    
	    for(int i=0;i<img.getHeight();i++)
	    	for(int j=0;j<img.getWidth();j++)
	    	{
	    		
	    		img.setRGB(j, i, pozadina.getRGB(j, i));
	    	}
	    
	}
	
	public void GenerisiMatricuIgre(String nazivSlike) throws IOException
	{
		File pathToFile = new File("C:\\Users\\PD\\Desktop\\Projektovanje softvera\\Slike\\"+ nazivSlike +".png");
	    imgObjekti= ImageIO.read(pathToFile);
		for(int i=0;i<VisinaPozadine;i++)
		{
			for(int j=0;j<SirinaPozadine;j++)
			{
				//System.out.println(imgObjekti.getRGB(j, i));
				if(imgObjekti.getRGB(j, i)==-16777216)
				{
					matricaIgre[i][j]=Platforma;
				}
				
			}
		}
	}
	
	public void GenerisiOkvirPozadine(int Debljina)
	{
		int visina=img.getHeight();
		int sirina=img.getWidth();
		for(int i=0; i<visina; i++)
		{
			for(int j=0; j<sirina; j++)
			{
				if(i<Debljina || i>visina-Debljina || j<Debljina || j>sirina-Debljina)
				{
					img.setRGB(j, i, Color.GREEN.getRGB());
					matricaIgre[i][j]=3;
				}
			}
		}
	}
	public void DobiBrojBanana()
	{
		
		for(int i=0; i<VisinaPozadine; i++)
		{
			for(int j=0; j<SirinaPozadine; j++)
			{
				//-4690096 -1792398
				if(imgObjekti.getRGB(j, i)==-3584)
				{
					brojBanana++;
				}
				//System.out.println(imgObjekti.getRGB(j,i));
			}
		}
	}
	
	public void GenerisiBanane() throws IOException
	{
		
		int tempBanane=0;
		for(int i=0; i<VisinaPozadine; i++)
		{
			for(int j=0; j<SirinaPozadine; j++)
			{
				//-4690096 -1792398
				if(imgObjekti.getRGB(j, i)==-3584)
				{
					for(int n=i-BananaRadius;n<i+BananaRadius;n++)
					{
						for(int m=j-BananaRadius;m<j+BananaRadius;m++)
							matricaIgre[n][m]=2;
					}
					
					banane[tempBanane]=new Banana(j,i);
					tempBanane++;
				}
				//System.out.println(imgObjekti.getRGB(j,i));
			}
		}
	}
	public void PostaviIgraca()
	{
		ImageIcon icon=new ImageIcon(igrac.slika);
	    lbSlikaIgraca.setIcon(icon);
	}
	
	public void PostaviPozadinu()
	{
		ImageIcon icon=new ImageIcon(img);
	    lbl.setIcon(icon);
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyChar()=='A' || e.getKeyChar()=='a' || e.getKeyCode()==e.VK_LEFT)
		{
			if(igrac.kretanje>-1)
			{
				igrac.kretanje-=1;				
				//igrac.GledaDesno=false;
			}
		}
		else if(e.getKeyChar()=='D' || e.getKeyChar()=='d' || e.getKeyCode()==e.VK_RIGHT)
		{
			if(igrac.kretanje<1)
			{
				igrac.kretanje+=1;
			   // igrac.GledaDesno=true;
			}
		 
		}
		else if((e.getKeyCode()==e.VK_SPACE || e.getKeyCode()==e.VK_UP || e.getKeyCode()==e.VK_W) && igrac.mozeDaSkace==true)
		{
			igrac.mozeDaSkace=false;
			igrac.jacinaSkoka=17;
		}
		else if(e.getKeyCode()==e.VK_ESCAPE)
		{
			glavniMeni.setVisible(true);
			this.dispose();
		}

	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyChar()=='A' || e.getKeyChar()=='a' || e.getKeyCode()==e.VK_LEFT)
		{
			if(igrac.kretanje<1)
			igrac.kretanje+=1;
		}
		else if(e.getKeyChar()=='D' || e.getKeyChar()=='d' || e.getKeyCode()==e.VK_RIGHT)
		{
			if(igrac.kretanje>-1)
			igrac.kretanje-=1;
		}
	}
	
	
	public void PomeriIgraca()
	{
		if(igrac.kretanje>0)
		{
			//Pixel po pixel pomeranje, za precizniju detekciju
			for(int i=1;i<=igrac.brzina;i++)
			{  //Pixel po pixel levo od karaktera ali za svaki pixel u koloni visine
			    boolean mozeDaSePomeri=true;
				for(int k=0;k<igrac.Height;k++)
				{
					
					if(matricaIgre[igrac.yCoordinate+k][igrac.xCoordinate+igrac.Width+1]==Platforma)
						mozeDaSePomeri=false;
				}
				if(mozeDaSePomeri==true)
				igrac.xCoordinate+=1;
			}
				
				
		}
			
		else if(igrac.kretanje<0)
		{
			for(int i=1;i<=igrac.brzina;i++)
			{  //Pixel po pixel levo od karaktera ali za svaki pixel u koloni visine
			    boolean mozeDaSePomeri=true;
				for(int k=0;k<igrac.Height;k++)
				{
					
					if(matricaIgre[igrac.yCoordinate+k][igrac.xCoordinate-1]==Platforma)
						mozeDaSePomeri=false;
				}
				if(mozeDaSePomeri==true)
				igrac.xCoordinate-=1;
			}
			
		}
			
		igrac.OkreniKaraktera();
	}
	
	public void VertikalnoPomeranjeKaraktera()
	{
		if(Gravitacija>=igrac.jacinaSkoka)
		{
			for(int i=1;i<=Gravitacija-igrac.jacinaSkoka;i++)
			{  
				boolean mozeDaPadne=true;
				for(int k=0;k<igrac.Width;k++)
				{
				
					if(matricaIgre[igrac.yCoordinate+igrac.Height+1][igrac.xCoordinate+k]==Platforma)
					{
						mozeDaPadne=false;
						igrac.mozeDaSkace=true;
						Gravitacija=BaseGravitacija;
					}
					else
					{
						//igrac.mozeDaSkace=false;
					}
				}
			if(mozeDaPadne==true)
				igrac.yCoordinate=igrac.yCoordinate+1;
			
			}
			Gravitacija+=1;
		}
		else if(igrac.jacinaSkoka>Gravitacija)
		{
			for(int i=1;i<=igrac.jacinaSkoka-Gravitacija;i++)
			{  
			    boolean mozeDaIdeGore=true;
				for(int k=0;k<igrac.Width;k++)
				{
					
					if(matricaIgre[igrac.yCoordinate-1][igrac.xCoordinate+k]==Platforma)
					{
						mozeDaIdeGore=false;
						igrac.jacinaSkoka=0;
					}
					
				}
				if(mozeDaIdeGore==true)
				igrac.yCoordinate=igrac.yCoordinate-1;
			}
			igrac.jacinaSkoka--;
		}
		
	}
}