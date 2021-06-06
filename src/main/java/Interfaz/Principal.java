package Interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONException;

import Estructuras_de_datos.Lista;
import Secciones.Examen;
import Secciones.Seccion;
import Sistema_Examen.Preguntas.Pregunta;
import Tipo_preguntas.Cargador;
import Tipo_preguntas.PreguntaInvalida;

public class Principal {
	
	private Pregunta preguntaActual;
	private Seccion seccionActual;
	private int indiceSeccion;
	private int indicePregunta;
	
	private JFrame frame;
	private JPanel panel;
	private JPanel panelCreacion;
	
	private JButton buttonCrear;
	private JButton buttonRealizar;
	private JButton buttonCargar;
	
	private JLabel labelExamenes;
	private JComboBox<Examen> listaExamenes;
	
	private JLabel labelExamen;
	private JLabel labelSecciones;
	private JLabel labelPreguntasTotales;
	private JLabel tipoPregunta;
	private JComboBox<String> tipoPreguntas;
	private JComboBox<Seccion> secciones;
	private JComboBox<String> preguntas;
	private JButton buttonEditarPregunta;
	private JButton buttonEliminarPregunta;
	private JButton buttonCrearSeccion;
	private JButton buttonEliminarSeccion;
	private JButton buttonAgregarPregunta;
	private JButton buttonGuardarExamen;
	
	public Principal() {
		frame = new JFrame("Sistema de examenes");
		panel = new JPanel();
		panelCreacion = new JPanel();
		panel.setLayout(null);
		panelCreacion.setLayout(null);
		buttonCrear = new JButton("Crear examen");
		buttonRealizar = new JButton("Realizar examen");
		buttonCargar = new JButton("Cargar Examen");
		labelExamenes = new JLabel("Examenes creados:");
		listaExamenes = new JComboBox<>();
		labelSecciones = new JLabel("Secciones:");
		labelExamen = new JLabel("Nombre: ");
		labelPreguntasTotales = new JLabel("Preguntas:");
		tipoPregunta = new JLabel("Tipo de seccion");
		tipoPreguntas = new JComboBox<>();
		secciones = new JComboBox<>();
		buttonCrearSeccion = new JButton("Agregar seccion");
		buttonAgregarPregunta = new JButton("Agregar pregunta");
		buttonGuardarExamen = new JButton("Guardar examen");
		buttonEliminarSeccion = new JButton("Eliminar seccion");
		buttonEditarPregunta = new JButton("Editar pregunta");
		buttonEliminarPregunta = new JButton("Eliminar pregunta");
		preguntas = new JComboBox<>();
	}
	
