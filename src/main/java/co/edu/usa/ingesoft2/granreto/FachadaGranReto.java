package co.edu.usa.ingesoft2.granreto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import co.edu.usa.ingesoft2.granreto.ValidacionArchivo;

public class FachadaGranReto implements IFachadaGranReto
{
	ValidacionArchivo validacionArchivo;
    public static String TIPO_ORDEN;
    
    public FachadaGranReto()
    {
    }
    
    
	public void cargarArchivo(String rutaArchivo) throws GranRetoException
	{
		try
		{
			FileReader fr= new FileReader(rutaArchivo);
			BufferedReader br=new BufferedReader(fr);
			validacionArchivo = new ValidacionArchivo(br);	
	    	validacionArchivo.ejecutarValidaciones();
	    	validacionArchivo.obtenerResultadoValidacion();
		}
		catch (IOException ex)
		{
			throw new GranRetoException("Se presento un error cargando el archivo");
		}
		catch (ParseException e)
		{
			throw new GranRetoException("error al convertir la fecha");
		}	
	}
	
	
	public List<String> obtenerArticulosMasVendidos(String fechaInicial, String fechaFinal) throws GranRetoException
	{		
		int cantidadArticulos = 0;
		List<String> articulosMasVendidos = new ArrayList<String>();
		List<Producto> productos = validacionArchivo.getListaDeProductos();
		
		if(productos==null || productos.size()==0)
		{
			throw new GranRetoException();
		}
		
		Collections.sort(productos,Collections.reverseOrder());
		
		try
		{
			Date fechaInicio=validacionArchivo.pasarString_A_Date(fechaInicial);
			Date fechaFin=validacionArchivo.pasarString_A_Date(fechaFinal);
			for (Producto producto : productos ) {
				if(cantidadArticulos<10 && !(producto.getFecha().before(fechaInicio)) && !(producto.getFecha().after(fechaFin))) {
					articulosMasVendidos.add(validacionArchivo.normalizarNombreProducto(producto.getNombreProducto()).concat(":@:").concat(producto.getCantidad()));
					cantidadArticulos++;
				}
			}
			
		} catch (Exception e) {
			throw new GranRetoException("Se presento un error al buscar articulos mas vendidos");
		}
		return articulosMasVendidos;
	}

	public List<String> obtenerHistorialVentaArticulo(String articulo) throws GranRetoException {
		List<Producto> productos = validacionArchivo.getListaDeProductos();		
		FachadaGranReto.TIPO_ORDEN = "xfecha";
		Collections.sort(productos);
		
		List<String> historialVentas = new ArrayList<String>();
		String nombreArticulo = validacionArchivo.normalizarNombreProducto(articulo);
		try {
			for (Producto producto : productos ) {
				if(nombreArticulo.equals(validacionArchivo.normalizarNombreProducto(producto.getNombreProducto()))) {
					historialVentas.add(validacionArchivo.pasarDate_A_String(producto.getFecha()).concat(":@:").concat(producto.getCantidad()));
				}
			}
			if(historialVentas.size()==0) {
				throw new GranRetoException("No existe historial de ventas");
			}
		} catch (Exception e) {
			throw new GranRetoException();
		}
		FachadaGranReto.TIPO_ORDEN = "";

		return historialVentas;
	}

}