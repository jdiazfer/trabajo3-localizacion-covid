package com.practica.genericas;



import java.time.LocalDateTime;

public class FechaHora implements Comparable<FechaHora>{
	public static class Fecha {{
		private int dia, mes, anio;
		public static  Fecha parseFecha(String data) throws IllegalArgumentException{
			String[] valores = data.split("\\/");
			if (valores.length != 3) {
				throw new IllegalArgumentException("Formato de fecha incorrecto");
			}

			int dia = Integer.parseInt(valores[0]);
			int mes = Integer.parseInt(valores[1]);
			int anio = Integer.parseInt(valores[2]);

			return new Fecha(dia, mes, anio);
		}
		public Fecha(int dia, int mes, int anio) {
			super();
			this.dia = dia;
			this.mes = mes;
			this.anio = anio;
		}

		public int getDia() {
			return dia;
		}

		public void setDia(int dia) {
			this.dia = dia;
		}

		public int getMes() {
			return mes;
		}

		public void setMes(int mes) {
			this.mes = mes;
		}

		public int getAnio() {
			return anio;
		}

		public void setAnio(int anio) {
			this.anio = anio;
		}

		@Override
		public String toString() {
			String cadena = String.format("%2d/%02d/%4d",dia,mes,anio);
			return cadena;
		}
		
		

	}

		public static class Hora { {
		private int hora, minuto;
			public static Hora parseHora(String data) throws IllegalArgumentException {
				String[] valores = data.split("\\:");
				if (valores.length != 2) {
					throw new IllegalArgumentException("Formato de hora incorrecto");
				}

				int hora = Integer.parseInt(valores[0]);
				int minuto = Integer.parseInt(valores[1]);

				return new Hora(hora, minuto);
			}


		public Hora(int hora, int minuto) {
			super();
			this.hora = hora;
			this.minuto = minuto;
		}

		public int getHora() {
			return hora;
		}

		public void setHora(int hora) {
			this.hora = hora;
		}

		public int getMinuto() {
			return minuto;
		}

		public void setMinuto(int minuto) {
			this.minuto = minuto;
		}

		@Override
		public String toString() {
			return String.format("%02d:%02d", hora,minuto);
		}
		

	}

	Fecha fecha;
	Hora hora;
			public static FechaHora parseFecha(String fecha) throws IllegalArgumentException{
				Fecha date = Fecha.parseFecha(fecha);
				Hora time = new Hora(0, 0);
				return new FechaHora(date, time);
			}

			public static FechaHora parseFecha(String fecha, String hora) throws IllegalArgumentException{
				Fecha date = Fecha.parseFecha(fecha);
				Hora time = Hora.parseHora(hora);
				return new FechaHora(date, time);
			}
			public FechaHora(Fecha fecha, Hora hora) {
		super();
		this.fecha = fecha;
		this.hora = hora;
	}

	public FechaHora(int dia, int mes, int anio, int hora, int minuto) {
		this.fecha = new Fecha(dia, mes, anio);
		this.hora = new Hora(hora, minuto);
	}

	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public Hora getHora() {
		return hora;
	}

	public void setHora(Hora hora) {
		this.hora = hora;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FechaHora fecha = (FechaHora) obj;
		return getFecha().getDia() == fecha.getFecha().getDia() && getFecha().getMes() == fecha.getFecha().getMes()
				&& getFecha().getAnio() == fecha.getFecha().getAnio()
				&& getHora().getHora() == fecha.getHora().getHora()
				&& getHora().getMinuto() == fecha.getHora().getMinuto();
	}

	@Override
	public int compareTo(FechaHora o) {
		LocalDateTime dateTime1= LocalDateTime.of(this.getFecha().getAnio(), this.getFecha().getMes(), this.getFecha().getDia(), 
				this.getHora().getHora(), this.getHora().getMinuto());
		LocalDateTime dateTime2= LocalDateTime.of(o.getFecha().getAnio(), o.getFecha().getMes(), o.getFecha().getDia(), 
				o.getHora().getHora(), o.getHora().getMinuto());
		
		return dateTime1.compareTo(dateTime2);
	}


	@Override
	public String toString() {
		return String.format("%02d/%02d/%04d;%02d:%02d;",
				fecha.getDia(),
				fecha.getMes(),
				fecha.getAnio(),
				hora.getHora(),
				hora.getMinuto());
	}


}
