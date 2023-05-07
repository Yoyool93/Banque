package ProjetBanque;
public class Compte {

	// Attributs
	private int numCompte;
	private float solde;
	private int idClient;
	private IhmComptes refIhm;
	// Constructeur
	public Compte(int numCpt, float soldInit, int id,IhmComptes ihm) {
		numCompte=numCpt;
		solde = soldInit;
		idClient=id;
		refIhm =ihm;
	}
	
	// Methodes 
	public float consulter() {
		return solde;
	}
	

	public void debiter(float somme) {
		if(solde>= somme) {
		solde = solde - somme;
	}
		else { 
			refIhm.afficherDansZoneArea("Desole solde insuffisant ");
		}
		
	}
	
	public void crediter(float somme) {
		solde = solde + somme;
	}

	public int getNumCompte() {
		return numCompte;
	}

	public void setNumCompte(int numCompte) {
		this.numCompte = numCompte;
	}

	public int getidCli() {
		return idClient;
	}

	public void setidCli(int idCli) {
		this.idClient = idCli;
	}
}
