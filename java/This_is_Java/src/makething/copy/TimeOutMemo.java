package makething.copy;

public class TimeOutMemo implements Memo{
	private Memo memo;
	
	public TimeOutMemo(Memo memo) {
		this.memo = memo;
	}
	
	@Override
	public void write() {
		while(true){
			long fistTime = timeCheck();
			memo.write();
			long secendTime = timeCheck();
			if(timeOutCheck(fistTime, secendTime)) {
				break;
			}
		}
	}
	
	private boolean timeOutCheck(long firstTime, long secendTime) {
		if(secendTime - firstTime > 3000) {
			System.out.println("Time out");
			return true;
		}
		return false;
	}
	
	private long timeCheck() {
		return System.currentTimeMillis();		
	}
}
