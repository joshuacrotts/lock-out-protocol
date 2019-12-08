package com.dsd.game.core;

/**
 * Text/numerical representation of the difficulty.
 *
 * @group [Data Structure Deadheads]
 *
 * @author Joshua, Rinty, Ronald
 *
 * @updated 11/30/19
 */
public enum DifficultyType {

    EASY(1), MEDIUM(2), HARD(3);

    private final int difficultyLevel;
    private String difficultyString;

    private DifficultyType(int _difficultyLevel) {
        this.difficultyLevel = _difficultyLevel;
        this.setDifficultyString(this.difficultyLevel);
    }

    /**
     * Depending on the difficulty int, we will set the labels of the enums to
     * their respective titles.
     *
     * @param _difficultyLevel
     */
    private void setDifficultyString(int _difficultyLevel) {
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
                break;
            default:
                throw new IllegalStateException("Invalid difficulty number!");
        }
        this.difficultyString = text;
    }

//==================== GETTERS ==========================
    public String getDifficultyLabel() {
        return this.difficultyString;
    }

    public int getDifficultyNumber() {
        return this.difficultyLevel;
    }

}
