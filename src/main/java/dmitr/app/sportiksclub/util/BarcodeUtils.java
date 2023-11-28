package dmitr.app.sportiksclub.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class BarcodeUtils {

    /**
     * Возвращает сгенерированное изобраэение QR-кода
     *
     * @param text текст
     * @return изображение
     */
    public static Image generateQrCodeImage(String text) {
        BufferedImage bufferedImage = generateQrCodeBufferedImage(text);
        return ImageUtils.getImage(bufferedImage);
    }

    /**
     * Возвращает буфферизированное изображение QR-кода
     * @param text текст
     * @return буфферизированное изображение
     */
    public static BufferedImage generateQrCodeBufferedImage(String text) {
        QRCodeWriter barcodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix;

        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            bitMatrix = barcodeWriter.encode(text, BarcodeFormat.QR_CODE, 400, 400, hints);
        } catch (WriterException exception) {
            throw new RuntimeException(exception);
        }

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}
