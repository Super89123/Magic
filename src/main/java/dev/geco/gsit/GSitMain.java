package dev.geco.gsit;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.*;

import dev.geco.gsit.api.event.*;
import dev.geco.gsit.cmd.*;
import dev.geco.gsit.cmd.tab.*;
import dev.geco.gsit.events.*;
import dev.geco.gsit.events.features.*;

import dev.geco.gsit.manager.*;
import dev.geco.gsit.manager.mm.*;
import dev.geco.gsit.util.*;
import org.super89.supermegamod.magic.Magic;

public class GSitMain extends JavaPlugin {

    private SVManager svManager;
    public SVManager getSVManager() { return svManager; }

    private CManager cManager;
    public CManager getCManager() { return cManager; }

    private DManager dManager;
    public DManager getDManager() { return dManager; }

    private SitManager sitManager;
    public SitManager getSitManager() { return sitManager; }

    private PlayerSitManager playerSitManager;
    public PlayerSitManager getPlayerSitManager() { return playerSitManager; }

    private PoseManager poseManager;
    public PoseManager getPoseManager() { return poseManager; }

    private CrawlManager crawlManager;
    public CrawlManager getCrawlManager() { return crawlManager; }

    private ToggleManager toggleManager;
    public ToggleManager getToggleManager() { return toggleManager; }

    private UManager uManager;
    public UManager getUManager() { return uManager; }

    private PManager pManager;
    public PManager getPManager() { return pManager; }

    private TManager tManager;
    public TManager getTManager() { return tManager; }

    private MManager mManager;
    public MManager getMManager() { return mManager; }

    private PassengerUtil passengerUtil;
    public PassengerUtil getPassengerUtil() { return passengerUtil; }

    private EnvironmentUtil environmentUtil;
    public EnvironmentUtil getEnvironmentUtil() { return environmentUtil; }

    private IEntityUtil entityUtil;
    public IEntityUtil getEntityUtil() { return entityUtil; }

    private IPackageUtil packageUtil;
    public IPackageUtil getPackageUtil() { return packageUtil; }



    private boolean supportsPaperFeature = false;
    public boolean supportsPaperFeature() { return supportsPaperFeature; }

    private boolean supportsTaskFeature = false;
    public boolean supportsTaskFeature() { return supportsTaskFeature; }

    public final String NAME = "GSit";

    public final String RESOURCE = "62325";

    private static GSitMain GPM;

    public static GSitMain getInstance() { return GPM; }

    private void loadSettings(CommandSender Sender) {

        if(!connectDatabase(Sender)) return;

        getToggleManager().createTable();

        if(getPackageUtil() != null) getPackageUtil().registerPlayers();
    }

    private void linkBStats() {


        getSitManager().resetFeatureUsedCount();
        getPlayerSitManager().resetFeatureUsedCount();
        getPoseManager().resetFeatureUsedCount();
        getCrawlManager().resetFeatureUsedCount();
    }

    public void onLoad() {

        GPM = this;

        svManager = new SVManager(getInstance());
        cManager = new CManager(getInstance());
        dManager = new DManager(getInstance());
        uManager = new UManager(getInstance());
        pManager = new PManager(getInstance());
        tManager = new TManager(getInstance());
        sitManager = new SitManager(getInstance());
        playerSitManager = new PlayerSitManager(getInstance());
        poseManager = new PoseManager(getInstance());
        crawlManager = new CrawlManager(Magic.getPlugin());
        toggleManager = new ToggleManager(getInstance());

        passengerUtil = new PassengerUtil();
        environmentUtil = new EnvironmentUtil(getInstance());



        mManager = supportsPaperFeature() && getSVManager().isNewerOrVersion(18, 2) ? new MPaperManager(getInstance()) : new MSpigotManager(getInstance());
    }

