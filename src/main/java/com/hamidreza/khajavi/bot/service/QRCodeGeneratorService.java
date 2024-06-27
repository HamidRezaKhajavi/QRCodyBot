package com.hamidreza.khajavi.bot.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.FastByteArrayOutputStream;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

/**
 * @author Hamidreza Khajavi
 * @since 6/20/2024
 */
@Service
public class QRCodeGeneratorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QRCodeGeneratorService.class);
    private static final String IMAGE_FORMAT_PNG = "PNG";

    public InputFile getQRCode(String text) {
        Assert.hasText(text, "text is null...");
        try {
            InputStream inputStream = generateQRCodeImage(text, 250, 250);
            return new InputFile().setMedia(inputStream, "QR-Code");
        } catch (WriterException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new InputFile();
    }

    private InputStream generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, Collections.singletonMap(EncodeHintType.CHARACTER_SET, "utf-8"));
        FastByteArrayOutputStream pngOutputStream = new FastByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig(Color.black.hashCode(), Color.white.hashCode());
        MatrixToImageWriter.writeToStream(bitMatrix, IMAGE_FORMAT_PNG, pngOutputStream, con);
        return pngOutputStream.getInputStream();
    }
}
