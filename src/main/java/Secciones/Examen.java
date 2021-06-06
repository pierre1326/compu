package Secciones;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Estructuras_de_datos.Lista;

public class Examen {
	
	private String nombre;
	private Lista<Seccion> secciones;

	public static Examen recuperarExamen(String archivo) throws ClassNotFoundException, JSONException {
		String ruta = archivo;
		String texto = leerArchivo(ruta);
		JSONObject json = new JSONObject(texto);
		Examen examen = new Examen(json.getString("nombre"));
		JSONArray secciones = json.getJSONArray("secciones");
		for(int i = 0; i < secciones.length(); i++) {
			examen.agregarSeccion(Seccion.obtenerSeccion(secciones.getString(i)));
		}
		return examen;
	}
	
	private static String leerArchivo(String ruta) {
		String content = "";
    try {
      content = new String(Files.readAllBytes(Paths.get(ruta)));
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
    return content;
	}
	
	public Examen(String nombre) {
		this.nombre = nombre;
		this.secciones = new Lista<Seccion>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Lista<Seccion> getSecciones() {
		return secciones;
	}

	public void setSecciones(Lista<Seccion> secciones) {
		this.secciones = secciones;
	}
	
	public void agregarSeccion(Seccion seccion) {
		this.secciones.insertar(seccion);
	}
	
	public void eliminarSeccion(String nombre) {
		int i;
		for(i = 0; i < secciones.getLargo(); i++) {
			Seccion seccion = secciones.obtenerIndice(i);
			if(seccion.getNombre().equals(nombre)) {
				break;
			}
		}
		this.secciones.eliminar(i);
	}
	
	public void guardarExamen(String ruta) {
		String examen = convertirExamen();
		String archivo = this.nombre + ".json";
		try {
      FileWriter myWriter = new FileWriter(ruta + "/" + archivo);
      myWriter.write(examen);
      myWriter.close();
    } 
		catch (IOException e) {
      e.printStackTrace();
    }
	}
	
	private String convertirExamen() {
		JSONObject json = new JSONObject();
		json.put("nombre", this.nombre);
		ArrayList<String> secciones = new ArrayList<>();
		for(int i = 0; i < this.secciones.getLargo(); i++) {
			secciones.add(this.secciones.obtenerIndice(i).convertirSeccion());
		}
		json.put("secciones", secciones);
		return json.toString();
	}
	
	public String toString() {
		return nombre;
	}
	
}
