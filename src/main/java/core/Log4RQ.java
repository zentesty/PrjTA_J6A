package core;


/**
 * Thread safe logging object
 * Log4DP is the log facade expose to the application. The implementation object will receive all the log entries,
 * regardless of the log purposes and based on settings will be outputting the entries to the proper medium (console,
 * reporter, HTML output, ...)
 * The facade will allow for the usage of libraries such as Log4J. This will open the door to using adapters
 * to aggregate logs for data intelligence capabilities
 */
public class Log4RQ {

    // Console output attributes -- Note that to avoid redondant entries
    // bReporterTestNG will superseed the bOutputToConsole entry
    private boolean bOutputToConsole = true;

    // TestNG output attributes
    private boolean bReporterTestNG = true;
    private String  szVisualPrefixReporter = "";


    private String  szLogInAllEntry = "";
    private String  szVisualPrefixConsole = "";

    private boolean bLogInfo = true;
    private boolean bLogPass = true;
    private boolean bLogFail = true;
    private boolean bLogError = true;
    private boolean bLogWarn = true;
    private boolean bLogDebug = true;
    private boolean bLogHeader = true;
    private boolean bLogRunning = true;
    private boolean bLogMassive = false;
    private boolean bLogException = false;

    // File log related attributed
    private String  szFileName = "";
    private String  szLogPath = "";
    private boolean bLogToFile = false;

    private static long     totalNumberSuiteException = 0;
    private long            totalNumberTestException = 0;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    /**
     * This local static variable store the thread singleton of the log object
     * the created object is obtainable through the getionstance method. Isolation
     * will be created for the user of this implementation
     */
    private static ThreadLocal<Log4RQ> _threadLocal =
            new ThreadLocal<Log4RQ>() {
                @Override
                protected Log4RQ initialValue() {
                    return new Log4RQ();
                }
            };

    /**
     * Returns the thread local singleton instance
     *
     * @return
     */
    private static Log4RQ getInstance() {
        return _threadLocal.get();
    }

    /**
     * Enumeration of LogLevel (INFO, PASS, FAIL, ERROR, WARN, DEBUG, HEADER) use to specify the log level.
     */
    public enum LogLevel {
        INFO, PASS, FAIL, ERROR, WARN, DEBUG, HEADER, RUNNING, MASSIVE, EXCEPTION
    }

    public static void log(String szEntry, LogLevel level){
        Log4RQ.getInstance()._log(szEntry, level);
    }

    public static void logError(String szEntry)
    {
        Log4RQ.getInstance()._log(szEntry, LogLevel.ERROR);
    }

    public static void logDebug(String szEntry)
    {
        Log4RQ.getInstance()._log(szEntry, LogLevel.DEBUG);
    }

    public static void logWarn(String szEntry)
    {
        Log4RQ.getInstance()._log(szEntry, LogLevel.WARN);
    }

    public static void logInfo(String szEntry)
    {
        Log4RQ.getInstance()._log(szEntry, LogLevel.INFO);
    }

    public static void logHeader(String szEntry)
    {
        Log4RQ.getInstance()._log(szEntry, LogLevel.HEADER);
    }

    public static void logMassive(String szEntry)
    {
        int idxTrace = 3;
        String strObject = Thread.currentThread().getStackTrace()[idxTrace].getClassName();
        String strMethod = Thread.currentThread().getStackTrace()[idxTrace].getMethodName();

        Log4RQ.getInstance()._log(szEntry + " ... " + strObject + ":" + strMethod, LogLevel.MASSIVE);
    }

    public static void logException(Exception e, String szEntry)
    {
        // Increment the number of test and suite exception
        Log4RQ.getInstance().incrementNumberOfTestException();
        // retrun if the level is not logged
        if(!Log4RQ.getInstance().isLevelLogged(LogLevel.EXCEPTION)) return;

        int idxTrace = 3;

        String strObject = Thread.currentThread().getStackTrace()[idxTrace].getClassName();
        String strMethod = Thread.currentThread().getStackTrace()[idxTrace].getMethodName();

        // Invoke the logger
        Log4RQ.getInstance()._log(szEntry, LogLevel.EXCEPTION);

        System.out.println(ANSI_PURPLE + "Exception: " + strObject +
                " ... " + strMethod + ANSI_CYAN + " \n" + e.getMessage()  + ANSI_BLACK);
    }


    /**
     * Reset the test counter and send the previous value. This is only to be called when a new
     * test is starting since it might be using an existing thread the constructor is would
     * not measure the real life span we want to measure
     */
    public static void resetNumberOfTestException(){
        Log4RQ.getInstance().totalNumberTestException = 0;
    }


    private void _log(String szEntry, LogLevel level){
        if(!isLevelLogged(level)) return;

        System.out.println( _getLogEntryPrefixColor(level) +
                getVisualPrefixConsoleByLevel(level) + " " +
                " " + szLogInAllEntry + " " +
                _convertLevelToString(level) +
                szEntry +
                _getLogEntrySuffixColor(level)
        );
    }

