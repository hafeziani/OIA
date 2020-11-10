import java.util.ArrayList;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class Reines {
    public static void main(String[] args) {
        int N = 5;
        // creation du modele
        Model model = new Model(N + "-queens problem");
        // creation des variables
        IntVar[][] x = new IntVar[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                x[i][j] = model.intVar("X"+i+j, 0, 1);
            }
        }
        // Une contrainte est associée à chaque ligne égale 1
        for(int i = 0; i < N; i++) {
            model.sum(x[i], "=", 1).post(); 
        }
        // Une contrainte est associée à chaque colonne égale 1
        for(int j = 0; j < N; j++) {
            IntVar[] col = new IntVar[N]; 
            for(int i = 0; i < N; i++) {
                col[i] = x[i][j];
        }

        model.sum(col, "=", 1).post(); 
    }

    ArrayList<IntVar>[] dUp = new ArrayList[2 * N-1];
    for(int k = 0; k < 2 * N-1; k++) {
        dUp[k] = new ArrayList<>();
    }
    
    for(int i = 0; i < N; i++) {
        for(int j = 0; j < N; j++) {
            dUp[i+j].add(x[i][j]); 
        }
    }

    for(int k = 0; k<2*N-1; k++) {
        IntVar[] tmp = dUp[k].toArray(new IntVar[0]);
            model.sum(tmp, "<=", 1).post(); 
    }

    Solution solution = model.getSolver().findSolution();
        if(solution != null) {
            System.out.println(solution.toString()); 
        }

    }
}
