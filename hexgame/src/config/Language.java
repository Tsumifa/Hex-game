package src.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import src.debug.Debug;

public class Language {
    
    public JSONObject mainJsonObject;
    private Debug debugManager;

    public Language(String languageFilePath) {

        loadDataToJson(
            loadDataFromFile(languageFilePath)
        );
    
    }

    // Associe le debug manager à la class
    public void setDebug( Debug debugManager ) {

        this.debugManager = debugManager;
    
    }

    // Prépare la librairie JSON
    private void loadDataToJson(String data) {

        try {

            JSONParser parser = new JSONParser();
            Object object = parser.parse(data);
            this.mainJsonObject = (JSONObject) object;

        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    // Charge les données stockées dans le fichier ( filepath ) en mémoire
    private String loadDataFromFile( String filePath ) {

        String data = "";
        
        try {

            BufferedReader bufferedReader = new BufferedReader(
                new FileReader( filePath, Charset.forName("UTF-8") )
            );
            String line;

            while ( ( line = bufferedReader.readLine() ) != null) {
                data += line + "\n";
            }

            bufferedReader.close();
        
        } catch ( Exception e ) {

            e.printStackTrace();
        }

        return data;
    }


    // Retourne le texte associé à key stocké dans le fichier de langue
    // Retourne null en cas d'échec
    public String getText( String key ) {
        
        try {
            
            return (String) this.mainJsonObject.get( key );
        
        } catch ( Exception e ) {

            this.debugManager.log( "Impossible de retourner le texte de : " + key, "ERROR" );
            this.debugManager.log( "Détails" + e, "ERROR" );
            return null;
        }
    }

}
