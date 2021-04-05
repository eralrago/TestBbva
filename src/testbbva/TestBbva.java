package testbbva;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Ernesto Ramirez eralrago@gmail.com
 */
public class TestBbva {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        obtieneJson();
    }

    public static JsonObject obtieneJson() {
        JsonObject json = new JsonObject();
        try {
            //creamos una URL donde esta nuestro webservice
            URL url = new URL("https://api.datos.gob.mx/v2/Records");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //indicamos por que verbo HTML ejecutaremos la solicitud
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                //si la respuesta del servidor es distinta al codigo 200 lanzaremos una Exception
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            //creamos un StringBuilder para almacenar la respuesta del web service
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = br.read()) != -1) {
                sb.append((char) cp);
            }
            //en la cadena output almacenamos toda la respuesta del servidor
            String output = sb.toString();
            //convertimos la cadena a JSON a traves de la libreria GSON
            json = new Gson().fromJson(output, JsonObject.class
            );
            //imprimimos como Json
            System.out.println("salida como JSON" + json);
            //imprimimos como String
            System.out.println("salida como String : " + output);

            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return json;
    }
}
