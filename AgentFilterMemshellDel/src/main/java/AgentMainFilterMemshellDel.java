import java.lang.instrument.Instrumentation;

public class AgentMainFilterMemshellDel {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        Class<?>[] classes = inst.getAllLoadedClasses();
        for (Class<?> aClass : classes) {
            if (aClass.getName().equals(TransformerFilterMemshellDel.editClassName)) {
                inst.addTransformer(new TransformerFilterMemshellDel(), true);
                try {
                    inst.retransformClasses(aClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}