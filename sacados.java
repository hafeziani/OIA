import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class sacados {
    public static void main(String[] args) {
        // Deux entiers 𝑊 et 𝑉 représentant respectivement le poids maximum
        int W = 50;
        int V = 40;
        // Une liste 𝑤0, ... 𝑤𝑛−1 des poids des objets susceptibles d’être mis dans le sac.
        int[] w = new int[]{2,5,7,8,15,21,23};
        // Une liste 𝑣0, ... 𝑣𝑛−1 des valeurs des objets susceptibles d’être mis dans le sac.
        int[] v = new int[]{3,11,8,10,10,8,9};

        // creation du modele
        Model model = new Model("Backpack");
        
        // creation des variables
        int N = w.length;
        IntVar[] x = new IntVar[N];
        for(int i = 0; i < N; i++) {
            x[i] = model.intVar("X"+i, 0, 1);
        }
        model.scalar(x, w, "<=", W).post();
        model.scalar(x, v, ">=", V).post();
        // Résolution
        Solution solution = model.getSolver().findSolution();
        if (solution != null) {
            System.out.println(solution.toString());
        }
    }
}
