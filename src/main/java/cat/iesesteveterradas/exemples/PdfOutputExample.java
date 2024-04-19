package cat.iesesteveterradas.exemples;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;

public class PdfOutputExample {

    public static void main(String[] args) {

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

                for (int i = 1; i <= 1000; i++) {
                    if (i % 45 == 0 && i != 1000) {
                        contentStream.endText();
                        contentStream.close();
                        
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.beginText();
                        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                        contentStream.setLeading(14.5f);
                        contentStream.newLineAtOffset(25, 750);
                    }
                    
                    String line = "Línia " + i + ": Exemple de text";
                    contentStream.showText(line);
                    contentStream.newLine();
                }
                
                contentStream.endText();
            } finally {
                if (contentStream != null) {
                    contentStream.close();
                }
            }

            String outputPath = System.getProperty("user.dir") + "/data/output/testoutput.pdf";
            document.save(outputPath);
            System.out.println("PDF creat a: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error al crear el PDF: " + e.getMessage());
        }
    }
}
