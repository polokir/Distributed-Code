import mpi.*;

public class Main {
    public static void main(String[] args) {
        int[] sizes = {100, 1000};

        for (int matrixSize : sizes) {
            SimpleMatrix.calculate(args, matrixSize);
            StringMatrix.calculate(args, matrixSize);
            Fox.calculate(args, matrixSize);
            Cannon.calculate(args, matrixSize);
        }
    }
}
