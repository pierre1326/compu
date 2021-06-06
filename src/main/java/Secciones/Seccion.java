package Secciones;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import Estructuras_de_datos.Lista;
import Sistema_Examen.Preguntas.Pregunta;

public class Seccion {

	private Lista<Pregunta> preguntas;
	private String nombre;
	private String tipoPregunta;
	
	public static Seccion obtenerSeccion(String json) throws ClassNotFoundException, JSONException {
		JSONObject seccion = new JSONObject(json);
		Seccion resultado = new Seccion(seccion.getString("nombre"), seccion.getString("tipoPregunta"));
		JSONArray preguntas = seccion.getJSONArray("preguntas");
		for(int i = 0; i < preguntas.length(); i++) {
			resultado.agregarPregunta(recuperarPregunta(preguntas.getString(i)));
		}
		return resultado;
	}
	
	private static Pregunta recuperarPregunta(String json) throws ClassNotFoundException, JSONException {
		Gson gson = new Gson();
  	JSONObject resultado = new JSONObject(json);
  	Class<?> newClass = Class.forName(resultado.getString("clase"));
  	Pregunta recuperado = (Pregunta) gson.fromJson(resultado.getString("pregunta"), newClass);
  	return recuperado;
	}
	
	public Seccion(String nombre, String tipoPregunta) {
		this.nombre = nombre;
		this.tipoPregunta = tipoPregunta;
		this.preguntas = new Lista<Pregunta>();
	}

	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public Lista<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(Lista<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void agregarPregunta(Pregunta pregunta) {
		this.preguntas.insertar(pregunta);
	}
	
	public void eliminarPregunta(int indice) {
		this.preguntas.eliminar(indice);
	}
	
	public String convertirSeccion() {
		JSONObject json = new JSONObject();
		json.put("nombre", this.nombre);
		json.put("tipoPregunta", this.tipoPregunta);
		ArrayList<String> preguntas = new ArrayList<>();
		for(int i = 0; i < this.preguntas.getLargo(); i++) {
			preguntas.add(convertirPregunta(this.preguntas.obtenerIndice(i)));
		}
		json.put("preguntas", preguntas);
		return json.toString();
	}
	
	private String convertirPregunta(Pregunta pregunta) {
		Gson gson = new Gson();
  	String json = gson.toJson(pregunta);
  	String className = pregunta.getClass().getCanonicalName();
  	JSONObject resultado = new JSONObject();
  	resultado.put("clase", className);
  	resultado.put("pregunta", json);
  	return resultado.toString();
	}
	
	public String toString() {
		return nombre;
	}
	
}
