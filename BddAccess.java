package ProjetBanque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class BddAccess {

	// Attributs
	// Classes necessaires a l'acces Bdd
	private Connection cnx = null;
	private Statement st = null;
	private ResultSet res = null;
	
	// Permet de recuperer des informations 
	// sur la reponse (nombre de colonnes, noms des 
	// colonnes, nom de la table ...)
	private ResultSetMetaData resMeta = null;
	
	//Reference vers Ihm afin d'ecrire les reponses dans la JTextArea
	private IhmComptes refIhm;
	
	// Constructeur
	
	// Methodes
	public void chargerDriver() {
		
		// Chargement du Driver (pilote)
		try {
			//recherche et chargement du driver appropri� � la BD
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver trouve!!!");
					
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void connecterBdd() {
		
		//Etablissement de la connexion � la base de donn�es
		// Connection a la BDD Films
		try {
			cnx = DriverManager.getConnection("jdbc:mysql://localhost/"+"banque"+"?autoReconnect=true&useSSL=false","root","");
			System.out.println("Connexion BDD Banque OK!");
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void creerRequete() {
		// construction de la requete SQL
		// Creation de la requete
		try {
			st = cnx.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executerRequete(String requete) {
		// Executer la requete
		try {
			res = st.executeQuery(requete);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executerUpdate(String requete) {
		// Executer la requete
		try {
			st.executeUpdate(requete);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void afficherResultats() {
		
		try {
			resMeta = res.getMetaData();
			
			// Nombre de colonnes contenues dans 
			// la reponse
			int nbCols = resMeta.getColumnCount();
			
			for(int i = 1; i <= nbCols; i=i+1) {
				
				System.out.print(resMeta.getColumnName(i) + " | " +"    ");
			}
			System.out.println();
			
			// Parcours de la reponse res
			while(res.next()) {
				for(int i = 1; i <= nbCols; i++){ 
					System.out.print(res.getObject(i).toString() + "   ");
				}
				
				System.out.println();
				System.out.println("------------------------------------");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

public ResultSet recupereReponse() {
		String[] rep;
		String s = "";
		
		try {
			resMeta = res.getMetaData();
			
			// Nombre de colonnes contenues dans 
			// la reponse
			int nbCols = resMeta.getColumnCount();
			
			for(int i = 1; i <= nbCols; i=i+1) {
				
				//System.out.print(resMeta.getColumnName(i) + " | " +"    ");
				refIhm.afficherDansZoneArea(resMeta.getColumnName(i) + " | " +"    ");
			}
			//System.out.println();
			refIhm.afficherDansZoneArea("\n");
			
			// Parcours de la reponserefIhm.afficherDansZoneArea(""); res
			
			while(res.next()) {
				for(int i = 1; i <= nbCols; i++){ 
					refIhm.afficherDansZoneArea(res.getObject(i).toString() + "   ");
					
					//System.out.print(res.getObject(i).toString() + "   ");
				}
				
				
				//System.out.println();
				refIhm.afficherDansZoneArea("\n");
				//System.out.println("------------------------------------");
				refIhm.afficherDansZoneArea("------------------------------------");
				refIhm.afficherDansZoneArea("\n");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	public void setRefIhm(IhmComptes ihm ) {
		refIhm = ihm;
	}
}



