package threadExam2;

public class SleepExam {

	public static void main(String[] args) {
		
		ThreadEx61 th1 = new ThreadEx61();
		ThreadEx62 th2 = new ThreadEx62();
		
		th1.start();
		th2.start();
		
		try {
			// 메인 스레드 적용된 sleep
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("2초경과 후<main스레드 종료>");
	}
	/*
		-----|||||<th1종료>
		2초경과 후<main스레드 종료>
		4초경과 후 <th2종료>
	*/
}


class ThreadEx61 extends Thread{
	
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.print("-");
		}
		System.out.println("<th1종료>");

	}
}


class ThreadEx62 extends Thread{
	
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.print("|");
		}
		try {
			// th2에 적용된 스레드
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("4초경과 후 <th2종료>");

	}
}