    private String getVisualPrefixConsoleByLevel(LogLevel level)
    {
        String ret = szVisualPrefixConsole;
        if(level == LogLevel.HEADER)
        {
            ret = "............................";
        }

        return ret;
    }

    private String getExceptionStackInfo(){
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }



    private boolean isLevelLogged(LogLevel level){
        switch(level){
            case INFO:
                return bLogInfo;
            case PASS:
                return bLogPass;
            case FAIL:
                return bLogFail;
            case ERROR:
                return bLogError;
            case WARN:
                return bLogWarn;
            case DEBUG:
                return bLogDebug;
            case HEADER:
                return bLogHeader;
            case RUNNING:
                return bLogRunning;
            case MASSIVE:
                return bLogMassive;
            case EXCEPTION:
                return bLogException;
            default:
                return true;
        }
    }

    /**
     * This method returns special caracter sets for entry colors based on log severity
     * This method needs to be called symetrically with the _getLogEntrySuffixColor in
     * order to reset the color scheme to the normal entry type
     * @param level
     * @return String to be added at the beginning of the log entry
     */
    private String _getLogEntryPrefixColor(LogLevel level){

//        ANSI_RESET = "\u001B[0m";
//        ANSI_BLACK = "\u001B[30m";
//        ANSI_RED = "\u001B[31m";
//        ANSI_GREEN = "\u001B[32m";
//        ANSI_YELLOW = "\u001B[33m";
//        ANSI_BLUE = "\u001B[34m";
//        ANSI_PURPLE = "\u001B[35m";
//        ANSI_CYAN = "\u001B[36m";
//        ANSI_WHITE = "\u001B[37m";


        switch(level){
//            case INFO:
//                return "INFO: ";
//            case PASS:
//                return "PASS:";
//            case FAIL:
//                return "FAIL: ";
            case ERROR:
                return ANSI_RED;
//            case WARN:
//                return "WARN: ";
//            case DEBUG:
//                return "DEBUG: ";
            case HEADER:
                return ANSI_BLUE;
//            case RUNNING:
//                return "RUNNING: ";
            case MASSIVE:
                return ANSI_GREEN;
            case EXCEPTION:
                return ANSI_PURPLE;
            default:
                return ANSI_BLACK;
        }
    }


    private String _getLogEntrySuffixColor(LogLevel level){
        return ANSI_BLACK;
    }


    public static void setSzLogInAllEntry(String szLogInAllEntry) {
        Log4RQ.getInstance()._setSzLogInAllEntry(szLogInAllEntry);
    }

    private String _convertLevelToString(LogLevel level){
        switch(level){
            case INFO:
                return "INFO: ";
            case PASS:
                return "PASS:";
            case FAIL:
                return "FAIL: ";
            case ERROR:
                return "ERROR: ";
            case WARN:
                return "WARN: ";
            case DEBUG:
                return "DEBUG: ";
            case HEADER:
                return "HEADER: ";
            case RUNNING:
                return "RUNNING: ";
            case MASSIVE:
                return "MASSIVE: ";
            case EXCEPTION:
                return "EXCEPTION: ";
            default:
                return "Log entry: ";
        }
    }

    public void _setSzLogInAllEntry(String szLogInAllEntry) {
        this.szLogInAllEntry = szLogInAllEntry;
    }

    public static void setSzVisualPrefixReporter(String szVisualPrefixReporter) {
        Log4RQ.getInstance()._setSzVisualPrefixReporter(szVisualPrefixReporter);
    }

    public static void setSzVisualPrefixConsole(String szVisualPrefixConsole) {
        Log4RQ.getInstance()._setSzVisualPrefixConsole(szVisualPrefixConsole);
    }

    private void _setSzVisualPrefixReporter(String szVisualPrefixReporter) {
        this.szVisualPrefixReporter = szVisualPrefixReporter;
    }

    private void _setSzVisualPrefixConsole(String szVisualPrefixConsole) {
        this.szVisualPrefixConsole = szVisualPrefixConsole;
    }

    public static void setLogLevel(LogLevel level, boolean bSet){
        Log4RQ.getInstance()._setLogLevel(level, bSet);
    }

    private void _setLogLevel(LogLevel level, boolean bSet){
        switch(level){
            case INFO:
                bLogInfo = bSet;
                break;
            case PASS:
                bLogPass = bSet;
                break;
            case FAIL:
                bLogFail = bSet;
                break;
            case ERROR:
                bLogError = bSet;
                break;
            case WARN:
                bLogWarn = bSet;
                break;
            case DEBUG:
                bLogDebug = bSet;
                break;
            case HEADER:
                bLogHeader = bSet;
                break;
            case RUNNING:
                bLogRunning = bSet;
                break;
            case MASSIVE:
                bLogMassive = bSet;
                break;
            case EXCEPTION:
                bLogException = bSet;
                break;
        }
    }


    public void incrementNumberOfTestException()
    {
        totalNumberTestException++;
        totalNumberSuiteException++;
    }

    public static long getTotalNumberSuiteException(){
        return totalNumberSuiteException;
    }

    public static long getTotalNumberTestException(){
        return Log4RQ.getInstance().totalNumberTestException;
    }
}
