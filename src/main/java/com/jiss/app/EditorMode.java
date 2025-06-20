package com.jiss.app;

import java.awt.image.VolatileImage;
import java.beans.Visibility;
import java.awt.image.VolatileImage;

public enum EditorMode {
    NORMAL{
        @Override
        public String getModeStr() {
            return "NORMAL";
        }
    },
    INSERT {
        @Override
        public String getModeStr() {
            return "INSERT";
        }
    },
    VISUAL {
        @Override
        public String getModeStr() {
            return "VISUAL";
        }
    },
    VISUALLINE {
        @Override
        public String getModeStr() {
            return "V-LINE";
        }
    },
    VISUALBLOCK {
        @Override
        public String getModeStr() {
            return "VISUAL-BLOCK";
        }
    },
    COMMAND {
        @Override
        public String getModeStr() {
            return "COMMAND";
        }
    };

    public abstract String getModeStr();
}
