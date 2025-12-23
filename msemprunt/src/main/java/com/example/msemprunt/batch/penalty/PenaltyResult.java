package com.example.msemprunt.batch.penalty;


import com.example.msemprunt.model.Emprunt;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PenaltyResult {

    private Emprunt emprunt;
    private boolean sendOverdueNotification;
    private double penaltyToAdd;
}