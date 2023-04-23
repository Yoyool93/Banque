package ProjetBanque;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class IhmComptes extends JFrame implements ActionListener{

	// Creation d'un objet JPanel (toile)
	private JPanel pan;
	
	// Liste des comptes
	private LinkedList<Compte> listCpt;
	
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
	private JLabel labelNumCpt;
	private JLabel labelResultat;
	private JLabel labelMontant;
	
	// Zones de texte
	private JTextField zoneSolde;
	private JTextField zoneNom;
	private JTextField zonePrenom;
	private JTextField zoneAdresse;
	private JTextField zoneIdentClient;
	private JTextField zoneNumCpt;
	private JTextField zoneMontant;
	
	// Zones de texte Area
	private JTextArea zoneResultat;
	
	// Boutons
	private JButton bCreerClient;
	private JButton bCreerCpt;
	private JButton bDebiterCpt;
	private JButton bCrediterCpt;
	private JButton bConsulterCpt;
	private JButton bSaveBdd;
	
	private Compte cpt; // Variable temporaire
	
	// Constructeur
	public IhmComptes() {
		
		// Creation des controles
		labelSolde = new JLabel("Solde: ");
		labelNom   = new JLabel("Nom client: ");
		labelPrenom   = new JLabel("Prenom client: ");
		labelAdresse   = new JLabel("Adresse client: ");
		labelIdentClient = new JLabel("Ident client: ");
		labelNumCpt = new JLabel("Num compte: ");
		labelResultat = new JLabel("Resultat: ");
		labelMontant = new JLabel("Montant: ");
		
		zoneSolde = new JTextField(20);
		zoneNom   = new JTextField(20);
		zonePrenom   = new JTextField(20);
		zoneAdresse   = new JTextField(30);
		zoneIdentClient = new JTextField(20);
		zoneNumCpt = new JTextField(20);
		zoneMontant= new JTextField(20);
		zoneResultat = new JTextArea(10, 20);
		
		bCreerClient = new JButton("Creer client");
		bCreerCpt = new JButton("Aller ouvrir compte");
		bDebiterCpt = new JButton("Aller retirer somme");
		bCrediterCpt = new JButton("Aller deposer compte");
		bConsulterCpt = new JButton("Aller consulter compte");
		bSaveBdd = new JButton("Sauvegarde BDD");
		
		// Creation du panel
		pan = new JPanel();
		
		// Creation de la liste des comptes
		listCpt = new LinkedList<Compte>();
		
		numeroCompte = 0;
		
		// Creer l'objet BddAccess
		refBdd = new BddAccess();
		
		// Charger le driver de BDD
		refBdd.chargerDriver();
		
		// Se connecter a la BDD
		refBdd.connecterBdd();
		
		// Creer la requete
		refBdd.creerRequete();
		
		// Ajoute un titre a la fenetre
		setTitle("Gestion Comptes IHM");
		
		// Dimensionner la fenetre (300 pixels de large 
		// sur 400 de haut
		setSize(990, 450);
				
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
		
		pan.add(labelNumCpt);
		pan.add(zoneNumCpt);
		
		pan.add(labelMontant);
		pan.add(zoneMontant);
		
		pan.add(labelSolde);
		pan.add(zoneSolde);
		
		pan.add(bCreerClient);
		pan.add(bCreerCpt);
		pan.add(bDebiterCpt);
		pan.add(bCrediterCpt);
		pan.add(bConsulterCpt);
		pan.add(labelResultat);
		pan.add(zoneResultat);
		pan.add(bSaveBdd);
		
		// Enregistrer les boutons comme sources
		// d'evenements aupres de la fenetre IhmComptes
		bCreerClient.addActionListener(this);
		bCreerCpt.addActionListener(this);
		bDebiterCpt.addActionListener(this);
		bCrediterCpt.addActionListener(this);
		bConsulterCpt.addActionListener(this);
		
		bSaveBdd.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		String text, proprio;
		float nb;
		int numCpt;
		
		
		// Determiner la source de l'evenement
		if(evt.getSource() == bCreerCpt) {
			
			// Recuperer les contenus des zones solde
			// et nom du proprietaire
			
			// Recuperer le solde et le convertir en float
			text = zoneSolde.getText();
			nb = Float.parseFloat(text);
			
			// Recuperer le nom du proprio
			proprio = zoneNom.getText();
			
			// Recuperer le numero de compte
			text = zoneNumCpt.getText();
			numCpt = Integer.parseInt(text);
			
			// Creer l'objet Compte 
			cpt = new Compte(numCpt, nb, proprio);
			/*
			System.out.println(cpt.consulter());
			System.out.println(cpt.getNumCompte());
			System.out.println(cpt.getNomProprietaire());
			*/
			// Sauvegarder le compte cree dans la liste
			listCpt.add(cpt);
			/*
			System.out.println( cpt);
			for(int i = 0; i< listCpt.size(); i++) {
				System.out.println("OK");
			}
			*/
			
			/*--
			zoneResultat.append(cpt.getNomProprietaire() +"\n");
			zoneResultat.append(cpt.getNumCompte() +"\n");
			zoneResultat.append(cpt.consulter() +"\n");
			--*/
			
		}
		else if(evt.getSource() == bDebiterCpt) {
			
			// Recuperer le numero de compte a debiter
			text = zoneNumCpt.getText();
			
			// Convertir le numero saisi en int
			numCpt = Integer.parseInt(text);
			
			// Recherche du compte dans la liste
			cpt = listCpt.get(numCpt-1);
			
			// Debiter le compte
			cpt.debiter(200);
		}
		else if(evt.getSource() == bCrediterCpt) {
			
			// Recuperer le numero de compte a debiter
			text = zoneNumCpt.getText();
						
			// Convertir le numero saisi en int
			numCpt = Integer.parseInt(text);
						
			// Recherche du compte dans la liste
			if(listCpt.size() !=0) {
				cpt = listCpt.get(numCpt-1);
				
				// Debiter le compte
				cpt.crediter(200);
			}
			else
				System.out.println("Liste comptes vide");
			
		}
		else if(evt.getSource() == bConsulterCpt) {
			// Recuperer le numero de compte a debiter
			text = zoneNumCpt.getText();
									
			// Convertir le numero saisi en int
			numCpt = Integer.parseInt(text);
			System.out.println( "Num cpt "+numCpt);
									
			// Recherche du compte dans la liste
			if(listCpt.isEmpty()) {
				zoneResultat.append("La liste est vide!!"+"\n");
			}
			else {
				cpt = listCpt.get(numCpt-1);
			
				zoneResultat.append(cpt.getNomProprietaire() +"\n");
				zoneResultat.append(cpt.consulter() +"\n");
			}
		}
		else if(evt.getSource() == bSaveBdd) {
			
			// Parourir la liste des comptes
			// pour les enrgistrer dans la table
			// Verifier si la liste est non vide
			if(listCpt.isEmpty()) {
				zoneResultat.append("La liste est vide!!"+"\n");
			}
			else {
				
				// Delete de la table Compte
				String req = "DELETE FROM compte";
				refBdd.executerUpdate(req);
				
				// Parcourir la liste des comptes
				int i;
				
				for(i = 0; i < listCpt.size(); i++) {
					
					// Recuperer de la liste l'objet Compte
					// se trouvant a l'indice i
					cpt = listCpt.get(i);
					
					// Recuperer les valeurs des attributs de
					// l'objet cpt (numCompte, solde, nomProprio...)
					numCpt = cpt.getNumCompte();
					nb = cpt.consulter();
					text = cpt.getNomProprietaire();
					
					// Formater la requete INSERT INTO
					String requete;
					requete = "INSERT INTO compte (numero,nom,solde) VALUES (";
					requete = requete + numCpt + ","+"'" + text + "'" + "," +nb +")";  
					
					// Executer la requete
					refBdd.executerUpdate(requete);
				}
			}
		}
	}
}
