import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        NameList nameList = new NameList();
        nameList.add("first");

        class MyThread extends Thread {
            @Override
            public void run() {
                if (Thread.currentThread().getName().equals("one")) {
                    System.out.println(nameList.removeFirst());
                } else {
                    System.out.println(nameList.removeLast());
                }
            }
        }

        MyThread myThread = new MyThread();
        myThread.setName("one");
        myThread.start();

        MyThread myThread2 = new MyThread();
        myThread2.setName("two");
        myThread2.start();
    }

    static class NameList {
        private List<String> list = Collections.synchronizedList(new ArrayList<>());

        public void add(String name) {
            list.add(name);
        }

        public synchronized String removeFirst() {
            System.out.println("Метод removeFirst()");
            if (list.size() > 0) {
                if (Thread.currentThread().getName().equals("one")) {
                    System.out.println(Thread.currentThread().getName() + " пытается передать управление другому потоку.");
                    Thread.yield();
                }

                System.out.println(Thread.currentThread().getName() + " - размер коллекции: " + list.size());
                return list.remove(0);
            } else {
                return null;
            }
        }

        public synchronized String removeLast() {
            System.out.println("Метод removeLast()");
            if (list.size() > 0) {
                if (Thread.currentThread().getName().equals("two")) {
                    System.out.println(Thread.currentThread().getName() + " пытается передать управление другому потоку.");
                    Thread.yield();
                }

                System.out.println(Thread.currentThread().getName() + " - размер коллекции: " + list.size());
                return list.remove(list.size() - 1);
            } else {
                return null;
            }
        }
    }
}
