/**
 * Created by suren on 2/5/15.
 */

public class Word implements Comparable<Word>
{
    private String word;
    private String description;
    private int frequency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;

        Word word1 = (Word) o;

        if (!word.equals(word1.word)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Word(String word, String description, int frequency) {

        this.word = word;
        this.description = description;
        this.frequency = frequency;
    }


    @Override
    public int compareTo(Word o) {
        return this.word.compareTo(o.getWord());
    }
}
