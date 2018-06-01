package co.edu.usa.ingesoft2.granreto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ValidacionArchivo {

	private BufferedReader br;
	private static List<String> errores;
	private int tipoDeLinea;
	static FachadaGranReto fachadaGranReto=new FachadaGranReto();
	
	public ValidacionArchivo(BufferedReader br) {
		this.br = br;
		errores = new ArrayList();
	}
	
	public String obtenerRutaArhivo(String rutaArchivo) {
		return rutaArchivo;
	}
	
	
	public static List<String> obtenerDatosFecha(String rutaArchivo) throws GranRetoException
	{
		List<String> fechas;
		fechas=new ArrayList();
		String datosArchivo=fachadaGranReto.cargarArchivo1(rutaArchivo);
		int j= 0;
		for (int i = 0; i < datosArchivo.length(); i++)
		{
			j = i+2;
			fechas.add(datosArchivo);
		}
		/*
		 * //String fechaHora="2010/05/20 20:05";
		String datosArchivo=fachadaGranReto.cargarArchivo1(rutaArchivo);
		//datosArchivo.getFechas();
		boolean encontroFecha=false;
		
		for (int i = 0; i < datosArchivo.length(); i++) {
			if(datosArchivo!=null ) {
				fechas.add("algo");
			//fechas.add(validarFecha1(fechaHora));
			encontroFecha=true;
			}
			else {
				encontroFecha=false;
				System.out.println("No se encontraron fechas");
			}
		}*/
		return fechas;
	}
	
	public void obtenerFechaInicialFinal(List<String> fechas) {
		/*int barraDeDividir;
		try {
			List listado = obtenerDatosFecha(null, listado);
			obtenerDatosFecha(rutaArchivo, fechas);
			for (int i = 0; i < fechas.size(); i++) {
				String a=fechas.get(i);
				String b=fechas.get(i+1);
				barraDeDividir = fechas.get(i).codePointAt(47);
				
				if(fechas.get(i).equals(barraDeDividir)==47) {
					
				}
					//corresponde a /
				
				//fechas.get(i).compareTo(fechas.get(i+1));
				i++;
			}
		} catch (GranRetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	public void guardarCambiosArchivos(String rutaArchivo) {
		File archivo = new File(rutaArchivo);
		BufferedReader br;
		
		
			try {
				if(archivo.exists()) {
				br = new BufferedReader(new FileReader(rutaArchivo));
				System.out.println("El archivo ya fue creado anteriormente");
				}
				else {
					br = new BufferedReader(new FileReader(archivo));
					System.out.println("El archivo es nuevo y no había sido "
							+ "creado antes");
				}
			} catch (IOException e) {
				errores.add("Se presento un error al cargar el archivo");
			}
			
		}
	
	
	public static List<String> guardarDatosNombreProducto(String rutaArchivo, String nombreProducto, List<String> nombres) throws GranRetoException {
		nombres=new ArrayList();
		fachadaGranReto.cargarArchivo1(rutaArchivo);
		String nombreProducto1=validarNombreProducto(nombreProducto);
		nombres.add(nombreProducto1);
		System.out.println(nombres);
		return nombres;
	}
	
	//Método Sterling
	public List<String> buscarRegistros(String nombreArticulo, String fechaHora, String cantidadVentas) {
		
		//validacionesNombreProducto   este método está bien
		//guardarDatosNombreProducto
		return null;
	}
	
	public static List<String> guardarDatosCantidadProducto(String rutaArchivo, String cantidadProducto,List<String> cantidad) throws GranRetoException {
		cantidad= new ArrayList();
		fachadaGranReto.cargarArchivo1(rutaArchivo);
		String cantidadProducto1=validacionesCantidad1(cantidadProducto);
		cantidad.add(cantidadProducto1);
		System.out.println(cantidad);
		return cantidad;
	}
	
	public static String validarNombreProducto(String nombreProducto) {
		validarCaracteresNombreProducto(nombreProducto);
		System.out.println(nombreProducto);
		
		return nombreProducto;
	}
	
	
	public void ejecutarValidaciones() {
		try {
			validarArchivoVacio(br.ready());
			recorrerArchivo();
		} catch (IOException e) {
			errores.add("Se presento un error al cargar el archivo");
		}
	}

	public void validarArchivoVacio(boolean estaLleno) {

		if (!estaLleno) {
			errores.add("El archivo no puede estar vacio");
		}

	}
	
	public static void main(String[] args) throws GranRetoException {
		FachadaGranReto ifg = new FachadaGranReto();
		List <String> fechas=new ArrayList();
		
		
		fechas.add("2010/05/25 20:05");
		fechas.add("2011/08/30 16:03");
		
		String rutaArchivo=ifg.cargarArchivo1("C:\\Users\\cristian\\eclipse-workspace\\GranReto\\direccion.txt");
		//obtenerDatosFecha(rutaArchivo, fechas);
		System.out.println("que es esto");
		String datosArchivo=fachadaGranReto.cargarArchivo1(rutaArchivo);
		System.out.println("esto se acabo");
		
		/*try {
			String rutaArchivo=ifg.cargarArchivo1("C:\\Users\\cristian\\eclipse-workspace\\GranReto\\direccion.txt");
			//obtenerDatosFecha(rutaArchivo, fechas);
			
			System.out.println("oe estoy aqui");
			System.out.println(obtenerDatosFecha(rutaArchivo));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Algo salio mal");
		}*/
		
		
	}

	public void validarSoloUnEspacioFechaHora(String fechaHora) {
		String[] elementos = fechaHora.trim().split(" ");
		int cantidadElementos = elementos.length;

		if (cantidadElementos != 2) {
			errores.add("Cantidad de espacios internos de la fecha " + fechaHora + " son incorrectos");
		}
	}

	public void validarFecha(String fechaHora) {
		try {
			// Validar enteros fecha.
			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			formato.setLenient(false);
			Date fechaParseasa = formato.parse(fechaHora);
			String fecha = formato.format(fechaParseasa);
		}

		catch (Exception e) {
			errores.add("Formato de fecha incorrecto " + fechaHora + " debe ser yyyy/MM/dd HH:mm");
		}		

	}
	
	public static String validarFecha1(String fechaHora){
		String fecha = "";
		try {
			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			formato.setLenient(false);
			Date fechaParseasa = formato.parse(fechaHora);
			fecha = formato.format(fechaParseasa);
			return fecha;
		} catch (Exception e) {
			errores.add("Formato de fecha incorrecto " + fechaHora + " debe ser yyyy/MM/dd HH:mm");
		}
		return fecha;
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
