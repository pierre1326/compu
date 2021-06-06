package Tipo_preguntas;

public class PreguntaInvalida extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public PreguntaInvalida(String error) {
		super(error);
	}
	
	public PreguntaInvalida(String error, Throwable thr) {
		super(error, thr);
	}

}
