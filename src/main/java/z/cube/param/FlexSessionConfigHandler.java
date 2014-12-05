package z.cube.param;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;

public class FlexSessionConfigHandler implements ConfigHandler {
    private transient final Logger log;
    private static final String REF_CHAR = ".";
    private FlexSession session;

    public FlexSessionConfigHandler() {
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Override
    public void init(InitConfig initConfig) {
        session = FlexContext.getFlexSession();
    }

    @Override
    public Object getValue(String key) {
        if (StringUtils.contains(key, REF_CHAR)) {
            int firstIndex = StringUtils.indexOf(key, REF_CHAR);
            String prefix = StringUtils.substring(key, 0, firstIndex);
            Object holder = session.getAttribute(prefix);
            if (holder != null) {
                String suffix = StringUtils.substring(key, firstIndex + 1);
                try {
                    return PropertyUtils.getProperty(holder, suffix);
                } catch (Exception e) {
                    log.warn("{}未取到对应的值!(异常:{})", key, e.getMessage());
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return session.getAttribute(key);
        }
    }
}
