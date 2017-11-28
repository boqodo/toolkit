package java.z.cube.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import z.cube.cxf.User;
import z.cube.utils.JAXBUtils;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JAXBUtilsTest {



    @Test
    public void testParseXml() throws Exception {

        InputStream in=JAXBUtilsTest.class.getClassLoader().getResourceAsStream("jaxb/users.xml");
        User user= JAXBUtils.parseXml(User.class,in);
        assertNotNull(user);
        assertEquals(20,user.getId());
        assertEquals("张三",user.getName());
        IOUtils.closeQuietly(in);

        InputStream in2= FileUtils.openInputStream(ResourceUtils.getFile("classpath:jaxb/users.xml")) ;

        byte[] bytes=new byte[in2.available()];
        in2.read(bytes);
        IOUtils.closeQuietly(in2);

        User user1=JAXBUtils.parseXml(User.class,new String(bytes,"UTF-8"));

        assertEquals(20,user1.getId());
        assertEquals("张三",user1.getName());
    }

    @Test
    public void testGenerateForamtXml() throws Exception {
        User user = getUser();
        System.out.println(JAXBUtils.generateFormatXml(user));
    }

    private User getUser() {
        User user=new User();
        user.setName("Zhangsan");
        user.setId(20);
        return user;
    }

    @Test
    public void testGenerateXml() throws Exception {
        User user = getUser();
        System.out.println(JAXBUtils.generateXml(user));
    }

    @Test
    public void testGenerateXml1() throws Exception {
        JAXBUtils.generateXml(getUser(),false);
        JAXBUtils.generateXml(getUser(),true);
    }

    @Test
    public void testGenerateXmlEx() throws Exception {
        JAXBUtils.generateXmlEx(getUser());
    }
}