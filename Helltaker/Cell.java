package Helltaker;

import java.util.Objects;

sealed
   interface
      Cell
         permits
            InteractiveCell,
            Player,
            Wall
{

   Cell toggleSpikes();

}
