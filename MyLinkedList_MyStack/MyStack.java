import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MyStack<AnyType> 
    {
    ArrayList<AnyType> list = new ArrayList<>(); 
    int getSize() 
        {
        return list.size();
        }
    void push( AnyType element) 
        {
        list.add(element);
        }
    public AnyType pop()  
        {
        return list.remove(list.size()-1);
        }
    @Override
    public String toString()     
        {  
        StringBuilder sb = new StringBuilder( "MyStack : " );
        for (AnyType x : list) 
            {
            sb.append(x + " ");
            }
        return new String( sb );
        }
        
    public AnyType top()
      {
      int size = list.size();
      if(size == 0)
            return null;
      else
          return list.get(size-1); 
      }
    
    public static void main(String[] args) throws NoSuchElementException
        {
        MyStack<Character> st = new MyStack<>() ;
        String format = "%-20s%s%n";
        
         String s = "[({}{})]"; 
        
        for(int i = 0; i < s.length(); i++) 
            {
            char element = s.charAt(i);
            if(element == '[' || element == '(' || element == '{' ) 
                {
                st.push(element);
                }
            else if(element == ']') 
                {
                if( st.getSize() == 0)   
                    {
                    System.out.println("No element inside stack.");     
                    break;
                    }     
                if(st.pop() != '[')  
                    {
                    System.out.println("NOT BALANCED.");
                    break;
                    }
                }
            else if(element == '}') 
                {
                if( st.getSize() == 0)
                    {
                    System.out.println("No element inside stack.");
                    break;
                    }     
                if(st.pop() != '{') 
                    {
                    System.out.println("NOT BALANCED."); 
                    break;
                    }
                }
            else if(element == ')') 
                {
                if( st.getSize() == 0)
                    {
                    System.out.println("No element inside stack."); 
                    break;
                    }     
                if(st.pop() != '(') 
                    {
                    System.out.println("NOT BALANCED.");
                    break;
                    }
                }
            else
                {
                throw new NoSuchElementException(element+" character is invalid."); 
                }
            System.out.printf(format,st,"Top of Stack: "+st.top());
            if(st.getSize() == 0)  
                {
                System.out.println("BALANCED");
                }
            }
        } 
    }