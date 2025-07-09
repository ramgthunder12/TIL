package com.edu.customTag;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;

public class MyCustomTagTEI extends TagExtraInfo {
	@Override
	public boolean isValid(TagData data) {
		String color = data.getAttributeString("color");
		if(color.equals("blue")) {
			return true;
		} else {
			return false;
		}
	}
}
