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
	public User userVoulu2;


	public Graph() { //Constructeur
		this.listeData = new ArrayList<Data>();
		this.listeUsers = new ArrayList<User>();
		this.listeNodes = new ArrayList<Node>();
		this.listeArcs = new ArrayList<Arc>();
		compteur = 0;
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
			for (Data donne : unUser.getDataVoulu()) {
				if (donne.getIndice() == unIdData) {
					existe = true;
				}
			}
		}
		return existe;
	}

	public void ratacheDataUser(int unIdUser, int unIdData) { //Relier une data a un utilisateur
		//if (rechercheUserVerife(unIdData) == false) { // Si la data n'est pas déja reliee
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
		//} else {
		//	System.out.println("Data déjà reliée");
		//}

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

	public User rechercheUser(int unIdData) {
		//Recherche user interesse par la data
		for (User unUser : listeUsers) {
			for (Data donne : unUser.getDataVoulu()) {
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

	public ArrayList<Arc> dijkstra(int idNoeudVoulu, int idUser) { // Algorithme de Dijkstra pour trouver le plus court chemin
		ArrayList<Arc> chemin = new ArrayList<Arc>();
		ArrayList<Node> NAV = new ArrayList<Node>();
		for (Node unNode : listeNodes) { // Création de la liste des noeud a visites
			NAV.add(unNode);
		}
		ArrayList<Float> distance = new ArrayList<Float>();
		for (int i = 0; i < NAV.size(); i++) { // Initialisation des distances a l'infini
			distance.add(Float.POSITIVE_INFINITY);
		}
		for (User unUser : listeUsers) { //Recherche de l'user correspondant a l'id
			if (unUser.getIndice() == idUser) {
				this.userVoulu = unUser;
			}
		}
		for (Arc unArc : listeArcs) { // Recherche de l'arc entre l'user et son node voisin
			if (unArc.getUser() != null) {
				if (userVoulu.getNodeVoisin().getIndice() == unArc.getNode2().getIndice() && userVoulu.getIndice() == unArc.getUser().getIndice()) {
					arcVoulu = unArc;
				}
			}
		}
		int indexNoeud = indexListe(NAV, userVoulu.getNodeVoisin()); //Indice du voisin dans NAV
		distance.set(indexNoeud, (float) arcVoulu.getTemps()); // Mise a jour de la premiere distance
		NAV.remove(userVoulu.getNodeVoisin()); //Suppression du noeud des noeud a visite
		Node nodeCurrent = userVoulu.getNodeVoisin(); // Initialisation du noeud courant
		Node exNode = userVoulu.getNodeVoisin(); // Initialisation du l'ancien noeud courant
		boolean stop = false; // Condition d'arret
		while (stop == false) {
			distance = miseAJourDist(distance, nodeCurrent, NAV); // Mise a jour des distances
			if (nodeCurrent.getIndice() != idNoeudVoulu) { // Si le noeud courant est le noeud voulu
				exNode=nodeCurrent; // le noeud courant devient l'ancien noeud courant
				nodeCurrent = nouveauNodeCourant(nodeCurrent, distance, NAV); // Determination du nouveau noeud courant
				NAV.remove(nodeCurrent); // Suppression du noeud courant des noeuds a vistes
			} else {
				stop = true; // Condition d'arret
			}
			for (Arc unArc : listeArcs) { // Recherche de l'arc entre le nouveau noeud courant et l'ancien
				if (unArc.getNode1()!=null){
					if (unArc.getNode2().getIndice() == nodeCurrent.getIndice()&& unArc.getNode1().getIndice() == exNode.getIndice()) {
						chemin.add(unArc);
					} else if (unArc.getNode2().getIndice() == exNode.getIndice() && unArc.getNode1().getIndice() == nodeCurrent.getIndice()) {
						chemin.add(unArc);
					}
				}
			}
		}
		// Affcichage du chemin
		System.out.println("Dijkstra");
		System.out.print("Chemin entre USER ".concat(Integer.toString(idUser)));
		System.out.println(" et le NODE ".concat(Integer.toString(idNoeudVoulu)));
		affichageListeArc(chemin);
		return chemin;
	}


	public void affichageListeFloat(ArrayList<Float> uneListe) { // Affichage des élément d'une liste de flotant
		for (float unFloat : uneListe) {
			System.out.print("FLOAT ".concat(Float.toString(unFloat)).concat(", "));
		}
		System.out.println("");
	}

	public void affichageListeArc(ArrayList<Arc> uneListe) { // Affichage des élément d'une liste d'arcs
		for (Arc unArc : uneListe) {
			unArc.affichageNN();
		}
	}

	public int indexListe(ArrayList<Node> uneListe, Node unNode) { // Determination de l'indice d'un node dans une liste de nodes
		int index = 0;
		int cpt = 0;
		for (Node unNoeud : uneListe) {
			if (unNode == unNoeud) {
				index = cpt;
			}
			cpt = cpt + 1;
		}
		return index;
	}

	public boolean dansNAV(ArrayList<Node> uneListe, Node unNode) { // Verification existance d'un node dans une liste de nodes
		boolean existe = false;
		for (Node unNoeud : uneListe) {
			if (unNoeud == unNode) {
				existe = true;
			}
		}
		return existe;
	}

	public ArrayList<Float> miseAJourDist(ArrayList<Float> distances, Node nodeCourant, ArrayList<Node> NAV) { // Mise a jour des distances de dijkstra
		ArrayList<Node> voisins = nodeCourant.getVoisins();
		for (Node unVoisin : voisins) {
			if (dansNAV(NAV, unVoisin) == true) { // Si le voisin peut etre visite
				for (Arc unArc : listeArcs) { // Recherche de l'arc entre l'user et son node voisin
					if (unArc.getNode1() != null) { // Arc sans user
						if (unVoisin.getIndice() == unArc.getNode2().getIndice() && nodeCourant.getIndice() == unArc.getNode1().getIndice()) {
							arcVoulu = unArc;
						} else if (unVoisin.getIndice() == unArc.getNode1().getIndice() && nodeCourant.getIndice() == unArc.getNode2().getIndice()) {
							arcVoulu = unArc;
						}
					}
				}
				int poids = arcVoulu.getTemps(); // Recherche poids de l'arc entre le node courant et son voisin
				float distNodeCourant = distances.get(indexListe(listeNodes, nodeCourant)); // Recuperation de la distance au noeud courant
				float distVoisin = distances.get(indexListe(listeNodes, unVoisin)); // Recuperation de la distance au voisin
				if (distVoisin > distNodeCourant + poids) { // Si la distance entre le voisin et le noeud racine est plus grand que celle calcule
					distances.set(indexListe(listeNodes, unVoisin), distNodeCourant + poids); // On remplace sa valeur dans la liste des distance
				}
			}
		}
		return distances;
	}

	public Node nouveauNodeCourant( Node exNode, ArrayList<Float> distances, ArrayList<Node> NAV) { // Determine le nouveau noeud courant
		float mini = Float.POSITIVE_INFINITY;
		for (Node unVoisin : exNode.getVoisins()) { // Parcours des voisins de l'ancien noeud courant
			if (distances.get(indexListe(listeNodes, unVoisin)) < mini) { // Recherche de celui avec la plus petite distance
				if (dansNAV(NAV, unVoisin) == true) { // Verification qu'il est dans les noeuds a visites
					mini = distances.get(indexListe(listeNodes, unVoisin)); 
					noeud1 = unVoisin; // Mise a jour du noeud courant
				}
			}
		}
		return noeud1;
	}


	public void placementDataUsers(int idUser1, int idUser2, int idData) { //Place une donnee voulu par deux utilisateurs
		for (User unUser : listeUsers) { // Recherche du premier utilisateur
			if (unUser.getIndice() == idUser1) {
				userVoulu = unUser; 
			}
		}
		for (User unUser : listeUsers) { // Recherche du deuxieme utilisateur
			if (unUser.getIndice() == idUser2) {
				userVoulu2 = unUser;
			}
		}
		noeud1 = userVoulu.getNodeVoisin(); //Recuperation de leurs noeuds voisins
		noeud2= userVoulu2.getNodeVoisin();
		ArrayList<Arc> chemin = dijkstra(noeud1.getIndice(), idUser2); // Calcul du chemin entre les deuxieme user et le noeud voisin du premier user
		int temps1 = 0; //Initialisation des temps entre les utilisateur et un noeud
		int temps2 = 0;
		temps1 = temps1 + arcTempsUN(idUser1, noeud1.getIndice()); // Parcours des premiers arc entre les utilisateurs et leurs noeuds voisins
		temps2 = temps2 + arcTempsUN(idUser2, noeud2.getIndice());
		while (noeud1.getIndice() != noeud2.getIndice()){ // Tant que les deux noeud ne sont pas identique
			if (temps1==temps2 || temps1>temps2){
				arcVoulu=chemin.get(0); //Recuperation du premier arc du chemin
				temps2 = temps2 + arcVoulu.getTemps(); // Mise a jour du temps entre l'utilisateur 2 et le noeud parcouru
				chemin.remove(arcVoulu);
				int indiceAutreEx =arcVoulu.autreExtremite(noeud2.getIndice()); // Recuperation de l'autre extremite de l'arc
				for (Node unNoeud : listeNodes){
					if(indiceAutreEx==unNoeud.getIndice()){
						noeud2=unNoeud; // Mise a jour du noeud parcour par l'utilisateur 2
					}
				}
			}else if (temps2>temps1){
				if (chemin.size()>1){ //Recuperation du dernier arc du chemin
					arcVoulu=chemin.get(chemin.size()-1);
				}else{
					arcVoulu=chemin.get(0);
				}
				temps1 = temps1 + arcVoulu.getTemps(); // Mise a jour du temps entre l'utilisateur 1 et le noeud parcouru
				chemin.remove(arcVoulu);
				int indiceAutreEx =arcVoulu.autreExtremite(noeud1.getIndice()); // Recuperation de l'autre extremite de l'arc
				for (Node unNoeud : listeNodes){
					if(indiceAutreEx==unNoeud.getIndice()){
						noeud1=unNoeud; // Mise a jour du noeud parcour par l'utilisateur 1
					}
				}
			}
		}
		for (Data uneData : listeData){
			if (uneData.getIndice()==idData){
				noeud1.ajoutData(uneData); // Placement de la data sur le bon noeud
			}
		}
	}
	public void placementDonne(){
	    int cpt = 0;
	    ArrayList<Integer> listeInteret = new ArrayList<Integer>();
	    for (Data uneData : listeData){
	        for (User unUser : listeUsers){
	            if (unUser.interetData(uneData)==true){
	                cpt=cpt+1;
	                listeInteret.add(unUser.getIndice());
                }
            }
            if (cpt==1){ // la donnee est voulu par un utilisateur
                userVoulu = rechercheUser(uneData.getIndice());
                noeud1 = userVoulu.getNodeVoisin();
                noeud1.ajoutData(uneData);
            } else if (cpt==2){ // la donnee est voulu par deux utilisateurs
                placementDataUsers(listeInteret.get(0),listeInteret.get(1),uneData.getIndice());
            }
            cpt=0;
        }
    }

	public int arcTempsUN(int unIdUser, int unIdNode) { // Recupaire le poid d'un arc entre un utilisateur et un noeud
		for (Arc unArc : listeArcs) {
			if (unArc.getUser()!= null){
				if (unArc.getNode2().getIndice()==unIdNode && unArc.getUser().getIndice()==unIdUser) {
					return unArc.getTemps();
				}
			}
		}
		return 0;
	}

	public int arcTempsNN(int unIdNode1, int unIdNode2) { // Recupaire le poid d'un arc entre deux noeuds
		for (Arc unArc : listeArcs) {
			if (unArc.getNode2().getIndice() == unIdNode2 && unArc.getNode1().getIndice() == unIdNode1) {
				return unArc.getTemps();
			} else if (unArc.getNode2().getIndice() == unIdNode1 && unArc.getNode1().getIndice() == unIdNode2) {
				return unArc.getTemps();
			}
		}
		return 0;
	}
    public void placerDataMKP() { //Placement de toutes les datas par ordre de taille
        trierListeDataTaille();
        for (Data uneData : listeData) {
            userVoulu = rechercheUser(uneData.getIndice());
            noeud1 = userVoulu.getNodeVoisin();
            noeud1.ajoutData(uneData);
        }
    }

    public void trierListeDataTaille() { // Trie une liste de data selon leurs tailles
        int size = listeData.size();
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < (size - i); j++) {
                if (listeData.get(j - 1).getTaille() > listeData.get(j).getTaille()) {
                    Data mem = listeData.get(j - 1);
                    listeData.set(j - 1, listeData.get(j));
                    listeData.set(j, mem);
                }
            }
        }
    }
}
