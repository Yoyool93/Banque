package ProjetBanque;
public class Compte {

	// Attributs
	private int numCompte;
	private float solde;
	private String nomProprietaire;
	
	// Constructeur
	public Compte(int numCpt, float soldInit, String nom) {
		setNumCompte(numCpt);
		solde = soldInit;
		setNomProprietaire(nom);
	}
	
	// Methodes 
	public float consulter() {
		return solde;
	}
	

	public void debiter(float somme) {
		solde = solde - somme;
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

	public String getNomProprietaire() {
		return nomProprietaire;
	}

	public void setNomProprietaire(String nomProprietaire) {
		this.nomProprietaire = nomProprietaire;
	}
}
