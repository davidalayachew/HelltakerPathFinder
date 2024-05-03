
package HelltakerPathFinderPackage;
   
record
   Wall
      ()
      implements
         Cell
{

   public Wall toggleSpikes()
   {
   
      return this;
   
   }
   
   @Override
   public String toString()
   {
   
      return "W";
   
   }

}
