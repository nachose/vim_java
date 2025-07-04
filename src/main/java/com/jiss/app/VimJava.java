package com.jiss.app;


import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TerminalPosition;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.jiss.app.display.ScreenRegion;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.jiss.app.display.Region;



import com.jiss.app.display.ScreenRegionFactory;
import com.jiss.app.display.WindowContext;
import com.jiss.app.input.KeyHandler;
import com.jiss.app.input.LoopStatus;
import com.jiss.app.util.FpsCounter;


public class VimJava {

    private KeyHandler handler_ = null;
    private List<ScreenRegion> regions_ = null;
    private Screen screen_ = null;
    private ArrayList<String> buffer_ = null;

    public VimJava (Screen screen) {
        this.screen_ = screen;
        Region screenRegion = new Region("screen",
                                         0,
                                         0,
                                         screen.getTerminalSize().getColumns(),
                                         screen.getTerminalSize().getRows());
        regions_ = ScreenRegionFactory.createRegions(screenRegion);

        buffer_ = new ArrayList<String>();
        handler_ = new KeyHandler(buffer_);

        //Continue removing buffer instances and pushing it into handler_


    }


    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext("com.jiss.app");
        try(Screen screen = new DefaultTerminalFactory().createScreen()){
            VimJava vj = new VimJava(screen);

            screen.startScreen();
            ScreenStatus screenStatus = new ScreenStatus(screen);
            boolean running = true;
            StringBuilder commandBuffer = new StringBuilder();
            FpsCounter counter = new FpsCounter();
            EditorMode mode = EditorMode.NORMAL;

            while (running) {
                vj.clearScreen();

                WindowContext context = vj.createWindowContext(
                        mode,
                        screenStatus.getPosition(),
                        commandBuffer,
                        counter.getFps());
                vj.drawRegions(screen, context);
                vj.drawCursor(screenStatus);

                // Example usage in main loop
                counter.increment();



                vj.refreshScreen();

                LoopStatus status = vj.handleTextInput(screenStatus, commandBuffer);
                running = status.mode() != EditorMode.STOPPED;
                screenStatus.setPosition(status.pos());
                mode = status.mode();
            }
            screen.stopScreen();
        }
        appContext.close();
    }

    private void drawCursor(ScreenStatus screenStatus) {

        screenStatus.updatePostion();
    }

    private void clearScreen() throws IOException {
        screen_.clear();
    }

    private void refreshScreen() throws IOException {
        screen_.refresh(Screen.RefreshType.AUTOMATIC);
    }

    private LoopStatus handleTextInput(ScreenStatus screen,
                                       StringBuilder commandBuffer) throws IOException {

        return handler_.handleTextInput(screen, commandBuffer);

    }

    private WindowContext createWindowContext(EditorMode mode,
                                              TerminalPosition position,
                                              StringBuilder commandBuffer,
                                              int fps) {
        return new WindowContext(screen_,
                                 mode,
                                 buffer_,
                                 commandBuffer,
                                 position,
                                 fps);
    }

    private void drawRegions(Screen screen, WindowContext context) {
        for (ScreenRegion region : regions_) {
            region.draw(context);
        }

    }




    private void drawDebuggingErrorMessage(Screen screen, String message) throws InterruptedException {
        drawMessage(screen, message);
        Thread.sleep(3000);
        drawMessage(screen, "");
    }

    // Draws a message in the command line region (bottom line)
    private void drawMessage(Screen screen, String message) {
        int height = screen.getTerminalSize().getRows();
        int width = screen.getTerminalSize().getColumns();
        for (int col = 0; col < width; col++) {
            char ch = col < message.length() ? message.charAt(col) : ' ';
            screen.setCharacter(col, height - 1, TextCharacter.fromCharacter(
                    ch, TextColor.ANSI.WHITE, TextColor.ANSI.RED)[0]);
        }
    }
}
