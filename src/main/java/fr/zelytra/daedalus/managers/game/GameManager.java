package fr.zelytra.daedalus.managers.game;

import fr.zelytra.daedalus.Daedalus;
import fr.zelytra.daedalus.managers.game.scoreboard.ScoreBoardManager;
import fr.zelytra.daedalus.managers.game.settings.DayCycleEnum;
import fr.zelytra.daedalus.managers.game.settings.GameSettings;
import fr.zelytra.daedalus.managers.game.settings.TemplesGenerationEnum;
import fr.zelytra.daedalus.managers.game.time.TimeManager;
import fr.zelytra.daedalus.managers.gods.MinosObject;
import fr.zelytra.daedalus.managers.maze.MazeHandler;
import fr.zelytra.daedalus.managers.team.TeamManager;
import fr.zelytra.daedalus.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GameManager {

    private final TeamManager teamManager;
    private final TimeManager timeManager;
    private final ScoreBoardManager scoreBoardManager;
    private GameStatesEnum state;
    private final MinosObject minos;

    public GameManager() {

        this.teamManager = new TeamManager();
        this.timeManager = new TimeManager();
        this.scoreBoardManager = new ScoreBoardManager(timeManager);
        this.minos = new MinosObject();
        this.state = GameStatesEnum.WAIT;

    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public ScoreBoardManager getScoreBoardManager() {
        return scoreBoardManager;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }

    public GameStatesEnum getState() {
        return state;
    }

    public void setState(GameStatesEnum state) {
        this.state = state;
    }

    public boolean isWaiting() {
        return state == GameStatesEnum.WAIT;
    }

    public boolean isRunning() {
        return state == GameStatesEnum.RUNNING;
    }

    public boolean isFinished() {
        return state == GameStatesEnum.FINISHED;
    }

    public void reverseTempleGeneration() {

        if (GameSettings.GOD_SELECTION == TemplesGenerationEnum.CHOSEN)
            GameSettings.GOD_SELECTION = TemplesGenerationEnum.RANDOM;
        else
            GameSettings.GOD_SELECTION = TemplesGenerationEnum.CHOSEN;

    }

    public boolean increaseGodLimit() {

        if (GameSettings.GOD_LIMIT >= GameSettings.GOD_MAXIMUM)
            return false;
        else
            GameSettings.GOD_LIMIT += 1;

        return true;
    }

    public boolean decreaseGodLimit() {

        if (GameSettings.GOD_LIMIT <= GameSettings.GOD_MINIMUM)
            return false;
        else
            GameSettings.GOD_LIMIT -= 1;

        return true;
    }

    public MinosObject getMinos() {
        return minos;
    }

    private void applySettings() {

        Bukkit.getWorld("world").setTime(GameSettings.DAY_CYCLE.getTicks());
        if (GameSettings.DAY_CYCLE == DayCycleEnum.DEFAULT)
            Bukkit.getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        else
            Bukkit.getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

        if (GameSettings.HARDCORE)
            Bukkit.getWorld("world").setGameRule(GameRule.NATURAL_REGENERATION, false);
        else
            Bukkit.getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);

    }

    // FONCTION DE DEBUT DE PARTIE
    public void start() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            player.getActivePotionEffects().clear();
            player.setMaxHealth(20.0);
        }
        Bukkit.broadcastMessage(Message.getPlayerPrefixe() + "§cStarting generation...");
        Bukkit.getScheduler().runTaskAsynchronously(Daedalus.getInstance(), () -> {
            Location origin = new Location(Bukkit.getWorld("world"), 0, 0, 0);
            origin.setY(Bukkit.getWorld("world").getHighestBlockYAt((int) origin.getX(), (int) origin.getZ()) + 1);
            MazeHandler maze = new MazeHandler(origin, 300, true, Daedalus.getInstance().getStructureManager().getGeneratedList());
            maze.generateScaleMaze();
            getTimeManager().start();
        });

    }

    // FONCTION DE FIN DE PARTIE
    public void stop() {
        getTimeManager().stop();
        this.state = GameStatesEnum.FINISHED;
        //TODO Mettre tout le monde en créatif
        //TODO FX de victoire

    }

}
