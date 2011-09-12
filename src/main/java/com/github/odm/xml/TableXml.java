package com.github.odm.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("table")
public class TableXml {
	@XStreamAsAttribute
	private String name;

	@XStreamImplicit
	private List<MaskXml> masks;

	public TableXml() {
		this.masks = new ArrayList<MaskXml>();
	}

	public TableXml(String name, MaskXml... masks) {
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
