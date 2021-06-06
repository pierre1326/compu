package Sistema_Examen.Preguntas;

public interface Pregunta {

		public void insertarInfo(); // GUI Para llenar datos
		public void eliminarInfo(); // GUI para eliminar respuesta
		public void evaluarPregunta(); //GUI para evaluar la pregunta
		public void desplegarPregunta(); //GUI para ver la respuesta
		public double getPuntaje(); //Cantidad de puntos luego de ser respondida
	
}
