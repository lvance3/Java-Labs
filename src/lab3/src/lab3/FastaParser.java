package lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FastaParser 
{
	public static List<FastaSequence> readFastaFile(String filePath) throws Exception
    {
        List<FastaSequence> fastaList = new ArrayList<>();
        
        BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
        String line;
        String header = null;
        StringBuilder sequence = new StringBuilder();

        while ((line = reader.readLine()) != null)
        {
            if (line.startsWith(">"))
            {
                if (header != null)
                {
                    fastaList.add(new FastaSequence(header, sequence.toString()));
                    sequence = new StringBuilder();
                }
                header = line.substring(1).trim();
            } 
            else
                {
                    sequence.append(line.trim());
                }
        }

        if (header != null && sequence.length() > 0)
        {
            fastaList.add(new FastaSequence(header, sequence.toString()));
        }

        reader.close();
        return fastaList;
    }

    public static void writeTableSummary( List<FastaSequence> list, File outputFile)throws Exception
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(
            "C:\\Users\\logan\\OneDrive\\Desktop\\java-class\\Projects\\out.txt"));
        
        writer.write("sequenceID\t numA\t numC\t numG\t numT\t sequence\n");

        for(FastaSequence fs : list)
        {
            int numA = 0;
            int numC = 0;
            int numG = 0;
            int numT = 0;

            char charA = 'A';
            char charC = 'C';
            char charG = 'G';
            char charT = 'T';

            for(char nucleotide : fs.getSequence().toCharArray())
            {
                if (nucleotide == charA)
                {
                    numA++;
                }
                if (nucleotide == charC)
                {
                    numC++;
                }
                if (nucleotide == charG)
                {
                    numG++;
                }
                if (nucleotide == charT)
                {
                    numT++;
                }
            }
            writer.write(fs.getHeader() + "\t" + numA + "\t" + numC + "\t" + numG + "\t" + numT + "\t" + fs.getSequence() + "\n");
        }
        writer.close();
    }

    public static void main(String[] args) throws Exception
    {
        String filePath = "c:\\pointsToSome\\FastaFile.txt";
        List<FastaSequence> fastaList = readFastaFile(filePath);

        for (FastaSequence fs : fastaList)
        {
            System.out.println(fs.getHeader());
            System.out.println(fs.getSequence());
            System.out.println(fs.getGCRatio());
        }
        File myFile = new File("c:\\yourFilePathHere\\out.txt");

        writeTableSummary( fastaList,  myFile);
    }
}

class FastaSequence
{
    private String header;
    private String sequence;

    public FastaSequence(String header, String sequence)
    {
        this.header = header;
        this.sequence = sequence;
    }

    public String getHeader()
    {
        return header;
    }

    public String getSequence()
    {
        return sequence;
    }

    public float getGCRatio()
    {
        int gcCount = 0;
        for (char nucleotide : sequence.toCharArray())
        {
            if (nucleotide == 'G' || nucleotide == 'C')
            {
                gcCount++;
            }
        }
        return (float) gcCount / sequence.length() * 100;
    }
}
