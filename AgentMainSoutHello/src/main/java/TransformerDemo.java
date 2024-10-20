import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class TransformerDemo implements ClassFileTransformer {
    public static final String editClassName = "com.example.Hello";
    public static final String editClassName2 = editClassName.replace('.', '/');
    public static final String editMethod = "hello";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.equals(editClassName2)) return null;
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.appendClassPath(new LoaderClassPath(loader));

            CtClass ctc = cp.get(editClassName);
            CtMethod method = ctc.getDeclaredMethod(editMethod);
            String source = "{System.out.println(\"hello transformer\");}";
            method.setBody(source);

            byte[] bytes = ctc.toBytecode();
            ctc.detach();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
