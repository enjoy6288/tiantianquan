package support.base.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SpringPropertyUtil extends PropertyPlaceholderConfigurer {
	private static Map<String, String> properties;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		properties = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyS = key.toString();
			String value = props.getProperty(keyS);
			properties.put(keyS.trim(), value.trim());
		}
	}
	
	public static String getContextProperty(String name) {
		return properties.get(name);
	}

}
