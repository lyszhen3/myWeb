package springframeworktest.beans.propertyeditor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lys on 7/6/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class DatePropertyEditorRegistar implements PropertyEditorRegistrar {
	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
