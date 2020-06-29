package com.example.demo;

import java.util.List;

public class Joke {
    private String type;
    private JokeInner value;

    public Joke() {}

    public Joke(String joke) {
        this.value = new JokeInner(joke);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JokeInner getValue() {
        return value;
    }

    public void setValue(JokeInner value) {
        this.value = value;
    }

    class JokeInner {
        private String id;
        private String joke;
        private List<String> categories;

        public JokeInner() {}

        public JokeInner(String joke) {
            this.joke = joke;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJoke() {
            return joke;
        }

        public void setJoke(String joke) {
            this.joke = joke;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }
    }
}
