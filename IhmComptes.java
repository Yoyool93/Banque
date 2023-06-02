package ProjetBanque;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//Attention !! verifier que lorsqu'on demande une ouverture d'un compte pour un client , ce dernier n'en possede pas deja un
//Mettre un flag cptOuvert a true lors de l'ouverture du compte et tester ce flag

public class IhmComptes extends JFrame implements ActionListener{

	// Creation d'un objet JPanel (toile)
	private JPanel pan;
	
	// Liste des comptes
	private LinkedList<Compte> listCpt;
	//Liste des clients
	private LinkedList<Client> listClients;
	
	// Numero de compte
	private int numeroCompte;
	
	// Reference a la BDD Comptes
	private BddAccess refBdd;
	
	// Etiquettes
	private JLabel labelSolde;
	private JLabel labelNom;
	private JLabel labelPrenom;
	private JLabel labelIdentClient;
	private JLabel labelAdresse;
	private JLabel labelResultat;
	private JLabel labelMontant;
	
	// Zones de texte
	private JTextField zoneSolde;
	private JTextField zoneNom;
	private JTextField zonePrenom;
	private JTextField zoneAdresse;
	private JTextField zoneIdentClient;
	private JTextField zoneMontant;
	
	// Zones de texte Area
	private JTextArea zoneResultat= new JTextArea();
	
	//Ascenseurs
	private JScrollPane scroll=new JScrollPane(zoneResultat);
	
	// Boutons
	private JButton bCreerClient;
	private JButton bOuvrirCpt;
	private JButton bDebiterCpt;
	private JButton bCrediterCpt;
	private JButton bConsulterCpt;
	
	
	private Compte cpt; // Variable temporaire
	private Client client;
	//Creation de l'objet banque 
	private Banque bnk ;
	
