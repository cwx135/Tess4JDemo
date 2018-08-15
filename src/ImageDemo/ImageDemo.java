package ImageDemo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import util.CreatExcel;
import util.ImageProcess;
import util.ListListSortComparator;
import util.ResultProcess;

public class ImageDemo {
	public static String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

	// public ImageDemo(){
	//
	// }
	public List<String> runImageProcess(String imagePath, String resultExcelName, int n, int x, int y, int width,
			int height, String[] titles) throws Exception {
		// �߳���
		List<String> list = new ArrayList<String>();

		// int n = 9;

		// ��ȡͼƬ��x��y�Ὺʼ����
		// int x = 0;
		// int y = 8;

		// ��ȡͼƬ��x��y�᳤��
		// int width = 533;
		// int height = 67;

		// ͼƬ��λ��
		// String imagePath = "C:\\��è������Ϣִ��";

		// ��ʼʱ��
		long millis1 = System.currentTimeMillis();

		// Excel������
		// String[] titles = { "ͼƬ����", "��˾����", "��˾ע���" };
		// String[] titles = { "��˾����", "��˾ע���" };

		// ���ɵ�Excel����
		// String resultExcelName = "C:\\��è������Ϣִ��\\��è������Ϣִ��ʶ����.xlsx";

		// ʹ��CountDownLatch�̹߳���������߳̿���
		final CountDownLatch latch = new CountDownLatch(n);

		List<List<? extends Object>> lists = new ArrayList<List<? extends Object>>();

		lists = Collections.synchronizedList(lists);
		processImage read = new processImage(lists, x, y, width, height, latch, imagePath);

		Thread[] threads = new Thread[n];
		for (int i = 0; i < n; i++) {
			threads[i] = new Thread(read, "�߳�" + i);
			threads[i].start();
		}
		latch.await();
		// �������̣߳��ȴ������̴߳������

		// ����,ListListSortComparatorΪ������
		Collections.sort(lists, new ListListSortComparator(0, "asc") {
		});

		int successSize = read.resultProcess.successSize;
		// System.out.println("ɨ��ͼƬ����" + read.imageSize + "��");
		// System.out.println("�ɹ�ʶ��" + successSize + "��");
		// System.out.println("ʶ���ʣ�" + (successSize * 100.0) / read.imageSize +
		// "%");
		String scanSize_Str = "ɨ��ͼƬ����" + read.imageSize + "��" + LINE_SEPARATOR;
		String successSize_Str = "�ɹ�ʶ��" + successSize + "��" + LINE_SEPARATOR;
		// String successPercent_Str = "ʶ���ʣ�" + (successSize * 100.0) /
		// read.imageSize + "%" + LINE_SEPARATOR;

		list.add(scanSize_Str);
		list.add(successSize_Str);
		// list.add(successPercent_Str);

		OutputStream os = new FileOutputStream(new File(resultExcelName));

		// �Խ��ʹ��ͼƬ�������������
		CreatExcel.creatExcel(lists, titles, "xlsx", "��ҵ��Ϣ��").write(os);
		os.close();

		// ����ʱ��
		long millis2 = System.currentTimeMillis();

		// �����ܺ�ʱ
		// System.out.println("��ʱ-------->" + (millis2 - millis1) / 1000.0 +
		// "��");
		String timeExpend = "��ʱ-------->" + (millis2 - millis1) / 1000.0 + "��" + LINE_SEPARATOR;
		list.add(timeExpend);
		return list;
	}

}

class processImage implements Runnable {
	// ͼƬ���Ƽ���
	List<File> filePathsList = new ArrayList<File>();
	List<List<? extends Object>> lists = null;
	int index = 0;
	int x, y, width, height;
	Object obj = new Object();
	CountDownLatch latch;
	int imageSize;
	ResultProcess resultProcess;

	public processImage(List<List<? extends Object>> lists, int x, int y, int width, int height, CountDownLatch latch,
			String imagePath) {
		this.lists = lists;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		// ͼƬ���ڵ�ַ
		File f = new File(imagePath);
		getFileList(f);
		this.latch = latch;
	}

	private int getFileList(File f) {
		File[] filePaths = f.listFiles();
		for (File s : filePaths) {
			if (s.isDirectory()) {
				getFileList(s);
			} else {
				if ((-1 != s.getName().lastIndexOf(".png")) || (-1 != s.getName().lastIndexOf(".jpg"))
						|| (-1 != s.getName().lastIndexOf(".jpeg"))) {
					filePathsList.add(s);
				}
			}
		}
		imageSize = filePathsList.size();
		resultProcess = new ResultProcess(imageSize);
		return filePathsList.size();
	}

	@Override
	public void run() {
		File file = null;
		BufferedImage image = null;
		// �洢����ͼƬ������
		List<String> list = null;
		String fileName = null;
		while (index < filePathsList.size()) {
			synchronized (this) {
				if (index >= filePathsList.size()) {
					return;
				}
				file = filePathsList.get(index);

				index++;
			}
			try {

				fileName = file.getName();
				ITesseract instance = new Tesseract();

				// ����ʹ������+Ӣ���ַ���
				instance.setDatapath("");
				instance.setLanguage("chi_sim+eng");
				// ͼƬ����
				image = ImageProcess.imageProcess(file, x, y, width, height);

				String result = instance.doOCR(image);
				// ��result���д���
				list = resultProcess.resultProcess(result, fileName);

				lists.add(list);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		// �̼߳���
		latch.countDown();
	}

}
