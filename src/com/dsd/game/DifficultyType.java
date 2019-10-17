package com.dsd.game;

/**
 * Text/numerical representation of the difficulty.
 *
 * @author Joshua
 */
public enum DifficultyType {
    EASY(1), MEDIUM(2), HARD(3);

    private final int difficultyLevel;
    private String difficultyString;

    private DifficultyType (int _difficultyLevel) {
        this.difficultyLevel = _difficultyLevel;
        this.setDifficultyString(this.difficultyLevel);
    }

    /**
     * Depending on the difficulty int, we will set the labels of the enums to
     * their respective titles.
     *
     * @param _difficultyLevel
     */
    private void setDifficultyString (int _difficultyLevel) {
        String text = "";

        switch (_difficultyLevel) {
            case 1:
                text = "I\'M TOO YOUNG TO DIE";
                break;
            case 2:
                text = "HURT ME PLENTY";
                break;
            case 3:
                text = "NIGHTMARE";
        }

        this.difficultyString = text;
    }

//==================== GETTERS ==========================//
    public String getDifficultyLabel () {
        return this.difficultyString;
    }

    public int getDifficultyNumber () {
        return this.difficultyLevel;
    }
}
