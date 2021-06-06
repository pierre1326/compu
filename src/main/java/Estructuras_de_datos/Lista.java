package Estructuras_de_datos;

public class Lista<T> {

	private Nodo<T> inicio;
	private int largo;
	
	public Lista() {}
	
	public int getLargo() {
		return largo;
	}
	
	public T obtenerIndice(int indice) {
		Nodo<T> actual = inicio;
		int indiceActual = 0;
		while(indiceActual != indice) {
			indiceActual++;
			actual = actual.getSiguiente();
		}
		return actual.getValor();
	}
	
	public void insertar(T valor) {
		if(inicio == null) {
			inicio = new Nodo<T>(valor);
		}
		else {
			Nodo<T> actual = this.inicio;
			while(actual.getSiguiente() != null) {
				actual = actual.getSiguiente();
			}
			Nodo<T> nuevoNodo = new Nodo<T>(valor);
			actual.setSiguiente(nuevoNodo);
		}
		largo++;
	}
	
	public void eliminar(int indice) {
		int indiceActual = 0;
		Nodo<T> actual = this.inicio;
		if(indice == 0) {
			if(largo == 1) {
				inicio = null;
			}
			else {
				inicio = inicio.getSiguiente();
			}
		}
		else {
			while(indiceActual != (indice -1) ) {
				indiceActual++;
				actual = actual.getSiguiente();
			}
			Nodo<T> siguiente = actual.getSiguiente();
			actual.setSiguiente(siguiente.getSiguiente());
			siguiente.setSiguiente(null);
		}
		largo--;
	}
	
}
