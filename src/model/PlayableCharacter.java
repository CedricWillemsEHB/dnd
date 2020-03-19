package model;

public class PlayableCharacter extends Character {

    public PlayableCharacter(String name, String background, String alignment, int experiencePoints, int strenght, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int strenghtSavingThrows, int dexteritySavingThrows, int constitutionSavingThrows, int intelligenceSavingThrows, int wisdomSavingThrows, int charismaSavingThrows, Skills skills) {
        super(name, background, alignment, experiencePoints, strenght, dexterity, constitution, intelligence, wisdom, charisma, strenghtSavingThrows, dexteritySavingThrows, constitutionSavingThrows, intelligenceSavingThrows, wisdomSavingThrows, charismaSavingThrows, skills);
    }
}
