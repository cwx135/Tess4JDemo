package util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreatExcel {
	public static Workbook creatExcel(List<List<? extends Object>> lists, String[] titles, String excelExtName,
			String sheetName) throws Exception {
		// System.out.println(lists);
		// �����µĹ�����
		Workbook wb = null;
		if ("xls".equals(excelExtName)) {
			wb = new HSSFWorkbook();
		} else if ("xlsx".equals(excelExtName)) {
			wb = new XSSFWorkbook();
		} else {
			throw new Exception("��ǰ�ļ�����excel�ļ�");
		}
		// ������һ��sheet��ҳ����������
		Sheet sheet = wb.createSheet(sheetName);
		// �ֶ������п���һ��������ʾҪΪ�ڼ����裻���ڶ���������ʾ�еĿ�ȣ�nΪ�иߵ���������
		for (int i = 0; i < titles.length; i++) {
			if (i == 0) {
				sheet.setColumnWidth((short) i, (short) (35 * 100));
				// sheet.setColumnWidth((short) i, (short) (35 * 300));
			} else {
				sheet.setColumnWidth((short) i, (short) (35 * 300));
			}
		}

		// ������һ��
		Row row = sheet.createRow((short) 0);

		// �������ֵ�Ԫ���ʽ
		CellStyle cs = wb.createCellStyle();
		CellStyle cs2 = wb.createCellStyle();

		// ������������
		Font f = wb.createFont();
		Font f2 = wb.createFont();

		// ������һ��������ʽ������������
		f.setFontHeightInPoints((short) 10);
		f.setColor(IndexedColors.BLACK.getIndex());
		f.setBold(true);
		// �����ڶ���������ʽ������ֵ��
		f2.setFontHeightInPoints((short) 10);
		f2.setColor(IndexedColors.BLACK.getIndex());

		// ���õ�һ�ֵ�Ԫ�����ʽ������������
		cs.setFont(f);
		cs.setBorderLeft(BorderStyle.THIN);
		cs.setBorderRight(BorderStyle.THIN);
		cs.setBorderTop(BorderStyle.THIN);
		cs.setBorderBottom(BorderStyle.THIN);
		cs.setAlignment(HorizontalAlignment.CENTER);

		// ���õڶ��ֵ�Ԫ�����ʽ������ֵ��
		cs2.setFont(f2);
		cs2.setBorderLeft(BorderStyle.THIN);
		cs2.setBorderRight(BorderStyle.THIN);
		cs2.setBorderTop(BorderStyle.THIN);
		cs2.setBorderBottom(BorderStyle.THIN);
		cs2.setAlignment(HorizontalAlignment.CENTER);
		// ��������
		for (int i = 0; i < titles.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(cs);
		}
		if (lists == null || lists.size() == 0) {
			return wb;
		}
		// ����ÿ��ÿ�е�ֵ
		
		// System.out.println("��ʶ��ͼƬ" + lists.size() + "��");
		
		for (short i = 1; i <= lists.size(); i++) {
			// Row ��,Cell ���� , Row �� Cell ���Ǵ�0��ʼ������
			// ����һ�У���ҳsheet��
			Row row1 = sheet.createRow((short) i);
			for (short j = 0; j < titles.length; j++) {
				// ��row���ϴ���һ������
				Cell cell = row1.createCell(j);
				cell.setCellValue((String) lists.get(i - 1).get(j));
				cell.setCellStyle(cs2);
			}
		}
		return wb;
	}
}
