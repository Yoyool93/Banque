package ProjetBanque;

import java.util.LinkedList;

public class Banque {

	private String raisonSoc;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
	private String adr;
	private int capital;
	private Compte cpt;
	private BddAccess refBdd;
	private IhmComptes refIhm;
	
	private int nbComptes = 0;
	
	private LinkedList<Compte> listComptes; 
	
	public Banque(String rs, String adresse, int cap, BddAccess bdd , IhmComptes ihm) {
		raisonSoc = rs;
		adr = adresse;
		capital = cap;
		refBdd = bdd;
		refIhm= ihm;
		
		// Creation de la liste de comptes
		listComptes = new LinkedList<Compte>();
	}

	public void ouvrirCompte(int idClient, String nom, String prenom, String adresse, float s) {
		
		cpt = new Compte((nbComptes+1), s, idClient,refIhm);
		
		// Placer le compte cree dans la liste
		listComptes.add(cpt);
		
		// Sauvegarder le compte dans la BDD
		// Formater la requete INSERT INTO
		String requete;
		int num = nbComptes+1;
		requete = "INSERT INTO compte (nom,prenom,adresse,ident,numCompte,solde) VALUES (";
		requete = requete + '"'+nom +'"' +","+ '"'+prenom+'"'+"," + '"'+adresse+'"'+"," +idClient +"," + num +"," + s +")";
		
		// Executer la requete
		refBdd.executerUpdate(requete);
		
		// Un compte de plus cree
		nbComptes++;
	}

		public void recupererMontantSolde(int idClient) {
		boolean trouve = false;
		int i = 0;
		
		// Verifier si cet Id existe dans la liste des comptes
		while(trouve == false && i < listComptes.size()) {
			cpt = listComptes.get(i);
			
			if(idClient == cpt.getidCli()) {
				trouve = true;
				refIhm.afficherDansZoneArea(""+cpt.consulter());
			}
			i++;
		}
		if(trouve == false) {
			refIhm.afficherDansZoneArea("Id Client non trouve" +"\n");
		}
	}

		public void crediterCompte(int ident, float somme) {
			
				boolean trouve = false;
				int i = 0;
				String req;
				float s=0;
				
				
				// Verifier si cet Id existe dans la liste des comptes
				while(trouve == false && i < listComptes.size()) {
					cpt = listComptes.get(i);
					
					if(ident == cpt.getidCli()) {
						trouve = true;
						cpt.crediter(somme);
						s = cpt.consulter();
					}
					//MAJ BDD
					req="update compte set solde=" + s + "where ident="+ident; 
					//Executer la requete
					refBdd.executerUpdate(req);
					
					i++;
				}
				if(trouve == false) {
					refIhm.afficherDansZoneArea("Id Client non trouve" +"\n");
				}
			}

		public void debiterCompte(int ident, float somme) {
		

			boolean trouve = false;
			int i = 0;
			String req;
			float s=0;
			
			
			// Verifier si cet Id existe dans la liste des comptes
			while(trouve == false && i < listComptes.size()) {
				cpt = listComptes.get(i);
				
				if(ident == cpt.getidCli()) {
					trouve = true;
					cpt.debiter(somme);
					s=cpt.consulter();
					
				}
				//MAJ BDD
				req="update compte set solde=" + s + "where ident="+ident; 
				//Executer la requete
				refBdd.executerUpdate(req);
				}
				i++;
			
			if(trouve == false) {
				refIhm.afficherDansZoneArea("Id Client non trouve" +"\n");
			}
		}
		}
		

