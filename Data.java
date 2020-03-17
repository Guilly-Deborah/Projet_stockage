import java.util.ArrayList;

public class Data {
	public int idData; // Indice de la data
	public int taille; // Taille de la donnee
	public Node lieu;
	public boolean existeNode;
	
	public Data(ArrayList<Data> listeId, int unId, int uneTaille){
		//Verification de l'unicite de l'index
		boolean existe = false;
		for (Data donnee : listeId){
			if (donnee.getIndice() == unId){
				existe=true; // Si l'index existe deja
			}
		}
		if (existe==false){
			// Si pas de probleme d'index => constructeur "classique"
			this.idData=unId;
			this.taille=uneTaille;
			this.existeNode=false;
		} else {
			System.out.println("Indice déjà existant");
		}
		
	}
	// Getters
	public int getIndice(){
		return this.idData;
	}
	public int getTaille(){
		return this.taille;
	}
	public boolean getExisteNode(){ return this.existeNode;}
	public Node getLieu(){return this.lieu;}

	public void ajoutNode(Node unNode){
		this.lieu=unNode;
		this.existeNode=true;
	}
}

//Afichage
	/*public void affichage(){
		System.out.println("	Indice ".concat(Integer.toString(this.idData)).concat(" Taille ").concat(Integer.toString(this.taille)));
		// Ex : Indice 4 Taille 25
	}*/