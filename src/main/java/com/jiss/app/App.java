package com.jiss.app;

import com.jiss.app.LanternaFirst;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
      System.out.println( "Hello World!" );
      LanternaFirst myApp = new LanternaFirst();

      try
      {
        myApp.testWindow();
      }
      catch(IOException e)
      {
      }
      catch(InterruptedException e)
      {
      }
    }

}
