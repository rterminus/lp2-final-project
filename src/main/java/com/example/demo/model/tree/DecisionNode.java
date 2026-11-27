package com.example.demo.model.tree;

// Liga um cenário aos seus possíveis caminhos à esquerda e à direita
public class DecisionNode {
    private final Scenario scenario;
    private final String leftOption;
    private final String rightOption;
    private final DecisionNode left;
    private final DecisionNode right;

    public DecisionNode(Scenario scenario, String leftOption, String rightOption, DecisionNode left, DecisionNode right) {
        if (scenario == null) {
            throw new IllegalArgumentException("Scenario cannot be null.");
        }
        validateOption(left, leftOption, "Left");
        validateOption(right, rightOption, "Right");

        this.scenario = scenario;
        this.leftOption = leftOption;
        this.rightOption = rightOption;
        this.left = left;
        this.right = right;
    }

    // Cada lado só tem uma opção de escolha se houver um filho correspondente.
    private static void validateOption(DecisionNode child, String option, String side) {
        if (child == null && option != null) {
            throw new IllegalArgumentException(side + " option must be null when there is no child.");
        }
        if (child != null && (option == null || option.isBlank())) {
            throw new IllegalArgumentException(side + " option cannot be null or blank when there is a child.");
        }
    }

    public Scenario getScenario() {
        return scenario;
    }

    public String getLeftOption() {
        return leftOption;
    }

    public String getRightOption() {
        return rightOption;
    }

    public DecisionNode getLeft() {
        return left;
    }

    public DecisionNode getRight() {
        return right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
