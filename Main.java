//import java.util.ArrayList;

public class Main {
	
	public static void main(String []args ){
		// Création du graphe initial
		Graph graph = new Graph();
		// Ajout des noeuds
		graph.ajoutNode(1,40);
		graph.ajoutNode(2,40);
		graph.ajoutNode(3,50);
		// Ajout des utilisateurs
		graph.ajoutUser(1);
		graph.ajoutUser(2);
		// Ajout des donnees
		graph.ajoutData(0,40);
		graph.ajoutData(1,25);
		graph.ajoutData(2,25);
		// Relier les utilisateur au donnees qui veulent
		graph.ratacheDataUser(1, 0);
		graph.ratacheDataUser(1, 1);
		graph.ratacheDataUser(1, 2);
		// Relier les noeuds entre eux
		graph.relierVoisins(1,2,1);
		graph.relierVoisins(1,3,1);
		// Relier les utilisateurs a un des noeuds du graph
		graph.relierUserNode(1,3,2);
		graph.relierUserNode(2,2,2);
		// Placement des datas
		graph.placerData();
		// Dijkstra
		graph.dijkstra(3,2);
		//AFFICHAGE
		//graph.affichage();

	}
}

/*
Indexage automatique
Commentaire : Main, Graph, Arc
Graph : Finir placementDataUsers
revoir testfin

 */
