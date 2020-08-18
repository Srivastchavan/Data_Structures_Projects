import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.LinkedHashSet;

public class WordPuzzle {

    public static void main(String args[]) {
        Scanner s1 = new Scanner(System.in);
        MyHashTable < String > table = new MyHashTable < > ();
        WordPuzzle wp = new WordPuzzle();

        System.out.println("Input the value for number of rows: ");
        int row = s1.nextInt();
        System.out.println("Input the value for number of columns: ");
        int col = s1.nextInt();

        if (row < 1 || col < 1) {
            System.out.println("Please input a number greater than 0 for both rows and columns!");
            s1.close();
        } else {
            System.out.println("Please press 0 to select normal mode or press 1 to select enhanced mode: ");
            int mode = s1.nextInt();

            char[][] wordMatrix = new char[row][col];

            Random r = new Random();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    wordMatrix[i][j] = (char)(97 + r.nextInt(26));
                }
            }

            // display word puzzle
            
            System.out.println("\n################################################################\n");
            System.out.println("The word puzzle is: \n");
            for (int i = 0; i < wordMatrix.length; i++) {
                for (int j = 0; j < wordMatrix[i].length; j++)
                    System.out.print(wordMatrix[i][j] + " ");
                System.out.println();
            }


            // load dictionary file into hash table
            
            
            /*
            ------------------------------------------------------------------
            Please copy/paste the location of the dictionary.txt file below
            ------------------------------------------------------------------
            */ 
	   
            //String dictionaryPath = "C:/Users/Srivastchavan/Documents/Java Practice/Word Puzzle/dictionary.txt";
            String dictionaryPath = "dictionary.txt";
            System.out.println("\n################################################################\n");
            wp.loadDictionary(dictionaryPath, table, mode);

            // check for words in puzzle

            ArrayList < String > wordsFound = new ArrayList < > ();

            long startTime = System.currentTimeMillis();

            wp.checkHorizontalFwd(table, wordsFound, wordMatrix, mode);
            wp.checkHorizontalBwd(table, wordsFound, wordMatrix, mode);
            wp.checkVerticalUp(table, wordsFound, wordMatrix, mode);
            wp.checkVerticalDown(table, wordsFound, wordMatrix, mode);
            wp.checkDiagonalFwdUp(table, wordsFound, wordMatrix, mode);
            wp.checkDiagonalBwdDown(table, wordsFound, wordMatrix, mode);
            wp.checkDiagonalBwdUp(table, wordsFound, wordMatrix, mode);
            wp.checkDiagonalFwdDown(table, wordsFound, wordMatrix, mode);


            long endTime = System.currentTimeMillis();

            
            Set < String > unqset = new LinkedHashSet < > (wordsFound);
            wordsFound.clear();
            wordsFound.addAll(unqset);


