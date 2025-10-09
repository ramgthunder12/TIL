public class GCDandLCM {
  private static int getGCD(int a, int b) {
    while(b != 0) {
      int temp = a % b;
      a = b;
      b = temp;
    }
    return a;
  }

  private static int getLCM(int a, int b) {
    return a * b / getGCD(a, b);
  }
  public static void main(String[] args) {
    int num1 = Integer.parseInt(args[0]);
    int num2 = Integer.parseInt(args[1]);
    
    System.out.println("최대 공약수 : " + getGCD(num1, num2));
    System.out.println("최소 공배수 : " + getLCM(num1, num2));
  }
}
