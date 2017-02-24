package es.cic.curso.curso17.ejercicio026.dto;

import es.cic.curso.curso17.ejercicio026.modelo.Lienzo;

public class FiguraDTO {

	public static final int INDEFINIDO = 0;
	public static final int LINEA = 1;
	public static final int CIRCULO = 2;
	public static final int TRIANGULO = 3;
	public static final int CRUZ = 4;
	public static final int ASPA = 5;
	public static final int RECTANGULO = 6;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	private Long id;

	/** Referencia al lienzo del que forma parte la figura. */
	private Lienzo lienzo;

	/** Código con el tipo de figura geométrica. Rango: <code>[0, 5]</code>. */
	private int tipo;

	/**
	 * Coordenada del origen con respecto a un punto de referencia en el eje X.
	 */
	private Double x;

	/**
	 * Coordenada del origen con respecto a un punto de referencia en el eje Y.
	 */
	private Double y;

	/** Rotación en sentido horario. (En grados). */
	private int rotacion;

	/** Color de la figura (cadena con su representación hexadecimal). */
	private String color;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the lienzo
	 */
	public Lienzo getLienzo() {
		return lienzo;
	}

	/**
	 * @return the tipo
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @return the x
	 */
	public Double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public Double getY() {
		return y;
	}

	/**
	 * @return the rotacion
	 */
	public int getRotacion() {
		return rotacion;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param lienzo
	 *            the lienzo to set
	 */
	public void setLienzo(Lienzo lienzo) {
		this.lienzo = lienzo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(int tipo) {
		if (tipo < 0)
			throw new IllegalArgumentException();
		this.tipo = tipo;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(Double x) {
		this.x = x;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(Double y) {
		this.y = y;
	}

	/**
	 * @param rotacion
	 *            the rotacion to set
	 */
	public void setRotacion(int rotacion) {
		this.rotacion = rotacion;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		if (color.length() < 3 || color.length() > 7)
			throw new IllegalArgumentException();
		this.color = color;
	}

}
