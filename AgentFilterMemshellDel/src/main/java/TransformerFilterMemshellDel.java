import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class TransformerFilterMemshellDel implements ClassFileTransformer {
    public static final String editClassName = "org.apache.catalina.core.ApplicationFilterChain";
    public static final String editClassName2 = editClassName.replace('.', '/');
    public static final String editMethod = "doFilter";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("start del filter memshell");
        if (!className.equals(editClassName2)) return null;
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.appendClassPath(new LoaderClassPath(loader));

            CtClass ctc = cp.get(editClassName);
            CtMethod method = ctc.getDeclaredMethod(editMethod);
            String source = "{\n" +
                    "    final javax.servlet.ServletRequest req = $1;\n" +
                    "    final javax.servlet.ServletResponse res = $2;\n" +
                    "    $0.internalDoFilter(req,res);\n" +
                    "}";
            method.setBody(source);

            byte[] bytes = ctc.toBytecode();
            ctc.detach();
            System.out.println("del filter memshell success");
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
