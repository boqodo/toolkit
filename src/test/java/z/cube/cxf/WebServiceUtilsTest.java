package z.cube.cxf;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import javax.xml.ws.Endpoint;

import org.apache.cxf.endpoint.Server;
import org.junit.Test;

import z.cube.utils.WebServiceUtils;

public class WebServiceUtilsTest {
	
	@Test
	public final void testCxfPublish() throws Exception {
        UserManager manager = buildUserManager();
        List<User> serverUsers = manager.queryUsers();
        String address = "http://127.0.0.1:8080/";
        Server server=WebServiceUtils.publish(address, IUserManager.class, manager);
	    assertTrue(server.isStarted());
        IUserManager client=WebServiceUtils.getServiceClazzInstance(IUserManager.class, address);
        assertEqList(serverUsers, client.queryUsers());

        Object[] result=WebServiceUtils.dynamicInvoke(address,"queryUsers");
        List<User> clientUsers= (List<User>) result[0];
        assertEqList(serverUsers, clientUsers);


        server.stop();
    }

    private UserManager buildUserManager() {
        UserManager manager = new UserManager();
        manager.addUser("Zhangsan");
        manager.addUser("Lisi");
        manager.addUser("Wangwu");
        return manager;
    }

    @Test
    public final void testJdkPublish() throws Exception {
        UserManager manager = buildUserManager();
        List<User> serverUsers = manager.queryUsers();
        String address = "http://127.0.0.1:8080/";
        Endpoint server=WebServiceUtils.publish(address, manager);
        assertTrue(server.isPublished());
        IUserManager client=WebServiceUtils.getServiceClazzInstance(IUserManager.class, address);
        List<User> clientUser = client.queryUsers();
        assertEqList(serverUsers, clientUser);

        Object[] result=WebServiceUtils.dynamicInvoke(address,"queryUsers");
        List<User> clientUsers= (List<User>) result[0];
        assertEqList(serverUsers, clientUsers);

        server.stop();
    }

    private void assertEqList(List<User> serverUsers, List<User> clientUser) {
        assertEquals(serverUsers.size(),clientUser.size());
        for (int i = 0; i < serverUsers.size(); i++) {
            assertEquals(serverUsers.get(i),clientUser.get(i));
        }
    }
}
