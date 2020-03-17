
public class Arc {
    public Node node1; // Une extremite
    public Node node2; // L'autre extremite
    public User user;
    public int temps; // Temps de communication

    public Arc (Node unNode1, Node unNode2,int unTemps){ // Constructeur
        this.node1 = unNode1;
        this.node2 = unNode2;
        this.temps = unTemps;
    }
    public Arc (User unUser, Node unNode, int unTemps){
        this.user = unUser;
        this.node2 = unNode;
        this.temps = unTemps;
    }
    // Getters
    public int getTemps(){
        return this.temps;
    }
    public Node getNode1(){ return this.node1;}
    public Node getNode2(){ return this.node2;}
    public User getUser(){ return this.user;}
    //Affichage
    public void affichageNN(){
        System.out.print("ARC entre le NODE ".concat(Integer.toString(node1.getIndice())));
        //node1.affichage();
        System.out.print(" et le NODE ".concat(Integer.toString(node2.getIndice())));
        //node2.affichage();
        System.out.println(" de temps : ".concat(Integer.toString(this.temps)));
    }
    public void affichageUN(){
        System.out.print("ARC entre l'USER ".concat(Integer.toString(user.getIndice())));
        //node1.affichage();
        System.out.print(" et le NODE ".concat(Integer.toString(node2.getIndice())));
        //node2.affichage();
        System.out.println(" de temps : ".concat(Integer.toString(this.temps)));
    }
}
