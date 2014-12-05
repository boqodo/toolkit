package z.cube.cxf;

import java.util.List;

import javax.jws.WebService;

@WebService  
public interface IUserManager {  
    int addUser(String name);  
    List<User> queryUsers();  
    User queryUser(int userId);  
    boolean deleteUser(int userId);  
}  