package java.filecheck;
class A {
   public static void show() {
      System.out.println("Class A show() function");
   }
}

class B extends A {
   public static void show() {
      System.out.println("Class B show() function");
   }
}

public class ClassDemo {

   public static void main(String[] args) {
        
     ClassDemo cls = new ClassDemo();
     Class c = cls.getClass();      
     System.out.println(c);  
   
     Object obj = new A();        
     B b1 = new B();
     b1.show();
        
     // casts object
     A a = A.class.cast(b1);
     a.show();
   
     System.out.println(obj.getClass());
     System.out.println(b1.getClass());
     System.out.println(a.getClass());               
   }
} 