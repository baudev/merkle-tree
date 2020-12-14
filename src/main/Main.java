public class Main {

    public static void main(String[] args) throws Exception {

        /////////////////////////////////////////////////////////////////////
        // ALL THE CODE LOGIC IS TESTED WITH JUNIT INTO THE TEST DIRECTORY //
        /////////////////////////////////////////////////////////////////////

        AuditorClass auditorClass = new AuditorClass(new LogServer("logs.txt"));

        System.out.println("The event \"THIRD EVENT\" is present into the tree: " + auditorClass.isMember("THIRD EVENT"));
        System.out.println("The event \"RANDOM VALUE\" is present into the tree: " + auditorClass.isMember("RANDOM VALUE"));

        // with a more complicated tree
        LogServer complicatedLogServer = new LogServer("logs.txt");
        complicatedLogServer.addBatchOfEvents(new String[]{"NEW159", "NEW256"});
        AuditorClass auditorClass1 = new AuditorClass(complicatedLogServer);

        System.out.println("The event \"NEW159\" is present into the tree: " + auditorClass1.isMember("NEW159"));
        System.out.println("The event \"TEST\" is present into the tree: " + auditorClass1.isMember("TEST"));
    }

}
