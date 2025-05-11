
package HelltakerPathFinderPackage;
   
sealed
   interface
      NonPlayerOccupant
         permits
            NoOccupant,
            Block,
				Enemy
{}
