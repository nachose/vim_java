package com.jiss.app;


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
    },
    STOPPED {
        @Override
        public String getModeStr() { return "STOPPED";}
    };

    public abstract String getModeStr();
}
