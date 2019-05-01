
package appcajero;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author swnder
 */


public final class logica {
    
    private int faseActual;
    private Cajero cajero;
    private cuenta[] cuentas;
    private cuenta CuentaActual;
    private String CadenaTeclado="";
    private String MascaraClave ="";
    private final String Saltos = "<br><br><br><br><br><br><br>";
    private int Opcion;
    private boolean TEcladoActivo = false;
    private int valorlogin = 1;
    private int intento=3;
    
    public CajeroInfo info= null;
    
    
    ConexionBD bd = new ConexionBD("centralcajeroauto");

            
    
    public logica(Cajero cajero) {
        this.cajero = cajero;
        if(bd.estaConectado()){
            //verificarsaldo();
            Inicio();
        }else{
            JOptionPane.showMessageDialog(null, "No pudo conectarse a la base de datos");
        }
        
        
    //CargarCuentas();
      
        
    }

    private void Inicio() {
        habilitar();
        //verificarsaldo();
        this.cajero.DisplayBajo.setText("<html><bod><h1 style='color:yellow;'>Bienvenidos</h1>"
                + "<div style='color:green;'>Oprima un boton para continuar</div></body></html>");
        this.cajero.DisplayDer.setText("");
        this.cajero.DisplayIzq.setText("");
        faseActual = 1;
        CadenaTeclado = "";
        desabilitar();
       
    }  

    public void bntElegir(JComboBox nombre, JDialog Dialogo){
           
        int encontrado = 0;
        String combo = String.valueOf(nombre.getSelectedItem());
        try {
            ResultSet rs =null;
            rs = bd.dameLista("SELECT id_cajero, direccion, "
                    + "d.calle, b.id_banco, saldo FROM cajeros "
                    + "INNER JOIN direccion d ON direcion=id_direccion "
                    + "INNER JOIN banco b ON banco=b.id_banco;");
            rs.beforeFirst();
            
            while (rs.next()) {
                
                if(rs.getString(3).equals(combo)){
                    
                     info  = new CajeroInfo(rs.getInt(1), rs.getInt(2), 
                            rs.getInt(4),rs.getInt(5));
                    encontrado = 1;
                     break;
                }
                
            }
               
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error debido a: "+ex);
        
        }
        if (encontrado != 1) {
            JOptionPane.showMessageDialog(null, "Debe elegir una opcion");
        } else {
            //JOptionPane.showMessageDialog(null, "encontrado");
            /*
                    JOptionPane.showMessageDialog(null,"ide: "+info.id
                        + "\nDireccion "+info.direccion
                        + "\nMonto: "+info.monto
                        +"\nBanco: "+info.banco);
             */
            Dialogo.dispose();
        }
    }
    
    
    
    public Boolean datosCuenta(int valorAcomparar){
        //cargar en na clases los datos de la tarjeta para poder administrar la transaccion
        boolean resultado = false;   
        ResultSet buscar=null;
           
            int codigo=0;
        
        buscar=bd.dameLista("select "
                     + "t.id_tarjeta, t.num_tarjeta, c.nombre, t.clave, t.tipo_tarjeta, t.linea, t.saldo, t.fecha_expedicion, t.fecha_expiracion, b.nombre, t.estado"
                     + " from tarjeta t"
                     + " INNER JOIN cliente c ON c.nro_cliente = t.cliente"
                     + " INNER JOIN banco b ON b.id_banco = t.idbanco"
                     + " order by t.id_tarjeta;");
            
            try {
                buscar.beforeFirst();
                   
                while(buscar.next()){
                
                    if(buscar.getInt(2)==(valorAcomparar)){
                        
                            codigo = buscar.getInt(1);
                            CuentaActual= new cuenta(buscar.getInt(1), 
                                    buscar.getInt(2), 
                                    buscar.getString(3), 
                                    buscar.getInt(4), 
                                    buscar.getString(5), 
                                    buscar.getInt(6), 
                                    buscar.getInt(7),
                                    buscar.getString(8),
                                    buscar.getString(9),
                                    buscar.getString(10),
                                    buscar.getString(11));
                            //JOptionPane.showMessageDialog(null, "Tarjeta encontrada");
                            resultado = true;
                            break;
                        
                        
                    }else{
                        //this.cajero.DisplayBajo.setText("<html><body> <h1 style='color:yellow;'>Tarjeta invalida</h1></body></html>");  
                    };
                }//fin del while
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error a la conexción de la base de datos"
                        + "\n por la razon de : "+ex);
            }
        
