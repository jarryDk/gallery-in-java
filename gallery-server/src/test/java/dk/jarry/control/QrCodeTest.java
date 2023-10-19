package dk.jarry.control;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.jarry.gallery.control.QrCode;
import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class QrCodeTest {

    @Test
    void createdHonnyBeeQr() throws Exception {

        String text = "Foo";
        String imageNameOrg = "Cartoon-Bee-Honningr.png";
        String imageNameQr = "Cartoon-Bee-Honningr-qr.png";
        int width = 640;

        Path imagePathOrg =
                Path.of(
                        this.getClass() //
                                .getClassLoader() //
                                .getResource(imageNameOrg) //
                                .toURI());
        System.out.println(imagePathOrg.toAbsolutePath());

        Path imagePathQr = Path.of(imagePathOrg.getParent().toString(), imageNameQr);
        System.out.println(imagePathQr.toAbsolutePath());

        QrCode.writeQrCode(text, imagePathOrg.toString(), imagePathQr.toString(), width);

        File qrFile = imagePathQr.toFile();

        assertTrue(qrFile.exists());
        assertTrue(qrFile.length() > 30_000);
    }
}
