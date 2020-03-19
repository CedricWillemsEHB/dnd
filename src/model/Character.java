package model;

public abstract class Character {
    private String name;
    private String background;
    private String alignment;
    private int experiencePoints;
    private int strenght;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private int strenghtSavingThrows;
    private int dexteritySavingThrows;
    private int constitutionSavingThrows;
    private int intelligenceSavingThrows;
    private int wisdomSavingThrows;
    private int charismaSavingThrows;
    private Skills skills;
    private Class cClass;

    public Character(String name, String background, String alignment, int experiencePoints, int strenght, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int strenghtSavingThrows, int dexteritySavingThrows, int constitutionSavingThrows, int intelligenceSavingThrows, int wisdomSavingThrows, int charismaSavingThrows, Skills skills) {
        this.name = name;
        this.background = background;
        this.alignment = alignment;
        this.experiencePoints = experiencePoints;
        this.strenght = strenght;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.strenghtSavingThrows = strenghtSavingThrows;
        this.dexteritySavingThrows = dexteritySavingThrows;
        this.constitutionSavingThrows = constitutionSavingThrows;
        this.intelligenceSavingThrows = intelligenceSavingThrows;
        this.wisdomSavingThrows = wisdomSavingThrows;
        this.charismaSavingThrows = charismaSavingThrows;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getStrenght() {
        return strenght;
    }

    public void setStrenght(int strenght) {
        this.strenght = strenght;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getStrenghtSavingThrows() {
        return strenghtSavingThrows;
    }

    public void setStrenghtSavingThrows(int strenghtSavingThrows) {
        this.strenghtSavingThrows = strenghtSavingThrows;
    }

    public int getDexteritySavingThrows() {
        return dexteritySavingThrows;
    }

    public void setDexteritySavingThrows(int dexteritySavingThrows) {
        this.dexteritySavingThrows = dexteritySavingThrows;
    }

    public int getConstitutionSavingThrows() {
        return constitutionSavingThrows;
    }

    public void setConstitutionSavingThrows(int constitutionSavingThrows) {
        this.constitutionSavingThrows = constitutionSavingThrows;
    }

    public int getIntelligenceSavingThrows() {
        return intelligenceSavingThrows;
    }

    public void setIntelligenceSavingThrows(int intelligenceSavingThrows) {
        this.intelligenceSavingThrows = intelligenceSavingThrows;
    }

    public int getWisdomSavingThrows() {
        return wisdomSavingThrows;
    }

    public void setWisdomSavingThrows(int wisdomSavingThrows) {
        this.wisdomSavingThrows = wisdomSavingThrows;
    }

    public int getCharismaSavingThrows() {
        return charismaSavingThrows;
    }

    public void setCharismaSavingThrows(int charismaSavingThrows) {
        this.charismaSavingThrows = charismaSavingThrows;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }
}
