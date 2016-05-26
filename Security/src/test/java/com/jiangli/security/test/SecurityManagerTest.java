package com.jiangli.security.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class SecurityManagerTest {
    private static Logger logger = LoggerFactory.getLogger(SecurityManagerTest.class);

    /**
     * -Djava.security.manager  -Djava.security.policy=my.policy
//     */
//    @Test
//    public void func() {
//        SecurityManager safeManager = System.getSecurityManager();
//        System.out.println(safeManager);
//    }
//
//    @Test
//    public  void main() {
//        SecurityManager safeManager = System.getSecurityManager();
//        System.out.println(safeManager);
//    }

    /**
     * -Djava.security.manager  -Djava.security.policy=my.policy
     * @param args
     */
    public static void main(String[] args) {
        try {
            System.setSecurityManager(new PasswordSecurityManager("123456"));
        } catch (SecurityException se) {
            System.out.println("SecurityManager already set!");
        }
        try {
            //DataInputStream fis = new DataInputStream(new FileInputStream("input.txt"));
            BufferedReader fis = new BufferedReader(new FileReader("input.txt"));
            //DataOutputStream fos = new DataOutputStream( new FileOutputStream("output.txt"));
            BufferedWriter fos = new BufferedWriter(new FileWriter("output.txt"));
            String inputString;
            while ((inputString = fis.readLine()) != null) {
                //fos.writeBytes(inputString);
                //fos.writeByte('\n');
                fos.write(inputString);
                fos.write('\n');
            }
            fis.close();
            fos.close();
        } catch (IOException ioe) {
            System.out.println("I/O failed for SecurityManagerTest.");
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }


    static  class PasswordSecurityManager extends SecurityManager {
        private String password;
        PasswordSecurityManager(String password) {
            super();
            this.password = password;
        }
        private boolean accessOK() {
            int c;
            //DataInputStream dis = new DataInputStream(System.in);
            BufferedReader dis = new BufferedReader(new InputStreamReader(System.in));
            String response;
            System.out.println("What's the secret password?");
            try {
                response = dis.readLine();
                if (response.equals(password))
                    return true;
                else
                    return false;
            } catch (IOException e) {
                return false;
            }
        }
        public void checkRead(FileDescriptor filedescriptor) {
            if (!accessOK())
                throw new SecurityException("Not a Chance!");
        }
        public void checkRead(String filename) {
            if (!accessOK())
                throw new SecurityException("No Way!");
        }
        public void checkRead(String filename, Object executionContext) {
            if (!accessOK())
                throw new SecurityException("Forget It!");
        }
        public void checkWrite(FileDescriptor filedescriptor) {
            if (!accessOK())
                throw new SecurityException("Not!");
        }
        public void checkWrite(String filename) {
            if (!accessOK())
                throw new SecurityException("Not Even!");
        }
    }

}
