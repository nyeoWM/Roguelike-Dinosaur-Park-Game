package game.skills;

/**
 * Enum to keep track of the type of dino. Useful as a switch to decide parameters
 * for a certain behaviour like hungerBehaviour that looks for different kinds of
 * food sources depending on the dino calling it
 */
public enum DinoType {
    PROTOCERATOPS, VELOCIRAPTOR, FISH, PLESIOSAURS, PTERANODONS, TREX, REAREDTREX
}