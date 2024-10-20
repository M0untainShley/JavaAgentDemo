import com.example.Hello;

import java.lang.management.ManagementFactory;
import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) throws Exception {
        Hello hello = new Hello();
        hello.hello();

        // 获取 pid
        String pid = getPid();
        System.out.println("pid ==> " + pid);

        // 产生中断，等待注入
        Scanner sc = new Scanner(System.in);
        sc.nextInt();

        Hello hello2 = new Hello();
        hello2.hello();
        System.out.println("ends...");
    }

    public static String getPid() throws Exception {
        // 获取当前进程 pid 的方法
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        return pid;
    }
}