package support.base.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import support.base.pojo.po.Category;

public class CommonUse {

	private CommonUse() {
	}

	private static Map<String, List<Category>> caches;

	static {
		caches = new HashMap<String, List<Category>>();
	}

	// 用于保存缓存
	public static void addCache(String key, List<Category> value) {
		caches.put(key, value);
	}

	// 用于得到缓存
	public static List<Category> getCache(String key) {
		return caches.get(key);
	}
}
