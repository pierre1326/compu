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

public class DobleAlternativa implements Pregunta {
 
	private String pregunta;
	private ArrayList<String> opciones;
	private String[] opcionesCorrectas = new String[2];
	private String respuestaSeleccionada;

	//Crear pregunta
	private transient JFrame frame;
	private transient JPanel panel;
	private transient JComboBox<String> comboOpciones;
	private transient JComboBox<String> comboOpciones2;
	private transient JTextArea areaPregunta;
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
	
	public DobleAlternativa() {}
	
	@Override
	public void insertarInfo() {
		if(frame != null) {
			frame.setVisible(true);
			return;
		}
		frame = new JFrame("Pregunta examen");
		panel = new JPanel();
		panel.setLayout(null);
		comboOpciones = new JComboBox<>();
		comboOpciones2 = new JComboBox<>();
		labelOpciones = new JLabel("Opciones: ");
		areaPregunta = new JTextArea();
		labelPregunta = new JLabel("Escriba su pregunta: ");
		buttonEliminarOpcion = new JButton("Eliminar opcion");
		buttonAgregarOpcion = new JButton("Agregar opcion");
		buttonFinalizar = new JButton("Finalizar");
		mensaje = new JLabel("La opciones seleccionadas en los combobox se consideran las opciones correctas");
		labelPregunta.setBounds(10, 10, 200, 30);
		areaPregunta.setBounds(10, 40, 200, 100);
		labelOpciones.setBounds(300, 10, 200, 30);
		comboOpciones.setBounds(230, 40, 200, 30);
		comboOpciones2.setBounds(460, 40, 200, 30);
		buttonAgregarOpcion.setBounds(230, 80, 200, 30);
		buttonEliminarOpcion.setBounds(230, 120, 200, 30);
		buttonFinalizar.setBounds(460, 220, 200, 30);
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
		panel.add(areaPregunta);
		panel.add(comboOpciones2);
		centrarFrame(frame);
		if(pregunta != null) {
			areaPregunta.setText(pregunta);
		}
		if(opciones != null) {
			for(String opcion: opciones) {
				comboOpciones.addItem(opcion);
				comboOpciones2.addItem(opcion);
			}
		}
		else {
			opciones = new ArrayList<>();
		}
		frame.setSize(700, 300);
		frame.add(panel);
		frame.setVisible(true);
	}

	private void actionListenerInsertar() {
		this.buttonAgregarOpcion.addActionListener(new ActionListener() { 
	     public void actionPerformed(ActionEvent e) {
	    	 String nombre = preguntarNombre("Insertar la opcion:");
	    	 if(nombre != null && !nombre.equals("")) {
	    		 opciones.add(nombre);
	    		 comboOpciones.removeAllItems();
	    		 comboOpciones2.removeAllItems();
	    		 for(String opcion: opciones) {
	    			 comboOpciones.addItem(opcion);
	    			 comboOpciones2.addItem(opcion);
	    		 }
	    	 }
	     }
	   });
		this.buttonEliminarOpcion.addActionListener(new ActionListener() { 
	     public void actionPerformed(ActionEvent e) {
	    	 if(comboOpciones.getSelectedIndex() != -1) {
	    		 opciones.remove((String)comboOpciones.getSelectedItem());
	    		 comboOpciones2.removeItem((String)comboOpciones.getSelectedItem());
	    		 comboOpciones.removeItem((String)comboOpciones.getSelectedItem());    		 
	    	 }
	     }
	   });
		this.buttonFinalizar.addActionListener(new ActionListener() { 
	     public void actionPerformed(ActionEvent e) {
	    	 if(areaPregunta.getText().equals("")) {
	    		 darAviso("Debe ingresar una pregunta", "Error");
	    		 return;
	    	 }
	    	 else if(comboOpciones.getItemCount() < 3) {
	    		 darAviso("Debe ingresar al menos 3 opciones", "Error");
	    	 }
	    	 else {
	    		 String respuesta1 = (String)comboOpciones.getSelectedItem();
	    		 String respuesta2 = (String)comboOpciones2.getSelectedItem();
	    		 if(respuesta1.contentEquals(respuesta2)) {
	    			 darAviso("Las opciones deben ser diferentes", "Error");
	    		 }
	    		 else {
	    			 opcionesCorrectas[0] = respuesta1;
	    			 opcionesCorrectas[1] = respuesta2;
	    			 pregunta = areaPregunta.getText();
	    			 frame.setVisible(false);
	    		 }
	    	 }
	     }
	   });
	}
	
	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public ArrayList<String> getOpciones() {
		return opciones;
	}

	public void setOpciones(ArrayList<String> opciones) {
		this.opciones = opciones;
	}

	public String[] getOpcionesCorrectas() {
		return opcionesCorrectas;
	}

	public void setOpcionesCorrectas(String[] opcionesCorrectas) {
		this.opcionesCorrectas = opcionesCorrectas;
	}

	public String getRespuestaSeleccionada() {
		return respuestaSeleccionada;
	}

	public void setRespuestaSeleccionada(String respuestaSeleccionada) {
		this.respuestaSeleccionada = respuestaSeleccionada;
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
		
		preguntaCompleta = new JLabel(pregunta);
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
		String texto = "";
		for(String opcion: opcionesCorrectas) {
			texto = texto + " " + opcion;
		}
		String mensaje = "Respuesta seleccionada: " + respuestaSeleccionada + " | Posibles opciones: " + texto;
		darAviso(mensaje, "Respuesta");
	}

	@Override
	public double getPuntaje() {
		for(String opcion: opcionesCorrectas) {
			if(opcion.equals(respuestaSeleccionada)) {
				return 100;
			}
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
