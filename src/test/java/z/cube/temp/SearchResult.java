package java.z.cube.temp;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class SearchResult {

	private List<Map<String, String>> listOfMap;

	private Map<String, Collection<String>> mapWithList;

	@XmlJavaTypeAdapter(MapAdapter.class)
	public List<Map<String, String>> getListOfMap() {
		return listOfMap;
	}

	public void setListOfMap(List<Map<String, String>> listOfMap) {
		this.listOfMap = listOfMap;
	}

	public Map<String, Collection<String>> getMapWithList() {
		return mapWithList;
	}

	public void setMapWithList(Map<String, Collection<String>> mapWithList) {
		this.mapWithList = mapWithList;
	}

	public SearchResult() {
	}

}