package com.wonders.xlab.qudongdong.menu.model;


/**
 * 父菜单按钮
 * @author Administrator
 *
 */
public class ComplexButton extends Button {
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
	
}
