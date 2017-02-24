package es.cic.curso.curso17.ejercicio026.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.cic.curso.grupo6.ejercicio027.modelo.Identificable;

@Entity
@Table(name = "CIRCULO")
public class Circulo implements Identificable<Long> {
	private static final long serialVersionUID = -3876741723091519309L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Referencia a la figura registrada en BB.DD. */
	@JoinColumn(name = "id_figura")
	@OneToOne(fetch = FetchType.EAGER)
	private Figura figura;
	
	/** Radio de la figura. */
	@Column(name = "radio")
	private Double radio;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the figura
	 */
	public Figura getFigura() {
		return figura;
	}

	/**
	 * @return the radio
	 */
	public Double getRadio() {
		return radio;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param figura the figura to set
	 */
	public void setFigura(Figura figura) {
		this.figura = figura;
	}

	/**
	 * @param radio the radio to set
	 */
	public void setRadio(Double radio) {
		this.radio = radio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((figura == null) ? 0 : figura.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Circulo other = (Circulo) obj;
		if (figura == null) {
			if (other.figura != null)
				return false;
		} else if (!figura.equals(other.figura))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Circulo [id=" + id + ", figura=" + figura + ", radio=" + radio + "]";
	}

}
