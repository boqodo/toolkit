package java.z.cube.spring;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(locations={"/spring-config.xml"})
public abstract class AbstractSessionTestCase {
    
//    @Autowired
//    private SessionFactory sessionFactory;
//    private Session session;
//
//    @Before
//    public void openSession()  throws Exception {
//        session = SessionFactoryUtils.getSession(sessionFactory, true);
//        session.setFlushMode(FlushMode.MANUAL);
//        TransactionSynchronizationManager.bindResource(sessionFactory,new SessionHolder(session));
//    }
//
//    @After
//    public void closeSession()  throws Exception {
//        TransactionSynchronizationManager.unbindResource(sessionFactory);
//        SessionFactoryUtils.releaseSession(session, sessionFactory);
//    }
}