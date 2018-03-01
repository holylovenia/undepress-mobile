package com.hulahoop.mentalhealth.undepress.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by agoun on 3/1/2018.
 */

public class Symptoms implements Serializable {
    private ArrayList<Integer> sum;
    private ArrayList<String> symptomDescription;
    private final String indicator1 = "Depressed mood nearly every day";
    private final String indicator2 = "Losing interest or pleasure in all activities";
    private final String indicator3 = "Significant weight gain or loss";
    private final String indicator4 = "Insomnia or hypersomnia";
    private final String indicator5 = "Psychomotor agitation or retardation";
    private final String indicator6 = "Loss of energy or fatigue";
    private final String indicator7 = "Excessive or inappropriate feelings of worthlessness or guilt";
    private final String indicator8 = "Unable to concentrate or indecisiveness";
    private final String indicator9 = "Recurrent thoughts of death";

    public Symptoms(ArrayList<Integer> sum) {
        this.sum = sum;
        this.symptomDescription = new ArrayList<>();
        symptomDescription.add(indicator1);
        symptomDescription.add(indicator2);
        symptomDescription.add(indicator3);
        symptomDescription.add(indicator4);
        symptomDescription.add(indicator5);
        symptomDescription.add(indicator6);
        symptomDescription.add(indicator7);
        symptomDescription.add(indicator8);
        symptomDescription.add(indicator9);
    }

    public ArrayList<Integer> getSum() {
        return sum;
    }

    public void setSum(ArrayList<Integer> sum) {
        this.sum = sum;
    }

    public ArrayList<String> getSymptomDescription() {
        return symptomDescription;
    }

    public void setSymptomDescription(ArrayList<String> symptomDescription) {
        this.symptomDescription = symptomDescription;
    }
}