    public void onEnable() {

        if(!versionCheck()) return;

        entityUtil = getSVManager().isNewerOrVersion(17, 0) ? (IEntityUtil) getSVManager().getPackageObject("util.EntityUtil") : new EntityUtil();
        packageUtil = getSVManager().isNewerOrVersion(17, 0) ? (IPackageUtil) getSVManager().getPackageObject("util.PackageUtil") : null;

        loadSettings(Bukkit.getConsoleSender());

        setupCommands();
        setupEvents();
        linkBStats();

        getMManager().sendMessage(Bukkit.getConsoleSender(), "Plugin.plugin-enabled");


        getUManager().checkForUpdates();
    }

    public void onDisable() {

        unload();
        getMManager().sendMessage(Bukkit.getConsoleSender(), "Plugin.plugin-disabled");
    }

    private void unload() {

        getDManager().close();
        getSitManager().clearSeats();
        getPlayerSitManager().clearSeats();
        getPoseManager().clearPoses();
        getCrawlManager().clearCrawls();


        if(getPackageUtil() != null) getPackageUtil().unregisterPlayers();
    }

    private void setupCommands() {

        getCommand("gsit").setExecutor(new GSitCommand(getInstance()));
        getCommand("gsit").setTabCompleter(new GSitTabComplete(getInstance()));
        getCommand("glay").setExecutor(new GLayCommand(getInstance()));
        getCommand("glay").setTabCompleter(new EmptyTabComplete());
        getCommand("glay").setPermissionMessage(getMManager().getMessage("Messages.command-permission-error"));
        getCommand("gbellyflop").setExecutor(new GBellyFlopCommand(getInstance()));
        getCommand("gbellyflop").setTabCompleter(new EmptyTabComplete());
        getCommand("gbellyflop").setPermissionMessage(getMManager().getMessage("Messages.command-permission-error"));
        getCommand("gspin").setExecutor(new GSpinCommand(getInstance()));
        getCommand("gspin").setTabCompleter(new EmptyTabComplete());
        getCommand("gspin").setPermissionMessage(getMManager().getMessage("Messages.command-permission-error"));
        getCommand("gcrawl").setExecutor(new GCrawlCommand(getInstance()));
        getCommand("gcrawl").setTabCompleter(new GCrawlTabComplete(getInstance()));
        getCommand("gsitreload").setExecutor(new GSitReloadCommand(getInstance()));
        getCommand("gsitreload").setTabCompleter(new EmptyTabComplete());
        getCommand("gsitreload").setPermissionMessage(getMManager().getMessage("Messages.command-permission-error"));
    }

    private void setupEvents() {



        Listener entityEvents = getSVManager().isNewerOrVersion(17, 0) ? (Listener) getSVManager().getPackageObject("events.EntityEvents", getInstance()) : null;
        if(entityEvents == null) entityEvents = (Listener) getSVManager().getLegacyPackageObject("events.EntityEvents", getInstance());
        if(entityEvents != null) getServer().getPluginManager().registerEvents(entityEvents, getInstance());

        getServer().getPluginManager().registerEvents(new SpinConfusionEvent(getInstance()), getInstance());
    }





    public void reload(CommandSender Sender) {

        Bukkit.getPluginManager().callEvent(new GSitReloadEvent(getInstance()));

        getCManager().reload();
        getMManager().loadMessages();

        unload();

        loadSettings(Sender);

        getUManager().checkForUpdates();
    }

    private boolean connectDatabase(CommandSender Sender) {

        boolean connect = getDManager().connect();

        if(connect) return true;

        getMManager().sendMessage(Sender, "Plugin.plugin-data");

        Bukkit.getPluginManager().disablePlugin(getInstance());

        return false;
    }

    private boolean versionCheck() {

        if(!getSVManager().isNewerOrVersion(16, 0) || (getSVManager().isNewerOrVersion(17, 0) && !getSVManager().isAvailable())) {

            getMManager().sendMessage(Bukkit.getConsoleSender(), "Plugin.plugin-version", "%Version%", getSVManager().getServerVersion());

            getUManager().checkForUpdates();

            Bukkit.getPluginManager().disablePlugin(getInstance());

            return false;
        }

        return true;
    }

}