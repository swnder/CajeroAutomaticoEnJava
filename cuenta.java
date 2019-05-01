
package appcajero;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;

public class cuenta {
    
    public int codigo;       
    public int nroTarjeta;
    public String cliente;
    public int clave;
    public String tipo_tarjeta;
    public int linea;
    public int saldo;
    public String fechaInicio;
    public String fechaCaduca;
    public String banco;
    public String estado;
    
    /*
  
    public cuenta(String Nombre, String Clave, int saldo) {
        this.Nombre = Nombre;
        this.Clave = Clave;
        this.saldo = saldo;
    }*/

    public cuenta(int codigo,int nroTarjeta, 
            String cliente, int clave, String tipo_tarjeta, int linea, 
            int saldo, String fechaInicio, String fechaCaduca, 
            String banco, String estado) {
        
        this.codigo = codigo;
        this.nroTarjeta = nroTarjeta;
        this.cliente = cliente;
        this.clave = clave;
        this.tipo_tarjeta = tipo_tarjeta;
        this.linea = linea;
        this.saldo = saldo;
        this.fechaInicio = fechaInicio;
        this.fechaCaduca = fechaCaduca;
        this.banco = banco;
        this.estado = estado;
    }

}
