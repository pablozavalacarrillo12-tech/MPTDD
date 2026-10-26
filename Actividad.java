public class Actividad {
    private String id;
    private String descripcion;
    private String predecesora;
    
    // Tiempos O, M, P
    private double to; 
    private double tm; 
    private double tp; 
    
    // Tiempos calculados PERT
    private double te; 
    private double varianza;
    
    // Tiempos CPM
    private double es; 
    private double ef; 
    private double ls; 
    private double lf; 
    private double h;  

    public Actividad(String id, String descripcion, String predecesora, double to, double tm, double tp) {
        this.id = id;
        this.descripcion = descripcion;
        this.predecesora = predecesora;
        this.to = to;
        this.tm = tm;
        this.tp = tp;
      
    }
}