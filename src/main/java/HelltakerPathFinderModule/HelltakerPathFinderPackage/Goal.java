
package HelltakerPathFinderPackage;
   
record
   Goal
      ()
      implements
         InteractiveCell
{

   public Goal toggleSpikes()
   {
   
      return this;
   
   }
   
   @Override
   public String toString()
   {
   
      return "G";
   
   }

}
