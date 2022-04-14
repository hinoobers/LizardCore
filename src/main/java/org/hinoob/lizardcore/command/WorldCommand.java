package org.hinoob.lizardcore.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.hinoob.lizardcore.LizardCore;
import org.hinoob.lizardcore.util.CommandUtil;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@CommandAlias("world")
@CommandPermission("lizardcore.command.world")
public class WorldCommand extends BaseCommand {

    @Default
    @HelpCommand
    @Subcommand("help")
    public void help(CommandSender sender){
        CommandUtil.sendMessage(sender, ChatColor.RED + "Sub-Commands:");
        CommandUtil.sendMessage(sender, ChatColor.RED + "/world create <name> <type> <environment> <generateStructures> [seed]");
        CommandUtil.sendMessage(sender, ChatColor.RED + "/world delete <name>");
        CommandUtil.sendMessage(sender, ChatColor.RED + "/world unload <name>");
        CommandUtil.sendMessage(sender, ChatColor.RED + "/world teleport <name>");
        CommandUtil.sendMessage(sender, ChatColor.RED + "/world clearmobs <name>");
        CommandUtil.sendMessage(sender, ChatColor.RED + "/world clearanimals <name>");
        CommandUtil.sendMessage(sender, ChatColor.RED + "/world clearitems <name>");
    }

    @Subcommand("create")
    @CommandPermission("lizardcore.command.world.create")
    public void create(CommandSender sender, String name, WorldType type, World.Environment environment, boolean generateStructures, @Optional Long seed){
        if(Bukkit.getWorld(name) != null){
            CommandUtil.sendMessage(sender, ChatColor.RED + "A world with that name already exists!");
            return;
        }

        if(new File(Bukkit.getWorldContainer(), name).exists()){
            CommandUtil.sendMessage(sender, ChatColor.RED + "A world with that name already exists, but is unloaded!");
            return;
        }

        WorldCreator creator = new WorldCreator(name);
        creator.type(type);
        creator.environment(environment);
        creator.generateStructures(generateStructures);
        if(seed != null){
            creator.seed(seed);
        }
        creator.createWorld();
        CommandUtil.sendMessage(sender, ChatColor.GREEN + "World created!");
    }

    @Subcommand("unload")
    @CommandPermission("lizardcore.command.world.unload")
    public void unload(CommandSender sender, String name){
        World world = Bukkit.getWorld(name);
        if(world != null){
            Bukkit.unloadWorld(name, false);
            CommandUtil.sendMessage(sender, ChatColor.GREEN + "World unloaded!");
        }else{
            CommandUtil.sendMessage(sender, ChatColor.RED + "A world with that name does not exist / is already unloaded!");
        }
    }

    @Subcommand("teleport")
    @CommandPermission("lizardcore.command.world.teleport")
    public void teleport(Player player, String name){
        World world = Bukkit.getWorld(name);
        if(world != null){
            player.sendMessage(ChatColor.GREEN + "Teleporting...");
            player.teleport(world.getSpawnLocation());
        }else{
            player.sendMessage(ChatColor.RED + "A world with that name does not exist!");
        }
    }

    @Subcommand("delete")
    @CommandPermission("lizardcore.command.world.delete")
    public void delete(CommandSender sender, String name){
        World world = Bukkit.getWorld(name);
        if(world != null){
            Bukkit.unloadWorld(name, false);
            world.getWorldFolder().delete();
            CommandUtil.sendMessage(sender, ChatColor.GREEN + "World deleted!");
        }else{
            File worldFolder = new File(Bukkit.getWorldContainer(), name);
            if(worldFolder.exists()){
                CommandUtil.sendMessage(sender, ChatColor.GREEN + "Detected a unloaded world, deleting...");
                worldFolder.delete();
                CommandUtil.sendMessage(sender, ChatColor.GREEN + "World deleted!");
            }else{
                CommandUtil.sendMessage(sender, ChatColor.RED + "A world with that name does not exist!");
            }
        }
    }

    @Subcommand("clearmobs")
    @CommandPermission("lizardcore.command.world.clearmobs")
    public void clearMobs(CommandSender sender, String name){
        World world = Bukkit.getWorld(name);
        if(world != null){
            world.getLivingEntities().stream().filter(entity -> entity instanceof Monster).forEach(Entity::remove);
            CommandUtil.sendMessage(sender, ChatColor.GREEN + "Mobs cleared!");
        }else{
            CommandUtil.sendMessage(sender, ChatColor.RED + "A world with that name does not exist!");
        }
    }

    @Subcommand("clearanimals")
    @CommandPermission("lizardcore.command.world.clearanimals")
    public void clearanimals(CommandSender sender, String name){
        World world = Bukkit.getWorld(name);
        if(world != null){
            List<Entity> entities = world.getLivingEntities().stream().filter(entity -> entity instanceof Animals).collect(Collectors.toList());
            entities.forEach(Entity::remove);

            CommandUtil.sendMessage(sender, ChatColor.GREEN + "Animals cleared!");
        }else{
            CommandUtil.sendMessage(sender, ChatColor.RED + "A world with that name does not exist!");
        }
    }

    @Subcommand("clearitems")
    @CommandPermission("lizardcore.command.world.clearitems")
    public void clearItems(CommandSender sender, String name){
        World world = Bukkit.getWorld(name);
        if(world != null){
            world.getEntities().stream().filter(entity -> entity instanceof Item).forEach(Entity::remove);
            CommandUtil.sendMessage(sender, ChatColor.GREEN + "Items cleared!");
        }else{
            CommandUtil.sendMessage(sender, ChatColor.RED + "A world with that name does not exist!");
        }
    }

    @Subcommand("list")
    @CommandPermission("lizardcore.command.world.list")
    public void list(CommandSender sender){
        if (Bukkit.getWorlds().isEmpty()) {
            CommandUtil.sendMessage(sender, ChatColor.RED + "There are no worlds!");
            return;
        }

        CommandUtil.sendMessage(sender, ChatColor.GREEN + "Displaying a total of " + ChatColor.YELLOW + LizardCore.getInstance().getServer().getWorlds().size() + ChatColor.GREEN + " world(s).");

        for(World world : LizardCore.getInstance().getServer().getWorlds()){
            if(sender instanceof Player){
                CommandUtil.sendMessage(sender, ChatColor.GREEN + "World: " + ChatColor.YELLOW + world.getName() + (((Player) sender).getPlayer().getWorld().equals(world) ? ChatColor.GREEN + " (You are here)" : ""));
            }else{
                CommandUtil.sendMessage(sender, ChatColor.GREEN + "World: " + ChatColor.YELLOW + world.getName());
            }
        }
    }


}