            System.out.println("\n################################################################\n");
            System.out.println("Output List of Words Found: \n");
            for (String a: wordsFound) {
                System.out.println(a);
            }
            System.out.println("\n################################################################\n");
            System.out.println("Total words found in the puzzle = " + wordsFound.size() + ", Toal time elapsed = " + (endTime - startTime) + " ms");

        }
    }



    // loads the dictionary into hash table
    public void loadDictionary(String path, MyHashTable < String > H, int mode) {
        Scanner s1 = null;
        try {
            s1 = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int wordCount = 0;

        while (s1.hasNext()) {
            String word = s1.nextLine();

            if (mode == 1) {

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < word.length(); i++) {
                    sb.append(word.charAt(i));

                    if (i == word.length() - 1) {
                        H.insert(sb.toString(), true);
                        wordCount++;
                    } else {
                        H.insert(sb.toString(), false);
                    }
                }

            } else {
                H.insert(word, true);
                wordCount++;
            }

        }

        s1.close();
        System.out.println("Dictionary is loaded successfully. Total words count = " + wordCount);
    }




    //Traverse in all direction to check for words in puzzle from dictionary
    public void checkHorizontalFwd(MyHashTable < String > table, ArrayList < String > results, char wordMatrix[][], int mode) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wordMatrix.length; i++) {

            for (int j = 0; j < wordMatrix[i].length; j++) {
                sb.setLength(0);

                for (int k = 0; k + j < wordMatrix[i].length; k++) {
                    sb.append(wordMatrix[i][k + j]);
                    int isWordExists = table.contains(sb.toString());

                    if (isWordExists == 0 && mode == 1) {
                        sb.setLength(0);
                        break;
                    } else if (isWordExists == 1) {

                        results.add(new String(sb));
                    } else {

                    }


                }
            }
        }

    }

    public void checkHorizontalBwd(MyHashTable < String > table, ArrayList < String > results, char wordMatrix[][], int mode) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wordMatrix.length; i++) {

            for (int j = wordMatrix[i].length - 1; j >= 0; j--) {
                sb.setLength(0);

                for (int k = j; k >= 0; k--) {
                    sb.append(wordMatrix[i][k]);

                    if (sb.length() < 2) {
                        continue;
                    }

                    int isWordExists = table.contains(sb.toString());

                    if (isWordExists == 0 && mode == 1) {
                        sb.setLength(0);
                        break;
                    } else if (isWordExists == 1) {

                        results.add(new String(sb));
                    } else {

                    }


                }
            }
        }

    }

    public void checkVerticalDown(MyHashTable < String > table, ArrayList < String > results, char wordMatrix[][], int mode) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wordMatrix[0].length; i++) {

            for (int j = 0; j < wordMatrix.length; j++) {
                sb.setLength(0);


                for (int k = 0; k + j < wordMatrix.length; k++) {
                    sb.append(wordMatrix[k + j][i]);
                    if (sb.length() < 2) {
                        continue;
                    }

                    int isWordExists = table.contains(sb.toString());

                    if (isWordExists == 0 && mode == 1) {
                        sb.setLength(0);
                        break;
                    } else if (isWordExists == 1) {
                        results.add(new String(sb));
                    } else {

                    }

                }
            }
        }

    }

    public void checkVerticalUp(MyHashTable < String > table, ArrayList < String > results, char wordMatrix[][], int mode) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wordMatrix[0].length; i++) {

            for (int j = 0; j < wordMatrix.length; j++) {
                sb.setLength(0);


                for (int k = wordMatrix.length - 1;
                    (k - j) >= 0; k--) {
                    sb.append(wordMatrix[k - j][i]);
                    if (sb.length() < 2) {
                        continue;
                    }

                    int isWordExists = table.contains(sb.toString());

                    if (isWordExists == 0 && mode == 1) {
                        sb.setLength(0);
                        break;
                    } else if (isWordExists == 1) {
                        results.add(new String(sb));
                    } else {

                    }

                }
            }
        }
    }

    public void checkDiagonalFwdUp(MyHashTable < String > table, ArrayList < String > results, char wordMatrix[][], int mode) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordMatrix.length; i++) {
            for (int j = 0; j < wordMatrix[0].length; j++) {
                int z = i;
                sb.setLength(0);
                for (int k = j; k < wordMatrix[0].length; k++) {
                    sb.append(wordMatrix[z][k]);
                    if (sb.length() < 2) {
                        z--;
                        if (z < 0) {
                            break;
                        }
                        continue;
                    }

                    int isWordExists = table.contains(sb.toString());

                    if (isWordExists == 0 && mode == 1) {
                        sb.setLength(0);
                        break;
                    } else if (isWordExists == 1) {
                        results.add(new String(sb));
                    } else {

                    }
                    z--;
                    if (z < 0) {
                        break;
                    }
                }
            }
        }
    }

    public void checkDiagonalBwdDown(MyHashTable < String > table, ArrayList < String > results, char wordMatrix[][], int mode) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordMatrix.length; i++) {
            for (int j = 0; j < wordMatrix[0].length; j++) {
                int z = i;
                sb.setLength(0);
                for (int k = j; k >= 0; k--) {
                    sb.append(wordMatrix[z][k]);
                    if (sb.length() < 2) {
                        z++;
                        if (z > wordMatrix.length - 1) {
                            break;
                        }
                        continue;
                    }
                    int isWordExists = table.contains(sb.toString());

                    if (isWordExists == 0 && mode == 1) {
                        sb.setLength(0);
                        break;
                    } else if (isWordExists == 1) {
                        results.add(new String(sb));
                    } else {

                    }
                    z++;
                    if (z > wordMatrix.length - 1) {
                        break;
                    }
                }
            }
        }
    }

    public void checkDiagonalFwdDown(MyHashTable < String > table, ArrayList < String > results, char wordMatrix[][], int mode) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wordMatrix.length; i++) {
            for (int j = 0; j < wordMatrix[0].length; j++) {
                int z = i;
                sb.setLength(0);
                for (int k = j; k < wordMatrix[0].length; k++) {
                    sb.append(wordMatrix[z][k]);
                    if (sb.length() < 2) {
                        z++;
                        if (z > wordMatrix.length - 1) {
                            break;
                        }
                        continue;
                    }

                    int isWordExists = table.contains(sb.toString());

                    if (isWordExists == 0 && mode == 1) {
                        sb.setLength(0);
                        break;
                    } else if (isWordExists == 1) {
                        results.add(new String(sb));
                    } else {

                    }
                    z++;
                    if (z > wordMatrix.length - 1) {
                        break;
                    }
                }
            }
        }
    }

    public void checkDiagonalBwdUp(MyHashTable < String > table, ArrayList < String > results, char wordMatrix[][], int mode) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wordMatrix.length; i++) {
            for (int j = 0; j < wordMatrix[0].length; j++) {
                int z = i;
                sb.setLength(0);
                for (int k = j; k >= 0; k--) {
                    sb.append(wordMatrix[z][k]);
                    if (sb.length() < 2) {
                        z--;
                        if (z < 0) {
                            break;
                        }
                        continue;
                    }

                    int isWordExists = table.contains(sb.toString());

                    if (isWordExists == 0 && mode == 1) {
                        sb.setLength(0);
                        break;
                    } else if (isWordExists == 1) {
                        results.add(new String(sb));
                    } else {

                    }
                    z--;
                    if (z < 0) {
                        break;
                    }
                }
            }
        }
    }

}