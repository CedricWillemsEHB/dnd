package model;

public abstract class Class {
    private String name;
    private String description;
    private int hitDie;
    private String primaryAbility;
    private String savingThrowProficiencies;
    private String armorAndWeaponProficiencies;

    public Class(String name, String description, int hitDie, String primaryAbility, String savingThrowProficiencies, String armorAndWeaponProficiencies) {
        this.name = name;
        this.description = description;
        this.hitDie = hitDie;
        this.primaryAbility = primaryAbility;
        this.savingThrowProficiencies = savingThrowProficiencies;
        this.armorAndWeaponProficiencies = armorAndWeaponProficiencies;
    }

    public String getPrimaryAbility() {
        return primaryAbility;
    }

    public String getSavingThrowProficiencies() {
        return savingThrowProficiencies;
    }
}
