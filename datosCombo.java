package appcajero;

public class datosCombo {
    
    private int codigo;
    private String nombre;
    
    public datosCombo(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    public datosCombo(){
    
    }
    
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String toString(){
        return this.getNombre();
    }
    
    public int toInt(){
        return this.getCodigo();
    }
    
}
