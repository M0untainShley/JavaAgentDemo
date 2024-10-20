import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentDemo {
    public static void agentmain(String agentArgs, Instrumentation inst) throws IOException, UnmodifiableClassException {
        Class<?>[] classes = inst.getAllLoadedClasses();
        for (Class<?> aClass : classes) {
            if (aClass.getName().equals(TransformerDemo.editClassName)) {
                inst.addTransformer(new TransformerDemo(), true);
                try {
                    inst.retransformClasses(aClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
