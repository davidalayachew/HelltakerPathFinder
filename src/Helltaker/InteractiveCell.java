package Helltaker;

sealed
   interface
      InteractiveCell
         extends
            Cell
         permits
            Goal,
            Lock,
            BasicCell
{}
