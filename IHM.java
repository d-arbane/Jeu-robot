import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IHM {

	private JFrame frame,tabPol,tabNett;
	private Monde m;
	private Vector<Vector<JPanel>> Cases;
	private int xSelec,ySelec;
	private JTable tablePollueurs;
	private JTable tableNettoyeurs;
	private JLabel lblDecharge;
	private JLabel lblPapiersGras;
	private JLabel lblInstant;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IHM window = new IHM();
					window.frame.setVisible(true);
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IHM() {
		String L = JOptionPane.showInputDialog("Combien de lignes y a t'il dans ce monde?");
		String c = JOptionPane.showInputDialog("Combien de colonnes y a t'il dans ce monde?");
		String xd = JOptionPane.showInputDialog("Sur quelle ligne se trouve la decharge?");
		String yd = JOptionPane.showInputDialog("Sur quelle colonne se trouve la decharge?");
		try {
		m= new Monde(Integer.parseInt(L),Integer.parseInt(c),Integer.parseInt(xd),Integer.parseInt(yd));
		if((m.getNbL()<2)||(m.getNbC()<2)||(m.getxDecharge()>=m.getNbL())||(m.getxDecharge()<0)||(m.getyDecharge()>=m.getNbC())||(m.getyDecharge()<0))
			throw new NumberFormatException();
		Cases = new Vector<Vector<JPanel>>();
		for(int i=0;i<m.getNbL();i++) {
			Cases.add(new Vector<JPanel>());
			for(int j=0;j<m.getNbC();j++) {
				JPanel p = new JPanel();
				p.setBorder(new LineBorder(new Color(0, 0, 0)));
				p.setBackground(Color.GREEN);
				p.setLayout(new GridLayout(1, 0, 0, 0));
				Cases.get(i).add(p);
				}
		}
		xSelec=0;
		ySelec=0;
		initialize();}
		catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Donn�es en entr�e non valides!");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 150, 600, 500);
		frame.setTitle("Monde");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabPol = new JFrame();
		tabPol.setBounds(180, 100, 220, 600);
		tabPol.setTitle("Pollueurs");
		tabPol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabNett = new JFrame();
		tabNett.setBounds(1000, 100, 220, 600);
		tabNett.setTitle("Nettoyeurs");
		tabNett.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel PanneauHaut = new JPanel();
		PanneauHaut.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		PanneauHaut.setBackground(Color.GRAY);
		PanneauHaut.setLayout(new FlowLayout());
		frame.getContentPane().add(PanneauHaut, BorderLayout.NORTH);
		
		JPanel panelInstant = new JPanel();
		panelInstant.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInstant.setBackground(Color.LIGHT_GRAY);
		PanneauHaut.add(panelInstant);
		
		lblInstant = new JLabel("Instant : 0");
		panelInstant.add(lblInstant);
		
		JPanel panelPapsDecharge = new JPanel();
		panelPapsDecharge.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelPapsDecharge.setBackground(Color.LIGHT_GRAY);
		PanneauHaut.add(panelPapsDecharge);
		
		lblDecharge = new JLabel("Papiers gras d\u00E9charg\u00E9s : 0");
		panelPapsDecharge.add(lblDecharge);
		
		JPanel panelPapsGras = new JPanel();
		panelPapsGras.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelPapsGras.setBackground(Color.LIGHT_GRAY);
		PanneauHaut.add(panelPapsGras);
		
		lblPapiersGras = new JLabel("Papiers gras : 0");
		panelPapsGras.add(lblPapiersGras);
		
		JPanel panelCoor = new JPanel();
		panelCoor.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelCoor.setBackground(Color.LIGHT_GRAY);
		PanneauHaut.add(panelCoor);
		
		JLabel labelCoord = new JLabel("  (0, 0)  ");
		panelCoor.add(labelCoord);
		
		JPanel PanneauBas = new JPanel();
		PanneauBas.setBackground(Color.GRAY);
		PanneauBas.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		PanneauBas.setLayout(new FlowLayout());
		
		frame.getContentPane().add(PanneauBas, BorderLayout.SOUTH);
		
		JButton btnToutPolluer = new JButton("Tout Polluer ");
		btnToutPolluer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				m.balayer(true);
				try {
					Raffraichir();
				} catch (ExceptionCaseExistePas e1) {
					JOptionPane.showMessageDialog(btnToutPolluer, "Echec lors du balayage.");
				}
			}
		});
		PanneauBas.add(btnToutPolluer);
		
		/////////////////////////////////////////////////////////////////////////////
		/////////////////Panneau des Coordonn�es/////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////
		
		JPanel panelCoordonnees = new JPanel();
		panelCoordonnees.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
		});
		panelCoordonnees.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelCoordonnees.setBackground(Color.LIGHT_GRAY);
		PanneauBas.add(panelCoordonnees);
		panelCoordonnees.setLayout(new BorderLayout(0,0));
		
		JButton UP =new JButton("HAUT");
		UP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				xSelec = (xSelec-1<0)? xSelec : xSelec-1;
				labelCoord.setText("  ("+xSelec+", "+ySelec+")  ");
			}
		});
		JPanel PannelUP = new JPanel();
		PannelUP.setBackground(Color.LIGHT_GRAY);
		PannelUP.setLayout(new FlowLayout());
		PannelUP.add(UP);
		panelCoordonnees.add(PannelUP, BorderLayout.NORTH);
		
		JButton RIGHT =new JButton("DROITE");
		RIGHT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ySelec = (ySelec+1>=m.getNbC())? ySelec : ySelec+1;
				labelCoord.setText("  ("+xSelec+", "+ySelec+")  ");
			}
		});
		panelCoordonnees.add(RIGHT, BorderLayout.EAST);
		
		JButton LEFT =new JButton("GAUCHE");
		LEFT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ySelec = (ySelec-1<0)? ySelec : ySelec-1;
				labelCoord.setText("  ("+xSelec+", "+ySelec+")  ");
			}
		});
		panelCoordonnees.add(LEFT, BorderLayout.WEST);
		
		JButton btnEvolution = new JButton("Evolution");
		panelCoordonnees.add(btnEvolution, BorderLayout.CENTER);
		
		JPanel panelDOWN = new JPanel();
		panelDOWN.setBackground(Color.LIGHT_GRAY);
		panelCoordonnees.add(panelDOWN, BorderLayout.SOUTH);
		
		JButton DOWN =new JButton("BAS");
		panelDOWN.add(DOWN);
		
		DOWN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				xSelec = (xSelec+1>=m.getNbL())? xSelec : xSelec+1;
				labelCoord.setText("  ("+xSelec+", "+ySelec+")  ");
			}
		});
		btnEvolution.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					m.getGestionnaire().Evolution();
				} catch (ExceptionCaseExistePas e) {
					JOptionPane.showMessageDialog(btnEvolution,"Erreur durant l'�volution !");
					e.printStackTrace();
				}
				try {
					Raffraichir();
				} catch (ExceptionCaseExistePas e) {
					JOptionPane.showMessageDialog(btnEvolution,"Erreur durant le raffraichissement !");
					e.printStackTrace();
				}
			}
		});
		///////////////////////////////////////////////////////////////////////////////////
		JButton btnToutNettoyer = new JButton("Tout Nettoyer");
		PanneauBas.add(btnToutNettoyer);
		btnToutNettoyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m.balayer(false);
				try {
					Raffraichir();
				} catch (ExceptionCaseExistePas e) {
					JOptionPane.showMessageDialog(btnToutPolluer, "Echec lors du balayage.");
				}
			}
		});
		///////////////////////////////////////////////////////////////////////////////////
		//////////////////Initialisation de la table des pollueurs/////////////////////////
		///////////////////////////////////////////////////////////////////////////////////
		tablePollueurs = new JTable();
		tablePollueurs.setModel(new DefaultTableModel(new Object[][] {},new String[] {"Reference", "En Vie", "Position"})
		{private static final long serialVersionUID = 1L;
		public boolean isCellEditable(int row, int column){
			return false;}
		});
		tablePollueurs.getColumnModel().getColumn(0).setMaxWidth(70);
		tablePollueurs.getColumnModel().getColumn(1).setMaxWidth(70);
		tablePollueurs.getColumnModel().getColumn(2).setMaxWidth(70);
		
		JScrollPane ScrollPanePollueurs = new JScrollPane();
		ScrollPanePollueurs.setViewportView(tablePollueurs);
		tablePollueurs.setBackground(new Color(255, 140, 0));
		tabPol.getContentPane().add(ScrollPanePollueurs);
		///////////////////////////////////////////////////////////////////////////////////
		
		///////////////////////////////////////////////////////////////////////////////////
		//////////////////Initialisation de la table des nettoyeurs////////////////////////
		///////////////////////////////////////////////////////////////////////////////////
		tableNettoyeurs = new JTable();
		tableNettoyeurs.setModel(new DefaultTableModel(new Object[][] {},new String[] {"Reference", "Surcharg�", "Position"}) 
		{private static final long serialVersionUID = 1L;
		public boolean isCellEditable(int row, int column){
			return false;}
		});
		tableNettoyeurs.getColumnModel().getColumn(0).setMaxWidth(70);
		tableNettoyeurs.getColumnModel().getColumn(1).setMaxWidth(70);
		tableNettoyeurs.getColumnModel().getColumn(2).setMaxWidth(70);
		tableNettoyeurs.setBackground(Color.GREEN);
		
		JScrollPane ScrollPaneNettoyeurs = new JScrollPane();
		ScrollPaneNettoyeurs.setViewportView(tableNettoyeurs);
		tabNett.getContentPane().add(ScrollPaneNettoyeurs);
		/////////////////////////////////////////////////////////////////////////////////////
		
		/////////////////////////////////////////////////////////////////////////////////////
		////////////////////////Panneau des pollueurs////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////
		JPanel PanneauPollueur = new JPanel();
		frame.getContentPane().add(PanneauPollueur, BorderLayout.WEST);
		PanneauPollueur.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnSauteur = new JButton("Sauteur");
		btnSauteur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String s = JOptionPane.showInputDialog(btnSauteur," Quel sera la port�e du robot sauteur en ("+xSelec+", "+ySelec+")?");
				try {
					int i = Integer.parseInt(s);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(btnSauteur,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				String v = JOptionPane.showInputDialog(btnSauteur," Quel sera la vitesse du Robot Pollueur Sauteur en ("+xSelec+", "+ySelec+")?");
				try {
					int i = Integer.parseInt(v);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(btnSauteur,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				try {
					m.getGestionnaire().AjouterRobot(new RobotPollueurSauteur(xSelec,ySelec,Integer.parseInt(v),m,Integer.parseInt(s)));
				} catch (NumberFormatException | ExceptionCaseExistePas e) {
					JOptionPane.showMessageDialog(btnSauteur, "Echec lors de l'ajout du Robot Pollueur Sauteur en ("+xSelec+", "+ySelec+").");
				}
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		PanneauPollueur.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblPollueurs = new JLabel("    POLLUEURS");
		panel.add(lblPollueurs);
		lblPollueurs.setForeground(new Color(255, 140, 0));
		lblPollueurs.setFont(new Font("Tahoma", Font.BOLD, 14));
		PanneauPollueur.add(btnSauteur);
		
		JButton btnTleporteur = new JButton("    T\u00E9leporteur    ");
		btnTleporteur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String v = JOptionPane.showInputDialog(btnSauteur," Quel sera la vitesse du Robot Pollueur T�leporteur ?");
				try {
					int i = Integer.parseInt(v);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(btnSauteur,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				try {
					m.getGestionnaire().AjouterRobot(new RobotPollueurTeleporteur(Integer.parseInt(v),m));
				} catch (NumberFormatException | ExceptionCaseExistePas e) {
					JOptionPane.showMessageDialog(btnSauteur, "Echec lors de l'ajout du Robot Pollueur T�leporteur.");
				}
			}
		});
		PanneauPollueur.add(btnTleporteur);
		
		JButton btnToutDroit = new JButton("Tout Droit");
		btnToutDroit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String v = JOptionPane.showInputDialog(btnSauteur," Quel sera la vitesse du Robot Tout Droit en ("+xSelec+", "+ySelec+")?");
				try {
					int i = Integer.parseInt(v);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnSauteur,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				try {
					m.getGestionnaire().AjouterRobot(new RobotPollueurToutDroit(xSelec,ySelec,m,Integer.parseInt(v)));
				} catch (NumberFormatException | ExceptionCaseExistePas ex) {
					JOptionPane.showMessageDialog(btnSauteur, "Echec lors de l'ajout du Robot Pollueur Sauteur en ("+xSelec+", "+ySelec+").");
				}
			}
		});
		PanneauPollueur.add(btnToutDroit);
		
		JButton btnTirDiagonal = new JButton("Tir Diagonal");
		btnTirDiagonal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String v = JOptionPane.showInputDialog(btnSauteur," Quel sera la vitesse de tir du Robot Pollueur � Tir Diagonal en ("+xSelec+", "+ySelec+")?");
				try {
					int i = Integer.parseInt(v);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnSauteur,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				try {
					m.getGestionnaire().AjouterRobot(new RobotPollueurTirDiagonal(xSelec,ySelec,Integer.parseInt(v),m));
				} catch (NumberFormatException | ExceptionCaseExistePas ex) {
					JOptionPane.showMessageDialog(btnSauteur, "Echec lors de l'ajout du Robot Pollueur Sauteur en ("+xSelec+", "+ySelec+").");
				}
			}
		});
		PanneauPollueur.add(btnTirDiagonal);
		
		///////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////Panneau de nettoyeurs/////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////
		
		JPanel PanneauNettoyeur = new JPanel();
		frame.getContentPane().add(PanneauNettoyeur, BorderLayout.EAST);
		PanneauNettoyeur.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnEssuieGlace = new JButton("Essuie Glace");
		btnEssuieGlace.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String c = JOptionPane.showInputDialog(btnSauteur," Quel sera la capacit� du Robot Nettoyeur Essuie Glace en ("+xSelec+", "+ySelec+")?");
				try {
					int i = Integer.parseInt(c);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnSauteur,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				String v = JOptionPane.showInputDialog(btnSauteur," Quel sera la vitesse du Robot Nettoyeur Essuie Glace en ("+xSelec+", "+ySelec+")?");
				try {
					int i = Integer.parseInt(v);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnSauteur,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				try {
					m.getGestionnaire().AjouterRobot(new RobotNettoyeurEssuieGlace(xSelec,ySelec,Integer.parseInt(v),m,Integer.parseInt(c)));
				} catch (NumberFormatException | ExceptionCaseExistePas ex) {
					JOptionPane.showMessageDialog(btnSauteur, "Echec lors de l'ajout du Robot Pollueur Sauteur en ("+xSelec+", "+ySelec+").");
				}
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		PanneauNettoyeur.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNettoyeurs = new JLabel("   NETTOYEURS");
		panel_1.add(lblNettoyeurs);
		lblNettoyeurs.setForeground(Color.GREEN);
		lblNettoyeurs.setFont(new Font("Tahoma", Font.BOLD, 14));
		PanneauNettoyeur.add(btnEssuieGlace);
		
		JButton btnChasseurSauteur = new JButton("Chasseur Sauteur");
		btnChasseurSauteur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String c = JOptionPane.showInputDialog(btnChasseurSauteur," Quel sera la capacit� du Robot Nettoyeur Essuie Glace en ("+xSelec+", "+ySelec+")?");
				try {
					int i = Integer.parseInt(c);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnChasseurSauteur,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				String s = JOptionPane.showInputDialog(btnChasseurSauteur," Quel sera la port�e du robot sauteur en ("+xSelec+", "+ySelec+")?");
				try {
					int i = Integer.parseInt(s);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnChasseurSauteur,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				String v = JOptionPane.showInputDialog(btnChasseurSauteur," Quel sera la vitesse du Robot Pollueur Sauteur en ("+xSelec+", "+ySelec+")?");
				try {
					int i = Integer.parseInt(v);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnChasseurSauteur,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				try {
					m.getGestionnaire().AjouterRobot(new RobotNettoyeurChasseurSauteur(xSelec,ySelec,Integer.parseInt(v),m,Integer.parseInt(c),Integer.parseInt(s)));
				} catch (NumberFormatException | ExceptionCaseExistePas ex) {
					JOptionPane.showMessageDialog(btnChasseurSauteur, "Echec lors de l'ajout du Robot Nettoyeur Chasseur Sauteur en ("+xSelec+", "+ySelec+").");
				}
			}
		});
		PanneauNettoyeur.add(btnChasseurSauteur);
		
		JButton btnNettoyeurToutDroit = new JButton("Tout Droit");
		btnNettoyeurToutDroit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String c = JOptionPane.showInputDialog(btnNettoyeurToutDroit," Quel sera la capacit� du Robot Nettoyeur Tout Droit en ("+xSelec+", "+ySelec+")?");
				try {
					int i = Integer.parseInt(c);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnNettoyeurToutDroit,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				String v = JOptionPane.showInputDialog(btnNettoyeurToutDroit," Quel sera la vitesse du Robot Nettoyeur Tout Droit en ("+xSelec+", "+ySelec+")?");
				try {
					int i = Integer.parseInt(v);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnNettoyeurToutDroit,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				try {
					m.getGestionnaire().AjouterRobot(new RobotNettoyeurToutDroit(xSelec,ySelec,Integer.parseInt(v),m,Integer.parseInt(c)));
				} catch (NumberFormatException | ExceptionCaseExistePas ex) {
					JOptionPane.showMessageDialog(btnNettoyeurToutDroit, "Echec lors de l'ajout du Robot Nettoyeur Chasseur Sauteur en ("+xSelec+", "+ySelec+").");
				}
			}
		});
		PanneauNettoyeur.add(btnNettoyeurToutDroit);
		
		JButton btnNettoyeurSpirale = new JButton("Spirale Maximale");
		btnNettoyeurSpirale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String c = JOptionPane.showInputDialog(btnNettoyeurSpirale," Quel sera la capacit� du Robot Nettoyeur en Spirale Maximale ?");
				try {
					int i = Integer.parseInt(c);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnNettoyeurSpirale,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				String v = JOptionPane.showInputDialog(btnNettoyeurSpirale," Quel sera la vitesse du Robot Nettoyeur en Spirale Maximale ?");
				try {
					int i = Integer.parseInt(v);
					if(i<=0)
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnNettoyeurSpirale,"Valeur non valable ! Veuillez donner un entier positif non nul!");
				}
				String t = JOptionPane.showInputDialog(btnNettoyeurSpirale," Quel sera le taux minimal du Robot Nettoyeur en Spirale Maximale ?");
				try {
					int i = Integer.parseInt(v);
					if((i<=0)&&(i<=100))
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnNettoyeurSpirale,"Valeur non valable ! Veuillez donner un entier positif non nul et inf�rieur � 100!");
				}
				String l = JOptionPane.showInputDialog(btnNettoyeurSpirale," Quel sera la largeur de la surface couverte par le Robot Nettoyeur en Spirale Maximale ?");
				try {
					int i = Integer.parseInt(v);
					if((i<=0)&&(i<m.getNbC()))
						throw new NumberFormatException();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(btnNettoyeurSpirale,"Valeur non valable ! Veuillez donner un entier positif non nul et inf�rieur au nombre de colonnes!");
				}
				try {
					m.getGestionnaire().AjouterRobot(new RobotNettoyeurSpiraleMaximale(Integer.parseInt(v),m,Integer.parseInt(c),Integer.parseInt(l),Integer.parseInt(t)));
				} catch (NumberFormatException | ExceptionCaseExistePas ex) {
					JOptionPane.showMessageDialog(btnNettoyeurToutDroit, "Echec lors de l'ajout du Robot Nettoyeur en Spirale Maximale.");
				}
			}
		});
		PanneauNettoyeur.add(btnNettoyeurSpirale);
		
		////////////////////////////////////////////////////////////////////////////////////////
		
		JPanel panelMonde = new JPanel();
		panelMonde.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelMonde.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panelMonde, BorderLayout.CENTER);
		panelMonde.setLayout(new GridLayout(m.getNbL(), m.getNbC(), 0, 0));
		
		for(Vector<JPanel> vec:Cases)  //Ajouter toutes les cases au pannau
			for(JPanel p:vec)
				panelMonde.add(p);
		tabPol.setVisible(true);
		tabNett.setVisible(true);
	}
	public void Raffraichir() throws ExceptionCaseExistePas {
		for(int i=0;i<m.getNbL();i++)
			for(int j=0;j<m.getNbC();j++) {
				if(m.TestPapierGras(i, j))
					Cases.get(i).get(j).setBackground(Color.ORANGE);
				else Cases.get(i).get(j).setBackground(Color.GREEN);
			}
		DefaultTableModel model = (DefaultTableModel) tableNettoyeurs.getModel();
		for(int i=model.getRowCount()-1;i>=0;i--) 
			model.removeRow(i);
		for(RobotNettoyeur r : m.getGestionnaire().Nettoyeurs)
			model.addRow(new Object[] {r.getref(),r.EstPasSurcharge()? "Non":"Oui","("+r.posx+", "+r.posy+")"});
		for(RobotNettoyeur r : m.getGestionnaire().Surcharges)
			model.addRow(new Object[] {r.getref(),r.EstPasSurcharge()? "Non":"Oui","("+r.posx+", "+r.posy+")"});
		model = (DefaultTableModel) tablePollueurs.getModel();
		for(int i=model.getRowCount()-1;i>=0;i--) 
			model.removeRow(i);
		for(RobotPollueur r : m.getGestionnaire().Pollueurs)
			model.addRow(new Object[] {r.getref(),r.estEnVie()? "Oui":"Non","("+r.posx+", "+r.posy+")"});
		for(RobotPollueur r : m.getGestionnaire().Detruits)
			model.addRow(new Object[] {r.getref(),r.estEnVie()? "Oui":"Non","("+r.posx+", "+r.posy+")"});
		lblDecharge.setText("Papiers gras decharges : "+m.getPapiersDecharges());
		lblPapiersGras.setText("Papiers gras : "+m.NombrePapierGras());
		lblInstant.setText("Instant : "+m.getGestionnaire().getInstant());
	}
}
