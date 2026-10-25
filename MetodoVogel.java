package MPTDD;
import java.util.Scanner;

public class MetodoVogel {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // =========================================================================
        // SECCIÓN 1: CAPTURA DE DATOS DE ENTRADA
        // =========================================================================
        System.out.println("=== MÉTODOS DE TRANSPORTE: ALGORITMO DE VOGEL ===");
        
        // 1.1 Lectura del número de orígenes (filas) y destinos (columnas)
        System.out.print("Ingrese la cantidad de fuentes (oferta/orígenes): ");
        int numFuentes = scanner.nextInt();
        
        System.out.print("Ingrese la cantidad de destinos (demanda/destinos): ");
        int numDestinos = scanner.nextInt();

        // 1.2 Captura de las ofertas de cada fuente y cálculo del total ofertado
        int[] ofertaOrig = new int[numFuentes];
        int sumaOferta = 0;
        System.out.println("\n--- Ingrese las Ofertas ---");
        for (int i = 0; i < numFuentes; i++) {
            System.out.print("Oferta para Fuente " + (i + 1) + ": ");
            ofertaOrig[i] = scanner.nextInt();
            sumaOferta += ofertaOrig[i];
        }

        // 1.3 Captura de las demandas de cada destino y cálculo del total demandado
        int[] demandaOrig = new int[numDestinos];
        int sumaDemanda = 0;
        System.out.println("\n--- Ingrese las Demandas ---");
        for (int j = 0; j < numDestinos; j++) {
            System.out.print("Demanda para Destino " + (j + 1) + ": ");
            demandaOrig[j] = scanner.nextInt();
            sumaDemanda += demandaOrig[j];
        }

        // 1.4 Captura de la matriz de costos unitarios de transporte
        int[][] costoOrig = new int[numFuentes][numDestinos];
        System.out.println("\n--- Ingrese la Matriz de Costos ---");
        for (int i = 0; i < numFuentes; i++) {
            for (int j = 0; j < numDestinos; j++) {
                System.out.print("Costo de Fuente " + (i + 1) + " a Destino " + (j + 1) + ": ");
                costoOrig[i][j] = scanner.nextInt();
            }
        }

        // =========================================================================
        // SECCIÓN 2: VERIFICACIÓN Y BALANCEO DEL PROBLEMA
        // =========================================================================
        // El algoritmo de Vogel requiere que Suma(Oferta) == Suma(Demanda).
        // Si no son iguales, se agregan filas o columnas ficticias con costo 0.

        int filas = numFuentes;
        int columnas = numDestinos;

        // Evaluación de desbalance:
        if (sumaOferta > sumaDemanda) {
            columnas++; // Se agrega una columna ficticia
            System.out.println("\n[!] La Oferta (" + sumaOferta + ") es MAYOR que la Demanda (" + sumaDemanda + ").");
            System.out.println("--> Se agregó una columna ficticia con demanda " + (sumaOferta - sumaDemanda) + " y costos 0.");
        } else if (sumaDemanda > sumaOferta) {
            filas++; // Se agrega una fila ficticia
            System.out.println("\n[!] La Demanda (" + sumaDemanda + ") es MAYOR que la Oferta (" + sumaOferta + ").");
            System.out.println("--> Se agregó una fila ficticia con oferta " + (sumaDemanda - sumaOferta) + " y costos 0.");
        } else {
            System.out.println("\n[✔] El problema está balanceado (Oferta total = Demanda total = " + sumaOferta + ").");
        }

        // =========================================================================
        // SECCIÓN 3: CONSTRUCCIÓN DE LA MATRIZ DE TRABAJO (BALANCEADA)
        // =========================================================================
        int[][] costo = new int[filas][columnas];
        int[] oferta = new int[filas];
        int[] demanda = new int[columnas];

        // 3.1 Copia de datos originales a la matriz de trabajo
        for (int i = 0; i < numFuentes; i++) {
            oferta[i] = ofertaOrig[i];
            for (int j = 0; j < numDestinos; j++) {
                costo[i][j] = costoOrig[i][j];
            }
        }
        for (int j = 0; j < numDestinos; j++) {
            demanda[j] = demandaOrig[j];
        }

        // 3.2 Asignación de valores a la fila o columna ficticia (si se creó alguna)
        if (sumaOferta > sumaDemanda) {
            demanda[numDestinos] = sumaOferta - sumaDemanda; // Demanda de la columna ficticia
            for (int i = 0; i < filas; i++) {
                costo[i][numDestinos] = 0; // Costos de la columna ficticia son 0
            }
        } else if (sumaDemanda > sumaOferta) {
            oferta[numFuentes] = sumaDemanda - sumaOferta; // Oferta de la fila ficticia
            for (int j = 0; j < columnas; j++) {
                costo[numFuentes][j] = 0; // Costos de la fila ficticia son 0
            }
        }

        // =========================================================================
        // SECCIÓN 4: EJECUCIÓN DEL ALGORITMO DE VOGEL
        // =========================================================================
        // Estructuras de control para rastrear filas/columnas ya satisfechas y las asignaciones hechas
        boolean[] filaSatisfecha = new boolean[filas];
        boolean[] colSatisfecha = new boolean[columnas];
        int[][] asignacion = new int[filas][columnas];

        int asignacionesRealizadas = 0;
        int totalCeldas = filas + columnas - 1; // Condición de solución no degenerada (m + n - 1)

