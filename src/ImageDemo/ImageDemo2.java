package ImageDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.xmlbeans.impl.jam.xml.TunnelledException;

//ʵ�ֽӿ�ActionListener
public class ImageDemo2 implements ActionListener {

	JFrame jf;
	JPanel jpanel;
	JButton jb1, jb2, jb3, open, run;
	JTextArea jta = null;
	JScrollPane jscrollPane;
	// ��Ҫɨ�貢ʶ���ͼƬ��ͼƬ�ļ���·��
	String imagePath = null;
	List<String> list = null;
	// ���ɵ�Excel��·��
	String resultExcelName = null;
	int threadSize;
	// ��ȡͼƬ��x��y�Ὺʼ����
	// ��ȡͼƬ��x��y�᳤��
	int x, y, width, height;
	// Excel������
	String[] titles;
	public static String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

	public ImageDemo2(String str[]) {

		resultExcelName = str[0];
		threadSize = Integer.parseInt(str[1]);
		x = Integer.parseInt(str[2]);
		y = Integer.parseInt(str[3]);
		width = Integer.parseInt(str[4]);
		height = Integer.parseInt(str[5]);
		titles = new String[] { str[6], str[7], str[8] };

		jf = new JFrame("����ͼƬʶ��");
		Image icon = Toolkit.getDefaultToolkit().getImage("ship.png");
		jf.setIconImage(icon);

		Container contentPane = jf.getContentPane();
		contentPane.setLayout(new BorderLayout());

		jta = new JTextArea(10, 15);
		jta.setTabSize(8);
		jta.setFont(new Font("�꿬��", Font.BOLD, 24));
		jta.setLineWrap(true);// �����Զ����й���
		jta.setWrapStyleWord(true);// ������в����ֹ���
		jta.setBackground(Color.pink);// ����ɫ��

		jscrollPane = new JScrollPane(jta);// ����
		jpanel = new JPanel();
		jpanel.setLayout(new GridLayout(1, 3));

		jb1 = new JButton("����");
		jb1.addActionListener(this);
		jb2 = new JButton("ճ��");
		jb2.addActionListener(this);
		jb3 = new JButton("����");
		jb3.addActionListener(this);
		open = new JButton("ѡ���ļ�");
		open.addActionListener(this);
		run = new JButton("��ʼ");
		run.addActionListener(this);

		jpanel.add(jb1);
		jpanel.add(jb2);
		jpanel.add(jb3);
		jpanel.add(open);
		jpanel.add(run);

		contentPane.add(jscrollPane, BorderLayout.CENTER);
		contentPane.add(jpanel, BorderLayout.SOUTH);

		jf.setSize(900, 700);
		jf.setLocation(400, 200);
		jf.setVisible(true);

		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	// ���ǽӿ�ActionListener�ķ���actionPerformed
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb1) {
			jta.copy();
		} else if (e.getSource() == jb2) {
			jta.paste();
		} else if (e.getSource() == jb3) {
			jta.cut();
		} else if (e.getSource() == open) {
			actionPerformed_Open(e);
		} else if (e.getSource() == run) {
			jta.append(LINE_SEPARATOR + "----���ڴ�����ȴ�----" + LINE_SEPARATOR);

			jta.append("ʶ���ٶ�ȡ�������ĵ�������" + LINE_SEPARATOR);
			jta.append("���ɵ�Excel�ļ�λ�ã�" + resultExcelName + LINE_SEPARATOR);
			jta.append("�������߳���" + threadSize + LINE_SEPARATOR);
			jta.append("ʶ��ͼƬ��ʾ���꣨" + x + "," + y + ")" + LINE_SEPARATOR);
			jta.append("ʶ��ͼƬ���ȺͿ�ȣ�" + width + "," + height + ")" + LINE_SEPARATOR);
			jta.append("���ɵ�Excel��������----" + titles[0] + "----" + titles[1] + "----" + titles[2] + LINE_SEPARATOR);
			jta.append("Ĭ�ϲ�����" + "C:\\��è������Ϣִ��\\��è������Ϣִ��ʶ����.xlsx, " + "9, " + "0, " + "8, " + "533, " + "67, " + "ͼƬ����, "
					+ "��˾����, " + "��˾ע��� " + LINE_SEPARATOR);
			jta.append("�����Ը��ݵ�������ѡ�������߳���Ŀ������ͼƬѡ��ʶ��λ�ã����������ļ��еĸ���Ĭ������" + LINE_SEPARATOR);
			jta.append("���C����ΪȨ�����ⲻ��д����ѡ����ʵ�Excel�ļ�����λ��" + LINE_SEPARATOR);
			jta.paintImmediately(jta.getBounds());

			actionPerformed_Run(e);
		}
	}

	public void actionPerformed_Open(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.showDialog(new JLabel(), "ѡ��");
		File file = jfc.getSelectedFile();
		if (file != null) {
			if (file.isDirectory()) {
				imagePath = file.getAbsolutePath();
				if (imagePath != null) {
					// System.out.println("��ѡ�ļ���:" + imagePath);
					jta.append(imagePath + LINE_SEPARATOR);
				}

			} else if (file.isFile()) {
				imagePath = file.getAbsolutePath();
				if (imagePath != null) {
					// System.out.println("�ļ�:" + file.getAbsolutePath());
					jta.append(file.getAbsolutePath() + LINE_SEPARATOR);
					jta.append("���ɵ�Excel�ļ�λ�ã�" + resultExcelName + LINE_SEPARATOR);
				}
			}
		}
		// System.out.println(jfc.getSelectedFile().getName());
	}

	public void actionPerformed_Run(ActionEvent e) {
		try {
			if (imagePath != null) {
				// String imagePath, String resultExcelName, int n, int x, int
				// y, int width,
				// int height, String[] titles

				list = new ImageDemo().runImageProcess(imagePath, resultExcelName, threadSize, x, y, width, height,
						titles);
				Iterator<String> iterator = list.iterator();
				while (iterator.hasNext()) {
					jta.append(iterator.next());
				}
			} else {
				// ʲô������
			}
		} catch (Exception e1) {
			// System.out.println(e1.toString());
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args == null || args.length < 9) {
			String[] str1 = { "C:\\��è������Ϣִ��\\��è������Ϣִ��ʶ����.xlsx", "9", "0", "8", "533", "67", "ͼƬ����", "��˾����", "��˾ע���" };
			args = str1;
		}
		new ImageDemo2(args);
	}
}
