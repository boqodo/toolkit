package java.z.cube.podam;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.builder.ToStringBuilder;

import z.cube.podam.ExPackage;
import z.cube.temp.MapAdapter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
	public Person() {
	}

	public Person(Point p, String name, Card card) {
		this.p = p;
		this.name = name;
		this.card = card;

	}

    @XmlTransient
    private Point p;

    private String                        name;
    private Card                          card;
    @XmlJavaTypeAdapter(MapAdapter.class)
    private Map<Integer, List<ExPackage>> map;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Point getP() {
        return p;
    }
    @JsonIgnore
    public void setP(Point p) {
        this.p = p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, List<ExPackage>> getMap() {
        return map;
    }

    public void setMap(Map<Integer, List<ExPackage>> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("p", p)
                .append("name", name)
                .append("card", card)
                .append("map", map)
                .toString();
    }
}