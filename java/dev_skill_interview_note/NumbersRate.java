public class NumbersRate{
  private int numberType(int number) {
    if(number > 0) {
      return 1;
    } else if(number == 0) {
      return 0;
    } else {
      return -1; 
    }
  }

  private void rateCaculate(int[] typeTemp) {
    int positiveCount = 0;
    int zeroCount = 0;
    int negativeCount = 0;
    
    for(int item : typeTemp) {
      if(item == 1) {
        positiveCount++;
      } else if(item == 0) {
        zeroCount++;
      } else {
        negativeCount++;
      }
    }
    float positiveRate = (float) positiveCount / typeTemp.length;
    float zeroRate = (float) zeroCount / typeTemp.length;
    float negativeRate = (float) negativeCount / typeTemp.length;

    System.out.printf("positive rate : %.6f\n", positiveRate);
    System.out.printf("zero rate : %.6f\n", zeroRate);
    System.out.printf("negative rate : %.6f\n", negativeRate);
  }
  
  private void numberTypeRate(int[] numbers) {
    int[] typeTemp = new int[numbers.length];

    //유형 판단
    for(int i = 0; i < numbers.length; i++) {
      typeTemp[i] = numberType(numbers[i]);
    }
    //비율 개산
    rateCaculate(typeTemp);
  }
  public static void main(String[] args) {
    int[] numbers = {-12, 33, -4, 0, 0, 9, 1, -2, 11, 0};

    NumbersRate numbersRate = new NumbersRate();
    numbersRate.numberTypeRate(numbers);
  }
}
