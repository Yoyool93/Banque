package ProjetBanque;

public class Client {

	private String nom;
	private String prenom;
	private String adresse;
	private int idClient;
	private Banque refBanque;
	
	public Client(int id, String name, String pren , String ad, Banque b) {
		
		idClient =id;
		nom = name;
		prenom = pren;
		adresse=ad;
		refBanque= b;
	}
	
	
	public void allerOuvrirUnCompte(float s) {
		refBanque.ouvrirCompte(idClient,nom,prenom,adresse,s);
	}
	
	public void allerConsulterCompte() {
		refBanque.recupererMontantSolde(idClient);
		
	}
	
	public void allerDeposerSommeSurCompte(int ident ,float somme) {
		refBanque.crediterCompte(ident,somme);
	}
	
	public void allerRetirerSommeDeCompte(int ident , float somme) {
		refBanque.debiterCompte(ident,somme);
	}
	public int getId() {
		return idClient;
	}
}