package cat.iesesteveterradas.exemples;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.json.JSONArray;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DescarregarModelsMain {
    private static final Logger logger = LoggerFactory.getLogger(DescarregarModelsMain.class);

    public static void main(String[] args) {
        String modelsListPath = System.getProperty("user.dir") + "/etc/models_list.json";
        String targetDirPath = System.getProperty("user.dir") + "/data/models";

        // Create target directory if it does not exist
        try {
            Files.createDirectories(Paths.get(targetDirPath));
        } catch (IOException e) {
            logger.error("Failed to create directories", e);
            return;
        }

        // Read JSON file
        try {
            String jsonContent = Files.readString(Paths.get(modelsListPath), StandardCharsets.UTF_8);
            JSONArray urls = new JSONArray(jsonContent);

            for (int i = 0; i < urls.length(); i++) {
                String urlString = urls.getString(i);
                URL url = new URL(urlString);
                String fileName = Paths.get(url.getPath()).getFileName().toString();
                Path targetPath = Paths.get(targetDirPath, fileName);

                logger.info("Downloading {} to {}", urlString, targetPath);

                // Download and save the file
                try (InputStream in = new BufferedInputStream(url.openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(targetPath.toString())) {
                    byte dataBuffer[] = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                    logger.info("Download completed: {}", fileName);
                } catch (IOException e) {
                    // Handle exceptions with logger
                    logger.error("Error downloading " + urlString, e);
                }
            }
        } catch (Exception e) {
            logger.error("Error processing models list", e);
        }
    }
}
