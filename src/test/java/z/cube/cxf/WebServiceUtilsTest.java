package java.z.cube.cxf;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import javax.xml.ws.Endpoint;

import org.apache.cxf.endpoint.Server;
import org.junit.Test;

import z.cube.cxf.IUserManager;
import z.cube.cxf.User;
import z.cube.cxf.UserManager;
import z.cube.utils.WebServiceUtils;

public class WebServiceUtilsTest {
	
	@Test
	public final void testCxfPublish() throws Exception {
        z.cube.cxf.UserManager manager = buildUserManager();
        List<z.cube.cxf.User> serverUsers = manager.queryUsers();
        String address = "http://127.0.0.1:8080/";
        Server server=WebServiceUtils.publish(address, z.cube.cxf.IUserManager.class, manager);
	    assertTrue(server.isStarted());
        z.cube.cxf.IUserManager client=WebServiceUtils.getServiceClazzInstance(z.cube.cxf.IUserManager.class, address);
        assertEqList(serverUsers, client.queryUsers());

        Object[] result=WebServiceUtils.dynamicInvoke(address,"queryUsers");
        List<z.cube.cxf.User> clientUsers= (List<z.cube.cxf.User>) result[0];
        assertEqList(serverUsers, clientUsers);


        server.stop();
    }

    private z.cube.cxf.UserManager buildUserManager() {
        z.cube.cxf.UserManager manager = new z.cube.cxf.UserManager();
        manager.addUser("Zhangsan");
        manager.addUser("Lisi");
        manager.addUser("Wangwu");
        return manager;
    }

    @Test
    public final void testJdkPublish() throws Exception {
        UserManager manager = buildUserManager();
        List<z.cube.cxf.User> serverUsers = manager.queryUsers();
        String address = "http://127.0.0.1:8080/";
        Endpoint server=WebServiceUtils.publish(address, manager);
        assertTrue(server.isPublished());
        z.cube.cxf.IUserManager client=WebServiceUtils.getServiceClazzInstance(IUserManager.class, address);
        List<z.cube.cxf.User> clientUser = client.queryUsers();
        assertEqList(serverUsers, clientUser);

        Object[] result=WebServiceUtils.dynamicInvoke(address,"queryUsers");
        List<z.cube.cxf.User> clientUsers= (List<z.cube.cxf.User>) result[0];
        assertEqList(serverUsers, clientUsers);

        server.stop();
    }

    private void assertEqList(List<z.cube.cxf.User> serverUsers, List<User> clientUser) {
        assertEquals(serverUsers.size(),clientUser.size());
        for (int i = 0; i < serverUsers.size(); i++) {
            assertEquals(serverUsers.get(i),clientUser.get(i));
        }
    }
}