	public void iniciar() {
		frame.setSize(640, 480);
		frame.setResizable(false);
		centrarFrame(frame);
		labelExamenes.setBounds(10, 10, 200, 10);
		listaExamenes.setBounds(10, 30, 200, 30);
		buttonCargar.setBounds(10, 70, 200, 30);
		buttonCrear.setBounds(300, 30, 200, 30);
		buttonRealizar.setBounds(300, 70, 200, 30);
		labelSecciones.setBounds(10, 10, 200, 10);
		labelExamen.setBounds(10, 270, 200, 30);
		secciones.setBounds(10, 30, 200, 30);
		buttonEliminarSeccion.setBounds(10, 70, 200, 30);
		labelPreguntasTotales.setBounds(300, 10, 200, 10);
		buttonAgregarPregunta.setBounds(300, 70, 200, 30);
		buttonEditarPregunta.setBounds(300, 110, 200, 30);
		buttonEliminarPregunta.setBounds(300, 150, 200, 30);
		tipoPregunta.setBounds(10, 110, 200, 30);
		tipoPreguntas.setBounds(10, 140, 200, 30);
		buttonCrearSeccion.setBounds(10, 180, 200, 30);
		buttonGuardarExamen.setBounds(390, 260, 200, 30);
		preguntas.setBounds(300, 30, 200, 30);
		panel.add(labelExamenes);
		panel.add(listaExamenes);
		panel.add(buttonCargar);
		panel.add(buttonCrear);
		panel.add(buttonRealizar);
		panel.setBounds(0, 0, 640, 120);
		panelCreacion.setBounds(10, 130, 600, 300);
		panelCreacion.setBorder(BorderFactory.createLineBorder(Color.black));
		panelCreacion.add(labelSecciones);
		panelCreacion.add(labelPreguntasTotales);
		panelCreacion.add(secciones);
		panelCreacion.add(buttonCrearSeccion);
		panelCreacion.add(buttonAgregarPregunta);
		panelCreacion.add(buttonGuardarExamen);
		panelCreacion.add(buttonEliminarSeccion);
		panelCreacion.add(tipoPregunta);
		panelCreacion.add(tipoPreguntas);
		panelCreacion.add(labelExamen);
		panelCreacion.add(buttonEditarPregunta);
		panelCreacion.add(preguntas);
		panelCreacion.add(buttonEliminarPregunta);
		panelCreacion.setVisible(false);
		actionButtons();
		listenerComboBox();
		frame.setLayout(null);
		frame.add(panel);
		frame.add(panelCreacion);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void actionButtons() {
	 buttonCrear.addActionListener(new ActionListener() { 
     public void actionPerformed(ActionEvent e) {
       String name = preguntarNombre("Ingrese el nombre del examen");
       if(!name.equals("")) {
      	 Examen examen = new Examen(name);
         examen.agregarSeccion(new Seccion("Seleccion Unica", "SeleccionUnica"));
         examen.agregarSeccion(new Seccion("Doble Alternativa", "DobleAlternativa"));
         listaExamenes.addItem(examen);
         listaExamenes.setSelectedItem(examen);
         labelExamen.setText("Nombre: " + name);
         cargarSecciones();
         panelCreacion.setVisible(true);
       }
     }
   }); 
	 buttonCrearSeccion.addActionListener(new ActionListener() { 
     public void actionPerformed(ActionEvent e) {
       String name = preguntarNombre("Ingrese el nombre de la seccion");
       String tipoPregunta = (String)tipoPreguntas.getSelectedItem();
       Examen examen = (Examen)listaExamenes.getSelectedItem();
       examen.agregarSeccion(new Seccion(name, tipoPregunta));
       cargarSecciones();
     }
   });
	 buttonEliminarSeccion.addActionListener(new ActionListener() { 
     public void actionPerformed(ActionEvent e) {
    	 Seccion seccion = (Seccion)secciones.getSelectedItem();
    	 if(!seccion.getNombre().equals("Seleccion Unica") && !seccion.getNombre().equals("Doble Alternativa")) {
    		 Examen examen = (Examen)listaExamenes.getSelectedItem();
         examen.eliminarSeccion(seccion.getNombre());
         cargarSecciones();
    	 }
     }
   });
	 buttonGuardarExamen.addActionListener(new ActionListener() { 
     public void actionPerformed(ActionEvent e) {
    	 Examen examen = (Examen)listaExamenes.getSelectedItem();
    	 String ruta = cargarRuta();
    	 if(!ruta.equals("")) {
    		 examen.guardarExamen(ruta);
      	 darAviso("El examen se guardo con exito", "Guardar examen");
    	 }
     }
   });
	 buttonCargar.addActionListener(new ActionListener() { 
     public void actionPerformed(ActionEvent e) {
    	 String archivo = cargarArchivo();
    	 if(archivo != null && !archivo.equals("")) {
    		 try {
     			Examen examen = Examen.recuperarExamen(archivo);
     			labelExamen.setText("Nombre: " + examen.getNombre());
     			listaExamenes.addItem(examen);
     			listaExamenes.setSelectedItem(examen);
     			cargarSecciones();
     			panelCreacion.setVisible(true);
       	 } 
       	 catch (ClassNotFoundException e1) {
       		 darAviso("No es posible cargar el examen", "Cargar examen");
       	 } 
       	 catch (JSONException e1) {
       		 darAviso("No es posible cargar el examen", "Cargar examen");
       	 }
    	 }
     }
   });
	 buttonRealizar.addActionListener(new ActionListener() { 
     public void actionPerformed(ActionEvent e) {
    	 if(buttonRealizar.getText().equals("Realizar examen")) {
    		 if(listaExamenes.getSelectedIndex() != -1) {
      		 iniciarExamen();
      		 iniciarValores();
      		 indicarProximaPregunta();
      	 }
    	 }
    	 else if(buttonRealizar.getText().equals("Siguiente Pregunta")) {
    		 indicarProximaPregunta();
    	 }
    	 else {
    		 finalizarExamen();
    	 }
     }
   }); 
	 buttonAgregarPregunta.addActionListener(new ActionListener() { 
     public void actionPerformed(ActionEvent e) {
    	 Seccion seccion = (Seccion)secciones.getSelectedItem();
    	 String tipoPregunta = seccion.getTipoPregunta();
    	 try {
    		 Pregunta pregunta = Cargador.obtenerInstancia().obtenerPregunta(tipoPregunta);
    		 seccion.agregarPregunta(pregunta);
    		 preguntas.addItem("Pregunta: " + seccion.getPreguntas().getLargo());
    		 preguntas.setSelectedIndex(preguntas.getItemCount() - 1);
    		 pregunta.insertarInfo();
    	 } 
    	 catch(PreguntaInvalida e1) {
    		 darAviso("No es posible generar el tipo de pregunta", "Agregar pregunta");
    	 }
     }
   });
	 buttonEditarPregunta.addActionListener(new ActionListener() { 
     public void actionPerformed(ActionEvent e) {
    	 Seccion seccion = (Seccion)secciones.getSelectedItem();
    	 int index = preguntas.getSelectedIndex();
    	 Pregunta pregunta = seccion.getPreguntas().obtenerIndice(index);
    	 pregunta.insertarInfo();
     }
   });
	 buttonEliminarPregunta.addActionListener(new ActionListener() { 
     public void actionPerformed(ActionEvent e) {
    	 Seccion seccion = (Seccion)secciones.getSelectedItem();
    	 int index = preguntas.getSelectedIndex();
    	 if(index != -1) {
    		 seccion.eliminarPregunta(index);
    		 cargarPreguntas();
    	 }
     }
   });
	}
	
	private void listenerComboBox() {
		try {
			String[] tipos = Cargador.obtenerInstancia().obtenerTipoPreguntas();
			tipoPreguntas.setModel(new DefaultComboBoxModel<String>(tipos));
		} catch (PreguntaInvalida e1) {
			e1.printStackTrace();
		}
		listaExamenes.addActionListener (new ActionListener () {
	    public void actionPerformed(ActionEvent e) {
	    	cargarSecciones();
	    }
		});
		secciones.addActionListener (new ActionListener () {
	    public void actionPerformed(ActionEvent e) {
	    	if(secciones.getSelectedIndex() != -1) {
	    		cargarPreguntas();
	    	}
	    }
		});
	}
	
	private void cargarPreguntas() {
		preguntas.removeAllItems();
		Seccion seccion = (Seccion)secciones.getSelectedItem();
		Lista<Pregunta> preguntas = seccion.getPreguntas();
		for(int i = 0; i < preguntas.getLargo(); i++) {
			this.preguntas.addItem("Pregunta: " + (i + 1));
		}
		this.preguntas.setSelectedIndex(this.preguntas.getItemCount() - 1);
	}
	
	private void iniciarExamen() {
  	Examen examen = (Examen)listaExamenes.getSelectedItem();
  	if(revisarPreguntasExamen(examen)) {
  		buttonRealizar.setText("Siguiente Pregunta");
    	listaExamenes.setEnabled(false);
    	buttonCrear.setEnabled(false);
    	buttonCargar.setEnabled(false);
    	panelCreacion.setVisible(false);
  	}
  	else {
  		darAviso("El examen no posee preguntas", "Realizar examen");
  	}
	}
	
	private void iniciarValores() {
		Examen examen = (Examen)listaExamenes.getSelectedItem();	
		Lista<Seccion> secciones = examen.getSecciones();
		for(int i = 0; i < secciones.getLargo(); i++) {
			Seccion seccion = secciones.obtenerIndice(i);
			if(seccion.getPreguntas().getLargo() > 0) {
				Lista<Pregunta> preguntas = seccion.getPreguntas();
				this.indicePregunta = 0;
				this.preguntaActual = preguntas.obtenerIndice(0);
				this.seccionActual = seccion;
				break;
			}
		}
	}
	
	private void indicarProximaPregunta() {
		this.preguntaActual.evaluarPregunta();
		Examen examen = (Examen)listaExamenes.getSelectedItem();
		this.indicePregunta++;
		if(this.indicePregunta >= this.seccionActual.getPreguntas().getLargo()) {
			this.indiceSeccion++;
			if(this.indiceSeccion >= examen.getSecciones().getLargo()) {
				buttonRealizar.setText("Obtener puntaje");
			}
			else {
				for(int i = this.indiceSeccion; i < examen.getSecciones().getLargo(); i++) {
					Seccion seccion = examen.getSecciones().obtenerIndice(i);
					if(seccion.getPreguntas().getLargo() > 0) {
						this.seccionActual = seccion;
						this.preguntaActual = seccion.getPreguntas().obtenerIndice(0);
						this.indicePregunta = 0;
						return;
					}
				}
				buttonRealizar.setText("Obtener puntaje");
			}
		}
		else {
			this.preguntaActual = this.seccionActual.getPreguntas().obtenerIndice(indicePregunta);
		}
	}
	
	private boolean revisarPreguntasExamen(Examen examen) {
		Lista<Seccion> secciones = examen.getSecciones();
		for(int i = 0; i < secciones.getLargo(); i++) {
			Seccion seccion = secciones.obtenerIndice(i);
			Lista<Pregunta> preguntas = seccion.getPreguntas();
			if(preguntas.getLargo() > 0) {
				return true;
			}
		}
		return false;
	}
	
	private void finalizarExamen() {
		Examen examen = (Examen)listaExamenes.getSelectedItem();	
		buttonRealizar.setText("Realizar examen");
  	listaExamenes.setEnabled(true);
  	buttonCrear.setEnabled(true);
  	buttonCargar.setEnabled(true);
  	panelCreacion.setVisible(true);
  	darAviso("Su nota final es: " + calcularNota(examen), "Examen: " + examen.getNombre());
	}
	
	private double calcularNota(Examen examen) {
		double totalPreguntas = 0;
		double puntajeTotal = 0;
		Lista<Seccion> secciones= examen.getSecciones();
		for(int i = 0; i < secciones.getLargo(); i ++) {
			Seccion seccion = secciones.obtenerIndice(i);
			Lista<Pregunta> preguntas = seccion.getPreguntas();
			for(int j = 0; j < preguntas.getLargo(); j++) {
				Pregunta pregunta = preguntas.obtenerIndice(j);
				totalPreguntas++;
				puntajeTotal += pregunta.getPuntaje();
				pregunta.eliminarInfo();
			}
		}
		return puntajeTotal / totalPreguntas;
	}
	
	private void darAviso(String mensaje, String titulo) {
		JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void cargarSecciones() {
		Examen examen = (Examen)listaExamenes.getSelectedItem();
		Lista<Seccion> secciones = examen.getSecciones();
		this.secciones.removeAllItems();
		for(int i = 0; i < secciones.getLargo(); i++) {
			this.secciones.addItem(secciones.obtenerIndice(i));
		}
		this.secciones.setSelectedIndex(this.secciones.getItemCount() -1);
		cargarPreguntas();
	}
	
	private void centrarFrame(JFrame frame) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	}
	
	private String preguntarNombre(String mensaje) {
		String name = JOptionPane.showInputDialog(frame, mensaje, null);
	 	return name;
	}
	
	private String cargarArchivo() {
		JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      return selectedFile.getAbsolutePath();
    }
    return "";
	}
	
	private String cargarRuta() {
		JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int option = fileChooser.showOpenDialog(frame);
    if(option == JFileChooser.APPROVE_OPTION) {
       File file = fileChooser.getSelectedFile();
       return file.getAbsolutePath();
    }
    return "";
	}
	
	
}
