package src.pilipovicd;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class MeniPosleIgre extends JDialog {

	private GlavniMeni glavniMeni;
	private final JPanel contentPanel = new JPanel();
    private String vreme;
    private String ime;
    private String karakter;
    MeniPosleIgre meni;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the dialog.
	 * @throws IOException 
	 */
	public void initComponents()  {
		{
			 meni=this;
			JButton okButton = new JButton("Igraj Ponovo");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GameWindowFrame igra;
					try {
						igra = new GameWindowFrame(ime,karakter,glavniMeni);
						igra.show();
						meni.dispose();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
			okButton.setBounds(148, 171, 141, 23);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Nazad U Meni");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					glavniMeni.setVisible(true);
					meni.dispose();
					
				}
			});
			cancelButton.setBounds(148, 214, 141, 23);
			contentPanel.add(cancelButton);
			cancelButton.setActionCommand("Cancel");
		}
		
		JButton btnSacuvajRez = new JButton("Sacuvaj Rezultat");
		btnSacuvajRez.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FileWriter fstream = new FileWriter("C:\\Users\\PD\\Desktop\\Projektovanje softvera\\Vremena.txt",true);
					
					fstream.write(ime+"|"+vreme+System.lineSeparator());
					fstream.close();
					btnSacuvajRez.setEnabled(false)	;
					
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		btnSacuvajRez.setBounds(148, 127, 141, 23);
		contentPanel.add(btnSacuvajRez);
		
		
		{
			JLabel lbVreme = new JLabel("Vaše vreme je:");
			lbVreme.setForeground(Color.BLACK);
			lbVreme.setHorizontalAlignment(SwingConstants.CENTER);
			lbVreme.setFont(new Font("Tahoma", Font.PLAIN, 36));
			lbVreme.setBounds(67, 11, 302, 44);
			contentPanel.add(lbVreme);
		}
		{
			JLabel lblVreme = new JLabel("vreme");
			lblVreme.setHorizontalAlignment(SwingConstants.CENTER);
			lblVreme.setForeground(Color.BLACK);
			lblVreme.setFont(new Font("Tahoma", Font.PLAIN, 36));
			lblVreme.setBounds(67, 56, 302, 44);
			lblVreme.setText(vreme);
			contentPanel.add(lblVreme);
		}
	}
	
	public MeniPosleIgre(String ImeIgraca,String Vreme,String Karakter,GlavniMeni meni) throws IOException {
		setBackground(new Color(255, 218, 185));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MeniPosleIgre.class.getResource("/Slike/Banana.png")));
		setTitle("Monkey Jump");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBackground(new Color(255, 218, 185));
		contentPanel.setBounds(0, 0, 434, 261);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		ime=ImeIgraca;
		karakter=Karakter;
		this.vreme=Vreme;
		this.glavniMeni = meni;
	    initComponents();
	}
}