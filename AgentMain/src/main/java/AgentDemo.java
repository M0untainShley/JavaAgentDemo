import java.lang.instrument.Instrumentation;

public class AgentDemo {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        for (int i = 0; i < 10; i++) {
            System.out.println("hello i'm agentmain!");
        }
    }
}
