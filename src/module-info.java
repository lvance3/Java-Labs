import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AminoAcidQuizGUI extends JFrame 
{
    private final String[] SHORT_NAMES = 
	{ "A", "R", "N", "D", "C", "Q", "E",
	"G", "H", "I", "L", "K", "M", "F",
	"P", "S", "T", "W", "Y", "V" };

    private final String[] FULL_NAMES = 
	{ "alanine", "arginine", "asparagine", 
	"aspartic acid", "cysteine", "glutamine", 
	"glutamic acid", "glycine", "histidine", 
	"isoleucine", "leucine", "lysine", 
	"methionine", "phenylalanine", "proline", 
	"serine", "threonine", "tryptophan", 
	"tyrosine", "valine" };
	
	private Random random = new Random();
	private int userTime = 30;
	private long startTime;
	private long endTime;
	private int wrongCount;
	private int correctCount;
    private JLabel timeLabel;
    private JLabel aminoAcidLabel;
    private JTextField userInputField;
    private JButton startButton;
    private JButton cancelButton;
    private JLabel correctLabel;
    private JLabel wrongLabel;
	private ActionListener userInputListener;

    public AminoAcidQuizGUI() 
	{
		setVisible(true);
        timeLabel = new JLabel("Time left: " + userTime + " seconds");
        aminoAcidLabel = new JLabel("");
        userInputField = new JTextField(10);
        startButton = new JButton("Start Quiz");
        cancelButton = new JButton("Cancel");
        correctLabel = new JLabel("Correct: 0");
        wrongLabel = new JLabel("Wrong: 0");

        JPanel inputPanel = new JPanel();
        inputPanel.add(userInputField);
        inputPanel.add(startButton);
        inputPanel.add(cancelButton);

        JPanel scorePanel = new JPanel();
        scorePanel.add(correctLabel);
        scorePanel.add(wrongLabel);

        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));
        quizPanel.add(aminoAcidLabel);
        quizPanel.add(inputPanel);
        quizPanel.add(timeLabel);
        quizPanel.add(scorePanel);

        startButton.addActionListener(new ActionListener() 
		{
            @Override
            public void actionPerformed(ActionEvent e) 
			{
                startQuiz();
            }
        });

        cancelButton.addActionListener(new ActionListener() 
		{
            @Override
            public void actionPerformed(ActionEvent e) 
			{
                cancelQuiz();
            }
        });

        add(quizPanel);
        setTitle("Amino Acid Quiz");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void startQuiz() 
	{
        startButton.setEnabled(false);
        cancelButton.setEnabled(true);
        timeLabel.setVisible(true);
        userInputField.setEnabled(true);

		correctCount = 0;
		wrongCount = 0;
		correctLabel.setText("Correct: " + correctCount);
        wrongLabel.setText("Wrong: " + wrongCount);

		startTime = System.currentTimeMillis();
		endTime = startTime + (userTime * 1000);

        newQuizRound();

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (System.currentTimeMillis() < endTime)
				{
					updateTimer();
					try
					{
						Thread.sleep(1000);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				endQuiz();
			}
		}).start();
    }
	private void updateTimer()
	{
		long currentTime = System.currentTimeMillis();
		long timeLeft = Math.max(0, (endTime - currentTime) / 1000);
		timeLabel.setText("Time left: " + timeLeft + " seconds");
	}
    private void newQuizRound() 
	{
        int randomSelection = random.nextInt(FULL_NAMES.length);
        String aminoAcid = FULL_NAMES[randomSelection];
        String aminoAcidCode = SHORT_NAMES[randomSelection];
        aminoAcidLabel.setText(aminoAcid);
        userInputField.setText("");

		userInputField.removeActionListener(userInputListener);
        userInputListener = new ActionListener() 
		{
            @Override
            public void actionPerformed(ActionEvent e) 
			{
                String userInput = userInputField.getText().toUpperCase();
                if (userInput.equals(aminoAcidCode)) 
				{
                    correctCount++;
                    correctLabel.setText("Correct: " + correctCount);
                } 
				
				else 
				{
                    wrongCount++;
                    wrongLabel.setText("Wrong: " + wrongCount);
                }

				if (System.currentTimeMillis() >= endTime) 
				{
					endQuiz();
				} 
				
				else 
				{
                	newQuizRound();
            	}
			}
        };
		userInputField.addActionListener(userInputListener);
    }

    private void endQuiz() 
	{
        timeLabel.setVisible(false);
        userInputField.setEnabled(false);
        cancelButton.setEnabled(false);
        startButton.setEnabled(true);
        aminoAcidLabel.setText("Quiz Over!");
        userInputField.setText("");
        
    }

    private void cancelQuiz() 
	{
        endQuiz();
    }

	public static void main(String[] args) 
	{
		new AminoAcidQuizGUI();
	}
}