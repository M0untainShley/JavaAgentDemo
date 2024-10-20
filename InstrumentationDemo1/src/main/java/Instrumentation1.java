import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Instrumentation1 {
    public static void agentmain(String agentArgs, Instrumentation inst) throws IOException {
        Class[] classes = inst.getAllLoadedClasses();
        FileOutputStream fileOutputStream = new FileOutputStream(new File("E:\\IDEProjects\\IdeaProjects" +
                "\\JavaAgentDemo\\InstrumentationDemo1\\1.txt"));
        for (Class aClass : classes) {
            String result = "class ==> " + aClass.getName() + "\n\t" + "Modifiable ==> " + (inst.isModifiableClass(aClass) ? "true" : "false") + "\n";
            fileOutputStream.write(result.getBytes());
        }
        fileOutputStream.close();
    }
}
