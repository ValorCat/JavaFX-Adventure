package util;

import dialog.DialogTree;
import javafx.event.EventTarget;
import javafx.scene.text.Text;

public final class StringUtil {
	
	public static String getRowFromListView(EventTarget target) {
		String stringForm = target.toString();
		if (target instanceof Text) {
			return ((Text) target).getText();
		} else if (stringForm.startsWith("ListViewSkin$2")) {
			int len = stringForm.length();
			StringBuilder row = new StringBuilder();
			for (int i = len - 2; i > 0; i--) {
				char c = stringForm.charAt(i);
				if (c != '\'') {
					row.insert(0, c);
				} else {
					break;
				}
			}
			return row.toString();
		} else {
			throw new IllegalArgumentException("Cannot process target type " + target.getClass());
		}
	}
	
	public static int getDialogOptionNumber(String text) {
		StringBuilder number = new StringBuilder();
		for (char c : text.toCharArray()) {
			if (c == DialogTree.OPTION_DIVIDER)
				break;
			number.append(c);
		}
		return Integer.valueOf(number.toString());
	}

}
