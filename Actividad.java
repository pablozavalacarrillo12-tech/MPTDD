/**
 * Representa una actividad individual dentro del proyecto PERT/CPM.
 * Almacena los tiempos estimados y calcula el tiempo esperado (Te) y la varianza.
 */
public class Actividad {
    private String id;
    private String descripcion;
    private String predecesora;
    private double to; // Tiempo optimista
    private double tm; // Tiempo más probable
    private double tp; // Tiempo pesimista
    private double te; // Tiempo esperado
    private double varianza;

    /**
     * Constructor para inicializar una actividad y calcular automáticamente sus valores PERT.
     */
    public Actividad(String id, String descripcion, String predecesora, double to, double tm, double tp) {
        this.id = id;
        this.descripcion = descripcion;
        this.predecesora = predecesora;
        this.to = to;
        this.tm = tm;
        this.tp = tp;
        calcularPert();
    }

    /**
     * Calcula el tiempo esperado (Te) utilizando la fórmula de distribución PERT:
     * Te = (To + 4*Tm + Tp) / 6
     * Y calcula la varianza: Varianza = ((Tp - To) / 6)^2
     */
    private void calcularPert() {
        this.te = (this.to + (4 * this.tm) + this.tp) / 6.0;
        this.varianza = Math.pow((this.tp - this.to) / 6.0, 2);
    }

    // Métodos Getters y Setters para acceder a los atributos privados
    public String getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public String getPredecesora() { return predecesora; }
    public double getTo() { return to; }
    public double getTm() { return tm; }
    public double getTp() { return tp; }
    public double getTe() { return te; }
    public double getVarianza() { return varianza; }
}