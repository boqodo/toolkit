package z.cube.ehcache;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

public class EhcacheEventListenerFactory extends CacheEventListenerFactory {

	protected static final Logger log = LoggerFactory
			.getLogger(EhcacheEventListenerFactory.class);

	@Override
	public CacheEventListener createCacheEventListener(Properties properties) {
		if (properties == null) {
			return null;
		}
		String listenerClasses = properties.getProperty("listenerClasses");
		String[] classes = StringUtils.split(listenerClasses, ",");
		if (ArrayUtils.isNotEmpty(classes)) {
			List<CacheEventListener> listeners = new ArrayList<CacheEventListener>(
					2);
			for (String clazz : classes) {
				CacheEventListener listener = instantiateClass(clazz);
				if (listener != null) {
					listeners.add(listener);
				}
			}
			return new QueueCacheEventListener(listeners);
		}
		return null;
	}

	private CacheEventListener instantiateClass(String type) {
		CacheEventListener clazz = null;
		try {
			clazz = BeanUtils.instantiateClass(Class.forName(type),
					CacheEventListener.class);
		} catch (BeanInstantiationException e) {
			log.warn("Class {} cannot be instantiated", type);
		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
		}
		return clazz;
	}

}
