package com.blobplop.collector.entities;

import jakarta.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ratingId;

    @Column
    private int numberOfVotes;

    @Column
    private int score;

    @Column
    private long userId;

    @Column
    private double totalScore;

    public Rating() {
    }

    public Rating(int numberOfVotes, double totalScore) {
        this.numberOfVotes = numberOfVotes;
        this.totalScore = totalScore;
    }

    public long getRatingId() {
        return ratingId;
    }

    public void setRatingId(long ratingId) {
        this.ratingId = ratingId;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getRating() {
        return numberOfVotes > 0 ? totalScore / numberOfVotes : 0;
    }
}