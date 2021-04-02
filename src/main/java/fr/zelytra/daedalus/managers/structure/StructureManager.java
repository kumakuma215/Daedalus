package fr.zelytra.daedalus.managers.structure;

import fr.zelytra.daedalus.managers.loottable.LootTableManager;
import fr.zelytra.daedalus.managers.game.settings.GameSettings;
import fr.zelytra.daedalus.managers.game.settings.TemplesGenerationEnum;
import fr.zelytra.daedalus.managers.gods.GodsEnum;
import fr.zelytra.daedalus.managers.maze.Maze;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.HashMap;

public class StructureManager {
    private ArrayList<Structure> temples;
    private ArrayList<Structure> dungeons;
    private ArrayList<Structure> builds;
    private ArrayList<Structure> fixedStructures;
    private ArrayList<Structure> mine;
    private ArrayList<Structure> generatedList;
    private HashMap<BoundingBox,Structure> structuresPosition;
    private Maze maze;
    private LootTableManager lootTableManager;


    public StructureManager() {
        initialize();
        randomGenerationList();
        this.lootTableManager = new LootTableManager();
    }

    public LootTableManager getLootTableManager() {
        return lootTableManager;
    }

    private void initialize() {
        this.structuresPosition = new HashMap<>();
        this.temples = new ArrayList<>();
        this.dungeons = new ArrayList<>();
        this.builds = new ArrayList<>();
        this.mine = new ArrayList<>();
        this.fixedStructures = new ArrayList<>();

        /*Temples init*/
        this.temples.add(new Structure(StructureEnum.ZEUS));
        this.temples.add(new Structure(StructureEnum.APHRODITE));
        this.temples.add(new Structure(StructureEnum.ARES));
        this.temples.add(new Structure(StructureEnum.ARTEMIS));
        this.temples.add(new Structure(StructureEnum.ATHENA));
        this.temples.add(new Structure(StructureEnum.DEMETER));
        this.temples.add(new Structure(StructureEnum.DIONYSOS));
        this.temples.add(new Structure(StructureEnum.HADES));
        this.temples.add(new Structure(StructureEnum.HERMES));
        this.temples.add(new Structure(StructureEnum.POSEIDON));

        /*Dungeons init*/
        this.dungeons.add(new Structure(StructureEnum.DUNGEON1));

        /*Fixed structures init*/
        this.fixedStructures.add(new Structure(StructureEnum.TEAM_RED));
        this.fixedStructures.add(new Structure(StructureEnum.TEAM_BLUE));
        this.fixedStructures.add(new Structure(StructureEnum.TEAM_GREEN));
        this.fixedStructures.add(new Structure(StructureEnum.TEAM_YELLOW));
        this.fixedStructures.add(new Structure(StructureEnum.MINOTAURE));

        /*Mines init*/
        this.mine.add(new Structure(StructureEnum.MINE1));
        this.mine.add(new Structure(StructureEnum.MINE1));
        this.mine.add(new Structure(StructureEnum.MINE1));

        /*Dungeon init*/
        this.dungeons.add(new Structure(StructureEnum.DUNGEON1));
    }

    private void randomGenerationList() {
        this.generatedList = new ArrayList<>();

        /*Fixed structures*/
        for (Structure structure : this.fixedStructures) {
            generatedList.add(structure);
        }

        /*Mines draw*/
        for (int x = 0; x < GameSettings.MINES_COUNT; x++) {
            //int random = 0 + (int) (Math.random() * ((this.mine.size() - 0) + 1));
            generatedList.add(this.mine.get(0));
        }

        /*Temples draw*/
        if (GameSettings.GOD_SELECTION == TemplesGenerationEnum.RANDOM) {
            for (int x = 0; x < GameSettings.GOD_LIMIT; x++) {
                //int random = 0 + (int) (Math.random() * ((this.temples.size() - 0) + 1));
                generatedList.add(this.temples.get(x));
            }
        } else {
            for (GodsEnum god : GameSettings.GOD_LIST) {
                for (Structure temple : this.temples) {
                    if (temple.getGod() == god) {
                        generatedList.add(temple);
                    }
                }
            }
        }
        /*Dungeons draw*/
        for (int x = 0; x < GameSettings.DUNGEONS_COUNT; x++) {
            //int random = 0 + (int) (Math.random() * ((this.dungeons.size() - 0) + 1));
            generatedList.add(this.dungeons.get(0));
        }

        /*Circee draw*/
        for (int x = 0; x < GameSettings.CIRCEE_ISLANDS_COUNT; x++) {
            generatedList.add(new Structure(StructureEnum.CIRCEE_ISLAND));
        }

        /*Hesperide draw*/
        for (int x = 0; x < GameSettings.HESPERIDES_GARDEN_COUNT; x++) {
            generatedList.add(new Structure(StructureEnum.HESPERIDES_GARDEN));
        }

    }

    public ArrayList<Structure> getGeneratedList() {
        return this.generatedList;
    }

    public void setStructuresPosition(HashMap<BoundingBox, Structure> structuresPosition) {
        this.structuresPosition = structuresPosition;
    }

    public HashMap<BoundingBox, Structure> getStructuresPosition() {
        return structuresPosition;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public Maze getMaze() {
        return maze;
    }
}