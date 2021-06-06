package Sistema_Examen.Preguntas;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SeleccionUnica implements Pregunta {
	
	private String respuestaCorrecta;
	private String respuestaSeleccionada;
	private ArrayList<String> opciones;
	private String preguntaGuardada;
	
	//Crear pregunta
	private transient JFrame frame;
	private transient JPanel panel;
	private transient JComboBox<String> comboOpciones;
	private transient JTextArea pregunta;
	private transient JLabel labelPregunta;
	private transient JLabel labelOpciones;
	private transient JButton buttonEliminarOpcion;
	private transient JButton buttonAgregarOpcion;
	private transient JButton buttonFinalizar;
	private transient JLabel mensaje;
	
	//Evaluar pregunta
	private transient JFrame frameEvaluar;
	private transient JPanel panelEvaluar;
	private transient JLabel preguntaCompleta;
	private transient JButton buttonAceptar;
	
	public SeleccionUnica() {}
	
	@Override
	public void insertarInfo() {
		if(frame != null) {
			panel.add(comboOpciones);
			frame.setVisible(true);
			return;
		}
		frame = new JFrame("Pregunta examen");
		panel = new JPanel();
		panel.setLayout(null);
		comboOpciones = new JComboBox<>();
		labelOpciones = new JLabel("Opciones: ");
		pregunta = new JTextArea();
		labelPregunta = new JLabel("Escriba su pregunta: ");
		buttonEliminarOpcion = new JButton("Eliminar opcion");
		buttonAgregarOpcion = new JButton("Agregar opcion");
		buttonFinalizar = new JButton("Finalizar");
		mensaje = new JLabel("La opcion seleccionada en el combobox se considera la opcion correcta");
		labelPregunta.setBounds(10, 10, 200, 30);
		pregunta.setBounds(10, 40, 200, 100);
		labelOpciones.setBounds(300, 10, 200, 30);
		comboOpciones.setBounds(300, 40, 200, 30);
		buttonAgregarOpcion.setBounds(300, 80, 200, 30);
		buttonEliminarOpcion.setBounds(300, 120, 200, 30);
		buttonFinalizar.setBounds(400, 220, 200, 30);
		mensaje.setBounds(10, 170, 600, 200);
		mensaje.setVerticalAlignment(JLabel.TOP);
		actionListenerInsertar();
		panel.add(comboOpciones);
		panel.add(labelOpciones);
		panel.add(buttonEliminarOpcion);
		panel.add(buttonAgregarOpcion);
		panel.add(buttonFinalizar);
		panel.add(mensaje);
		panel.add(labelPregunta);
		panel.add(pregunta);
		centrarFrame(frame);
		if(preguntaGuardada != null) {
			pregunta.setText(preguntaGuardada);
		}
		if(opciones != null) {
			for(String opcion: opciones) {
				comboOpciones.addItem(opcion);
			}
		}
		else {
			opciones = new ArrayList<>();
		}
		frame.setSize(640, 300);
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public String getRespuestaSeleccionada() {
		return respuestaSeleccionada;
	}

	public void setRespuestaSeleccionada(String respuestaSeleccionada) {
		this.respuestaSeleccionada = respuestaSeleccionada;
	}

	public ArrayList<String> getOpciones() {
		return opciones;
	}

	public void setOpciones(ArrayList<String> opciones) {
		this.opciones = opciones;
	}

	private void actionListenerInsertar() {
		this.buttonAgregarOpcion.addActionListener(new ActionListener() { 
	     public void actionPerformed(ActionEvent e) {
	    	 String nombre = preguntarNombre("Insertar la opcion:");
	    	 if(nombre != null && !nombre.equals("")) {
	    		 opciones.add(nombre);
	    		 comboOpciones.removeAllItems();
	    		 for(String opcion: opciones) {
	    			 comboOpciones.addItem(opcion);
	    		 }
	    	 }
	     }
	   });
		this.buttonEliminarOpcion.addActionListener(new ActionListener() { 
	     public void actionPerformed(ActionEvent e) {
	    	 if(comboOpciones.getSelectedIndex() != -1) {
	    		 opciones.remove((String)comboOpciones.getSelectedItem());
	    		 comboOpciones.removeItem((String)comboOpciones.getSelectedItem());
	    	 }
	     }
	   });
		this.buttonFinalizar.addActionListener(new ActionListener() { 
	     public void actionPerformed(ActionEvent e) {
	    	 if(pregunta.getText().equals("")) {
	    		 darAviso("Debe ingresar una pregunta", "Error");
	    		 return;
	    	 }
	    	 else if(comboOpciones.getItemCount() < 2) {
	    		 darAviso("Debe ingresar al menos dos opciones", "Error");
	    	 }
	    	 else {
	    		 respuestaCorrecta = (String)comboOpciones.getSelectedItem();
	    		 preguntaGuardada = pregunta.getText();
	    		 frame.setVisible(false);
	    	 }
	     }
	   });
	}
	
	@Override
	public void eliminarInfo() {
		respuestaSeleccionada = "";
	}

	@Override
	public void evaluarPregunta() {
		this.frameEvaluar = new JFrame("Pregunta examen");
		this.panelEvaluar = new JPanel();
		panelEvaluar.setLayout(null);
		
		preguntaCompleta = new JLabel(preguntaGuardada);
		buttonAceptar = new JButton("Confirmar");
		
		preguntaCompleta.setVerticalAlignment(JLabel.TOP);
		
		comboOpciones = new JComboBox<>();
		comboOpciones.setBounds(300, 40, 200, 30);
		preguntaCompleta.setBounds(10, 10, 200, 300);
		buttonAceptar.setBounds(400, 220, 200, 30);
		
		panelEvaluar.add(comboOpciones);
		panelEvaluar.add(preguntaCompleta);
		panelEvaluar.add(buttonAceptar);
		
		comboOpciones.removeAllItems();
		for(String opcion: opciones) {
			comboOpciones.addItem(opcion);
		}
		
		actionListenerEvaluar();
		centrarFrame(frameEvaluar);
		
		frameEvaluar.add(panelEvaluar);
		frameEvaluar.setSize(640, 300);
		frameEvaluar.setVisible(true);
	}

	private void actionListenerEvaluar() {
		this.buttonAceptar.addActionListener(new ActionListener() { 
	     public void actionPerformed(ActionEvent e) {
	    	 respuestaSeleccionada = (String)comboOpciones.getSelectedItem();
	    	 frameEvaluar.setVisible(false);
	    	 desplegarPregunta();
	     }
	   });
	}
	
	@Override
	public void desplegarPregunta() {
		darAviso("Respuesta seleccionada: " + respuestaSeleccionada + " | Respuesta correcta: " + respuestaCorrecta, "Mostrar respuesta");
	}

	@Override
	public double getPuntaje() {
		if(respuestaSeleccionada.equals(respuestaCorrecta)) {
			return 100;
		}
		return 0;
	}

	private void darAviso(String mensaje, String titulo) {
		JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void centrarFrame(JFrame frame) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	}
	
	private String preguntarNombre(String mensaje) {
		String name = JOptionPane.showInputDialog(frame, mensaje, null);
	 	return name;
	}
	
}
