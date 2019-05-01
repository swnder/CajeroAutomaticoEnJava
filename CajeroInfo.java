
package appcajero;

  
public class CajeroInfo {
      public int id;
      public int direccion;
      public int banco;
      public int monto;

    public CajeroInfo(int id, int direccion, int banco, int monto) {
        this.id = id;
        this.direccion = direccion;
        this.banco = banco;
        this.monto = monto;
    }
    public CajeroInfo (){
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public int getBanco() {
        return banco;
    }

    public void setBanco(int banco) {
        this.banco = banco;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }
    
            

}
