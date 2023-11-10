package dmitr.app.sportiksclub.util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.IntBuffer;

public class ImageUtils {

    public static Image getImage(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        newImage.createGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

        int[] typeIntArgb = ((DataBufferInt) newImage.getRaster().getDataBuffer()).getData();
        IntBuffer buffer = IntBuffer.wrap(typeIntArgb);

        PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(newImage.getWidth(), newImage.getHeight(), buffer, pixelFormat);
        return new WritableImage(pixelBuffer);
    }

}
