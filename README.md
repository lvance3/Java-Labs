# Lab 5

"Prime number finder" or "a gui with a slow process in the background"

The multi threading speedup seemed to cap at about 5x at 16 threads.

==================================================================================================
Lab 4 - Logan Vance - lvance3@uncc.edu

Amino Acid Quiz GUI

==================================================================================================

Lab 3 - Logan Vance - lvance3@uncc.edu

Fasta Pasrer

==================================================================================================

Lab 2
Lab 2 - Logan Vance - lvance3@uncc.edu

The ChatGPT code for the project was pretty close, but wasn't able to meet the specifications without a fair amount of fine tuning after the initial prompt. I found it interesting that the Chat code used a single array for the amino acid - one letter code information and split the item into name and code before using them in the quiz. Chat used strings for the amino acid components and longs for the time elements. Chat also used a boolean it named 'quizRunning' to use with a while statement to loop the quiz.

I asked chat to allow for user input on time, but it interpreted this as displaying time elapsed to answer the question then asking if the user would like to continue after each question. If the user entered 'yes' the program would continue and display total time of the quiz thusfar.

I was able to get Chat to more or less function like the code I wrote, but it took several itterations and starting from a new prompt at one point.

==================================================================================================

Lab 1 - Logan Vance - lvance3@uncc.edu

Answers:

With 1.000 codons generated at 25% chance for each nucleotide to apear, the average occurence of 'AAA' would be 1.56%, or about 15 to 16 out of 1.000.
This is calculated from the probability 0.25*0.25*0.25 = 0.015625 * 100 = 1.5626%
After running the program several times at 1.000 and 10.000 sequences, this rate was true on average.

With the adjusted weights of the nucleotides given, the new occurence of 'AAA' would be 0.17%, or about 2 out of 1000 random codons.
This is calculated from the probability 0.12*0.12*0.12 = 0.001728 * 100 = 0.1728%
After running the program several times at 1.000 and 10.000 sequences, this rate was true on average.
