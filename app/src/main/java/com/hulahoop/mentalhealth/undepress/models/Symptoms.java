package com.hulahoop.mentalhealth.undepress.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by agoun on 3/1/2018.
 */

public class Symptoms implements Serializable {
    private ArrayList<Integer> sum;
    private ArrayList<String> symptomDescription;

    public Symptoms(ArrayList<Integer> sum, ArrayList<String> symptomDescription) {
        this.sum = sum;
        this.symptomDescription = symptomDescription;
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
