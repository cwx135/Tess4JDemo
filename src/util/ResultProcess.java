package util;

import java.util.ArrayList;
import java.util.List;

public class ResultProcess {
	public int successSize;

	public ResultProcess(int imageSize) {
		this.successSize = imageSize;
	}

	public List<String> resultProcess(String result, String fileName) {
		int beginIndexEr;
		int endIndexQi;
		int endIndexEr;
		String busName = null;
		String busId = null;
		// static int successSize;
		List<String> list = new ArrayList<String>();
		beginIndexEr = result.indexOf("��");
		endIndexQi = result.lastIndexOf("��");
		endIndexEr = result.lastIndexOf("��");

		if (endIndexEr == -1) {
			busName = "��ҵ����ʶ��ʧ��";
			successSize--;
		} else {
			busName = result.substring(endIndexEr + 1, result.length() - 2);
			busName = RemoveChar4Str.removeChar4Str(busName, ' ');
			if (busName.contains("B��")) {
				busName = busName.replaceAll("B��", "��");
			}
			if (busName.contains("����")) {
				busName = busName.replaceAll("����", "����");
			}
			if (busName.contains("����")) {
				busName = busName.replaceAll("����", "����");
			}
			if (busName.contains("��")) {
				busName = busName.replaceAll("��", ")");
			}
			if (busName.contains("_")) {
				busName = busName.replaceAll("_", "һ");
			}

		}

		if ((beginIndexEr == -1) || (endIndexQi == -1)) {
			busId = "ʶ��ע���ʧ��";
		} else {
			busId = result.substring(beginIndexEr + 1, endIndexQi - 1);
			busId = RemoveChar4Str.removeChar4Str(busId, ' ');
			if (busId.contains("��")) {
				busId = busId.replaceAll("��", "T");
			}
		}

		list.add(fileName);
		list.add(busName);
		list.add(busId);

		return list;
	}
}
