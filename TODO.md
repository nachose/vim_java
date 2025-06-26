# TODO


## NEAR FUTURE
- [ ] Change the mode indicator, which is in the first line, to the last one, displaying the full mode instead
of just the first letter. Implement the two lines in the bottom: a status line and a command line.
- [ ] Investigate of the options for not having each character use an object. Either use something for each line or
for each paragraph, or maybe use a flyweight pattern.
- [ ] Continue with the rest of the features copilot suggested:
 (file I/O, more commands, multi-line support, etc.)
- [ ] Now the regions in display package should implement Region, not ScreenRegion
- [ ] Buffer should be its own abstraction, that is, create the Buffer class, and give it the
      appropiate functionality.
- [ ] I want to have regions inside the buffer (numbers on the left, signs on the left, bufferline,
      etc), so for that region should probably be a composite pattern.
- [ ] Related with regions be a composite pattern, I need to change LayoutManager to accept
      restrictions. That is, the numbers pane should only be as wide as neccesary. The bufferline
      should just be one line high. So need to change regions and LayoutManager

## ARCHIVED
- [:x:] Change from vim keybindings to helix keybindings. --> Won't do.
- [:white_check_mark:] Display in full screen, instead of a window.
- [:white_check_mark:] Change the mode indicator, which is in the first line, to the last one, displaying the full mode instead
- [:white_check_mark:] Get rid of LoopStatus, and comeback to returning just a boolean. It is not needed, we will keep track
of the state (mode) in KeyHandler.

## FAR FUTURE
- [ ] Support the two input modes, vim and helix. Create two directories: input_vim and input_helix,
      with different hadlers, then make it configurable, and be read from configuration file.
- [ ] Support multiple buffers, and be able to switch between them.
- [ ] Support multiple windows, and be able to switch between them.
- [ ] Once the buffer is implemented, support multiple regions inside the buffer.
- [ ] Once the buffer is implemented, consider switching to a more performant data structure, like a rope.
- [ ] Once the buffer is implemented, make it thread-safe, so I can to display it while still reading.
- [ ] Consider loadiging a big file in chunks, like Minecraft, or other video games do for the terrain.
