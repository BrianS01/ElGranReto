/**
 * 
 */
package co.edu.usa.ingesoft2.granreto;

import java.util.List;

//Ver especificación de los métodos en el enunciado del proyecto.  No cambie nada de este archivo, le va a jugar en contra.

public interface IFachadaGranReto
{
	void cargarArchivo( String rutaArchivo ) throws GranRetoException;
    
    List<String> obtenerArticulosMasVendidos( String fechaInicial, String fechaFinal ) throws GranRetoException;
    
    List<String> obtenerHistorialVentaArticulo( String articulo ) throws GranRetoException;
}