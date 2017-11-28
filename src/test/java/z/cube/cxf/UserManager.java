package java.z.cube.cxf;

import z.cube.cxf.IUserManager;
import z.cube.cxf.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
  
public class UserManager implements IUserManager {
    Map<Integer, z.cube.cxf.User> userMap = new HashMap<Integer, z.cube.cxf.User>();
    int newID = 0;  
    @Override  
    public int addUser(String name) {  
          
        int newId = this.newID++;  
        System.out.println("add a user begin,userId:"+newId + ",name:"+name);  
        z.cube.cxf.User user = new z.cube.cxf.User();
        user.setId(newId);  
        user.setName(name);  
        userMap.put(newId, user);  
        System.out.println("add a user ok");  
        return newId;  
    }  
  
    @Override  
    public List<z.cube.cxf.User> queryUsers() {
        System.out.println("query all users begin");  
        List<z.cube.cxf.User> userList = new ArrayList<z.cube.cxf.User>();
        for(z.cube.cxf.User user:userMap.values()){
            userList.add(user);  
        }  
        System.out.println("query all users end,count:"+userList.size());  
        return userList;  
    }  
  
    @Override  
    public z.cube.cxf.User queryUser(int userId) {
        System.out.println("query a user begin, userId:"+userId);  
        if(userMap.containsKey(userId)){  
            z.cube.cxf.User user = userMap.get(userId);
            System.out.println("query a user begin, userId:"+userId+", name:"+user.getName());  
            return user;  
        }  
        System.out.println("query a user end,no the user("+userId+")");  
        return null;  
    }  
  
    @Override  
    public boolean deleteUser(int userId) {  
        System.out.println("delete a user begin, userId:"+userId);  
        if(userMap.containsKey(userId)){  
            User user = userMap.remove(userId);
            System.out.println("delete a user end, userId:"+userId+", name:"+user.getName());  
        }  
        return true;  
    }  
}  