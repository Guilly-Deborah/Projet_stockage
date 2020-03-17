import java.util.ArrayList;

public class Graph {
	public ArrayList<Data> listeData;// Liste de toutes les data du graph
	public ArrayList<User> listeUsers; // Liste de tous les utilisateurs relie au graph
	public ArrayList<Node> listeNodes; // Liste de tous les noeuds du graph
	public ArrayList<Arc> listeArcs; // Liste des arcs du graph
	public User userVoulu; // Utile pour ratacher une donnee a un utilisateur
	public Data dataVoulu; // idem
	public Node noeud1;
	public Node noeud2;
	public int compteur;
	public Arc arcVoulu;


	public Graph() { //Constructeur
		this.listeData = new ArrayList<Data>();
		this.listeUsers = new ArrayList<User>();
		this.listeNodes = new ArrayList<Node>();
		this.listeArcs = new ArrayList<Arc>();
		compteur = 0;
	}

	// Getters
	public ArrayList<Data> getListeData() {
		return this.listeData;
	}

	public ArrayList<Node> getListeNodes() {
		return this.listeNodes;
	}

	public ArrayList<User> getListeUsers() {
		return this.listeUsers;
	}

	public int getSizeListeData() {
		return this.listeData.size();
	}

	// Ajout de nouveau element aux liste
	public void ajoutData(int unId, int uneTaille) {
		listeData.add(new Data(this.listeData, unId, uneTaille));
		System.out.println("DATA ".concat(Integer.toString(unId)).concat(" de taille ").concat(Integer.toString(uneTaille)));
	}
	public void ajoutUser(int unId) {
		listeUsers.add(new User(this.listeUsers, unId));
		System.out.println("USER ".concat(Integer.toString(unId)));
	}
	public void ajoutNode(int unId, int uneCapacite) {
		listeNodes.add(new Node(this.listeNodes, unId, uneCapacite));
		System.out.println("NODE ".concat(Integer.toString(unId)).concat(" de capacité ").concat(Integer.toString(uneCapacite)));
	}
	// Relier un User et une Data
	public boolean rechercheUserVerife(int unIdData) {
		boolean existe = false;
		//Recherche user interesse par la data
		for (User unUser : listeUsers) {
			for (Data donne : unUser.getIdDataVoulu()) {
				if (donne.getIndice() == unIdData) {
					existe = true;
				}
			}
		}
		return existe;
	}

	public void ratacheDataUser(int unIdUser, int unIdData) { //Relier une data a un utilisateur
		if (rechercheUserVerife(unIdData) == false) { // Si la data n'est pas déja reliee
			//Recherche de la data
			for (Data uneDonnee : listeData) {
				if (uneDonnee.getIndice() == unIdData) {
					this.dataVoulu = uneDonnee;
				}
			}
			//Recherche de l'user
			for (User unUser : listeUsers) {
				if (unUser.getIndice() == unIdUser) {
					this.userVoulu = unUser;
				}
			}
			userVoulu.ajoutData(dataVoulu); // Fonction dans User
			// Affichage
			System.out.print("l'USER ".concat(Integer.toString(userVoulu.getIndice())));
			System.out.println(" a accès à la DATA ".concat(Integer.toString(dataVoulu.getIndice())));
		} else {
			System.out.println("Data déjà reliée");
		}

	}
	// Relier deux Node
	public boolean verificationVoisins(int unIdNode1, int unIdNode2) {
		boolean existe = false;
		// Si il existe deja un arc entre les deux nodes
		for (Arc unArc : listeArcs) {
			if (unIdNode1 == unArc.getNode1().getIndice() && unIdNode2 == unArc.getNode2().getIndice()) {
				existe = true;
			} else if (unIdNode1 == unArc.getNode2().getIndice() && unIdNode2 == unArc.getNode1().getIndice()) {
				existe = true;
			}
		}
		return existe;
	}
	public void relierVoisins(int unIdNode1, int unIdNode2, int unTemps) { // Relie deux nodes entre eux
		if (verificationVoisins(unIdNode1, unIdNode2) == false) { // Verification qui n'existe pas deja d'arc entre eux
			for (Node unNoeud : listeNodes) { // Recherche des deux nodes
				if (unNoeud.getIndice() == unIdNode1) {
					this.noeud1 = unNoeud;
				}
				if (unNoeud.getIndice() == unIdNode2) {
					this.noeud2 = unNoeud;
				}
			}
			Arc arc = new Arc(noeud1, noeud2, unTemps); //Creation de l'arc
			listeArcs.add(arc); // Ajout a la liste des arcs
			arc.affichageNN(); // Afficher l'arc
			noeud1.ajoutVoisin(noeud2); // Ajout des noeud a leurs listes de voisin
		} else {
			System.out.println("Les deux NODE sont déjà relier");
		}
	}
	// Relier un User a un Node
	public boolean verificationUserNode(int unIdUser, int unIdNode) {
		boolean existe = false;
		for (Arc unArc : listeArcs) { // Verification de l'existence d'un arc
			if (unArc.getUser() != null) { // Cas ou l'arc possede un User
				if (unIdNode == unArc.getNode2().getIndice() && unIdUser == unArc.getUser().getIndice()) {
					existe = true;
				}
			}
		}
		return existe;
	}
	public void relierUserNode(int unIdUser, int unIdNode, int unTemps) { // Relie un node a un user
		if (verificationUserNode(unIdUser, unIdNode) == false) {
			for (Node unNoeud : listeNodes) { // Recherche du node
				if (unNoeud.getIndice() == unIdNode) {
					this.noeud2 = unNoeud;
				}
			}
			for (User unUser : listeUsers) { // Recherhce de l'user
				if (unUser.getIndice() == unIdUser) {
					this.userVoulu = unUser;
				}
			}
			this.noeud2.ajoutUser(userVoulu);// Ajout de l'utilisateur au noeud
			//System.out.println("abc ".concat(Integer.toString(userVoulu.getNodeVoisin().getIndice())));
			Arc arc = new Arc(userVoulu, noeud2, unTemps); // Creation de l'arc
			listeArcs.add(arc); // Ajout a la liste des arcs
			arc.affichageUN(); // Afficher l'arc
		} else {
			System.out.println("Le NODE et l'USER sont déjà reliés");
		}
	}
	public Data rechercheData(int unIdData) {
		for (Data uneData : listeData) {
			if (uneData.getIndice() == unIdData) {
				dataVoulu = uneData;
			}
		}
		return dataVoulu;
	}

	public User rechercheUser(int unIdData) {
		//Recherche user interesse par la data
		for (User unUser : listeUsers) {
			for (Data donne : unUser.getIdDataVoulu()) {
				if (donne.getIndice() == unIdData) {
					userVoulu = unUser;
				}
			}
		}
		return userVoulu;
	}

	public void placerData() { //Placement de toutes les datas par ordre d'indice
		trierListeData();
		for (Data uneData : listeData) {
			userVoulu = rechercheUser(uneData.getIndice());
			noeud1 = userVoulu.getNodeVoisin();
			noeud1.ajoutData(uneData);
		}
	}

	public void trierListeData() { // Trie une liste de data selon leurs indices
		int size = listeData.size();
		for (int i = 0; i < size; i++) {
			for (int j = 1; j < (size - i); j++) {
				if (listeData.get(j - 1).getIndice() > listeData.get(j).getIndice()) {
					Data mem = listeData.get(j - 1);
					listeData.set(j - 1, listeData.get(j));
					listeData.set(j, mem);
				}
			}
		}
	}
}
