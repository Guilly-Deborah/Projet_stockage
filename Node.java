import java.util.ArrayList;

public class Node {
	public int idNode;
	public int capacite;
	public ArrayList<Data> idData; // Liste des datas contenu
	public ArrayList<User> idUser; // Liste des utilisateur reliés
	public ArrayList<Node> Voisins; // Liste des voisins
	public ArrayList<Node> nodesVisite;
	
	public Node(ArrayList<Node> listeId, int unId, int uneCapacite){
		//Verification de l'unicite de l'index
		boolean existe = false;
		for (Node noeud : listeId){
			if (noeud.getIndice() == unId){
				existe=true; // Si l'index existe deja
			}
		}
		if (existe==false){
			// Si pas de probleme d'index => constructeur "classique"
			this.idNode = unId;
			this.capacite = uneCapacite;
			this.idData = new ArrayList<Data>();
			this.idUser = new ArrayList<User>();
			this.Voisins = new ArrayList<Node>();
			this.nodesVisite = new ArrayList<Node>();
		} else {
			// Sinon affichage erreur
			System.out.println("Indice déjà existant");
		}
	}
    //Getters
    public int getIndice(){
        return this.idNode;
    }
    public ArrayList<Node> getVoisins(){return this.Voisins;}

	public boolean verificationAjoutData(Data uneData){ // Permet d'ajouter une data dans le noeud
		boolean ajoutPossible=false;
		if (uneData.getTaille()<=this.capacite) { // Verification de la place suffisante
			ajoutPossible = true;
		}
		return ajoutPossible;
	}
	public boolean verificationNodeVisite(){
		boolean existe=false;
		for (Node unNode : nodesVisite){
			if (this==unNode){
				existe=true;
			}
		}
		return existe;
	}
	public void ajoutData(Data uneData){
		nodesVisite.add(this);
		if (verificationAjoutData(uneData)){
			idData.add(uneData);
			uneData.ajoutNode(this);
			this.capacite=this.capacite-uneData.getTaille();// Mise a jour de la capacite restante du noeud
			System.out.print("DATA ".concat(Integer.toString(uneData.getIndice())));
			System.out.print(" sur le NODE ".concat(Integer.toString(this.idNode)));
			System.out.println(" de capacité restante ".concat(Integer.toString(this.capacite)));
			nodesVisite.clear();
		}else if (verificationAjoutData(uneData)==false){
			for (Node unVoisin : Voisins){
				if (unVoisin.verificationNodeVisite()==false && uneData.getExisteNode()==false){
					unVoisin.ajoutData(uneData);
				}
			}
		}
	}

	public void ajoutUser(User unUser){
		idUser.add(unUser); // Ajout un utilisateur
		unUser.RatacheUser(this); // Ajoute le noeud actuel comme noeud de ratachement a l'utilisateur
	}
	public void ajoutVoisin(Node unVoisin){ // Utiliser dans graph.relierVoisins
		boolean existe = false;
		for (Node noeud : Voisins){
			if (noeud.getIndice() == unVoisin.getIndice()){ //Verification existance dans la liste des voisins
				existe=true;
			}
		}
		if (existe==false){
			Voisins.add(unVoisin); // Ajout du voisin
			unVoisin.ajoutVoisin(this); // Ajout du noeud courant au noeud voisin
		}
		
	}

}
