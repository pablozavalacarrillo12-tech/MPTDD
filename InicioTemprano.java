/**
 * Modela y calcula los tiempos de Inicio Temprano (ES) y Final Temprano (EF).
 */
public class InicioTemprano {
    private double es; //(Inicio Temprano)
    private double ef; //(Final Temprano)

    /**
     * Constructor por defecto que inicializa los valores en cero.
     */
    public InicioTemprano() {
        this.es = 0.0;
        this.ef = 0.0;
    }

    
     // Calcula el Inicio Temprano y el Final Temprano en función del ES determinado por sus predecesoras y el Te de la tarea.
     
    public void calcular(double esCalculado, double te) {
        this.es = esCalculado;
        this.ef = this.es + te;
    }

    public double getEs() { return es; }
    public double getEf() { return ef; }
}