package z.cube.ehcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;

import org.apache.commons.lang3.StringUtils;

public class CookieCacheEventListener extends CacheEventListenerAdapter {
	@Override
	public void notifyElementPut(Ehcache cache, Element element)
			throws CacheException {
		cache.flush();
		System.out.println(StringUtils.center("刷新到硬盘", 50, "-"));
	}
}
