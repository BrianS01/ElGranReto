package co.edu.usa.ingesoft2.granreto;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ValidacionArchivo
{
	private BufferedReader br;
	private static List<String> errores;
	private int tipoDeLinea;
	static FachadaGranReto fachadaGranReto=new FachadaGranReto();
	
	public ValidacionArchivo(BufferedReader br)
	{
		this.br = br;
		errores = new ArrayList();
	}
	
	
	public static void obtenerFecha(String fechaInicial, String fechaFinal, String rutaArchivo)
	{
		try
		{
			fachadaGranReto.cargarArchivo1(rutaArchivo);
		}
		catch (GranRetoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static List<String> obtenerDatosFecha(String rutaArchivo, String fechaHora, List<String> fechas) throws GranRetoException
	{
		fechas=new ArrayList();
		String datosArchivo=fachadaGranReto.cargarArchivo1(rutaArchivo);
		boolean encontroFecha=false;
		
		for (int i = 0; i < datosArchivo.length(); i++)
		{
			if(datosArchivo!=null)
			{
				fechas.add(validarFecha1(fechaHora));
				encontroFecha=true;
			}
			else
			{
				encontroFecha=false;
				System.out.println("No se encontraron fechas");
			}
		}
		
		return fechas;
	}
	
	
	public static List<String> guardarDatosNombreProducto(String rutaArchivo, String nombreProducto, List<String> nombres) throws GranRetoException
	{
		nombres=new ArrayList();
		fachadaGranReto.cargarArchivo1(rutaArchivo);
		String nombreProducto1=validarNombreProducto(nombreProducto);
		nombres.add(nombreProducto1);
		System.out.println(nombres);
		return nombres;
	}
	
	
	//Método Sterling
	public List<String> buscarRegistros(String nombreArticulo, String fechaHora, String cantidadVentas)
	{
		//validacionesNombreProducto   este método está bien
		//guardarDatosNombreProducto
		return null;
	}
	
	
	public static List<String> guardarDatosCantidadProducto(String rutaArchivo, String cantidadProducto,List<String> cantidad) throws GranRetoException
	{
		cantidad= new ArrayList();
		fachadaGranReto.cargarArchivo1(rutaArchivo);
		String cantidadProducto1=validacionesCantidad1(cantidadProducto);
		cantidad.add(cantidadProducto1);
		System.out.println(cantidad);
		return cantidad;
	}
	
	
	public static String validarNombreProducto(String nombreProducto)
	{
		validarCaracteresNombreProducto(nombreProducto);
		System.out.println(nombreProducto);
		
		return nombreProducto;
	}
	
	
	public void ejecutarValidaciones()
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
	
	
	public static void main(String[] args)
	{
		FachadaGranReto ifg = new FachadaGranReto();
		List <String> nombreProductos=new ArrayList();
		//String rutaArchivo=ifg.cargarArchivo1("C:\\Users\\cristian\\eclipse-workspace\\GranReto\\direccion.txt");
		
		try
		{
		//	obtenerDatosFecha(rutaArchivo, fechaHora, fechas);
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			System.out.println("Algo salio mal");
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
	
	public static String validarFecha1(String fechaHora) {
		try {
			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			formato.setLenient(false);
			Date fechaParseasa = formato.parse(fechaHora);
			String fecha = formato.format(fechaParseasa);
			
		} catch (Exception e) {
			errores.add("Formato de fecha incorrecto " + fechaHora + " debe ser yyyy/MM/dd HH:mm");
		}
		return fechaHora;
	}

	public String obtenerResultadoValidacion() throws GranRetoException {
		String mensaje = "\n";
		if (errores.size() == 0) {
			mensaje = "No se presentaron errores";
		} else {
			for (String error : errores) {
				mensaje += error + "\n";
			}
			throw new GranRetoException(mensaje);
		}
		return mensaje;
	}

	public String normalizarNombreProducto(String nombreProducto) {
		return nombreProducto.trim().toUpperCase();
	}

	public void recorrerArchivo() throws IOException {
		String linea = "";
		int tipoLinea = 0;
		while ((linea = br.readLine()) != null) {
			tipoLinea = validarTipoDeEntrada(linea);
			switch (tipoLinea) {
			case 1:
				validacionesFecha(linea);
				break;
			case 2:
				validacionesNombreProducto(linea);
				break;
			case 3:
				validacionesCantidad(linea);
				break;
			default:
				continue;
			}
		}
	}

	public void validacionesFecha(String linea) {
		validarFecha(linea);
		validarSoloUnEspacioFechaHora(linea);
	}

	public void validacionesNombreProducto(String linea) {
		linea = normalizarNombreProducto(linea);
		System.out.println(linea);
	}
	
	public void validacionesCantidad(String linea) {
		 validarFormatoCantidad(linea);
		 validarRangoCantidad(linea);
	}
	
	public static String validacionesCantidad1(String linea) {
		 validarFormatoCantidad(linea);
		 validarRangoCantidad(linea);
		 return linea;
	}
	
	public int validarTipoDeEntrada(String linea) {
		if (tipoDeLinea == 3) {
			tipoDeLinea = 0;
		}

		if (linea.trim().length() != 0) {
			tipoDeLinea++;
		}else {
			return 4;
		}
		
		return tipoDeLinea;
	}

	public static void validarFormatoCantidad(String linea) {
		String[] elementos = linea.trim().split(",");
		if(elementos.length!=2) {
			errores.add("La cantidad debe contener una coma unicamente "+linea.trim());
		}

		for (int i = 0; i < linea.length(); i++) {
		int rangoCaracteres = linea.codePointAt(i);
			if(!((rangoCaracteres >= 48 && rangoCaracteres <= 57) ||rangoCaracteres ==44)) {
				errores.add("la línea contiene carácteres especiales "+linea.trim());
			}
		}
	}
	public static void validarRangoCantidad(String linea) {
		
		
		double cantidad=0;
		try {
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
	
	
	public static void validarCaracteresNombreProducto(String linea) {
		int rangoCaracteres;
		try {
			for (int i = 0; i < linea.length(); i++) {
				rangoCaracteres = linea.codePointAt(i);
				
				if (!((rangoCaracteres >= 97 && rangoCaracteres <= 122)
						|| (rangoCaracteres >= 48 && rangoCaracteres <= 57) ||
						(rangoCaracteres>=65 && rangoCaracteres<=90)|| rangoCaracteres == 39
						|| rangoCaracteres == 95 || rangoCaracteres == 45 || rangoCaracteres == 40
						|| rangoCaracteres == 41 || rangoCaracteres == 91 || rangoCaracteres == 93)) { 
					errores.add("el nombre del producto " + linea + " no esta escrito correctamente");
					break;
				}
			}

		} catch (Exception e) {
			errores.add("Nombre del producto incorrecto" + linea);
		}
		
		
	}
	
	
}
