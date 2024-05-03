
package HelltakerPathFinderPackage;
   
sealed
   interface
      NonPlayerOccupant
         permits
            NoOccupant,
            OneOccupant
{}
