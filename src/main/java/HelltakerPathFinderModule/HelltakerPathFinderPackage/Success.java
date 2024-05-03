
package HelltakerPathFinderPackage;
   
import java.util.Objects;

public record Success(DirectionChain chain, int numberOfStates) implements Result
{

   public Success
   {
   
      Objects.requireNonNull(chain);
   
   }
   
   public Success(Success success, int numberOfStates)
   {
   
      this(success.chain(), numberOfStates);
   
   }

   public Success(DirectionChain chain)
   {
   
      this(chain, 0);
   
   }

   public String toString()
   {
   
      return 
         "Success\tNumber of states = " 
         + this.numberOfStates 
         + "\t chain =  " 
         + this.chain
         ;
   
   }

}
