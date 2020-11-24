package threadExam2;

import java.util.ArrayList;

public class SynchroExam2 {

	public static void main(String[] args) throws Exception{
		
		// wait() : 쓰레드가 공유객체에 대한 임계영역에 도달해 락을 가질 떄
		// 작업상황이 아니면 객체의 wait pool로 대기상태로 쓰레드를 전환시킨다.
		// 이 떄 임계영역에서는 빠져나온다. 그리고 obj내에 wait pool로 들어간다.
		// notify() : 작업상황이 되면 통보해서 wait pool 안에 있는 임의의 객체에게 락을 부여한다. 
		// 그러면 다시 그 쓰레드는 임계영역으로 들어와서 처음부터 다시 실행된다.
		
		Table table = new Table();
		
		new Thread(new Cook(table),"COOK").start();
		new Thread(new Customer(table, "donut"),"CUST1").start();
		new Thread(new Customer(table, "burger"),"CUST2").start();
		
		Thread.sleep(5000);
		System.exit(0);
	}

}
class Cook implements Runnable{
	
	private Table table;
	
	public Cook(Table table) {
		this.table = table;
	}
	
	@Override
	public void run() {
		while(true) {
			int idx = (int)(Math.random()*table.disNum());
			table.add(table.dishNames[idx]);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // while
		
	}	
}// Cook

class Customer implements Runnable{
	
	private Table table;
	private String food;
	
	public Customer(Table table, String food) {
		this.table = table;
		this.food = food;
	}
	
	@Override
	public void run() {
		while(true) {
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String name = Thread.currentThread().getName();
			
			table.remove(food);
			System.out.println(name + " ate a " + food);
			/*
			if(eatFood())
				System.out.println(name + "ate a " + food);
			else
				System.out.println(name + "failed to eat. :(");
			*/
		}
	} // run
	//boolean eatFood() {return table.remove(food);}
	
} // customer


class Table{
	
	String[] dishNames = {"donut", "donut", "burger"};
	final int MAX_FOOD = 6;
	private ArrayList<String> dishes = new ArrayList<String>();
	
	// 메서드 전체를 임계영역으로 지정
	public synchronized void add(String dish) {
		// Cook객체가 리스트의 크기가 초과해서 음식을 추가할 수 없는 상황.
		// Cook객체를 waiting pool에 대기를 시킨다.
		if(dishes.size() >= MAX_FOOD) {
			String name = Thread.currentThread().getName();
			System.out.println(name + " is a waiting");
			try {
				wait();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 음식이 추가가 되면 waiting pool 대기하던 임의의 스레드에게 lock을 부여한다.
		dishes.add(dish);
		notify();
		System.out.println("Dishes" + dishes.toString());
	} // add
	
	public void remove(String DishName) {
		
		// table 객체를 임계영역으로 지정
		synchronized (this) {
			String name = Thread.currentThread().getName();

			// 음식이 없다면 계속 기다린다
			while(dishes.size() == 0) {
				System.out.println(name + " is waiting");
				try {
					wait();
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
			}
			while(true) {
				for (int i = 0; i < dishes.size(); i++) {
					// 원하는 음식이 있을 때
					if(DishName.equals(dishes.get(i))) {
						dishes.remove(i);
						notify();
						return;
					}
				} // for
				
				// 원하는 음식이 없을 때
				System.out.println(name + " is waiting");
				try {
					wait();
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}// while
		}// synchronized
		//return false;
	} // rmove
	public int disNum() {return dishNames.length;}
}
