package cat.iesesteveterradas.exemples;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoQueryExample {
    public static void main(String[] args) {
        // Connectar-se a MongoDB (substitueix amb la teva URI de connexió)
        try (var mongoClient = MongoClients.create("mongodb://root:example@localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("yourDatabaseName");
            MongoCollection<Document> collection = database.getCollection("yourCollectionName");

            // Utilitzar una expressió regular per buscar 'with' en el títol
            Bson regexQuery = new Document("title", new Document("$regex", ".*with.*").append("$options", "i"));

            // Realitzar la consulta
            FindIterable<Document> result = collection.find(regexQuery);

            // Iterar i mostrar els resultats
            for (Document doc : result) {
                System.out.println(doc.toJson());
            }

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}