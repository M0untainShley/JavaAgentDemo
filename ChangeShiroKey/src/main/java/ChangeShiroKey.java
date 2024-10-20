import java.lang.instrument.Instrumentation;

public class ChangeShiroKey {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        Class<?>[] classes = inst.getAllLoadedClasses();
        for (Class<?> aClass : classes) {
            if (aClass.getName().equals(ChangeShiroKeyTransformer.editClassName)) {
                inst.addTransformer(new ChangeShiroKeyTransformer(), true);
                try {
                    inst.retransformClasses(aClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
