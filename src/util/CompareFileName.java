package util;

import java.util.ArrayList;
import java.util.List;

public class CompareFileName {

	/**
	 * ��������ַ������Ϊ �ɱȽϵ�Ԫ��ɵ�����
	 * 
	 * @param s
	 * @return
	 */
	public static List<String> split(String s) {

		List<String> list = new ArrayList<String>();

		char[] cs = s.toCharArray();

		// ��¼���ֵ�Ԫ��ͷ������λ��
		int tmp = -1;

		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (Character.isDigit(c)) {
				if (tmp < 0) {
					tmp = i;
				}
			} else {
				if (tmp >= 0) {
					// �����ַ�֮ǰ�����ֲ��ּ���Ƚϵ�Ԫ
					list.add(s.substring(tmp, i));
					tmp = -1;
				}
				list.add(String.valueOf(c));
			}
		}

		// ������һ��������,���������ּ���list��
		if (Character.isDigit(cs[cs.length - 1])) {
			tmp = tmp < 0 ? cs.length - 1 : tmp;
			list.add(s.substring(tmp, cs.length));
			tmp = -1;
		}

		return list;

	}

	/**
	 * �ȽϹ���
	 * 
	 * @1�������������ַ�����һ���Ƚϵ�Ԫ���Ƚ�ʱ���Դ�Сд
	 * @2�������������֣����������ֿ���һ������������һ���Ƚϵ�Ԫ������ С�� �������ַ�
	 * @3�������ȣ��򵥶��Ƚ����е����ֵ�Ԫ����������λ���϶��С��λ�����ٵ�
	 * 
	 */
	public static int compareFileName(String s1, String s2) {

		// ʡ����ǰ�жϺʹ�Сдת�����ֵĴ��� �������

		List<String> ss1 = split(s1);
		List<String> ss2 = split(s2);

		// ȡ�����Ƚϵ�Ԫ����С����
		int len = ss1.size() < ss2.size() ? ss1.size() : ss2.size();

		// �ȽϽ��
		int r = 0;

		// t1��t2 ��Ӧ�Ƚϵ�Ԫ
		String t1 = null;
		String t2 = null;

		// b1 b2 ��ʶ�Ƚϵ�Ԫ�Ƿ�Ϊ����
		boolean b1 = false;
		boolean b2 = false;

		for (int i = 0; i < len; i++) {
			t1 = ss1.get(i);
			t2 = ss2.get(i);

			b1 = Character.isDigit(t1.charAt(0));
			b2 = Character.isDigit(t2.charAt(0));

			// t1������ t2������
			if (b1 && !b2) {
				return -1;
			}

			// t2������ t1������
			if (!b1 && b2) {
				return 1;
			}

			// t1��t2 ������
			if (!b1 && !b2) {
				r = t1.compareTo(t2);
				if (r != 0) {
					return r;
				}
			}

			// t1 t2��������
			if (b1 && b2) {

				// r = compareNumber(t1, t2);
				// r = Integer.parseInt(t1) - Integer.parseInt(t2);
				long l = Long.parseLong(t1) - Long.parseLong(t2);
				if (l > 0) {
					r = 1;
				} else if (l < 0) {
					r = -1;
				} else {
					r = 0;
				}

				if (r != 0) {
					return r;
				}
			}

		}
		// ����������ϵ� 0-(len-1)�������
		if (r == 0) {
			if (ss1.size() > ss2.size()) {
				r = 1;
			} else if (ss1.size() < ss2.size()) {
				r = -1;
			} else {
				// r = compareNumberPart(s1, s2);
				r = 1;
			}
		}

		return r;
	}

}
