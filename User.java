import java.util.ArrayList;

public class User {
	public int idUser;
	public Node NodeVoisin; // Noeud auquel l'utilisateur est ratache
	public ArrayList<Data> DataVoulu ; // Liste des data voulu
	
	public User(ArrayList<User> listeId,int unId){
		boolean existe = false;
		for (User utilisateur : listeId){
			if (utilisateur.getIndice() == unId){ // Verification de l'unicite de l'index
				existe=true;
			}
		}
		if (existe==false){ // Si pas de probleme d'index => constructeur "classique"
			this.idUser = unId;
			//this.idNodeVoisin = unIdNodeVoisin;
			this.DataVoulu = new ArrayList<Data>();
		} else {
			System.out.println("Indice déjà existant");
		}
	}
	// Getter
    public int getIndice(){
        return this.idUser;
    }
    public Node getNodeVoisin(){return this.NodeVoisin;}
    
	public void ajoutData(Data uneData){DataVoulu.add(uneData); } // Ajout une donnee a la liste des data voulues + utiliser dans graph.RattacherDataUser

	public void RatacheUser(Node unNode){
		this.NodeVoisin = unNode;
	} //Ajout un noeud voisin

	public ArrayList<Data> getDataVoulu(){ return this.DataVoulu; }

	public boolean interetData(Data uneData){
	    boolean existe=false;
	    for (Data uneDonne : DataVoulu){
	        if (uneDonne==uneData){
	            existe=true;
            }
        }
	    return existe;
    }
}