               return resultado;
      
     }
       
    private void CargarCuentas() {
        /*cuentas = new cuenta[3];
        cuentas[0] = new cuenta("Pedro Perez", "123", 1000000);
        cuentas[1] = new cuenta("Ana Quintana","4567", 150000);
        cuentas[2] = new cuenta("Carlos Lopez","5901", 800000);
         */
        //cajero.BasedeDatos.dameLista("select cliente from *");
        CajeroInfo info =new CajeroInfo();
        JOptionPane.showMessageDialog(null,"ide: "+info.id
                        + "\nDireccion "+info.direccion
                        + "\nMonto: "+info.monto
                        +"\nBanco: "+info.banco);
    }

    public void EventoTeclado(String Digito){
        if(TEcladoActivo){
               
            
            
            if(faseActual==3){
                if(!Digito.equals("C")&&!Digito.equals("A")){
                    CadenaTeclado += Digito;
                    
                    //para ocultar la contraseña
                    if(valorlogin==0){
                        MascaraClave += "<FONT SIZE=9 color = 'yellow'>*</FONT> ";
                    this.cajero.DisplayBajo.setText("<html><body>"
                            +MascaraClave+"</body></html>");
                            
                    }else if(valorlogin ==1){
                        this.cajero.DisplayBajo.setText("<html><body>"
                            +CadenaTeclado+"</body></html>");
                        
                    }
                    
                }else if (Digito.equals("A")){
                    FaseTres();
                }else if (Digito.equals("C")){
                    
                    estadoInicial();
                    Inicio();
                }    
            }else if(faseActual==6){
                
                if(!Digito.equals("C")&&!Digito.equals("A")){
                    //otro monto
                    CadenaTeclado += Digito;
                    int entero = Integer.parseInt(CadenaTeclado);
                    //formato para la moneda
                    //NumberFormat formato = NumberFormat.getCurrencyInstance(); 
                    //String Saldo = formato.format(entero);
                    
                    
                    
                    String Saldo= String.valueOf(entero);
                    this.cajero.DisplayBajo.setText("<html><body style='color:green;'><h1>"
                            +Saldo+"</h1></body></html>");
                    
                }else if (Digito.equals("A")){
                    //una vez que le da aceptar manda en la fase 5 para descontar de la base de datos
                    int entero = Integer.parseInt(CadenaTeclado);
                    if(entero<=1500000){
                        FaseCinco(entero);
                    }else{
                        this.cajero.DisplayBajo.setText("<html><body style='color:red;'><h1> NO PUEDE SACAR MÁS DE 1.500.000 GS"
                            +"</h1></body></html>");
                        Timer timer = new Timer(2000, (ActionEvent e) -> {

                        Inicio();
                    });
                    timer.setRepeats(false);
                    timer.start();
                    return;
                        
                    }
                    
                    
                    
                 }else if (Digito.equals("C")){
                    
                    estadoInicial();
                    Inicio();
                }    
                    }
            if (Digito.equals("C")){
                    
                    estadoInicial();
                    Inicio();
                }    
            }else {
            if (Digito.equals("C")){
                    
                    estadoInicial();
                    Inicio();
                }    
        }
        
    }
        
    private void FaseUno(){
        this.cajero.DisplayDer.setText("<html><body> Retiro"+Saltos+"</body></html>");  
        this.cajero.DisplayIzq.setText("<html><body> Solicitar Saldo"+Saltos+"</body></html>");  
        this.cajero.DisplayBajo.setText("<html><body> <h1 style='color:yellow;'>Bienvenido</h1></body></html>");  
        faseActual=2;
    
    }
        
    private void FaseDos(int Opc){
        desabilitar();
        //JOptionPane.showMessageDialog(null, "Estoy aca fase 2 la copcion es : "+Opc);
        cajero.btn1.setEnabled(false);
        cajero.btn5.setEnabled(false);
        this.cajero.DisplayDer.setText("");
        this.cajero.DisplayIzq.setText("");
        
        if(valorlogin==1){
            this.cajero.DisplayBajo.setText("<html><body style='color:yellow;'>Digite El Nro de su Tarjeta</body></html>");
            faseActual = 3;
            Opcion = Opc;
            CadenaTeclado = "";
            TEcladoActivo = true;
        }else if (valorlogin==0){
            this.cajero.DisplayBajo.setText("<html><body style='color:yellow;'>Digite su Contraseña</body></html>");
            faseActual = 3;
            Opcion = Opc;
            CadenaTeclado = "";
            MascaraClave = "";
            TEcladoActivo = true;
        
        }
        
        
        
        
    }
    
    private void volveralInicio(){
        
        Timer timer = new Timer(2000, (ActionEvent e) -> {

                Inicio();
            });
            timer.setRepeats(false);
           timer.start();
           return;
    }
    
    public void estadoInicial(){
        //Vacia la clase cuenta de los datos del cliente
        //el valor inicial y los intentos vuelven al lo predeterminado
        CuentaActual= new cuenta(0,0,null,0,null,0,0,null,null,null,null);
        valorlogin =1;
        intento=3;
    }
    
    
    
    private void FaseTres(){
        desabilitar();
        faseActual = 4;
        TEcladoActivo = false;
        if (valorlogin == 1) {
            //manda el en metodo un string para comprobar si esta en la base de datos
            //vendrí hacer el nro de tarjeta
            if (datosCuenta(Integer.valueOf(CadenaTeclado))) {
                //comprueba si esta bloquedo
                if (CuentaActual.estado.equals("Bloqueado")) {

                    this.cajero.DisplayBajo.setText("<html><body style='color:red;'>Tarjeta Bloqueda <br>Favor contacte con la Entidad Encargada</body></html>   ");

                    Timer timer = new Timer(2000, (ActionEvent e) -> {

                        Inicio();
                    });
                    timer.setRepeats(false);
                    timer.start();
                    return;
                }
                //comprueba si esta inactivo
                 if (CuentaActual.estado.equals("Inactivo")) {

                    this.cajero.DisplayBajo.setText("<html><body style='color:red;'>Tarjeta Inactiva <br> Favor contacte con la Entidad Encargada</body></html>   ");

                    Timer timer = new Timer(2000, (ActionEvent e) -> {

                        Inicio();
                    });
                    timer.setRepeats(false);
                    timer.start();
                    return;
                }
                    
                valorlogin = 0;
                // señala en el panel izquierdo el nombre del titular
                this.cajero.DisplayIzq.setText("<html><body style='color:white;'>"
                        + "Titular: <br> "+CuentaActual.cliente+
                        "  </body></html>   ");
                if (Opcion==2){
                    FaseDos(2);
                }else{
                    FaseDos(1);
                }
                

            } else {

                this.cajero.DisplayBajo.setText("<html><body style='color:red;'>Tarjeta erronea</body></html>   ");
                Timer timer = new Timer(1000, (ActionEvent e) -> {

                    Inicio();
                });
                timer.setRepeats(false);
                timer.start();
                return;
            }
        } else if (valorlogin == 0) {
            
            
            if(intento<=3 && intento>0){
                
                if (String.valueOf(CuentaActual.clave).equals(CadenaTeclado)) {

                    this.cajero.DisplayBajo.setText("<html><body style='color:yellow;'><h2 style='color:green;'>Bienvenido</h2> "
                            + CuentaActual.cliente + "<br>Su saldo es de: " + CuentaActual.saldo + " Gs. <br></body></html>");
                    valorlogin = 1;

                    
                    if (Opcion == 2) {
                        //JOptionPane.showMessageDialog(null, "fase tres la opcion es :"+Opcion);
                        
                        Timer timer = new Timer(2000, (ActionEvent e) -> {

                            FaseCuatro();

                        });
                        timer.setRepeats(false);
                        timer.start();
                        return;
                    }

                } else {
                    intento--;        
                    this.cajero.DisplayBajo.setText("<html><body style='color:red;'>Clave Erronea tiene intentos: "+intento+"</body></html>");
                    //JOptionPane.showMessageDialog(null, intento);
                    
                    Timer timer = new Timer(2000, (ActionEvent e) -> {
                        if(Opcion==2){
                            FaseDos(2);
                        }else{
                            FaseDos(1);
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                    return;
                    
                }// fin si

            }//fin si 
            //tiempo de espera para pasar a otro metodo en este caso
            //vuelve al menu inicial
            if (intento == 0) {
                this.cajero.DisplayBajo.setText("<html><body style='color:red;'>Tarjeta Bloqueada </body></html>");
                intento=3;
                valorlogin=1;
                bd.actualizarRegistro("tarjeta", "estado = 'Bloqueado'", "id_tarjeta = "+CuentaActual.codigo);
                
                Timer timer = new Timer(2000, (ActionEvent e) -> {
                    Inicio();
                });
                timer.setRepeats(false);
                timer.start();
                return;

            }

        }

    }
     
    
    
    
    private void FaseCuatro(){
        habilitar();
        //era opcion 1
        //JOptionPane.showMessageDialog(null, "Fase Cuatro la opcione es : "+Opcion);
        
        if(Opcion==1){
            MostrarSaldo();
            
        }else if (Opcion==2){
            this.cajero.DisplayBajo.setText("");
            String texto = "Gs. 1.500.000 <br><br>"
                    + "Gs. 1.000.000 <br><br>"
                    + "Gs. 500.000<br><br>"
                    + "Otro valor";
            this.cajero.DisplayIzq.setText("<html><body>" + texto + "</body></html>");
            texto= "Gs. 400.000<br><br>"
                    + "Gs. 300.000<br><br>"
                    + "Gs. 200.000<br><br>"
                    + "Gs. 100.000";
            this.cajero.DisplayDer.setText("<html><body>" + texto + "</body></html>");
        }
        faseActual = 5;
    
    }
    
    private void FaseCinco (int opc){
        int Saldofinal=0;
        int saldoCajero=0;
         LocalDate fechaActual = LocalDate.now();
        //JOptionPane.showMessageDialog(null, DatoCajero.monto);
  //       saldoCajero=Integer.valueOf(DatoCajero.getMonto());
        habilitar();
        if (Opcion == 1 && opc == 1){
            Inicio();
        }else if (Opcion ==1){
            //imprime la transacción
            //fecha actual del sistema
            //java.util.Date fecha = new Date();
            //formato moneda
            //NumberFormat formato = NumberFormat.getCurrencyInstance();
            LocalDate fecha = LocalDate.now();
            String texto = "Banco RobaMas\n"
                    + "Cliente: " +CuentaActual.cliente+"\n"
                    + "Fecha: "+fecha.format(DateTimeFormatter.ISO_DATE)+"\n"
                    + "Saldo: "+CuentaActual.saldo;      
            JOptionPane.showOptionDialog(this.cajero, texto, "",
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,new Object[]{"OK"}, "OK");
            Inicio();
        }else{
            //validar valor multiplos de 
            //validar saldo
            // Mostrar Mensaje con las denominaciones de los billetes
            
            int saldo = CuentaActual.saldo;
            if(saldo<opc){
                //JOptionPane.showMessageDialog(null,"Su saldo es insuficiente");
                                
                
                 this.cajero.DisplayDer.setText("");
                        this.cajero.DisplayIzq.setText("");
                        this.cajero.DisplayBajo.setText("<html><body style='color:red;'><h1>Su saldo es insuficiente!!</h1> "
                                + "</body></html>");
                Timer timer = new Timer(2000, (ActionEvent e) -> {
                    Inicio();
                });
                timer.setRepeats(false);
                timer.start();
                return;
        
            } else {
                
                if (saldoDelCajero(info.id) > 1) {
                    Opcion = opc;

                    //valida para no dar saldo negativo en el dinero del cajero
                    if (saldoDelCajero(info.id) >= Opcion) {
                        //VALIDA PARA NO SUPERAR 6000000 POR DIA
                        JOptionPane.showMessageDialog(null,fechaActual);
                        int validarMonto =validadMonto(fechaActual, CuentaActual.cliente);
                        if ( validarMonto<=6000000) {

                            CuentaActual.saldo -= Opcion;
                            Saldofinal = info.monto - Opcion;
                            //actualiza el saldo en la tarjeta
                            bd.actualizarRegistro("tarjeta", "saldo = '" + CuentaActual.saldo + "'", "num_tarjeta = '" + CuentaActual.nroTarjeta + "'");
                            // actuliza el saldo del caero
                            bd.actualizarRegistro("cajeros", "saldo = '" + Saldofinal + "'", "id_cajero = '" + info.id + "'");
                            //Genera el codigo para insetar en la transacion
                            String cod = bd.GenerarCodgio("id_transaccion", "transaccion");
                            if (bd.insertarRegistro("transaccion", "id_transaccion, "
                                    + "tarjeta, cliente, tipo_tarjeta,"
                                    + "linea, saldo, banco, fecha, monto, cajero ",
                                    " '" + cod + "', '"
                                    + CuentaActual.codigo + "', '"
                                    + CuentaActual.cliente + "', '"
                                    + CuentaActual.tipo_tarjeta + "', '"
                                    + CuentaActual.linea + "', '"
                                    + CuentaActual.saldo + "', '"
                                    + CuentaActual.banco + "', '"
                                    + fechaActual + "', '"
                                    + Opcion + "', '"
                                    + info.id + "' ")) {

                                MostrarSaldo();
                                faseActual = 7;

                            }

                        } else {
                            
                            //MENSAJE CUANDO YA SUPERA EL LIMITE DE 6000000
                            this.cajero.DisplayDer.setText("");
                            this.cajero.DisplayIzq.setText("");
                            this.cajero.DisplayBajo.setText("<html><body style='color:red;'><h1>NO PUEDE REALIZAR MÁS OPERACIONES!!"
                                    + "<BR> HA LLEGADO AL TOPE DE 6.000.000 GS EN UN DÍA <br> Tarjeta Inabilitado</h1> "
                                    + "</body></html>");
                            //inabilita la tarjeta
                            bd.actualizarRegistro("tarjeta", "estado = 'Inactivo'", "id_tarjeta = "+CuentaActual.codigo);
                            Timer timer = new Timer(2000, (ActionEvent e) -> {

                                Inicio();
                            });
                            timer.setRepeats(false);
                            timer.start();
                            return;                     
                                                   
                            
                        }//fin validar 6millones

                        //JOptionPane.showMessageDialog(null, info.id);
                    } else {
                        this.cajero.DisplayDer.setText("");
                        this.cajero.DisplayIzq.setText("");
                        this.cajero.DisplayBajo.setText("<html><body style='color:red;'><h1>Cajero Con saldo insuficiente!!</h1> "
                                + "</body></html>");
                        //JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el proceso, reintente.");
                    }//fin if para no dar saldo negativo

                } else {
                    this.cajero.DisplayDer.setText("");
                    this.cajero.DisplayIzq.setText("");
                    this.cajero.DisplayBajo.setText("<html><body style='color:red;'><h1>Cajero fuera de Servicio!!</h1> "
                            + "</body></html>");


                            
                }//fin If verificar saldo del cajero

            }
            //faseActual = 7;

        }
        
        
    


}
    
    public int validadMonto(LocalDate fecha, String cliente){
        int monto =0;
        try {
                        
            ResultSet buscar=null;
            int codigo=1;
            //selecciona todos los campos de tarjeta y nombres del cliente
            //busca y compara el nombre por una instancia de nombre
            //devuelve el codigo de la tarjeta
            buscar=bd.dameLista("select tarjeta, cliente from transaccion");
            
            buscar.first();
            
            while(buscar.next()){
                
                if(buscar.getString(2).equals(cliente)){
                    
                    codigo = buscar.getInt(1);
                    break;
                    
                    
                }
            }
            //selecciona y suma el monto realizado en una fecha especifica
            ResultSet Suma = bd.dameLista("SELECT tarjeta, SUM(monto)"
                    + "   FROM transaccion"
                    + "   WHERE fecha = '"+fecha+"' "
                            + "AND tarjeta = '"+codigo+"'");
            // devuelve el monto
            //monto = Integer.parseInt(String.valueOf(Suma));
           //monto = Integer.parseInt(Suma.getInt(2));
            Suma.first();
            monto = Suma.getInt(2); 
            
            //JOptionPane.showMessageDialog(null,"el monto es: "+monto+"el cliente : "+cliente);  
            
        } catch (SQLException ex) {
            Logger.getLogger(logica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return monto;
    }

    
    
    
    
    
    
    /*
    public void verificarsaldo(){
        //JOptionPane.showMessageDialog(null, "el salo es de: "+saldoDelCajero(info.id));
                
        if (saldoDelCajero(info.id) > 0) {
            Inicio();
        } else {
            this.cajero.DisplayBajo.setText("<html><body style='color:red;'><h1>Cajero Consldo insuficiente!!</h1> "
                    + "</body></html>");
        }
    }
    */
    
    public int saldoDelCajero(int valor){
        int saldo=0;
        ResultSet buscar = bd.dameLista("select id_cajero,saldo from cajeros");
        try {
            buscar.beforeFirst();
             while(buscar.next()){
                 if (buscar.getInt(1)==valor) {
                     saldo = buscar.getInt(2);
                     //JOptionPane.showMessageDialog(null, "saldo "+saldo +" buscar: "+buscar.getInt(1));
                 }
                 //JOptionPane.showMessageDialog(null, "saldo "+buscar.getInt(3) +" buscar: "+buscar.getInt(1));    
             }
             
        } catch (SQLException ex) {
            Logger.getLogger(logica.class.getName()).log(Level.SEVERE, null, ex);
        }
      
           
       
        
        
        return saldo;
    }
    
    
    
    private void FaseSiete(){
        //java.util.Date fecha = new Date();
        LocalDate fecha = LocalDate.now();
            //formato moneda
            //NumberFormat formato = NumberFormat.getCurrencyInstance();
            
            String texto = "Banco RobaMas\n"
                    +""+CuentaActual.banco+"\n"
                    + "Cliente: " +CuentaActual.cliente+"\n"
                    + "Fecha: "+fecha+"\n"
                    + "Valor Retirado: "+Opcion+"\n"
                    + "Nuevo Saldo: "+CuentaActual.saldo;      
            JOptionPane.showOptionDialog(this.cajero, texto, "",
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,new Object[]{"OK"}, "OK");
            
            Inicio();
   }   
    
    private void MostrarSaldo(){
        //JOptionPane.showMessageDialog(null,"Mostar Saldo la opcion es :"+Opcion);
       //NumberFormat formato = NumberFormat.getCurrencyInstance();
       //String saldo = formato.format(CuentaActual);
       LocalDate fecha = LocalDate.now();
       String saldo = String.valueOf(CuentaActual.saldo);
       this.cajero.DisplayBajo.setText("<html><body style='color:yellow;'>Su saldo a la fecha: "+fecha+" <br>es de: "
               + "<h1 style='color:green'>"+saldo+"</h1>"
               + "</body></html>");
       this.cajero.DisplayDer.setText("<html><body>Imprimir"+Saltos+"</body></html>");
       this.cajero.DisplayIzq.setText("<html><body>Salir"+Saltos+"</body></html>");
       
   } 
    
    public void EventoBoton1(){
        switch(faseActual){
            case 1:
                FaseUno();
                break;
            case 2:
                FaseDos(1);
                break;
            case 5:
                if(Opcion==1){
                    FaseCinco(1);
                }else{
                    FaseCinco(1500000);
                
                }
                break;
            case 7:
                Inicio();
                break;
            default:
                break;
        }
    }
    
    public void EventoBoton2(){
        if (faseActual == 5){
            FaseCinco(1000000);
        }
    
    }
    public void EventoBoton3(){
        if(faseActual==5){
            FaseCinco(500000);
        }
    }
    
    public void EventoBoton4(){
        //otros montos
        if(faseActual==5){
            TEcladoActivo = true;
            CadenaTeclado="";
            this.cajero.DisplayBajo.setText("<html><body style='color:blue;'>"
                    +"<h1>Digite el monto a Extraer</h1> </body></html>");
            this.cajero.DisplayDer.setText("");
            this.cajero.DisplayIzq.setText("");
            faseActual=6;
        }
    }
    public void EventoBoton5(){
        switch (faseActual){
            case 1:
                FaseUno();
                break;
            case 2:
                FaseDos(2);
                break;    
            case 5:
                if(Opcion == 1){
                    FaseCinco(2);
                }else{
                    FaseCinco(400000);
                }
                
                break;
            case 7:
                FaseSiete();
                break;
            default:
                break;
        }
        
        
        
        
    }

    public void Eventoboton6(){
        if(faseActual==5){
            FaseCinco(300000);
        }
    }
    public void Eventoboton7(){
        if(faseActual==5){
            FaseCinco(200000);
        }
    }
    public void Eventoboton8(){
        if(faseActual==5){
            FaseCinco(100000);
        }
    }
    
    public void habilitar(){
        cajero.btn1.setEnabled(true);
        cajero.btn2.setEnabled(true);
        cajero.btn3.setEnabled(true);
        cajero.btn4.setEnabled(true);
        cajero.btn5.setEnabled(true);
        cajero.btn6.setEnabled(true);
        cajero.btn7.setEnabled(true);
        cajero.btn8.setEnabled(true);
            
    }
    public void desabilitar(){
        cajero.btn2.setEnabled(false);
        cajero.btn3.setEnabled(false);
        cajero.btn4.setEnabled(false);
        cajero.btn6.setEnabled(false);
        cajero.btn7.setEnabled(false);
        cajero.btn8.setEnabled(false);
        
    }
}
    
    
        
