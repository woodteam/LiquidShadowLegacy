package net.ccbluex.liquidbounce.features.command.commands;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.special.Setting;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SettingsCommand extends Command {
    public SettingsCommand() {
        super("settings", "setting","config");
    }

    @Override
    public void execute(@NotNull String[] args) {
        if (args.length <= 1) {
            chatSyntax("settings <load/save/list>");
            return;
        }

        switch (args[1]) {
            case "load": {
                if (args.length < 3) {
                    chatSyntax("settings load <name>");
                    return;
                }
                Setting setting = LiquidBounce.settingManager.getSetting(args[2]);
                if (setting != null) {
                    chat("loading setting " + args[2] + ",please wait...");
                    setting.load();
                    LiquidBounce.commandManager.getCommand("reload").execute(new String[]{"reload"});
                    chat("loaded setting " + args[2] + " success!");
                } else {
                    chat("this setting is not exist!");
                    return;
                }
                break;
            }
            case "save": {
                if (args.length < 3) {
                    chatSyntax("settings save <name>");
                    return;
                }
                Setting setting = LiquidBounce.settingManager.getSetting(args[2]);
                if (setting != null) {
                    chat("saving setting " + args[2] + ",please wait...");
                    setting.save();
                    chat("saved setting" + args[2] + " success!");
                } else {
                    chat("saving setting " + args[2] + ",please wait...");
                    LiquidBounce.settingManager.addSetting(new Setting(args[2],true));
                    chat("saved setting" + args[2] + " success!");
                }
                break;
            }
            case "list":
                chat("your settings:");
                for (Setting setting : LiquidBounce.settingManager.getSettings()) {
                    chat(setting.getName());
                }
                break;
        }
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("load","save","list");
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("load")) {
                List<String> settings = new ArrayList<>();
                for (Setting setting : LiquidBounce.settingManager.getSettings()) {
                    settings.add(setting.getName());
                }
                return settings;
            } else if (args[1].equalsIgnoreCase("save")) {
                List<String> settings = new ArrayList<>();
                for (Setting setting : LiquidBounce.settingManager.getSettings()) {
                    settings.add(setting.getName());
                }
                return settings;
            }
        }
        return Collections.emptyList();
    }
}
