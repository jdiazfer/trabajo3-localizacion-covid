package com.practica.ems.covid;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.List;

import com.practica.excecption.EmsDuplicateLocationException;
import com.practica.excecption.EmsDuplicatePersonException;
import com.practica.excecption.EmsInvalidNumberOfDataException;
import com.practica.excecption.EmsInvalidTypeException;
import com.practica.excecption.EmsLocalizationNotFoundException;
import com.practica.excecption.EmsPersonNotFoundException;
import com.practica.genericas.Constantes;

import com.practica.genericas.Persona;
import com.practica.genericas.PosicionPersona;
import com.practica.lista.ListaContactos;

public class ContactosCovid {
	private Poblacion poblacion;
	private Localizacion localizacion;
	private ListaContactos listaContactos;

	public ContactosCovid() {
		this.poblacion = new Poblacion();
		this.localizacion = new Localizacion();
		this.listaContactos = new ListaContactos();
	}

	public Poblacion getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}



	public ListaContactos getListaContactos() {
		return listaContactos;
	}

	public void setListaContactos(ListaContactos listaContactos) {
		this.listaContactos = listaContactos;
	}

	public void loadData(String data, boolean reset) throws EmsInvalidTypeException, EmsInvalidNumberOfDataException,
			EmsDuplicatePersonException, EmsDuplicateLocationException {
		// borro información anterior
		if (reset) {
			this.poblacion = new Poblacion();
			this.localizacion = new Localizacion();
			this.listaContactos = new ListaContactos();
		}
		String datas[] = dividirEntrada(data);
		for (String linea : datas) {
			String datos[] = this.dividirLineaData(linea);
			if (!datos[0].equals("PERSONA") && !datos[0].equals("LOCALIZACION")) {
				throw new EmsInvalidTypeException();
			}
			if (datos[0].equals("PERSONA")) {
				if (datos.length != Constantes.MAX_DATOS_PERSONA) {
					throw new EmsInvalidNumberOfDataException("El número de datos para PERSONA es menor de 8");
				}
				this.poblacion.addPersona(Persona.parsePersona(datos));
			}
			if (datos[0].equals("LOCALIZACION")) {
				if (datos.length != Constantes.MAX_DATOS_LOCALIZACION) {
					throw new EmsInvalidNumberOfDataException("El número de datos para LOCALIZACION es menor de 6");
				}
				PosicionPersona pp = PosicionPersona.parsePosicionPersona(datos);
				this.localizacion.addLocalizacion(pp);
				this.listaContactos.insertarNodoTemporal(pp);
			}
		}
	}

	public void loadDataFile(String fichero, boolean reset) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String datas[] = null, data = null;
		loadDataFile(fichero, reset, archivo, fr, br, datas, data);

	}

	@SuppressWarnings("resource")
	public void loadDataFile(String fichero, boolean reset, File archivo, FileReader fr, BufferedReader br, String datas[], String data ) {
		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File(fichero);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			if (reset) {
				this.poblacion = new Poblacion();
				this.localizacion = new Localizacion();
				this.listaContactos = new ListaContactos();
			}
			/**
			 * Lectura del fichero	línea a línea. Compruebo que cada línea
			 * tiene el tipo PERSONA o LOCALIZACION y cargo la línea de datos en la
			 * lista correspondiente. Sino viene ninguno de esos tipos lanzo una excepción
			 */
			while ((data = br.readLine()) != null) {
				datas = dividirEntrada(data.trim());
				for (String linea : datas) {
					String datos[] = this.dividirLineaData(linea);
					if (!datos[0].equals("PERSONA") && !datos[0].equals("LOCALIZACION")) {
						throw new EmsInvalidTypeException();
					}
					if (datos[0].equals("PERSONA")) {
						if (datos.length != Constantes.MAX_DATOS_PERSONA) {
							throw new EmsInvalidNumberOfDataException("El número de datos para PERSONA es menor de 8");
						}
						this.poblacion.addPersona(Persona.parsePersona(datos));
					}
					if (datos[0].equals("LOCALIZACION")) {
						if (datos.length != Constantes.MAX_DATOS_LOCALIZACION) {
							throw new EmsInvalidNumberOfDataException(
									"El número de datos para LOCALIZACION es menor de 6" );
						}
						PosicionPersona pp = PosicionPersona.parsePosicionPersona(datos);
						this.localizacion.addLocalizacion(pp);
						this.listaContactos.insertarNodoTemporal(pp);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public int findPersona(String documento) throws EmsPersonNotFoundException {
		return poblacion.findPersona(documento);
	}

	public int findLocalizacion(String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {
		return localizacion.findLocalizacion(documento, fecha, hora);
	}

	public List<PosicionPersona> localizacionPersona(String documento) throws EmsPersonNotFoundException {
		return localizacion.localizacionPersona(documento);
	}

	public boolean delPersona(String documento) throws EmsPersonNotFoundException {
		poblacion.delPersona(documento);
		return false;
	}

	private String[] dividirEntrada(String input) {
		return input.split("\\n");
	}

	private String[] dividirLineaData(String data) {
		return data.split("\\;");
	}

}