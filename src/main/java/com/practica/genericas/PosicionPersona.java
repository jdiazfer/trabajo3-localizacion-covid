package com.practica.genericas;

import com.practica.excecption.EmsInvalidNumberOfDataException;

public class PosicionPersona {

	private static int MAX_DATOS_LOCALIZACION = 5;

	private Coordenada coordenada;
	private String documento;
	private FechaHora fechaPosicion;

	public static PosicionPersona parsePosicionPersona(String[] data) throws EmsInvalidNumberOfDataException {

		if (data.length < MAX_DATOS_LOCALIZACION) {
			throw new EmsInvalidNumberOfDataException("La cantidad de campos de la localización es incorrecta");
		}

		String documento = data[1];
		FechaHora fechaPosicion = FechaHora.parseFecha(data[2], data[3]);
		Coordenada coordenada = new Coordenada(Float.parseFloat(data[4]), Float.parseFloat(data[5]));
		return new PosicionPersona(coordenada, documento, fechaPosicion);
	}

	public PosicionPersona() {

	}

	public PosicionPersona(Coordenada coordenada, String documento, FechaHora fechaPosicion) {
		this.coordenada = coordenada;
		this.documento = documento;
		this.fechaPosicion = fechaPosicion;
	}

	public Coordenada getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public FechaHora getFechaPosicion() {
		return fechaPosicion;
	}

	public void setFechaPosicion(FechaHora fechaPosicion) {
		this.fechaPosicion = fechaPosicion;
	}

	@Override
	public String toString() {
		String cadena = "";
        cadena += String.format("%s;", getDocumento());
        FechaHora fecha = getFechaPosicion();        
        cadena+=String.format("%02d/%02d/%04d;%02d:%02d;", 
	        		fecha.getFecha().getDia(), 
	        		fecha.getFecha().getMes(), 
	        		fecha.getFecha().getAnio(),
	        		fecha.getHora().getHora(),
	        		fecha.getHora().getMinuto());
        cadena+=String.format("%.4f;%.4f\n", getCoordenada().getLatitud(), 
	        		getCoordenada().getLongitud());
	
		return cadena;
	}
		
}
