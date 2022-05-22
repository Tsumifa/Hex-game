package src.debug;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Debug {

    private Boolean consoleDebug;
    private Boolean fileDebug;
    private String filePath;
    private String prefix = "\n";

    public Debug( Boolean consoleDebug, Boolean fileDebug, String filePath ) {

        this.consoleDebug = consoleDebug;
        this.fileDebug = fileDebug;
        this.filePath = filePath;

        // Affiche une séparation entre la partie précédente et la partie actuelle
        log( "\n", "NEW GAME" );
        this.prefix = "";

    }

    private String getDateTime() {
    
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "yyyy/MM/dd HH:mm:ss" );  
        LocalDateTime now = LocalDateTime.now();  
        return "" + dtf.format( now );
    
    }

    public void log ( String data, String type ) {

        consoleLog( data, type );
        fileLog( data, type );
    
    }


    // Cette fonction permet les informations de debugage
    // Elle est active que si le mode consoleDebug est activé 
    public void consoleLog( String data, String type ) {

        if ( this.consoleDebug ) {
        
            String log = "[" + type + "] [" + getDateTime() + "] - " + data;
            System.out.println( log );
        
        }
    }

    // Cette fonction écrit les informations dans le fichier de log
    // Elle s'active si le mode fileDebug est activé 
    private void fileLog( String data, String type ) {

        if ( this.fileDebug ) {

            try {
        
                File file = new File( this.filePath );
                FileWriter fw = new FileWriter( file.getAbsoluteFile(), true );
                BufferedWriter bw = new BufferedWriter( fw );

                String content = prefix + "[" + type + "] [";
                content += getDateTime() + "] - ";
                content += data + "\n";

                bw.write( content );
                bw.close();

            } catch ( Exception e ) {
        
                consoleLog( "Impossible de d'enregistrer le log", "ERROR" );
            }
        }
    }
}
