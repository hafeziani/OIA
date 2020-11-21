package ProjetF1;

import java.util.ArrayList;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class ProjF1 {
    public static void main(String[] args) {
        // dimention de image
        int N = 10;
        // creation du modele
        Model model = new Model("reconstituer_une_image:" +N+ "." +N);

        // les valeurs associées à chaques lignes, colonnes, diagonales
        int[] lig = {5,4,4,4,3,3,4,4,4,4};
        int[] col = {2,2,7,7,3,2,3,4,4,5};
        int[] dUp = {0,1,1,2,1,3,2,3,3,7,4,3,1,2,1,2,2,0,1}; 
        int[] dDwn = {0,0,1,2,2,2,5,2,4,4,4,3,2,1,1,0,3,2,1};
        
        // initialisation du tableau
        // creation des variables
        IntVar[][] x = new IntVar[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                x[i][j] = model.intVar("X"+i+j, 0, 1);
            }
        }
        // Une contrainte est associée à chaque ligne
        // on tester les valeurs de ligne i de tableau x
        // avec notre initial données
        for(int i = 0; i < N; i++) {
            model.sum(x[i], "=", lig[i]).post(); 
        }

        // Une contrainte est associée à chaque colonne
        for(int j = 0; j < N; j++) {
            IntVar[] colo = new IntVar[N]; 
            for(int i = 0; i < N; i++) {
                colo[i] = x[i][j];
            }
            // on mettre les valeurs le colonnes dans notre tableau
            model.sum(colo, "=", col[j]).post(); 
        }

    // diagonales montantes
    ArrayList<IntVar>[] dUpp = new ArrayList[2 * N-1];

    for(int i = 0; i < 2 * N-1; i++) {
        dUpp[i] = new ArrayList<IntVar>();
    }
    // remplir de liste de diagonale montantes
    for(int i = 0; i < N; i++) {
        for(int j = 0; j < N; j++) {
            dUpp[i+j].add(x[i][j]); 
        }
    }
    // comparer les valeurs avec notre valeurs de tableau 
    for(int k = 0; k<2*N - 1; k++) {
        IntVar[] tmpM = dUpp[k].toArray(new IntVar[0]);
            model.sum(tmpM, "=", dUp[k]).post();
    }


    // diagonales descendantes
    // crée le liste de diagonal descendanetes
    ArrayList<IntVar>[] dDwnn = new ArrayList[2 * N-1];
    for(int i = 0; i < 2 * N-1; i++) {
        dDwnn[i] = new ArrayList<IntVar>();
    }

    for(int i = 0; i < N; i++) {
        for(int j = 0; j < N; j++) {
            dDwnn[i-j+N-1].add(x[i][j]); 
        }
     }
    for(int k = 1; k < 2*N; k++) {
        IntVar[] tmpD = dDwnn[k-1].toArray(new IntVar[0]);
            model.sum(tmpD, "=", dDwn[2*N-1-k]).post();
    }
    
    // Résolution
    Solution solution = model.getSolver().findSolution();
        if(solution != null) {
            //System.out.println(solution.toString());
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    if(x[i][j].getValue() == 1){
                        System.out.print("[1]");
                    }
                    else{
                        System.out.print("[0]");
                    }
                }
                System.out.print("");
                System.out.print("\n");

            } 
        }

    }
}