        while (asignacionesRealizadas < totalCeldas) {
            int maxPenalizacion = -1;
            boolean esFila = true;
            int indiceSeleccionado = -1;

            // ---------------------------------------------------------------------
            // 4.1 Cálculo de penalizaciones en FILAS no satisfechas
            // ---------------------------------------------------------------------
            for (int i = 0; i < filas; i++) {
                if (!filaSatisfecha[i]) {
                    int pen = calcularPenalizacionFila(costo, i, colSatisfecha, columnas);
                    if (pen > maxPenalizacion) {
                        maxPenalizacion = pen;
                        indiceSeleccionado = i;
                        esFila = true; // La mayor penalización se encuentra en una fila
                    }
                }
            }

            // ---------------------------------------------------------------------
            // 4.2 Cálculo de penalizaciones en COLUMNAS no satisfechas
            // ---------------------------------------------------------------------
            for (int j = 0; j < columnas; j++) {
                if (!colSatisfecha[j]) {
                    int pen = calcularPenalizacionColumna(costo, j, filaSatisfecha, filas);
                    if (pen > maxPenalizacion) {
                        maxPenalizacion = pen;
                        indiceSeleccionado = j;
                        esFila = false; // La mayor penalización se encuentra en una columna
                    }
                }
            }

            // Si no se encuentra un índice válido, rompe el bucle de asignación
            if (indiceSeleccionado == -1) break;

            // ---------------------------------------------------------------------
            // 4.3 Selección de la celda de menor costo dentro del vector elegido
            // ---------------------------------------------------------------------
            int fSel = -1, cSel = -1;
            int menorCosto = Integer.MAX_VALUE;

            if (esFila) {
                fSel = indiceSeleccionado;
                for (int j = 0; j < columnas; j++) {
                    if (!colSatisfecha[j] && costo[fSel][j] < menorCosto) {
                        menorCosto = costo[fSel][j];
                        cSel = j; // Guarda la columna con el costo más bajo de la fila
                    }
                }
            } else {
                cSel = indiceSeleccionado;
                for (int i = 0; i < filas; i++) {
                    if (!filaSatisfecha[i] && costo[i][cSel] < menorCosto) {
                        menorCosto = costo[i][cSel];
                        fSel = i; // Guarda la fila con el costo más bajo de la columna
                    }
                }
            }

            // ---------------------------------------------------------------------
            // 4.4 Asignación de unidades (Minimizar oferta vs demanda)
            // ---------------------------------------------------------------------
            int cantidad = Math.min(oferta[fSel], demanda[cSel]);
            asignacion[fSel][cSel] = cantidad;
            oferta[fSel] -= cantidad;
            demanda[cSel] -= cantidad;
            asignacionesRealizadas++;

            // Marcar filas o columnas que han alcanzado 0 en oferta o demanda
            if (oferta[fSel] == 0) filaSatisfecha[fSel] = true;
            if (demanda[cSel] == 0) colSatisfecha[cSel] = true;
        }

        // =========================================================================
        // SECCIÓN 5: DESPLIEGUE DE RESULTADOS Y COSTO TOTAL Z
        // =========================================================================
        System.out.println("\n================ RESULTADOS ================");
        System.out.println("Matriz de Asignaciones (Unidades enviadas):");
        
        int costoTotal = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.printf("%5d ", asignacion[i][j]);
                // El costo total Z es la suma de (unidades asignadas * costo unitario)
                costoTotal += asignacion[i][j] * costo[i][j];
            }
            System.out.println();
        }

        System.out.println("\nCosto Total de Transporte Z = " + costoTotal);
        scanner.close();
    }

    // =============================================================================
    // SECCIÓN 6: MÉTODOS AUXILIARES PARA EL CÁLCULO DE PENALIZACIONES
    // =============================================================================

    /**
     * Calcula la penalización de una fila: diferencia entre los dos costos más bajos
     * de las celdas cuyos destinos aún no han sido satisfechos.
     */
    private static int calcularPenalizacionFila(int[][] costo, int fila, boolean[] colSatisfecha, int columnas) {
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int count = 0;

        for (int j = 0; j < columnas; j++) {
            if (!colSatisfecha[j]) {
                count++;
                int c = costo[fila][j];
                if (c < min1) {
                    min2 = min1;
                    min1 = c;
                } else if (c < min2) {
                    min2 = c;
                }
            }
        }
        if (count == 0) return -1;
        if (count == 1) return min1; // Si solo queda una celda, la penalización es el valor mismo
        return min2 - min1;          // Diferencia entre el segundo menor y el menor costo
    }

    /**
     * Calcula la penalización de una columna: diferencia entre los dos costos más bajos
     * de las celdas cuyas fuentes aún no han sido satisfechas.
     */
    private static int calcularPenalizacionColumna(int[][] costo, int col, boolean[] filaSatisfecha, int filas) {
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int count = 0;

        for (int i = 0; i < filas; i++) {
            if (!filaSatisfecha[i]) {
                count++;
                int c = costo[i][col];
                if (c < min1) {
                    min2 = min1;
                    min1 = c;
                } else if (c < min2) {
                    min2 = c;
                }
            }
        }
        if (count == 0) return -1;
        if (count == 1) return min1; // Si solo queda una celda, la penalización es el valor mismo
        return min2 - min1;          // Diferencia entre el segundo menor y el menor costo
    }
}