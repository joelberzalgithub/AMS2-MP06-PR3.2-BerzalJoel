package cat.iesesteveterradas;

import java.io.IOException;

import org.basex.api.client.ClientSession;
import org.basex.core.*;
import org.basex.core.cmd.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);    

    public static void main(String[] args) throws IOException {
         // Initialize connection details
        String host = "127.0.0.1";
        int port = 1984;
        String username = "admin"; // Default username
        String password = "admin"; // Default password

        // Establish a connection to the BaseX server
        try (ClientSession session = new ClientSession(host, port, username, password)) {
            logger.info("Connected to BaseX server.");

            session.execute(new Open("factbook")); 
            
            // Example query - adjust as needed
            String myQuery = "sum(//country/@population/number())";

            // Execute the query
            String result = session.execute(new XQuery(myQuery));
            // Print the result
            logger.info("Query Result:");
            logger.info(result);

            myQuery = """
                declare function local:gdpPerArea($country as element(country)) as xs:double? {
                    let $gdpTotal := number($country/@gdp_total) * 1000000 (: Convertint de milions a unitats per precisió :)
                    let $totalArea := number($country/@total_area) (: Asumint que l'àrea està en quilòmetres quadrats :)
                    return if ($totalArea > 0) then $gdpTotal div $totalArea else ()
                  };
                
                  for $country in //country
                  let $gdpRatio := local:gdpPerArea($country)
                  order by $gdpRatio descending
                  return 
                    <country name="{$country/@name}" gdp_per_area="{$gdpRatio}"/>                    
            """;

            // Execute the query
            result = session.execute(new XQuery(myQuery));
            // Print the result
            logger.info("Query Result:");
            logger.info(result);

        } catch (BaseXException e) {
            logger.error("Error connecting or executing the query: " + e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }        
    }
}
