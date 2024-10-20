import com.sun.tools.attach.VirtualMachine;

public class Main {
    public static void main(String[] args) throws Exception {
        String id = args[0];
        String jarName = args[1];

        System.out.println("id ==> " + id);
        System.out.println("jarName ==> " + jarName);

        System.out.println("start attach");

        VirtualMachine virtualMachine = VirtualMachine.attach(id);
        virtualMachine.loadAgent(jarName);
        virtualMachine.detach();

        System.out.println("attach end");
    }
}
