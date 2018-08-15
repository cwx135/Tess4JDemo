package util;

import java.util.Comparator;
import java.util.Map;

public abstract class ListMapSortComparator implements
		Comparator<Map<String, Object>> {

	private String key;

	private String order;

	public ListMapSortComparator(String key, String order) {
		this.key = key;
		this.order = order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */

	// �������򷽷��и�ȱ�ݣ����������ֵ����ַ������������򣬵���������12��2С�Ĵ����߼���
	// �������дcompare������ʱ����Ҫ�ԱȽϵĶ����Ƚ������ͷ����ٱȽϣ����磺
	// if(o1.get(key) instanceOf Integer){
	// return
	// Integer.valueOf(o1.get(key).toString())-Integer.valueOf(o2.get(key).toString());
	// }
	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		if (order.equals("asc")) {
			if (o1.get(key) instanceof Integer) {
				return Integer.valueOf(o1.get(key).toString())
						- Integer.valueOf(o2.get(key).toString());
			} else {
				return o1.get(key).toString().compareTo(o2.get(key).toString());
			}
		} else {
			if (o2.get(key) instanceof Integer) {
				return Integer.valueOf(o2.get(key).toString())
						- Integer.valueOf(o1.get(key).toString());
			} else {
				return o2.get(key).toString().compareTo(o1.get(key).toString());
			}
		}
	}

}
