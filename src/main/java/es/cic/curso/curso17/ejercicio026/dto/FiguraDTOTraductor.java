package es.cic.curso.curso17.ejercicio026.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.cic.curso.curso17.ejercicio026.modelo.Circulo;
import es.cic.curso.curso17.ejercicio026.modelo.Figura;
import es.cic.curso.curso17.ejercicio026.modelo.Linea;
import es.cic.curso.curso17.ejercicio026.modelo.Rectangulo;

@Service
public class FiguraDTOTraductor {
	
	private FiguraDTO figura2DTO(Figura figura) {
		FiguraDTO resultado = new FiguraDTO();
		resultado.setId(figura.getId());
		resultado.setLienzo(figura.getLienzo());
		resultado.setX(figura.getX());
		resultado.setY(figura.getY());
		resultado.setRotacion(figura.getRotacion());
		resultado.setColor(figura.getColor());
		resultado.setTipo(FiguraDTO.INDEFINIDO);
		return resultado;
	}
	
	public FiguraDTO linea2DTO(Linea linea) {
		FiguraDTO resultado = figura2DTO(linea.getFigura());
		resultado.setTipo(FiguraDTO.LINEA);
		return resultado;
	}
	
	public FiguraDTO circulo2DTO(Circulo circulo) {
		FiguraDTO resultado = figura2DTO(circulo.getFigura());
		resultado.setTipo(FiguraDTO.CIRCULO);
		return resultado;
	}
	
	public FiguraDTO rectangulo2DTO(Rectangulo rectangulo) {
		FiguraDTO resultado = figura2DTO(rectangulo.getFigura());
		resultado.setTipo(FiguraDTO.RECTANGULO);
		return resultado;
	}
	
	public Figura DTO2Figura(FiguraDTO figuraDTO) {
		Figura resultado = new Figura();
		resultado.setId(figuraDTO.getId());
		resultado.setLienzo(figuraDTO.getLienzo());
		resultado.setX(figuraDTO.getX());
		resultado.setY(figuraDTO.getY());
		resultado.setRotacion(figuraDTO.getRotacion());
		resultado.setColor(figuraDTO.getColor());
		return resultado;		
	}
	
	public List<FiguraDTO> figuras2DTO(List<Figura> figuras) {
		List<FiguraDTO> resultado = new ArrayList<>();
		for (Figura figura : figuras) {
			resultado.add(figura2DTO(figura));
		}
		return resultado;
	}
	
	public List<FiguraDTO> lineas2DTO(List<Linea> lineas) {
		List<FiguraDTO> resultado = new ArrayList<>();
		for (Linea linea : lineas) {
			resultado.add(linea2DTO(linea));
		}
		return resultado;
	}
	
	public List<FiguraDTO> circulos2DTO(List<Circulo> circulos) {
		List<FiguraDTO> resultado = new ArrayList<>();
		for (Circulo circulo : circulos) {
			resultado.add(circulo2DTO(circulo));
		}
		return resultado;
	}
	
	public List<FiguraDTO> rectangulos2DTO(List<Rectangulo> rectangulos) {
		List<FiguraDTO> resultado = new ArrayList<>();
		for (Rectangulo rectangulo : rectangulos) {
			resultado.add(rectangulo2DTO(rectangulo));
		}
		return resultado;
	}

}
