package com.avit.itdap.util.xml;

import javax.xml.bind.annotation.XmlRegistry;

import com.avit.itdap.bean.xml.BroadcastData;
import com.avit.itdap.bean.xml.SynCategory;

@XmlRegistry
public class ObjectFactory {

	public BroadcastData createBroadcastData() {
		return new BroadcastData();
	}
	public SynCategory createSynCategory(){
		return new SynCategory();
	}
}
