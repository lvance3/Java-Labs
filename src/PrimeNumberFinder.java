package lab5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class PrimeNumberFinder 
{
	private JTextField inputField;
    private JTextField threadField;
    private JTextArea outputArea;
    private JButton startButton;
    private JButton cancelButton;
    private long startTime;

    private volatile boolean isCancelled = false;

    public PrimeNumberFinder() 
    {
        setTitle("Prime Number Finder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(200, 30)); 

        threadField = new JTextField("1"); 
        threadField.setPreferredSize(new Dimension(50, 30));

        outputArea = new JTextArea();
        outputArea.setPreferredSize(new Dimension(400, 200));
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                startPrimeNumberFinder();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                cancelPrimeNumberFinder();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Enter a number:"));
        controlPanel.add(inputField);
        controlPanel.add(new JLabel("Number of threads:"));
        controlPanel.add(threadField);
        controlPanel.add(startButton);
        controlPanel.add(cancelButton);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private void startPrimeNumberFinder() 
    {
        outputArea.setText("");
        isCancelled = false;

        int inputValue;
        int threadCount;
        try 
        {
            inputValue = Integer.parseInt(inputField.getText());
            threadCount = Integer.parseInt(threadField.getText());
        } 
        catch (NumberFormatException ex) 
        {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer.");
            return;
        }

        SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() 
        {
            @Override
            protected Integer doInBackground() 
            {
                return calculatePrimes(inputValue, threadCount);
            }

            @Override
            protected void done() 
            {
                try 
                {
                    int totalPrimeCount = get();

                    long elapsedTime = System.currentTimeMillis() - startTime;

                    outputArea.append("Total prime numbers found: " + totalPrimeCount + "\n");
                    outputArea.append("Total time elapsed: " + elapsedTime / 1000.0 + " seconds\n");
                } 
                catch (InterruptedException | ExecutionException e) 
                {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    private int calculatePrimes(int inputValue, int threadCount) 
    {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Integer>> futures = new ArrayList<>();

        startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) 
        {
            final int threadNumberForLambda = i;
            Callable<Integer> primeNumberTask = () -> 
            {
                PrimeNumberThread primeNumberThread = new PrimeNumberThread(inputValue, threadCount, threadNumberForLambda);
                return primeNumberThread.run();
            };
            futures.add(executor.submit(primeNumberTask));
        }

        executor.shutdown();

        int totalPrimeCount = 0;
        for (Future<Integer> future : futures) 
        {
            try 
            {
                totalPrimeCount += future.get();
            } 
            catch (InterruptedException | ExecutionException e) 
            {
                e.printStackTrace();
            }
        }

        return totalPrimeCount;
    }

    private void cancelPrimeNumberFinder() 
    {
        isCancelled = true;
    }

    private class PrimeNumberThread 
    {
        private final int maxValue;
        private int primeCount;
        private long startTime;
        private final int threadCount;
        private final int threadNumber;

        PrimeNumberThread(int maxValue, int threadCount, int threadNumber) 
        {
            this.maxValue = maxValue;
            this.threadCount = threadCount;
            this.threadNumber = threadNumber;
            this.startTime = System.currentTimeMillis();
        }

        public Integer run() 
        {
            int primeCount = 0;
            for (int i = 2 + threadNumber; i <= maxValue; i += threadCount) 
            {
                if (isPrime(i)) 
                {
                    primeCount++;
                    if (isCancelled) 
                    {
                        break;
                    }
                }
            }

            return primeCount;
        }

        private boolean isPrime(int num) 
        {
            if (num < 2) 
            {
                return false;
            }
            for (int i = 2; i <= Math.sqrt(num); i++) 
            {
                if (num % i == 0) 
                {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> new PrimeNumberFinder().setVisible(true));
    }
}
