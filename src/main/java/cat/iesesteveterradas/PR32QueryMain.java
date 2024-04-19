package cat.iesesteveterradas;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.bson.conversions.Bson;
import org.bson.Document;

public class PR32QueryMain {

    public static void main(String[] args) {
        // Establim la connexió a MongoDB
        try (var mongoClient = MongoClients.create("mongodb://root:example@localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("admin");
            MongoCollection<Document> collection = database.getCollection("boardgames");
            
            // Obtenim les preguntes amb ViewCount més gran que la mitjana de ViewCounts que tenim a la col·lecció
            double averageViewCount = getAverageViewCount(collection);
            FindIterable<Document> informe1 = getTitlesAboveAverage(collection, averageViewCount);

            // Generem un fitxer PDF amb els títols obtinguts
            generatePDF("informe1.pdf", informe1);

            // Obtenim les preguntes que continguin certes lletres en el títol
            FindIterable<Document> informe2 = getTitlesWithLetters(collection);

            // Generem un fitxer PDF amb els títols obtinguts
            generatePDF("informe2.pdf", informe2);

            // Tanquem la connexió amb MongoDB
            mongoClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mètode per calcular la mitjana de ViewCounts en la col·lecció
    private static double getAverageViewCount(MongoCollection<Document> collection) {
        MongoCursor<Document> cursor = collection.find().iterator();
        int totalCount = 0;
        int documentCount = 0;

        while (cursor.hasNext()) {    
            // Obtenim l'objecte "question"
            Document document = cursor.next();
            Document question = (Document) document.get("question");

            // Comprovem que l'objecte "question" no sigui Null
            if (question != null) {
                Integer viewCount = question.getInteger("ViewCount");
                if (viewCount != null) {
                    totalCount += viewCount.intValue();
                    documentCount++;
                }
            }
        }
        cursor.close();
        return documentCount == 0 ? 0 : totalCount / documentCount;
    }

    // Mètode per obtenir els títols de les preguntes amb ViewCount major que la mitjana
    private static FindIterable<Document> getTitlesAboveAverage(MongoCollection<Document> collection, double averageViewCount) {
        try  {
            FindIterable<Document> titles = collection.find(Filters.gt("ViewCount", averageViewCount));
            return titles;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Mètode per obtenir els títols de les preguntes que contenen certes lletres
    private static FindIterable<Document> getTitlesWithLetters(MongoCollection<Document> collection) {
        try  {
            String regex = ".*\\b(pug|wig|yak|nap|jig|mug|zap|gag|oaf|elf)\\b.*";
            Bson regexDoc = new Document("Title", new Document("$regex", regex).append("$options", "i"));
            FindIterable<Document> titles = collection.find(regexDoc);
            return titles;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Mètode per generar un arxiu PDF amb els títols proporcionats
    private static void generatePDF(String filename, FindIterable<Document> docs) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = null;

            try {
                contentStream = new PDPageContentStream(document, page);
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 750);
                
                int lines = 0;
                for (Document doc : docs) {
                    if (lines > 50) {
                        contentStream.endText();
                        contentStream.close();
                        
                        page = new PDPage();
                        document.addPage(page);
                        
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.beginText();
                        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                        contentStream.setLeading(14.5f);
                        contentStream.newLineAtOffset(25, 750);
                        lines = 0;
                    }

                    lines++;
                    String line = (String) doc.get("Title");
                    contentStream.showText(line);
                    contentStream.newLine();
                }
                contentStream.endText();
            } finally {
                if (contentStream != null) {
                    contentStream.close();
                }
            }
            
            File dir = new File("./data/out");
            if (!dir.exists()) {
                dir.mkdir();
            }

            document.save("./data/out/" + filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
