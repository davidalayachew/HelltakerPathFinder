
package HelltakerPathFinderPackage;
   
import java.util.Objects;

public record Unchanged (Cell first, Cell second, Cell third) 
   implements Triple
{

   public Unchanged
   {
   
      Objects.requireNonNull(first);
      Objects.requireNonNull(second);
      Objects.requireNonNull(third);
   
   }
   
   public Unchanged(CellPair pair, Cell third)
   {
   
      this(Objects.requireNonNull(pair).first(), pair.second(), third);
   
   }

   public Unchanged(Cell first, CellPair pair)
   {
   
      this(first, Objects.requireNonNull(pair).first(), pair.second());
   
   }

}
