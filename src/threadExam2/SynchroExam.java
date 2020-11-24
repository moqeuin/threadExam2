package threadExam2;

public class SynchroExam {

	public static void main(String[] args) {
		// 스레드의 동기화 : 한 프로세스 내에서 멀티스레드를 이용할 경우 
		// 한 스레드가 공유데이터를 작업중일 경우 도중에 스레드의 작업전환이 일어나 다른 스레드가
		// 그 데이터에 접근해서 데이터가 바뀔 수 있다. 방지하기위해서
		// 그 데이터를 임계영역(critical section)으로 정해서 락을 가진 스레드만
		// 접근해서 안정성을 유지하는 방법이다
		
		Runnable r = new ThreadEx9();
		new Thread(r).start();
		new Thread(r).start();
		// 동기화를 설정하지 않았다면 다른 스레드가 도중에 접근하게 되서 음수가 나올 수도 있다
	}

}

class Account{
	
	private int balance = 1000; // private로 지정해야만 동기화를 할 의미가 있다
	
	public int getBalance() {
		
		return balance;
	}
	// 메서드의 동기화를 설정하면 한 스레드가 작업을 끝날때까지 다른 스레드는 접근이 불가능하다.
	public synchronized void withdraw(int money) { 
		if(balance >= money) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			balance -= money;
		}
	}// withdraw
	
	
}

class ThreadEx9 implements Runnable{
	Account acc = new Account();
	@Override
	public void run() {
		while(acc.getBalance() > 0) {
			
			int money = (int)(Math.random()*3+1)*100;
			acc.withdraw(money);
			System.out.println(acc.getBalance());
		}
		
		
	}
	
}
