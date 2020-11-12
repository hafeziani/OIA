package ProjetF;

import java.util.ArrayList;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class ProjF1 {
    public static void main(String[] args) {

        int[] lig = new int[]{5,4,4,4,3,3,4,4,4,4};
        int[] col = new int[]{2,2,7,7,3,2,3,3,4,5};
        int[] dUp = new int[]{0,1,1,2,1,3,2,3,3,7,4,3,1,2,1,2,2,0,1}; 
        int[] dDwn = new int[]{0,0,1,2,2,2,5,2,4,4,4,3,2,1,1,0,3,2,1};

        int N = 10;
        // creation du modele
        Model model = new Model(N + "_reconstituer_une_image");
        
        // creation des variables
        IntVar[][] x = new IntVar[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                x[i][j] = model.intVar("X"+i+j, 0, 1);
            }
        }
        // Une contrainte est associée à chaque ligne égale 1
        for(int i = 0; i < N; i++) {
            model.sum(x[i], "=", lig[i]).post(); 
        }
        // Une contrainte est associée à chaque colonne égale 1
        for(int j = 0; j < N; j++) {
            IntVar[] colo = new IntVar[N]; 
            for(int i = 0; i < N; i++) {
                colo[i] = x[i][j];
            }
            model.sum(colo, "=", col[j]).post(); 
        }

    ArrayList<IntVar>[] dUpp = new ArrayList[2 * N-1];
    for(int k = 0; k < 2 * N-1; k++) {
        dUpp[k] = new ArrayList<>();
    }
    
    for(int i = 0; i < N; i++) {
        for(int j = 0; j < N; j++) {
            dUpp[i+j].add(x[i][j]); 
        }
    }
    for(int k = 0; k<2*N-1; k++) {
        IntVar[] tmp = dUpp[k].toArray(new IntVar[0]);
            model.sum(tmp, "<=", dUp[k]).post();
    }

    ArrayList<IntVar>[] dDwnn = new ArrayList[2 * N-1];
    for(int k = 0; k < 2 * N-1; k++) {
        dDwnn[k] = new ArrayList<>();
    }
    
    for(int i = 0; i < N; i++) {
        for(int j = 0; j < N; j++) {
            dDwnn[i+j].add(x[i][j]); 
        }
    }
    for(int k = 0; k<2*N-1; k++) {
        IntVar[] tmp = dUpp[k].toArray(new IntVar[0]);
            model.sum(tmp, "<=", dDwn[k]).post();
    }
    
    // Résolution
    Solution solution = model.getSolver().findSolution();
        if(solution != null) {
            System.out.println(solution.toString()); 
        }

    }
}