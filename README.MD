### 描述
记录 java agent 技术学习笔记以及 tomcat agent 内存马注入以及清除环境
笔记内容在 
1. https://www.yuque.com/m0untainshley/mcv717/bbbq9guk7vms6aho
2. https://www.yuque.com/m0untainshley/mcv717/xhegwaue7rhq6gwf

### 环境
tomcat 8.5.91
jdk 8u151

### 模块介绍
1. Main&PreMain：premain 方法
2. Attacher&AgentMain：agentmain 方法和注入器（attacher）
3. InstrumentationDemo1：getAllLoadedClasses 和 isModifiableClasses 方法，遍历某个 jvm 中加载的所有 class
4. SoutHello&AgentMain：addTransformer 和 retransformClasses 方法，修改字节码 demo
5. AgentFilterMemshell：tomcat filter 内存马注入
6. AgentFilterMemshellDel：tomcat filter 内存马清除


### 参考
1. https://github.com/safe6Sec/MemoryShell
2. https://cangqingzhe.github.io/2021/10/13/JavaAgent%E5%86%85%E5%AD%98%E9%A9%AC%E7%A0%94%E7%A9%B6/
3. https://xz.aliyun.com/t/9450
4. https://mp.weixin.qq.com/s/VnSXmSjdLcbn8L02jp64mA