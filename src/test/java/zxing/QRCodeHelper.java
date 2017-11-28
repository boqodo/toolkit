package java.zxing;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeHelper {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	private static final int DEFAULT_WIDTH = 280;
	private static final int DEFAULT_HEIGHT = DEFAULT_WIDTH;
	private static final String IMAGE_FORMAT = "jpeg";

	public static void out2Web(HttpServletRequest request, HttpServletResponse response,
			String data) throws Exception {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/" + IMAGE_FORMAT);
        OutputStream outputStream = response.getOutputStream();

		String sWidth = request.getParameter("width");
		String sHeight = request.getParameter("height");
		int width = DEFAULT_WIDTH;
		int height = DEFAULT_HEIGHT;
		if (StringUtils.isNotBlank(sWidth)) {
			width = NumberUtils.toInt(sWidth, DEFAULT_WIDTH);
		}
		if (StringUtils.isNotBlank(sHeight)) {
			height = NumberUtils.toInt(sHeight, DEFAULT_HEIGHT);
		}

		BufferedImage image = generateImage(data, width, height);
		try{
            ImageIO.write(image, IMAGE_FORMAT, outputStream);
        }finally {
            IOUtils.closeQuietly(outputStream);
        }
	}

	public static BufferedImage generateImage(String data, int width, int height)
			throws WriterException {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, "0");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE,
				width, height, hints);
		return toBufferedImage(bitMatrix);
	}

	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
}
