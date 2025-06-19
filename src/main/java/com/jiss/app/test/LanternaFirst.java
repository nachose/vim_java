package com.jiss.app.test;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

public class LanternaFirst
{
    public LanternaFirst()
    {}

    public void testWindow() throws IOException, InterruptedException
    {
      Terminal terminal = new DefaultTerminalFactory().createTerminal();
      Screen screen = new TerminalScreen(terminal);
      screen.startScreen();

      Label label = new Label("test");

      BasicWindow window = new BasicWindow();
      window.setComponent(label);

      MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
          new EmptySpace(TextColor.ANSI.BLACK));
      gui.setTheme(LanternaThemes.getRegisteredTheme("businessmachine"));
      gui.addWindowAndWait(window);

  }
}


