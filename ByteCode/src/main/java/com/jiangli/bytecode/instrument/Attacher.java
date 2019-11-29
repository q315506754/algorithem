package com.jiangli.bytecode.instrument;

import com.jiangli.bytecode.ByteDtoMain;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

/**
 * @author Jiangli
 * @date 2019/11/28 17:52
 */
public class Attacher {
    public static void main(String[] args) {
        try {
            List<VirtualMachineDescriptor> list = VirtualMachine.list();
            for (VirtualMachineDescriptor descriptor : list) {
                String s = descriptor.displayName();
                if (s.equals(ByteDtoMain.class.getName())) {
                    System.out.println(descriptor);
                    String id = descriptor.id();

                    VirtualMachine vm = VirtualMachine.attach(id);
                    vm.loadAgent("C:\\myprojects\\algorithem\\ByteCode\\target\\ByteCode-1.0-SNAPSHOT.jar");
                }
            }

            //VirtualMachine vm = VirtualMachine.attach("21652");
            //vm.loadAgent("C:\\myprojects\\algorithem\\ByteCode\\target\\ByteCode-1.0-SNAPSHOT.jar");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
