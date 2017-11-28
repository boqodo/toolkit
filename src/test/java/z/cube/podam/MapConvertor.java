package java.z.cube.podam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author wcp
 * @version 创建时间：2011-12-4 下午11:21:49
 * @DO      Map转换器        
 */
@XmlType(name = "MapConvertor")
@XmlAccessorType(XmlAccessType.FIELD)
public class MapConvertor {
	private List<MapEntry> entries = new ArrayList<MapEntry>();

	public void addEntry(MapEntry entry) {
		entries.add(entry);
	}

	public static class MapEntry {
		public MapEntry() {
			super();
		}

		public MapEntry(Map.Entry<Object, Object> entry) {
			super();
			this.key = entry.getKey();
			this.value = entry.getValue();
		}

		public MapEntry(Object key, Object value) {
			super();
			this.key = key;
			this.value = value;
		}

		private Object key;
		private Object value;

		public Object getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}

	public List<MapEntry> getEntries() {
		return entries;
	}
}