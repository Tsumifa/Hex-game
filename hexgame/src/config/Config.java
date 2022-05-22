package src.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import src.debug.Debug;


// Cette class permet de communiquer les informations du fichier de config au programme
public class Config {
    
   public JSONObject mainJsonObject;
   private Debug debugManager;

    public Config( String configFilePath) {

        loadDataToJson(
            loadDataFromFile(configFilePath)
        );

    }

    // Associe la class Debug
    public void setDebug( Debug debugManager ) {

        this.debugManager = debugManager;
        
    }

    // Charge le lecteur des données dans la class
    private void loadDataToJson( String data ) {

        try {

            JSONParser parser = new JSONParser();
            Object object = parser.parse(data);
            this.mainJsonObject = (JSONObject) object;

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    } 
    
    // Cette fonction récupère les données enregistrées dans le fichier de configuration
    private static String loadDataFromFile( String configPath ) {
        String data = "";

        try {

            BufferedReader bufferedReader = new BufferedReader(
                new FileReader( configPath, Charset.forName("UTF-8") )
            );
            String line;

            // Tant qu'il reste des données
            while ( ( line = bufferedReader.readLine() ) != null ) {
                data += line + "\n";
            }

            bufferedReader.close();

        } catch ( Exception e ) {

            e.printStackTrace();
        }
        return data;
    }

    // Recherche dans le fichier "conf/settings.json" la valeur associée à name
    // Retourne la valeur au format int
    // Retourne -1 en cas d'erreur
    public int getIntValue( String name ) {

        String value = (String) this.mainJsonObject.get( name );
        try {
            
            return Integer.parseInt(value);

        } catch ( Exception e ) {

            this.debugManager.log( "Impossible d'accéder à la valeur : " + name + " dans le fichier de config", "ERROR" );
            this.debugManager.log( "Détails : " + e, "ERROR" );
            return -1;
        }
    }

    // Recherche dans le fichier "conf/settings.json" la valeur associée à name
    // Retourne la valeur au format String
    // Retourne null en cas d'erreur
    public String getStringValue( String name ) {

        try {

            return (String) this.mainJsonObject.get (name );

        } catch ( Exception e ) {

            this.debugManager.log( "Impossible d'accéder à la valeur : " + name + " dans le fichier de config", "ERROR" );
            this.debugManager.log( "Détails : " + e, "ERROR" );
            return null;
        }
    }

    // Recherche dans le fichier "conf/settings.json" la valeur associée à name
    // Retourne la valeur au format Boolean
    // Retourne null en cas d'erreur
    public Boolean getBooleanValue( String name ) {

        try {

            return Boolean.parseBoolean( (String) this.mainJsonObject.get( name ) );

        } catch ( Exception e ) {

            this.debugManager.log( "Impossible d'accéder à la valeur : " + name + " dans le fichier de config", "ERROR" );
            this.debugManager.log( "Détails : " + e, "ERROR" );
            return null;
        } 
    }

}
