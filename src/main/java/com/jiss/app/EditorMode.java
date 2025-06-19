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
    COMMAND {
        @Override
        public String getModeStr() {
            return "COMMAND";
        }
    };

    public abstract String getModeStr();
}
