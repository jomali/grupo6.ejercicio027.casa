package es.cic.curso.grupo6.ejercicio027.dto;

public class FicheroDTO {

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	private Long id;

	/** Directorio del que cuelga el fichero. */
	private String directorio;

	/** Nombre del fichero. */
	private String nombre;

	/** Descripción <em>opcional</em>. */
	private String descripcion;

	/** Código con la versión del fichero. */
	private Double version;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the directorio
	 */
	public String getDirectorio() {
		return directorio;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return the version
	 */
	public Double getVersion() {
		return version;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param directorio
	 *            the directorio to set
	 */
	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Double version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "FicheroDTO [id=" + id + ", directorio=" + directorio + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", version=" + version + "]";
	}

}
