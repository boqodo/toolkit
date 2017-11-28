package java.z.cube.cxf;

import z.cube.cxf.User;

import java.util.List;

import javax.jws.WebService;

@WebService  
public interface IUserManager {  
    int addUser(String name);  
    List<z.cube.cxf.User> queryUsers();
    User queryUser(int userId);
    boolean deleteUser(int userId);  
}  