import java.util.Scanner;

public class MetodoMODI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        System.out.print("Ingrese el número de filas (m): ");
        int m = scanner.nextInt();
        System.out.print("Ingrese el número de columnas (n): ");
        int n = scanner.nextInt();

        double[][] costo = new double[m][n];
        double[][] asignacion = new double[m][n];

      
        System.out.println(" Ingrese los valores de la Matriz de Costos");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("Costo en [%d][%d]: ", i, j);
                costo[i][j] = scanner.nextDouble();
            }
        }

        
        System.out.println("Ingrese los valores de la Matriz de Asignación -1 para vacías");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("Asignación en [%d][%d]: ", i, j);
                asignacion[i][j] = scanner.nextDouble();
            }
        }

        scanner.close();

        // Imprimir datos ingresados
        System.out.println("MATRIZ DE COSTOS INGRESADA:");
        imprimirTabla(costo, m, n);

        System.out.println("\nMATRIZ DE ASIGNACIÓN INGRESADA:");
        imprimirTabla(asignacion, m, n);
      
    }

    
    public static void imprimirTabla(double[][] matriz, int m, int n) {
        for (int i = 0; i < m; i++) {
            System.out.print("| ");
            for (int j = 0; j < n; j++) {
               
                if (matriz[i][j] == (int) matriz[i][j]) {
                    System.out.printf("%6d ", (int) matriz[i][j]);
                } else {
                    System.out.printf("%6.2f ", matriz[i][j]);
                }
            }
            System.out.println("|");
        }
    }
}