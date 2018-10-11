package form;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Idiomas extends Properties {

	private static final long serialVersionUID = 8179966328830883496L;


	/* contructor para las propiedades de los lenguajes*/
	public Idiomas(String idioma){

		if(!idioma.equals("es")){	// español
        	idioma = "en";			// ingles
        }

        getProperties("/resource/language/"+ idioma +".properties");
    }


    /* lectura de las propiedades */
    private void getProperties(String idioma) {
       try {
           this.load( getClass().getResourceAsStream(idioma) );
       } catch (IOException ex) {
    	   Logger.getLogger( Idiomas.class.getName() ).log(Level.SEVERE, "Languages Properties Error Loading", ex);
       }
  }


}