	// Constructeur
	public IhmComptes() {
		
		// Creation des controles
		labelSolde = new JLabel("Solde: ");
		labelNom   = new JLabel("Nom client: ");
		labelPrenom   = new JLabel("Prenom client: ");
		labelAdresse   = new JLabel("Adresse client: ");
		labelIdentClient = new JLabel("Ident client: ");
		labelResultat = new JLabel("Resultat: ");
		labelMontant = new JLabel("Montant: ");
		
		zoneSolde = new JTextField(20);
		zoneNom   = new JTextField(20);
		zonePrenom   = new JTextField(20);
		zoneAdresse   = new JTextField(20);
		zoneIdentClient = new JTextField(20);
		zoneMontant= new JTextField(20);
		//zoneResultat = new JTextArea(40,100);
		
		bCreerClient = new JButton("Creer client");
		bOuvrirCpt = new JButton("Aller ouvrir compte");
		bDebiterCpt = new JButton("Aller retirer somme");
		bCrediterCpt = new JButton("Aller deposer compte");
		bConsulterCpt = new JButton("Aller consulter compte");
		
		// Creation du panel
		pan = new JPanel();
		
		// Creation de la liste des comptes
		listCpt = new LinkedList<Compte>();
		//Creation de la liste des clients
		listClients= new LinkedList<Client>();
		
		numeroCompte = 0;
		
		// Creer l'objet BddAccess
		refBdd = new BddAccess();
		
		
		// Charger le driver de BDD
		refBdd.chargerDriver();
		
		// Se connecter a la BDD
		refBdd.connecterBdd();
		
		// Creer la requete
		refBdd.creerRequete();
		
		//Passer la reference de l'Ihm a la Bdd
		refBdd.setRefIhm(this);
		
		//Creation de l'objet banque
		
		bnk=new Banque("SG","55 rue Dufric",1000000,refBdd, this);
		
		
		// Ajoute un titre a la fenetre
		setTitle("Gestion Comptes IHM");
		
		// Dimensionner la fenetre (300 pixels de large 
		// sur 400 de haut
		setSize(810, 530);
				
		// Rend la fenetre visible a l'ecran
		setVisible(true);
		
		// A la fermeture de la fenetre fermer le programme
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Associer le panel au cadre defini
		setContentPane(pan);
		
		// Associer une couleur de fond au Panel
		pan.setBackground(Color.green);
		
		// Positionnement des controles sur le JPanel
		pan.add(labelNom);
		pan.add(zoneNom);
		pan.add(labelPrenom);
		pan.add(zonePrenom);
		pan.add(labelAdresse);
		pan.add(zoneAdresse);
		pan.add(labelIdentClient);
		pan.add(zoneIdentClient);
		pan.add(labelMontant);
		pan.add(zoneMontant);
		pan.add(labelSolde);
		pan.add(zoneSolde);
		pan.add(bCreerClient);
		pan.add(bOuvrirCpt);
		pan.add(bDebiterCpt);
		pan.add(bCrediterCpt);
		pan.add(bConsulterCpt);
		pan.add(labelResultat);
		//pan.add(zoneResultat);
		zoneResultat.setBackground(Color.lightGray);
		zoneResultat.setForeground(Color.blue);
		zoneResultat.setRows(20);
		zoneResultat.setColumns(30);
		scroll.setAutoscrolls(true);
		pan.add(scroll);
		
		// Enregistrer les boutons comme sources
		// d'evenements aupres de la fenetre IhmComptes
		bCreerClient.addActionListener(this);
		bOuvrirCpt.addActionListener(this);
		bDebiterCpt.addActionListener(this);
		bCrediterCpt.addActionListener(this);
		bConsulterCpt.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		String text1 , text2,proprio;
		float nb;
		int numCpt;
		int id;
		Client cli;
		
		// Determiner la source de l'evenement
		if(evt.getSource() == bOuvrirCpt) {
			
			// Recuperer les contenus des zones Id,solde 
			
			
			//Recuperer l'identifiant du client
			text1 = zoneIdentClient.getText();
			// Recuperer le solde et le convertir en float
			text2 = zoneSolde.getText();
			if(text1.isEmpty()|| text2.isEmpty()) {
				afficherDansZoneArea("Un des champs est vide");
			}
			else {
				nb= Float.parseFloat(text2);
				id=Integer.parseInt(text1);
			
			
				boolean trouve=false;
				int i=0;
				
				//Verifier si cet Id existe dans la liste des client
				while(trouve == false && i< listClients.size()) {
					
					cli=listClients.get(i);
					
					if(id ==cli.getId()){
						trouve=true;
						cli.allerOuvrirUnCompte(nb);
					}
					i++;
				}
				if(trouve==false) {
					zoneResultat.append("Client non trouve " + "\n");
					
				}
			}
		}
		else if(evt.getSource() == bDebiterCpt) {
		float somme;
			// Recuperer l'ident du client
			text1 = zoneIdentClient.getText();
			//Recuperer le montant a crediter
			text2=zoneMontant.getText();
			
			if(text1.isEmpty()|| text2.isEmpty()) {
				afficherDansZoneArea("Un des champs est vide");
			}
			else {
				// Convertir le numero saisi en int
				id=Integer.parseInt(text1);
			
				//Convertir le montant saisi en float
				somme =Float.parseFloat(text2);
			
				//Recherche du client dans la liste
				if(listClients.isEmpty()) {
					zoneResultat.append("La liste de client est vide !" +"\n");
				}
				else {
					boolean trouve=false;
					int i=0;
					
					//Verifier si cet Id existe dans la liste des clients
					while(trouve==false&& i<listClients.size()) {
						cli=listClients.get(i);
						
						if(id==cli.getId()){
							trouve=true;
							cli.allerRetirerSommeDeCompte(id,somme);
							
						}
						i++;
					}
					if(trouve==false) {
						zoneResultat.append("Client non trouve" + "\n");
						
					}
				}
			}
		}
		else if(evt.getSource() == bCrediterCpt) {
			float somme;
			
			// Recuperer l'ident du client
			text1 = zoneIdentClient.getText();
			//Recuperer le montant a crediter
			text2=zoneMontant.getText();
			// Convertir le numero saisi en int
			id=Integer.parseInt(text1);
			//Convertir le montant saisi en float
			somme =Float.parseFloat(text2);
			if(text1.isEmpty()|| text2.isEmpty()) {
				afficherDansZoneArea("Un des champs est vide");
			}
			else {
			
				//Recherche du client dans la liste
				if(listClients.isEmpty()) {
					zoneResultat.append("La liste de client est vide !" +"\n");
					
				}
				else {
					boolean trouve=false;
					int i=0;
					//Verifier si cet Id existe dans la liste des clients
					while(trouve==false && i<listClients.size()) {
						cli=listClients.get(i);
						
						if(id==cli.getId()){
							trouve=true;
							cli.allerDeposerSommeSurCompte(id,somme);
							
						}
						i++;
					}
					if(trouve==false) {
						zoneResultat.append("Client non trouve" + "\n");
						
							}
						}
				}
		}
		else if(evt.getSource() == bConsulterCpt) {
			// Recuperer le numero de compte a debiter
			text1=zoneIdentClient.getText();
			if(text1.isEmpty()){
				afficherDansZoneArea("Le champ Id client est vide");
			}
				else {
										
				// Convertir l'Id saisi en int
				id = Integer.parseInt(text1);
				System.out.println( "Id" +id);
										
				// Recherche du compte dans la liste
				if(listClients.isEmpty()) {
					zoneResultat.append("La liste est vide!!"+"\n");
				}
				else {
					boolean trouve=false;
					int i=0;
					//Verifier si cet Id existe dans la liste des clients
					while(trouve==false && i<listClients.size()) {
						cli=listClients.get(i);
						if(id==cli.getId()) {
							trouve=true;
							cli.allerConsulterCompte();
						}
						i++;
					}
					if(trouve==false) {
						
						zoneResultat.append("Client non trouvé" +"\n");
					}
				}
			}
		}
			else if(evt.getSource() == bCreerClient) {
			
			// Recuperer l'id client , nom prenom et adresse et le convertir en int
			text1 = zoneIdentClient.getText();
			String nom = zoneNom.getText();
			String prenom = zonePrenom.getText();
			String adresse = zoneAdresse.getText();
			if(text1.isEmpty()||
				nom.isEmpty()||
				prenom.isEmpty()||
				adresse.isEmpty()){
				afficherDansZoneArea("Un des champs est vide");
			}
				else {
					id = Integer.parseInt(text1);
					client = new Client(id, nom, prenom,adresse, bnk);
					listClients.add(client);
					}
				}
			}
	
	public void afficherDansZoneArea(String message) {
		
		zoneResultat.append(message);
		zoneResultat.append("\n");
		
	}
}
		
	
