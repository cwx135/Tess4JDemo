package util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.util.ImageHelper;

public class ImageProcess {
	public static BufferedImage imageProcess(File imageFile, int x, int y, int width, int height) throws IOException {
		InputStream is = null;
		BufferedImage image = null;
		String fileName = imageFile.getName();

		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		// ��ȡͼƬ
		is = new FileInputStream(imageFile.getPath());
		image = ImageIO.read(is);

		if (image.getWidth() < x + width) {
			throw new RuntimeException("ͼƬ����С����ָ�ĳ��ȣ�������ָ����");
		}
		if (image.getHeight() < y + height) {
			throw new RuntimeException("ͼƬ���С����ָ�Ŀ�ȣ�������ָ����");
		}
		// ͼƬԤ���������pngͼƬ�Ļ���͸������Ҫ��ͼ
		if ("png".equals(fileType)) {
			image = Png2Jpg.png2Jpg(image, x, y, width, height);
		} else {
			BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
			Graphics2D g2 = tmp.createGraphics();
			// ��ͼƬ�����и�
			g2.drawImage(image.getSubimage(x, y, width, height), 0, 0, null);
			g2.dispose();
		}
		// ��ֵ��
		image = ImageHelper.convertImageToBinary(image);
		// �Ŵ�
		image = ImageHelper.getScaledInstance(image, (width + 3) * 2, height * 2);

		is.close();

		return image;
	}
}
