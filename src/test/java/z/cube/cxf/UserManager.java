package z.cube.cxf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
  
public class UserManager implements IUserManager {  
    Map<Integer, User> userMap = new HashMap<Integer, User>();  
    int newID = 0;  
    @Override  
    public int addUser(String name) {  
          
        int newId = this.newID++;  
        System.out.println("add a user begin,userId:"+newId + ",name:"+name);  
        User user = new User();  
        user.setId(newId);  
        user.setName(name);  
        userMap.put(newId, user);  
        System.out.println("add a user ok");  
        return newId;  
    }  
  
    @Override  
    public List<User> queryUsers() {  
        System.out.println("query all users begin");  
        List<User> userList = new ArrayList<User>();  
        for(User user:userMap.values()){  
            userList.add(user);  
        }  
        System.out.println("query all users end,count:"+userList.size());  
        return userList;  
    }  
  
    @Override  
    public User queryUser(int userId) {  
        System.out.println("query a user begin, userId:"+userId);  
        if(userMap.containsKey(userId)){  
            User user = userMap.get(userId);  
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