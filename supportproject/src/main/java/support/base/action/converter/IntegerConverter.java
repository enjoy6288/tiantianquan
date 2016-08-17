package support.base.action.converter;

import org.springframework.core.convert.converter.Converter;
/**
 * 去除前后空格
 * @author miaoruntu
 *
 */
public class IntegerConverter implements Converter<String, Integer> {

	public Integer convert(String source) {
		//如果源字符串不为空则进行转换
		if(source != null){
			//去除源字符串前后空格
			source = source.trim();
			if(!source.equals("")){ 
				return new Integer(source);
			}
		}
		return null;
	}
}
