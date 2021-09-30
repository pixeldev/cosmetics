package me.pixeldev.ecosmetics.plugin.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;

import me.pixeldev.alya.api.auto.command.AutoCommand;
import me.pixeldev.ecosmetics.plugin.command.cosmetic.GetCosmeticCommand;

@AutoCommand
@SubCommandClasses({GetCosmeticCommand.class})
@Command(names = {"cosmetics", "ecosmetics", "cosmetic"})
public class CosmeticCommands implements CommandClass {

}
