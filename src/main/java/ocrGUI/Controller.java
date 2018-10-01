package ocrGUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DateFormat;
import java.util.*;

public class Controller {

    private final ObservableList<File> images = FXCollections.observableArrayList();

    private final FileChooser fileChooser = new FileChooser();
    private Parent stage;

    public void addImage() {
        List<File> list = fileChooser.showOpenMultipleDialog(this.stage.getScene().getWindow());
        if (list != null) {
            for (File file : list) {
                images.add(file);
                System.out.println(file.getAbsolutePath());
            }
        }
    }

    public void convertImages() {
        final String requestURL = "https://www.newocr.com/";
        GregorianCalendar date = new GregorianCalendar();
        String time = "";
        if (date.get(Calendar.HOUR) < 10)
            time += "0" + date.get(Calendar.HOUR);
        else
            time += date.get(Calendar.HOUR);
        if (date.get(Calendar.MINUTE) < 10)
            time += "0" + date.get(Calendar.MINUTE);
        else
            time += date.get(Calendar.MINUTE);

        File fOut = new File("resultados conversion " + time + " " + DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH).format(System.currentTimeMillis()) + ".txt");
        List<String> output = new LinkedList<>();

        for (File file : images) {
            try {
                MultipartFormRequest multipart = new MultipartFormRequest(requestURL);
                multipart.addHeaderField("Accept-Language", "es-ES,es");
                multipart.addFormField("ocr", "1");
                multipart.addFilePart("userfile", file);

                String response = multipart.finish();
                //System.out.println(response);
                output.add(filterResponse(response));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            Files.write(fOut.toPath(), output, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String filterResponse(String html) {
        System.out.println("HTML\n"+html);
        Document doc = Jsoup.parse(html);
        Element content = doc.getElementById("ocr-result");

        return content.text();
    }

    void setStage(Parent stage) {
        this.stage = stage;
    }

    public ObservableList<File> getImages() {
        return images;
    }
}
