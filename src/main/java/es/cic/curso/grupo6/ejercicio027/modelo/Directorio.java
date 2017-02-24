package es.cic.curso.grupo6.ejercicio027.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "DIRECTORIO")
public abstract class Directorio implements Identificable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8800715225024553533L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "version")
	private String version;
	
	@OneToMany(mappedBy = "directorio")
	private List <Directorio> directorios = new ArrayList<>();
	
	
	public Directorio() {
		super();
		
	}

	
	public Directorio(String nombre, String descripcion, String version, List<Directorio> directorios) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.version = version;
		this.directorios = directorios;
	}

	

	public Directorio(Long id, String nombre, String descripcion, String version, List<Directorio> directorios) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.version = version;
		this.directorios = directorios;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public List<Directorio> getDirectorios() {
		return directorios;
	}


	public void setDirectorios(List<Directorio> directorios) {
		this.directorios = directorios;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Directorio other = (Directorio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Directorio [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", version=" + version
				+ ", directorios=" + directorios + "]";
	}




	
	}
