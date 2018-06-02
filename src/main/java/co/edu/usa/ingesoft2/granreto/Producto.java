package co.edu.usa.ingesoft2.granreto;

import java.util.Date;

public class Producto implements Comparable<Producto>
{
	private Date fecha;
	private String nombreProducto;
	private String cantidad;
	
	public Producto()
	{
	}
	
	
	public int compareTo(Producto producto)
	{		
		int resultado;
		
		if(FachadaGranReto.TIPO_ORDEN.equals("xfecha"))
		{
			resultado = fecha.compareTo(producto.fecha);
		}
		else
		{
			resultado = cantidad.compareTo(producto.cantidad);
		}
		
		return resultado;
	}

	
	public Date getFecha()
	{
		return fecha;
	}
	

	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}
	

	public String getNombreProducto()
	{
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto)
	{
		this.nombreProducto = nombreProducto;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String linea) {
		this.cantidad = linea;
	}	
}
