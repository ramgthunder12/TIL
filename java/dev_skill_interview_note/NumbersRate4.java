public class NumbersRate4 {
  private static float[] itemCount(int[] numbers) {
    long positives = Arrays.stream(numbers).filter(num -> num > 0).count();
    long negatives = Arrays.stream(numbers).filter(num -> num < 0).count();
    long zeros = Arrays.stream(numbers).filter(num -> num == 0).count();

    float point = (float) numbers.length;
    float positive = positives / point;
    float negative = negatives / point;
    float zero = zeros / point;

    float[] results = {positive, negative, zero};
    return results;
  }
  public static void main(String[] args) {
    int[] numbers = {-12, 33, -4, 0, 0, 9, 1, -2, 11, 0};
    for(item : itemCount(numbers)) {
      System.out.println(item);
    }
  }
}
