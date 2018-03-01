package com.ace.encry.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Utils {
	
	public static void copyText2Clipboard(String str) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection stringSel = new StringSelection(str);
		clipboard.setContents(stringSel, null);
	}
}
