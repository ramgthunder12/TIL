package makething.copy;

public class MemoMain {
	public static void main(String[] args) {
		Memo computerMemo = new ComputerMemo();
		Memo noteBookMemo = new NoteBookMemo();
		
		Memo timeOutMemoProxy1 = new TimeOutMemo(noteBookMemo);
		Memo timeOutMemoProxy2 = new TimeOutMemo(computerMemo);
		
		timeOutMemoProxy1.write();
	}
}
