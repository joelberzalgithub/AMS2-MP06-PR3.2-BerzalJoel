package cat.iesesteveterradas;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bson.Document;
import org.jsoup.Jsoup;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PR32CreateMain {

    private static final Logger logger = Logger.getLogger(PR32CreateMain.class.getName());

    public static void main(String[] args) {
        try {
            // Creem un directori log en cas que no existeixi
            File logDir = new File("./data/logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Configurem el logger per desar missatges en un arxiu log
            FileHandler fileHandler = new FileHandler("./data/logs/PR32CreateMain.java.log");
            logger.addHandler(fileHandler);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error en configurar el logger", e);
        }
        
        // Establim la connexió a MongoDB
        try (var mongoClient = MongoClients.create("mongodb://root:example@localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("admin"); 
            MongoCollection<Document> collection = database.getCollection("boardgames");

            // Comprovem si MongoDB ja té algún Document
            if (collection.countDocuments() > 0) {
                collection.deleteMany(new Document());
            }

            try {
                // Carreguem l'arxiu XML
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                org.w3c.dom.Document doc = dBuilder.parse(new File("Posts.xml"));
                doc.getDocumentElement().normalize();
                
                // Obtenim tots els elements "row"
                NodeList nodeList = doc.getElementsByTagName("row");

                // Emmagatzemem els posts dins d'una llista
                List<Map<String, String>> posts = new ArrayList<>();
                
                // Iterem a tots els elements "row"
                for (int i=0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        
                        if (element.getAttribute("PostTypeId").equals("1")) {
                            // Obtenim els atributs de cada element "row" i els emmagatzemem dins d'un Map
                            Map<String, String> postMap = new HashMap<>();
                            postMap.put("Id", element.getAttribute("Id"));
                            postMap.put("PostTypeId", element.getAttribute("PostTypeId"));
                            postMap.put("CreationDate", element.getAttribute("CreationDate"));
                            postMap.put("Score", element.getAttribute("Score"));
                            postMap.put("ViewCount", element.getAttribute("ViewCount"));
                            postMap.put("Body", element.getAttribute("Body"));
                            postMap.put("Title", element.getAttribute("Title"));
                            postMap.put("Tags", element.getAttribute("Tags"));
                            postMap.put("AnswerCount", element.getAttribute("AnswerCount"));
                            postMap.put("CommentCount", element.getAttribute("CommentCount"));
                            postMap.put("ContentLicense", element.getAttribute("ContentLicense"));
                            
                            // Afegim el post a la llista de posts
                            posts.add(postMap);
                        }
                    }
                }

                // Ordenem les preguntes descendentment en funció del ViewCount
                Collections.sort(posts, (p1, p2) -> Integer.parseInt(p2.get("ViewCount")) - Integer.parseInt(p1.get("ViewCount")));

                // Inserim les 10000 preguntes amb més ViewCount a MongoDB
                int count = 0;
                for (Map<String, String> post : posts) {
                    if (count >= 10000) break;
                    Document document = new Document("question", new Document()
                            .append("Id", post.get("Id"))
                            .append("PostTypeId", post.get("PostTypeId"))
                            .append("AcceptedAnswerId", post.get("AcceptedAnswerId"))
                            .append("CreationDate", post.get("CreationDate"))
                            .append("Score", Integer.parseInt(post.get("Score")))
                            .append("ViewCount", Integer.parseInt(post.get("ViewCount")))
                            .append("Body", Jsoup.parse(post.get("Body")).body().text())
                            .append("OwnerUserId", post.get("OwnerUserId"))
                            .append("LastActivityDate", post.get("LastActivityDate"))
                            .append("Title", post.get("Title"))
                            .append("Tags", post.get("Tags"))
                            .append("AnswerCount", Integer.parseInt(post.get("AnswerCount")))
                            .append("CommentCount", Integer.parseInt(post.get("CommentCount")))
                            .append("ContentLicense", post.get("ContentLicense")));
                    collection.insertOne(document);
                    count++;
                }

                logger.info("S'han inserit " + count + " documents a MongoDB");

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Tanquem la connexió amb MongoDB
            mongoClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
