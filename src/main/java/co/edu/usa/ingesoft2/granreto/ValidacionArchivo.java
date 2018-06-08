package co.edu.usa.ingesoft2.granreto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ValidacionArchivo
{
	private BufferedReader br;
	private List<String> errores;
	private int tipoDeLinea;
    FachadaGranReto fachadaGranReto=new FachadaGranReto();
	private List<Producto> productos;
	
	
	public ValidacionArchivo(BufferedReader br)
	{
		this.br = br;
		errores = new ArrayList();
		productos=new ArrayList();
	}

	
	public List<Producto> getListaDeProductos()
	{
		return productos;
	}
	
	
	public String obtenerRutaArhivo(String rutaArchivo)
	{
		return rutaArchivo;
	}
	
	
	public void guardarCambiosArchivos(String rutaArchivo)
	{
		File archivo = new File(rutaArchivo);
		BufferedReader br;
		
		try
		{
			if(archivo.exists())
			{
				br = new BufferedReader(new FileReader(rutaArchivo));
				System.out.println("El archivo ya fue creado anteriormente");
			}
			else
			{
				br = new BufferedReader(new FileReader(archivo));
			}
		}
		catch (IOException e)
		{
			errores.add("Se presento un error al cargar el archivo");
		}	
	}
	
	
	public String validarNombreProducto(String nombreProducto) throws GranRetoException
	{
		validarCaracteresNombreProducto(nombreProducto);		
		return nombreProducto;
	}
	
	
	public void ejecutarValidaciones() throws ParseException, GranRetoException
	{
		try
		{
			validarArchivoVacio(br.ready());
			recorrerArchivo();
		}
		catch (IOException e)
		{
			errores.add("Se presento un error al cargar el archivo");
		}
	}

	
	public void validarArchivoVacio(boolean estaLleno)
	{
		if (!estaLleno)
		{
			errores.add("El archivo no puede estar vacio");
		}

	}
	
	
	public void validarSoloUnEspacioFechaHora(String fechaHora)
	{
		String[] elementos = fechaHora.trim().split(" ");
		int cantidadElementos = elementos.length;

		if (cantidadElementos != 2)
		{
			errores.add("Cantidad de espacios internos de la fecha " + fechaHora + " son incorrectos");
		}
	}
	

	public void validarFecha(String fechaHora)
	{
		try
		{
			// Validar enteros fecha.
			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			formato.setLenient(false);
			Date fechaParseasa = formato.parse(fechaHora);
			String fecha = formato.format(fechaParseasa);
		}
		catch (Exception e)
		{
			errores.add("Formato de fecha incorrecto " + fechaHora + " debe ser yyyy/MM/dd HH:mm");
		}		

	}
	

	public String obtenerResultadoValidacion() throws GranRetoException
	{
		String mensaje = "\n";
		
		if (errores.size() == 0)
		{
			mensaje = "No se presentaron errores";
		}
		else
		{
			for (String error : errores)
			{
				mensaje += error + "\n";
			}
			throw new GranRetoException(mensaje);
		}
		
		return mensaje;
	}
	

	public String normalizarNombreProducto(String nombreProducto)
	{
		String nombreNormalizado = nombreProducto.trim().toUpperCase();
		return nombreNormalizado;
	}
	
	
	public void SinDobleEspaciosNombreProducto(String nombreProducto) throws GranRetoException
	{
		String nombreNormalizado =normalizarNombreProducto(nombreProducto);
		
		if(nombreNormalizado.contains("  "))
		{
			throw new GranRetoException();
		}
	}
	
	
	//Filtrar los productos cuya fecha este entre los rangos que estén entre dos fechas. 
	public void recorrerArchivo() throws IOException, ParseException, GranRetoException
	{
		Producto producto=new Producto();
		String linea = "";
		int tipoLinea = 0;
		
		while ((linea = br.readLine()) != null)
		{
			tipoLinea = validarTipoDeEntrada(linea);
			switch (tipoLinea)
			{
				case 1:
					validacionesFecha(linea);
					producto.setFecha(pasarString_A_Date(linea));
				break;
				case 2:
					validacionesNombreProducto(linea);
					producto.setNombreProducto(linea);//al producto le asigno el nombre del producto
				break;
				case 3:
					validacionesCantidad(linea);
					producto.setCantidad(linea);//al producto le agrego la cantidad
					
					if(producto!=null && producto.getCantidad()!=null && producto.getFecha()!=null && producto.getNombreProducto()!=null)
					{
						productos.add(producto);
					}
				
					producto = new Producto();
				break;
				default:
				continue;
			}
		}
	}
	

	public void validacionesFecha(String linea)
	{
		validarFecha(linea);
		validarSoloUnEspacioFechaHora(linea);
	}
	

	public void validacionesNombreProducto(String linea) throws GranRetoException
	{
		SinDobleEspaciosNombreProducto(linea);
		validarNombreProducto(linea);
	}
	
	
	public void validacionesCantidad(String linea)
	{
		 validarFormatoCantidad(linea);
		 validarRangoCantidad(linea);
	}
	
	
	public String validacionesCantidad1(String linea)
	{
		 validarFormatoCantidad(linea);
		 validarRangoCantidad(linea);
		 return linea;
	}
	
	
	public int validarTipoDeEntrada(String linea)
	{
		if (tipoDeLinea == 3)
		{
			tipoDeLinea = 0;
		}

		if (linea.trim().length() != 0)
		{
			tipoDeLinea++;
		}
		else
		{
			return 4;
		}
		
		return tipoDeLinea;
	}
	

	public void validarFormatoCantidad(String linea)
	{
		String[] elementos = linea.trim().split(",");
		
		if(elementos.length!=2)
		{
			errores.add("La cantidad debe contener una coma unicamente "+linea.trim());
		}

		for (int i = 0; i < linea.length(); i++)
		{
			int rangoCaracteres = linea.codePointAt(i);
			
			if(!((rangoCaracteres >= 48 && rangoCaracteres <= 57) ||rangoCaracteres ==44))
			{
				errores.add("la línea contiene carácteres especiales "+linea.trim());
			}
		}
	}
	
	
	public void validarRangoCantidad(String linea)
	{
		double cantidad=0;
		
		try
		{
		cantidad = Double.parseDouble(linea.trim().replace(",", "."));
		if(!(cantidad>0.0 && cantidad<=1000000.0)) {
			errores.add("El rango debe ser mayor a 0,0 y menor o igual a 1000000,0 "
					+ "y se ingreso "+linea.trim());
		}
		}catch(Exception e) {
			errores.add("El formato de cantidad es incorrecto "+linea.trim());
		}
	}
	
	
  
	// Estos nombres admiten letras, números, apóstrofes, guiones y paréntesis.
	
	
	// Buscar el nombre del producto no puede divirdirse entre líneas.

	
	public void separarCantidadProductoPorComas(String cantidadProducto) {
		String[] elementos = cantidadProducto.trim().split(",");

	}


  
	// Estos nombres admiten letras, números, apóstrofes, guiones y paréntesis.
	
	
	public void validarCaracteresNombreProducto(String linea) throws GranRetoException {
		int rangoCaracteres;
		try {
			for (int i = 0; i < linea.length(); i++) {
				rangoCaracteres = linea.codePointAt(i);
				
				if (!(rangoCaracteres>=65 && rangoCaracteres<=90) &&
						!(rangoCaracteres>=97 && rangoCaracteres<=122) &&
						!(rangoCaracteres>=48 && rangoCaracteres<=57) && 
						!(rangoCaracteres==45) &&
						!(rangoCaracteres>=40 && rangoCaracteres<=41) &&
						!(rangoCaracteres>=65 && rangoCaracteres<=90) &&
						!(rangoCaracteres==39) ) { 
					throw new GranRetoException();
				//	errores.add("el nombre del producto " + linea + " no esta escrito correctamente");
				//	break;
				}
			}

		} catch (Exception e) {
			errores.add("Nombre del producto incorrecto" + linea);
			throw new GranRetoException();
		}
		
		
	}
	
	public Date pasarString_A_Date(String fechaHora) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		formato.setLenient(false);
		return formato.parse(fechaHora);
	}
	
	public String pasarDate_A_String(Date fechaHora) throws ParseException {
		String fecha="";
		SimpleDateFormat anio = new SimpleDateFormat("yyyy");
		anio.setLenient(false);
		fecha+=anio.format(fechaHora)+"/";
		SimpleDateFormat mes = new SimpleDateFormat("MM");
		mes.setLenient(false);
		fecha+=mes.format(fechaHora)+"/";
		SimpleDateFormat dia = new SimpleDateFormat("dd");
		dia.setLenient(false);
		fecha+=dia.format(fechaHora);
		return fecha;
	}
	
}
