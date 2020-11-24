package threadExam2;

public class joinYieldExam {
	static long startTime = 0;
	public static void main(String[] args) {
		// suspend() : sleep처럼 쓰레드를 멈추게하고 resume()으로 다시 실행대기로 바꾼다.
		// stop() : 쓰레드를 종료시켜버린다.
		// 위 두 메서드는 하위호환성으로 유지하고 있으며 사용하지 말라고 권고하고 있다.
		
		// join() : 현재 실행 중인 쓰레드가 끝날때까지 대기상태로 전환한다.
		// 현재 쓰레드가 아닌 특정쓰레드에 동작해서 static메서드가 아니다.
		// yeild() : 쓰레드의 실행시간을 다른 쓰레드에게 양보한다.
		
		ThreadEx81 th1 = new ThreadEx81();
		ThreadEx82 th2 = new ThreadEx82();
		th1.start();
		th2.start();
		startTime = System.currentTimeMillis();
		
		try {
			// th1, th2의 작업이 끝날떄 까지 메인스레드는 대기한다
			th1.join();
			th2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("소요시간 : " + (System.currentTimeMillis() - joinYieldExam.startTime));
		
	}

}

class ThreadEx81 extends Thread{
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.print("|");
		}
	}
}

class ThreadEx82 extends Thread{
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.print("-");
		}
	}
}
