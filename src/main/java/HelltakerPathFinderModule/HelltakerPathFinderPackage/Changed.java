
package HelltakerPathFinderPackage;
   
import java.util.Objects;

public record Changed (Cell first, Cell second, Cell third) 
   implements Triple
{

   public Changed
   {
   
      Objects.requireNonNull(first);
      Objects.requireNonNull(second);
      Objects.requireNonNull(third);
   
   }
   
   public Changed(CellPair pair, Cell third)
   {
   
      this(Objects.requireNonNull(pair).first(), pair.second(), third);
   
   }

   public Changed(Cell first, CellPair pair)
   {
   
      this(first, Objects.requireNonNull(pair).first(), pair.second());
   
   }

}
