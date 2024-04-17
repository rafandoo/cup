package br.dev.rplus.log;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;

public abstract class LoggerFactory {
    private static Logger logger;
    private static String id;
    private static boolean toFile;
    private static final String DEFAULT_LEVEL = "ALL";


    public static void init() {
        init("br.dev.rplus", false, DEFAULT_LEVEL);
    }


    public static void init(String id) {
        init(id, false, DEFAULT_LEVEL);
    }

    public static void init(String id, boolean toFile) {
        init(id, toFile, DEFAULT_LEVEL);
    }


    public static void init(String id, String level) {
        init(id, false, level);
    }


    public static void init(String id, boolean toFile, String level) {
        try {
            if (logger == null) {
                setId(id);
                logger = Logger.getLogger(id);
                logFile(toFile);
                setLevel(level);
            }
        } catch (Exception e) {
            Logger.getLogger(LoggerFactory.class.getName()).log(Level.SEVERE, "Error logging.", e);
        }

    }

    public static Logger getLogger() {
        if (logger == null) {
            init();
        }
        return logger;
    }

    public static void setId(String id) {
        LoggerFactory.id = id;
    }

    public static String getId() {
        return id;
    }

    public static void setLevel(Level level) {
        getLogger().setLevel(level);
        try {
            Logger.getGlobal().getParent().getHandlers()[0].setLevel(level);
        } catch (Exception e) {
            for (Handler h : getLogger().getHandlers()) {
                h.setLevel(level);
            }
        }
    }

    public static void setLevel(String level) {
        setLevel(levelParser(level));
    }

    private static Level levelParser(String level) {
        try {
            return Level.parse(level);
        } catch (IllegalArgumentException e) {
            return Level.parse(DEFAULT_LEVEL);
        }
    }

    private static String getFile() {
        File file;
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            String path = processMessage("%s-%s-%s_%s.log", dft.format(LocalDateTime.now()), getId());
            file = new File(path);
            if (!file.getParentFile().exists()) {
                boolean created = file.getParentFile().mkdirs();
                if (!created) {
                    throw new Exception();
                }
            }
            return file.getAbsolutePath();
        } catch (Exception e) {
            return "cup.log";
        }
    }

    public static void logFile(boolean toFile) {
        if (toFile) {
            FileHandler fh;
            try {
                fh = new FileHandler(getFile(), true);
                fh.setFormatter(new SimpleFormatter());
                getLogger().addHandler(fh);
            } catch (Exception e) {
                error("Error creating log file. Error:<br>%s", e);
            }
        }
    }

    private static String processMessage(String message, Object... params) {
        try {
            String rMessage = message.replaceAll("<br>", System.lineSeparator());
            if (params.length > 0) {
                return String.format(rMessage, params);
            }
            return rMessage;
        } catch (Exception e) {
            return message;
        }
    }

    private static void log(Level level, String message) {
        try {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            getLogger().logp(level, ste.getClassName(), (ste.getMethodName() + " - " + ste.getLineNumber()), message);
        } catch (NullPointerException npe) {
            getLogger().log(level, message);
        } catch (Exception e) {
            Logger.getLogger(LoggerFactory.class.getName()).log(Level.SEVERE, "Error logging.", e);
        }
    }

    public static void info(String message, Object... params) {
        log(Level.INFO, processMessage(message, params));
    }

    public static void warn(String message, Object... params) {
        log(Level.WARNING, processMessage(message, params));
    }

    public static void error(String message, Object... params) {
        log(Level.SEVERE, processMessage(message, params));
    }

    public static void debug(String message, Object... params) {
        log(Level.FINE, processMessage(message, params));
    }

    public static void trace(String message, Object... params) {
        log(Level.FINER, processMessage(message, params));
    }

    public static void config(String message, Object... params) {
        log(Level.CONFIG, processMessage(message, params));
    }
}
