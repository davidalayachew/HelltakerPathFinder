
package HelltakerPathFinderPackage;
   
sealed
   interface
      InteractiveCell
         extends
            NonPlayer
         permits
            Goal,
            Lock,
            BasicCell
{}
