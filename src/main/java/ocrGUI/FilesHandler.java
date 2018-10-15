package ocrGUI;

import ocrGUI.model.AppImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FilesHandler {
    private static final File TEMP_IMG_FOLDER = Paths.get("tmp", "").toFile();
    private static final File OCR_DATA_FOLDER = Paths.get("tessdata", "").toFile();

    public static List<File> saveImages(List<AppImage> images, int firstPage) {
        List<File> fileList = new LinkedList<>();
        int currPage = firstPage;
        for (AppImage appImage : images) {
            BufferedImage image = appImage.getImage();
            System.out.println("Saving page " + currPage + " | PDF page for public is " + (currPage + 1));
            File target = Paths.get("tmp", "Image-" + (currPage + 1) + ".png").toFile();
            try {
                ImageIO.write(image, "png", target);
                target.deleteOnExit();
                fileList.add(target);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error saving Image of Page " + currPage);
                System.exit(1);
            }
            currPage++;
        }
        return fileList;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void createFolders() {
        System.out.println("Created folders for execution");
        if (!TEMP_IMG_FOLDER.exists()) {
            TEMP_IMG_FOLDER.mkdir();
            TEMP_IMG_FOLDER.deleteOnExit();
        }
        if (!OCR_DATA_FOLDER.exists()) {
            OCR_DATA_FOLDER.mkdir();
            OCR_DATA_FOLDER.deleteOnExit();
        }
    }
}
