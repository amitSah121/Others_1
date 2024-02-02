package dragonquest.test;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.Serializable;

public class DragonQuestTest implements Serializable{
    @SuppressWarnings("compatibility:-1143413300788980265")
    private static final long serialVersionUID = 8552568115790455990L;

  
  
  public void equals(String text_test,String text){
      assertEquals(text_test+" doesnot equal "+text,text_test,text);
  }
  
  public void equals(int num_test,int num){
      assertFalse(Integer.toString(num_test)+" doesnot equal "+Integer.toString(num),num_test==num);
  }
  
  
  public void greater_than(int num_test,int num){
      assertFalse(Integer.toString(num_test)+" is not less than "+Integer.toString(num),num_test>num);
  }
  
  public void less_than(int num_test,int num){
      assertFalse(Integer.toString(num_test)+" is not greater than "+Integer.toString(num),num_test<num);
  }
  
  public void not_null(Object o){
      assertNotNull(o.toString() + " is null",o);
  }
  
  public void string_lies_in_array(String[] text_test,String text){
      boolean b = false;
      for(int i=0 ; i<text_test.length ; i++){
          if(text_test[i].equals(text)){
              b = true;
              break;
          }
      }
      String f = "";
      for(int i=0 ; i<text_test.length ; i++){
          f += text_test[i]+" , ";
      }
      
      assertFalse(text+" doesnot belong to given array "+f,!b);
  }
}