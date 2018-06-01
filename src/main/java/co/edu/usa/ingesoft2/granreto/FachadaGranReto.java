package co.edu.usa.ingesoft2.granreto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import co.edu.usa.ingesoft2.granreto.ValidacionArchivo;


public class FachadaGranReto implements IFachadaGranReto{

	ValidacionArchivo validacionArchivo;
    
    public FachadaGranReto() {
    }
	
	public void cargarArchivo(String rutaArchivo) throws GranRetoException {
		try {
			
			FileReader fr= new FileReader(rutaArchivo);
			BufferedReader br=new BufferedReader(fr);

			
			
			validacionArchivo = new ValidacionArchivo(br);	
	    	validacionArchivo.ejecutarValidaciones();
	    	validacionArchivo.obtenerResultadoValidacion();
		} catch (IOException ex) {
			throw new GranRetoException("Se presento un error cargando el archivo");
		}	
		
		
	}

	public String cargarArchivo1(String rutaArchivo) throws GranRetoException {
		try {
			
			FileReader fr= new FileReader(rutaArchivo);
			BufferedReader br=new BufferedReader(fr);

			validacionArchivo = new ValidacionArchivo(br);	
	    	validacionArchivo.ejecutarValidaciones();
	    	validacionArchivo.obtenerResultadoValidacion();
		} catch (IOException ex) {
			throw new GranRetoException("Se presento un error cargando el archivo");
		}	
		return rutaArchivo;
		
	}
	
	public static void main(String[] args)  {
			FachadaGranReto ifg = new FachadaGranReto();
			try {
				ifg.cargarArchivo("C:\\Users\\cristian\\eclipse-workspace\\GranReto\\direccion.txt");
				
			} catch (GranRetoException e) {
				System.out.println("GranRetoException:"+e.getMessage());
			}
	}
	
	public List<String> obtenerArticulosMasVendidos(String fechaInicial, String fechaFinal) throws GranRetoException {
		FachadaGranReto ifg = new FachadaGranReto();
	
		try {
         
		} catch (Exception e) {
			System.out.println("GranRetoException:"+e.getMessage());
		}
		return null;
	}

	public List<String> obtenerHistorialVentaArticulo(String articulo) throws GranRetoException {
		// validacionesNombreProducto
		//
		return null;
	}

}
