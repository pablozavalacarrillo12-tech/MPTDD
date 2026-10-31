
 // Modela y calcula los tiempos de Inicio Tardío (LS) y Final Tardío (LF).
 
public class InicioTardio {
    private double ls; // Late Start (Inicio Tardío)
    private double lf; // Late Finish (Final Tardío)

     // Constructor por defecto que inicializa los valores en cero.
     
    public InicioTardio() {
        this.ls = 0.0;
        this.lf = 0.0;
    }

   
     //Calcula el Final Tardío y el Inicio Tardío en función del LF determinado por sus sucesoras y el Te de la tarea.
   
    public void calcular(double lfCalculado, double te) {
        this.lf = lfCalculado;
        this.ls = this.lf - te;
    }

    public double getLs() { return ls; }
    public double getLf() { return lf; }
}