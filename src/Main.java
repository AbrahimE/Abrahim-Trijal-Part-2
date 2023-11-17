import java.util.concurrent.*;

public class Main implements Callable<Integer> {
    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Future<Integer>[] futures = new Future[10];
        int numbersPerThread = 100000000 / 10;

        int start = 1;
        int end = numbersPerThread;
        int count = 0;


        for(int i = 0; i<10; i++)
        {
            final int finalStart = start;
            final int finalEnd = end;
            Callable<Integer> callable = () -> countNumbers(finalStart, finalEnd);
            futures[i] = executor.submit(callable);
            start = end + 1;
            end += numbersPerThread;
        }

        for(int i = 0; i< futures.length; i++)
        {
            try {
                count += futures[i].get(); // Summing up counts from all threads
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        

        System.out.println("Total count between 1 and 100,000,000: " + count);


        executor.shutdown();

    }

    @Override
    public Integer call() throws Exception {
        return null;
    }

    public static int countNumbers(int start, int end) {
        int count = 0;
        for (int i = start; i <= end; i++) {
            count++;
        }
        return count;
    }
}