package springframeworktest.beans.propertyeditor;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lys on 7/6/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class DatePropertyEditor extends PropertyEditorSupport  {
	private String format = "yyyy-MM-dd";

	public void setFormat(String format){
		this.format = format;
	}

	public void setAsText(String arg0){
		System.out.println("arg0:"+arg0);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			Date d = simpleDateFormat.parse(arg0);
			this.setValue(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
