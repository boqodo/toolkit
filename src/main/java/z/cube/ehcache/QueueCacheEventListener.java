package z.cube.ehcache;

import java.util.List;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class QueueCacheEventListener implements CacheEventListener {

	private List<CacheEventListener> listeners;

	public QueueCacheEventListener(List<CacheEventListener> listeners) {
		this.listeners = listeners;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public void notifyElementRemoved(Ehcache cache, Element element)
			throws CacheException {
		for (CacheEventListener listener : listeners) {
			listener.notifyElementRemoved(cache, element);
		}
	}

	@Override
	public void notifyElementPut(Ehcache cache, Element element)
			throws CacheException {
		for (CacheEventListener listener : listeners) {
			listener.notifyElementPut(cache, element);
		}
	}

	@Override
	public void notifyElementUpdated(Ehcache cache, Element element)
			throws CacheException {
		for (CacheEventListener listener : listeners) {
			listener.notifyElementUpdated(cache, element);
		}
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
		for (CacheEventListener listener : listeners) {
			listener.notifyElementExpired(cache, element);
		}
	}

	@Override
	public void notifyElementEvicted(Ehcache cache, Element element) {
		for (CacheEventListener listener : listeners) {
			listener.notifyElementEvicted(cache, element);
		}
	}

	@Override
	public void notifyRemoveAll(Ehcache cache) {
		for (CacheEventListener listener : listeners) {
			listener.notifyRemoveAll(cache);
		}
	}

	@Override
	public void dispose() {
		for (CacheEventListener listener : listeners) {
			listener.dispose();
		}
	}

}
