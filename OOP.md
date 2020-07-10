Object Oriented Programming Concept Questions

As you should know by now, there are 4 pillars of Object Oriented Programming.

Please write 1-3 paragraphs explaining these 4 concepts further.  Please provide a sufficient enough explanation about these pillars, as well as some examples to illustrate the practical use cases of these principles.  

********************
1. Encapsulation
Encapsulation in Java is a process of wrapping code and data together into a single unit, for example, a capsule which is mixed of several medicines.

One way to create a fully encapsulated class in Java is to set all the data members of the class as private. Now, we can use setter and getter methods to set and get the data in it. 

Encapsulation provides you with control over the data. Suppose you want to set the value of id which should be greater than 100 only, you can write the logic inside the setter method. You can write the logic not to store the negative numbers in the setter methods.

It is also a way to achieve data hiding in Java because other classes will not be able to access the data through the private data members.

The following is an example of an encapsulated class:

public class Student{  
//private data member  
private String name;  
//getter method for name  
public String getName(){  
return name;  
}  
//setter method for name  
public void setName(String name){  
this.name=name  
}  
}

//A Java class to test the encapsulated class.  
class Test{  
public static void main(String[] args){  
//creating instance of the encapsulated class  
Student s=new Student();  
//setting value in the name member  
s.setName("Matthew");  
//getting value of the name member  
System.out.println(s.getName());  
}  
}

More resources on Encapsulation:
https://www.geeksforgeeks.org/encapsulation-in-java/
https://beginnersbook.com/2013/05/encapsulation-in-java/


********************
2. Inheritance

Inheritance in Java is a mechanism in which one object acquires all the properties and behaviors of a parent object. It is an important part of Object Oriented programming.

The idea behind inheritance in Java is that you can create new classes that are built upon existing classes. When you inherit from an existing class, you can reuse methods and fields of the parent class. Moreover, you can add new methods and fields in your current class also.

Inheritance represents the IS-A relationship which is also known as a parent-child relationship.

Advantages of inheritane include: Method Overriding (so runtime polymorphism can be achieved) and Code Reusability.

The extends keyword indicates that you are making a new class that derives from an existing class. The meaning of "extends" is to increase the functionality. 

The Subclass-name extends Superclass-name as shown in the following example:

{  
   //methods and fields added here 
}

The relationship between the two classes is Programmer IS-A Employee. It means that Programmer is a type of Employee. 

class Employee{  
 float salary=40000;  
}  
class Programmer extends Employee{  
 int bonus=10000;  
 public static void main(String args[]){  
   Programmer p=new Programmer();  
   System.out.println("Programmer salary is:"+p.salary);  
   System.out.println("Bonus of Programmer is:"+p.bonus);  
}  
} 

More resources on Inheritance:
https://www.geeksforgeeks.org/inheritance-in-java/
https://beginnersbook.com/2013/03/inheritance-in-java/
https://www.programiz.com/java-programming/inheritance


********************
3. Abstraction

Abstraction is a process of hiding the implementation details and showing only functionality to the user.
Another way, it shows only essential things to the user and hides the internal details, for example, sending SMS where you type the text and send the message. You don't know the internal processing about the message delivery.
Abstraction lets you focus on what the object does instead of how it does it. 

Generalization is the process of extracting shared characteristics from two or more classes, and combining them into a generalized superclass. Shared characteristics can be attributes, associations, or methods. 

Specialization means creating new subclasses from an existing class. If it turns out that certain attributes, associations, or methods only apply to some of the objects of the class, a subclass can be created. 

To achieve Abstraction in java, use Abstract classes (0 to 100% abstraction) or Interfaces (100% abstraction).

Rules for abstract classes:
An abstract class must be declared with an abstract keyword.
It can have abstract and non-abstract methods.
It cannot be instantiated.
It can have constructors and static methods also.
It can have final methods which will force the subclass not to change the body of the method.

A method which is declared as abstract and does not have implementation is known as an abstract method. 

abstract void printStatus();  //no method body and abstract  

Example of Abstract Class with an Abstract Method
abstract class Bike{  // Abstract class 
  abstract void run();  // Abstract method
}  
class Honda4 extends Bike{  
void run(){System.out.println("running safely");}  
public static void main(String args[]){  
 Bike obj = new Honda4();  
 obj.run();  
}  
} 

More resources on Abstraction:
https://www.geeksforgeeks.org/abstraction-in-java-2/
https://www.youtube.com/watch?v=52frlN8webg
https://www.tutorialspoint.com/java/java_abstraction.htm

********************
4. Polymorphism
   
Polymorphism is the ability of an object to take on many forms. The most common use of polymorphism in OOP occurs when a parent class reference is used to refer to a child class object.

Any Java object that can pass more than one IS-A test is considered to be polymorphic. In Java, all Java objects are polymorphic since any object will pass the IS-A test for their own type and for the class Object.

If a class has multiple methods having same name but different in parameters, it is known as Method Overloading. 
If we have to perform only one operation, having same name of the methods increases the readability of the program. 

Suppose we have to perform addition of the given numbers but there can be any number of arguments, if you write the method such as a(int,int) for two parameters, and b(int,int,int) for three parameters then it may be difficult for you as well as other programmers to understand the behavior of the method because its name differs. 

There are two ways to overload the method in java:
By changing the number of arguments or by changing the data type.

Method Overloading example: Changing the number of arguments
class Adder{  
static int add(int a,int b){return a+b;}  // 2 arguments
static int add(int a,int b,int c){return a+b+c;}  //3 arguments
}  
class TestOverloading1{  
public static void main(String[] args){  
System.out.println(Adder.add(11,11));  
System.out.println(Adder.add(11,11,11));  
}} 

In the above example, we have created two methods, first add() method performs addition of two numbers and second add method performs addition of three numbers.
In the above example, we are creating static methods so that we don't need to create instance for calling methods.

If subclass (child class) has the same method as declared in the parent class, it is known as method overriding in Java. In other words, If a subclass provides the specific implementation of the method that has been declared by one of its parent class, it is known as method overriding.

Method overriding is used to provide the specific implementation of a method which is already provided by its superclass. Method overriding is used for runtime polymorphism.

Rules for method overriding:
The method must have the same name as in the parent class
The method must have the same parameter as in the parent class.
There must be an IS-A relationship (inheritance).

Example for Method Overriding 
class ABC{
   //Overridden method
   public void disp()
   {
     System.out.println("disp() method of parent class");
   }     
}
class Demo extends ABC{
   //Overriding method
   public void disp(){
     System.out.println("disp() method of Child class");
   }
   public void newMethod(){
      System.out.println("new method of child class");
   }
   public static void main( String args[]) {
 
  ABC obj = new ABC();
  obj.disp();
  ABC obj2 = new Demo(); // Covariance with reference types
  obj2.disp();
   }
}
In the above example, we have defined the disp() method in the subclass as defined in the parent class but it has some specific implementation. The name and parameter list of the methods are the same, and there is IS-A relationship between the classes.

More resources on Polymorphism:
https://www.geeksforgeeks.org/polymorphism-in-java/
https://www.youtube.com/watch?v=SwEzCBM7n-Q
https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html



