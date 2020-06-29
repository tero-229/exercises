package com.example.demo;

public class Value {
    private long id;
    private String joke;
    private String[] categories;
    public Value (){
    }
    public long getId() {
        return id;
    }
    public String getJoke() {
        return joke;
    }
    public String[] getCategories() {
        return categories;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setJoke(String joke) {
        this.joke = joke;
    }
    public void setCategories(String[] categories) {
        this.categories = categories;
    }
}
