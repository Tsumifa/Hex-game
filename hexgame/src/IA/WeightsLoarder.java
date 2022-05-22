package src.IA;

import java.io.BufferedReader;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class WeightsLoarder {
    
    public JSONObject mainJsonObject;

    public WeightsLoarder( String path ) {

        loadDataToJson(
            loadDataFromFile( path )
        );

    }

    private void loadDataToJson( String data ) {

        try {

            JSONParser parser = new JSONParser();
            Object object = parser.parse( data );
            this.mainJsonObject = ( JSONObject ) object;


        } catch ( Exception ex ) {
            
            ex.printStackTrace();
        
        }
    
    } 

    private static String loadDataFromFile( String configPath ) {

        // Cette fonction récupère les données enregistrées dans le fichier de configuration
        String data = "";

        try {

            BufferedReader bufferedReader = new BufferedReader(
                new FileReader( configPath )
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

    public double getWeight( String weight ) {

        String value = (String) this.mainJsonObject.get( weight );
        
        try {
     
            return Double.parseDouble( value );
     
        } catch ( Exception e ) {
     
            return 0.0;
     
        }
    
    }
}
