package draft.controller.logger;

import draft.core.Logger;

public class LoggerFactory {
    public Logger createLogger(LoggerType type) {
        return switch (type) {
            case FILE -> new FileLogger();
            case CONSOLE -> new ConsoleLogger();
            default -> null;
        };
    }
}
