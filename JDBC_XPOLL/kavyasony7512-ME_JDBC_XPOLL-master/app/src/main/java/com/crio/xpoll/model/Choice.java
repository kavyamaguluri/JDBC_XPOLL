package com.crio.xpoll.model;

public class Choice {
    private int id;
    private int pollId;
    private String choiceText;

    public Choice(int id, int pollId, String choiceText) {

        if (id < 0 || pollId < 0) {
            throw new IllegalArgumentException("ID and poll ID must be non-negative");
        }

        this.id = id;
        this.pollId = pollId;
        this.choiceText = choiceText;
    }

    public int getId() {
        return id;
    }

    public int getPollId() {
        return pollId;
    }

    public String getChoiceText() {
        return choiceText;
    }

    @Override
    public String toString() {
        return "Choice{" +
               "id=" + id +
               ", choiceText='" + choiceText + '\'' +
               '}';
    }
}
