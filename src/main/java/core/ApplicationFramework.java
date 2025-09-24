package draft.core;

import draft.controller.logger.LoggerFactory;
import draft.controller.logger.LoggerType;
import draft.controller.message.MessageGeneratorImplementation;
import draft.controller.serializer.Serializer;
import draft.controller.serializer.implementation.JacksonSerializer;
import draft.view.SwingGui;
import draft.view.swing.MainFrame;
import draft.model.draftRepository.DraftRepositoryImplementation;

public class ApplicationFramework {
    private static ApplicationFramework instance;
    private static MessageGenerator messageGenerator;
    private static Logger consoleLogger;
    private static Logger fileLogger;
    private static Gui gui;
    private static DraftRepository draftRepository;
    private static Serializer serializer;

    private ApplicationFramework(){}

    private static void initialize(){
        gui = new SwingGui();
        LoggerFactory logFactory = new LoggerFactory();
        messageGenerator = new MessageGeneratorImplementation();
        consoleLogger = logFactory.createLogger(LoggerType.CONSOLE);
        fileLogger = logFactory.createLogger(LoggerType.FILE);
        draftRepository = new DraftRepositoryImplementation();
        serializer = new JacksonSerializer();

        messageGenerator.addSubscriber(consoleLogger);
        messageGenerator.addSubscriber(fileLogger);
        messageGenerator.addSubscriber(MainFrame.getInstance());
        gui.start();
    }

    public static ApplicationFramework getInstance(){
        if (instance == null) {
            instance = new ApplicationFramework();
            initialize();
        }
        return instance;
    }

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public Logger getConsoleLogger() {
        return consoleLogger;
    }

    public Logger getFileLogger() {
        return fileLogger;
    }

    public Gui getGui() {
        return gui;
    }

    public Serializer getSerializer() { return serializer;}

    public DraftRepository getDraftRepository() {return draftRepository;}
}
