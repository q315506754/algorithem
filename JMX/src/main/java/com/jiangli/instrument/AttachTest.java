package com.jiangli.instrument;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

public class AttachTest extends Thread {

    private final List<VirtualMachineDescriptor> listBefore;

    private final String jar;

    AttachTest(String attachJar, List<VirtualMachineDescriptor> vms) {
        listBefore = vms;  // 记录程序启动时的 VM 集合
        jar = attachJar;
    }

    public void run() {
        VirtualMachine vm = null;
        List<VirtualMachineDescriptor> listAfter = null;
        try {
            int count = 0;
            while (true) {
                listAfter = VirtualMachine.list();
                for (VirtualMachineDescriptor vmd : listAfter) {
                    if (!listBefore.contains(vmd)) {
                        // 如果 VM 有增加，我们就认为是被监控的 VM 启动了
                        // 这时，我们开始监控这个 VM
                        vm = VirtualMachine.attach(vmd);
                        break;
                    }
                }
                Thread.sleep(500);
                count++;
                if (null != vm || count >= 10) {
                    break;
                }
            }
            vm.loadAgent(jar);
//            vm.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        自动附加
//        new AttachThread("TestInstrument1.jar", VirtualMachine.list()).run();

//        VirtualMachine attach = VirtualMachine.attach("5741");
//        System.out.println(attach.id());
//        执行一次附加操作
        for (VirtualMachineDescriptor vmd : VirtualMachine.list()) {
            System.out.println(vmd);
            if (vmd.displayName().contains("TestMainInJar")) {
                VirtualMachine vm = VirtualMachine.attach(vmd);
                vm.loadAgent("/home/conquer/Desktop/aaaa/agentmain.jar");
                System.out.println("loaded");
                vm.detach();
                System.out.println("detached");
                break;
            }
        }
    }
}