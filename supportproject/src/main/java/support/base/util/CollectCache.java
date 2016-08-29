package support.base.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import support.base.pojo.vo.SweetCollectVo;

/**
 * 缓存用户收藏的商品信息
 * 
 * @author NE1634
 * 
 */
public class CollectCache {

	private CollectCache() {
	}

	private static Map<String, Set<SweetCollectVo>> caches;

	static {
		caches = new HashMap<String, Set<SweetCollectVo>>();
	}

	// 用于保存缓存
	public static void addCache(String userToken,Set<SweetCollectVo> value) {
		caches.put(userToken, value);
	}

	// 根据用户token得到该用户缓存
	public static Set<SweetCollectVo> getCache(String userToken) {
		Set<SweetCollectVo> set = caches.get(userToken);
		if (set == null) {
			set = new HashSet<>();
		}
		return set;
	}

	public static void removeCache(String userToken) {
		caches.remove(userToken);
	}

}
