package com.github.odm.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("column")
public class ColumnXml {
	@XStreamAsAttribute
	private String name;

	@XStreamImplicit
	private List<MaskXml> masks;

	public ColumnXml() {
		this.masks = new ArrayList<MaskXml>();
	}

	public ColumnXml(String name, MaskXml... masks) {
		this();
		this.name = name;
		
		for (MaskXml maskXml : masks) {
			this.masks.add(maskXml);
		}
	}

	public String getName() {
		return name;
	}

	public List<MaskXml> getMasks() {
		return masks;
	}
}
