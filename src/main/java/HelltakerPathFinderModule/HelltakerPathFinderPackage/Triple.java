
package HelltakerPathFinderPackage;
   
import java.util.Objects;

public sealed interface Triple permits Changed, Unchanged
{

   Cell first();
   Cell second();
   Cell third();

}
