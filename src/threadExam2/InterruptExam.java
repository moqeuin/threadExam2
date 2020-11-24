package threadExam2;

import javax.swing.JOptionPane;

public class InterruptExam {

	public static void main(String[] args) {
		// 인터럽트는 쓰레드가 sleep(), wait(), join()에 의해 일시정지(waiting)일 때
		// 쓰레드에 대해 interrupt()를 호출하면 sleep()...에서 인터럽트예외가 발생하고
		// 쓰레드는 실행대기상태(runnable)로 바뀐다
		// 정지상태인 쓰레드를 꺠운다
		ThreadEx7 th = new ThreadEx7();
		th.start();
		String input = JOptionPane.showInputDialog("아무 값이나 입력하세요");
		System.out.println("입력하신 값은 " + input + "입니다.");
		th.interrupt();
		System.out.println(th.isInterrupted());
	}

}

class ThreadEx7 extends Thread{
	
	@Override
	public void run() {
		int i = 10;
		// isInterrupted() : 스레드의 상태반환, interrupt를 호출하면 true(runnable상태)
		// Interrupted() : 스레드의 상태반환, 다시 false로 되돌린다.
		while(i != 0 && !isInterrupted()) {
			System.out.println(i--);
			for (int j = 0; j < 2000000000L; j++);
		}
		System.out.println("카운트가 종료");
	}
}