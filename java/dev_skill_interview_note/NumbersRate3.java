public class NumbersRate2{
  private IntegerType numberType(int number) {
    if(number > 0) {
      return IntegerType.positive;
    } else if(number == 0) {
      return IntegerType.zero;
    } else {
      return IntegerType.negative; 
    }
  }

  private void rateCalculate(IntegerType[] typeTemp) {
    for(IntegerType item : typeTemp) {
      item.setCount();
    }
    
    for(IntegerType item : IntegerType.values()) {
      System.out.printf("%s rate : %.6f\n", item.name(), (double)item.getCount() / typeTemp.length);
    }
  }
  
  private void numberTypeRate(int[] numbers) {
    IntegerType[] typeTemp = new IntegerType[numbers.length];
    
    //유형 판단
    for(int i = 0; i < numbers.length; i++) {
      typeTemp[i] = numberType(numbers[i]);
    }
    
    //비율 개산
    rateCalculate(typeTemp);
  }
  public static void main(String[] args) {
    int[] numbers = {-12, 33, -4, 0, 0, 9, 1, -2, 11, 0};

    NumbersRate2 numbersRate = new NumbersRate2();
    numbersRate.numberTypeRate(numbers);
  }
}

enum IntegerType{
  positive, negative, zero;
  private int count = 0;
  
  public void setCount() {
    this.count++;
  }

  public int getCount() {
    return this.count;
  }
}
