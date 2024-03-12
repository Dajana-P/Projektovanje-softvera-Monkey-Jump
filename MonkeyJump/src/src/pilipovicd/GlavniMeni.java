package src.pilipovicd;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import java.awt.Toolkit;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.sound.sampled.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class GlavniMeni extends JFrame {

	private JPanel contentPane;
	private JTextField txtIme;
	private JTable tableScores;
	private JButton btnZapocni;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlavniMeni frame = new GlavniMeni();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GlavniMeni() throws Exception {
		setTitle("Monkey Jump");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GlavniMeni.class.getResource("/Slike/Banana.png")));
		GlavniMeni meni=this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(228, 209, 146));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(DISPOSE_ON_CLOSE);
			}
		});
		btnExit.setBounds(568, 390, 114, 31);
		contentPane.add(btnExit);
		
		txtIme = new JTextField();
		txtIme.setBounds(500, 110, 114, 20);
		contentPane.add(txtIme);
		txtIme.setColumns(10);
		
		JLabel lbl_Ime = new JLabel("Ime");
		lbl_Ime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_Ime.setHorizontalAlignment(SwingConstants.TRAILING);
		lbl_Ime.setBounds(426, 110, 46, 20);
		contentPane.add(lbl_Ime);
		
		TableModel dataModel = new AbstractTableModel() {
	          public int getColumnCount() { return 10; }
	          public int getRowCount() { return 10;}
	          public Object getValueAt(int row, int col) { return new Integer(row*col); }
	      };
		
		JTable tableScores = new JTable(dataModel);
		JScrollPane scrollpane = new JScrollPane();
		tableScores.setBorder(new LineBorder(new Color(210, 180, 140), 3, true));
		tableScores.setBackground(new Color(184, 134, 11));
		tableScores.setBounds(300, 380, -200, -280);
		      
		JComboBox cmbKarakter = new JComboBox();
		cmbKarakter.setModel(new DefaultComboBoxModel(new String[] {"Monkey", "Froggy"}));
		cmbKarakter.setBounds(500, 180, 116, 22);
		contentPane.add(cmbKarakter);
		
		btnZapocni = new JButton("ZapoÄ�ni Igru");
		btnZapocni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Ime=txtIme.getText();
				if(txtIme.getText().isEmpty())
					Ime="Guest";
				try {
					GameWindowFrame igra= new GameWindowFrame(Ime,cmbKarakter.getSelectedItem().toString(),meni);
					igra.show();
					meni.setVisible(false);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
			}
		});
		btnZapocni.setBounds(500, 320, 114, 31);
		contentPane.add(btnZapocni);
		
		Muzika("//MonkeyJump//Resursi//Muzika//StringAdagio-ClassicStringBalladInDorianMood.wav");
		
		JSlider slider = new JSlider(0, 100, 100);
		slider.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		slider.setForeground(Color.BLACK);
		slider.setMinorTickSpacing(5);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(10);
		slider.setSnapToTicks(true);
		slider.setToolTipText("");
		slider.setBounds(500, 250, 182, 40);
		slider.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(slider);
		
		JLabel lbNaslov = new JLabel("Monkey Jump");
		lbNaslov.setForeground(new Color(184, 134, 11));
		lbNaslov.setHorizontalAlignment(SwingConstants.CENTER);
		lbNaslov.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 40));
		lbNaslov.setBounds(206, 11, 279, 109);
		contentPane.add(lbNaslov);
		
		JLabel lbl_Volume = new JLabel("Volume");
		lbl_Volume.setBounds(410, 250, 60, 39);
		lbl_Volume.setHorizontalAlignment(SwingConstants.TRAILING);
		lbl_Volume.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(lbl_Volume);
		
		JLabel lbl_Karakter = new JLabel("Karakter");
		lbl_Karakter.setHorizontalAlignment(SwingConstants.TRAILING);
		lbl_Karakter.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_Karakter.setBounds(400, 180, 72, 22);
		contentPane.add(lbl_Karakter);
	}
	
	void Muzika(String lokacija) throws Exception {
		URI uri=new URI(lokacija);
		URL path=uri.toURL();
        AudioInputStream muzika=AudioSystem.getAudioInputStream(path);
        Clip clip=AudioSystem.getClip();
        clip.open(muzika);
        clip.setFramePosition(0);
        clip.start();
	}
}