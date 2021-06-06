package Tipo_preguntas;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import Sistema_Examen.Preguntas.Pregunta;

public class Cargador {

	private final String PAQUETE = "Sistema_Examen.Preguntas";
	
	private static Cargador cargador;
	private List<Class<? extends Pregunta>> preguntas;
	
	private Cargador() throws PreguntaInvalida {
		this.preguntas = cargarPreguntas(PAQUETE);
	}
	
	public String[] obtenerTipoPreguntas() {
		String[] preguntas = new String[this.preguntas.size()];
		for(int i = 0; i < this.preguntas.size(); i++) {
			preguntas[i] = this.preguntas.get(i).getSimpleName();
		}
		return preguntas;
	}
	
	public Pregunta obtenerPregunta(String tipo) throws PreguntaInvalida {
		for(Class<? extends Pregunta> clase: this.preguntas) {
			if(clase.getSimpleName().equals(tipo)) {
				try {
					Constructor<? extends Pregunta> ctor = clase.getConstructor();
					Pregunta pregunta = ctor.newInstance();
					return pregunta;
				}
				catch(Exception e) {
					throw new PreguntaInvalida("La clase no implementa un constructor vacio para iniciar", e.fillInStackTrace());
				}		
			}
		}
		return null;
	}
	
	public static Cargador obtenerInstancia() throws PreguntaInvalida {
		if(cargador == null) {
			cargador = new Cargador();
		}
		return cargador;
	}

	
	private List<Class<? extends Pregunta>> cargarPreguntas(String packageName) throws PreguntaInvalida {
		String path = packageName.replaceAll("\\.", "\\/");
    List<Class<? extends Pregunta>> classes = new ArrayList<>();
    String[] classPathEntries = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
    String jarPath = checkJar();
    String name;
    if(!jarPath.equals("")) {
    	File jar = new File(jarPath);
    	try {
        JarInputStream is = new JarInputStream(new FileInputStream(jar));
        JarEntry entry;
        while((entry = is.getNextJarEntry()) != null) {
          name = entry.getName();
          if(name.endsWith(".class")) {
            if(name.contains(path) && name.endsWith(".class")) {
              String classPath = name.substring(0, entry.getName().length() - 6);
              classPath = classPath.replaceAll("[\\|/]", ".");
              Class<?> newClass = Class.forName(classPath);        
              if(!newClass.getName().contains("$")) {
              	if(Pregunta.class.isAssignableFrom(newClass)) {
                	if(!newClass.getSimpleName().equals("Pregunta")) {
                		classes.add((Class<? extends Pregunta>) newClass);
                	}  
                }
                else {
                	throw new PreguntaInvalida("La clase no implementa la interfaz requerida");
                }
              }   
            }
          }
        }
        is.close();
      } 
      catch (Exception ex) {
      	throw new PreguntaInvalida("No es posible cargar la informacion", ex.fillInStackTrace());
      }
    }
    else {
    	for(String classpathEntry : classPathEntries) {
      	if(!classpathEntry.endsWith(".jar")) {
      		try {
            File base = new File(classpathEntry + File.separatorChar + path);
            for(File file : base.listFiles()) {
            	name = file.getName();
            	if(name.endsWith(".class")) {
                name = name.substring(0, name.length() - 6);
                Class<?> newClass = Class.forName(packageName + "." + name);
                if(!newClass.getName().contains("$")) {
                	if(Pregunta.class.isAssignableFrom(newClass)) {
                  	if(!newClass.getSimpleName().equals("Pregunta")) {
                  		classes.add((Class<? extends Pregunta>) newClass);
                  	}  
                  }
                  else {
                  	throw new PreguntaInvalida("La clase no implementa la interfaz requerida");
                  }
                }   
              }
            }
          } 
          catch (Exception ex) {
          	throw new PreguntaInvalida("No es posible cargar la informacion", ex.fillInStackTrace());
          }
        } 
      }
    }
    return classes;
	}
	
	private String checkJar() {
		String jarPath = System.getProperty("user.dir");
		File folder = new File(jarPath);
		for (File fileEntry : folder.listFiles()) {
			if(fileEntry.getName().contains(".jar")) {
				return jarPath + "/" + fileEntry.getName();
			}
		}
		return "";
	}
	
}
