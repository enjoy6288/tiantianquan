package support.base.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import support.base.pojo.po.Category;

public class CommonUse {

	private CommonUse() {
	}

	private static Map<String, Object> caches;

	static {
		caches = new HashMap<String, Object>();
	}
	// 用于保存缓存
	public static void addCache(String key, Object value) {
		caches.put(key, value);
	}

	// 用于得到缓存
	public static Object getCache(String key) {
		return caches.get(key);
	}
	
	public static void removeCache(String key){
		caches.remove(key);
	}
	
	
}
