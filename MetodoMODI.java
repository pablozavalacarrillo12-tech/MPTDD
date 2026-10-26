import java.util.Scanner;
import java.util.Arrays;

public class MetodoMODI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el número de filas (m): ");
        int m = scanner.nextInt();
        System.out.print("Ingrese el número de columnas (n): ");
        int n = scanner.nextInt();

        double[][] costo = new double[m][n];
        double[][] asignacion = new double[m][n];

        System.out.println("\n--- Ingrese los valores de la Matriz de Costos ---");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("Costo en [%d][%d]: ", i, j);
                costo[i][j] = scanner.nextDouble();
            }
        }

        System.out.println("\n--- Ingrese los valores de la Matriz de Asignación (-1 para vacías) ---");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("Asignación en [%d][%d]: ", i, j);
                asignacion[i][j] = scanner.nextDouble();
            }
        }

        scanner.close();

        // Imprimir datos ingresados
        System.out.println("\nMATRIZ DE COSTOS INGRESADA:");
        imprimirTabla(costo, m, n);

        System.out.println("\nMATRIZ DE ASIGNACIÓN INGRESADA:");
        imprimirTabla(asignacion, m, n);

        // --- LÓGICA DEL MÉTODO MODI ---
        evaluarMODI(costo, asignacion, m, n);
    }

    public static void evaluarMODI(double[][] costo, double[][] asignacion, int m, int n) {
        double[] u = new double[m];
        double[] v = new double[n];
        boolean[] uAsignado = new boolean[m];
        boolean[] vAsignado = new boolean[n];

        Arrays.fill(u, Double.NaN);
        Arrays.fill(v, Double.NaN);

        // Fijar u[0] = 0 para iniciar el cálculo de potenciales
        u[0] = 0;
        uAsignado[0] = true;

        // Calcular potenciales u_i y v_j para celdas básicas (asignacion != -1)
        boolean cambiado = true;
        while (cambiado) {
            cambiado = false;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (asignacion[i][j] != -1) { // Celda básica
                        if (uAsignado[i] && !vAsignado[j]) {
                            v[j] = costo[i][j] - u[i];
                            vAsignado[j] = true;
                            cambiado = true;
                        } else if (!uAsignado[i] && vAsignado[j]) {
                            u[i] = costo[i][j] - v[j];
                            uAsignado[i] = true;
                            cambiado = true;
                        }
                    }
                }
            }
        }

        System.out.println("\n--- POTENCIALES CALCULADOS ---");
        System.out.println("u (Filas): " + Arrays.toString(u));
        System.out.println("v (Columnas): " + Arrays.toString(v));

        // Calcular costos reducidos para celdas vacías (asignacion == -1)
        System.out.println("\n--- COSTOS REDUCIDOS (Celdas Vacías) ---");
        double minCostoReducido = 0;
        int filaEntrante = -1, colEntrante = -1;
        boolean hayCeldasVaciasEvaluables = false;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (asignacion[i][j] == -1) {
                    if (uAsignado[i] && vAsignado[j]) {
                        double costoReducido = costo[i][j] - (u[i] + v[j]);
                        System.out.printf("Célula [%d][%d]: Costo Reducido = %.2f - (%.2f + %.2f) = %.2f\n", 
                            i, j, costo[i][j], u[i], v[j], costoReducido);
                        
                        hayCeldasVaciasEvaluables = true;
                        if (costoReducido < minCostoReducido) {
                            minCostoReducido = costoReducido;
                            filaEntrante = i;
                            colEntrante = j;
                        }
                    } else {
                        System.out.printf("Célula [%d][%d]: No se pudieron calcular potenciales debido a degeneración.\n", i, j);
                    }
                }
            }
        }

        // Evaluar optimalidad
        System.out.println("\n--- EVALUACIÓN DE OPTIMALIDAD ---");
        if (!hayCeldasVaciasEvaluables || minCostoReducido >= -1e-5) {
            System.out.println(" ¡La solución actual es ÓPTIMA! No hay costos reducidos negativos.");
            
            // Calcular costo total de transporte
            double costoTotal = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (asignacion[i][j] != -1) {
                        costoTotal += asignacion[i][j] * costo[i][j];
                    }
                }
            }
            System.out.println("Costo Total de Transporte: " + costoTotal);
        } else {
            System.out.printf(" La solución NO es óptima.\n");
            System.out.printf("Celda entrante recomendada: [%d][%d] con un costo reducido de %.2f\n", 
                filaEntrante, colEntrante, minCostoReducido);
        }
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