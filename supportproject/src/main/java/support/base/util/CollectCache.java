package support.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存用户收藏的商品信息
 * @author NE1634
 *
 */
public class CollectCache {

	private CollectCache() {
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
