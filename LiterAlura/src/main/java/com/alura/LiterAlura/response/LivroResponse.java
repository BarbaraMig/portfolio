package com.alura.LiterAlura.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import com.alura.LiterAlura.entities.Livro;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroResponse {

    private int count;
    private String next;
    private String previous;
    private List<Livro> results;

    // Getters e setters
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public String getNext() { return next; }
    public void setNext(String next) { this.next = next; }
    public String getPrevious() { return previous; }
    public void setPrevious(String previous) { this.previous = previous; }
    public List<Livro> getResults() { return results; }
    public void setResults(List<Livro> results) { this.results = results; }

    @Override
    public String toString() {
        return "LivroResponse{" +
                "count=" + count +
                ", results=" + results +
                '}';
    }
}
