
package HelltakerPathFinderPackage;
   
import java.util.Objects;

sealed
   interface
      Cell
         permits
				Player,
				NonPlayer
{

   Cell toggleSpikes();

}
