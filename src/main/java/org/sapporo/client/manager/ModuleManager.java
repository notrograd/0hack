package org.sapporo.client.manager;

import org.sapporo.client.mods.Module;
import org.sapporo.client.mods.movement.AutoSprint;
import org.sapporo.client.mods.movement.CreativeFly;
import org.sapporo.client.mods.movement.ElytraControl;
import org.sapporo.client.mods.movement.Speed;
import org.sapporo.client.mods.pvp.AutoCrystal;
import org.sapporo.client.mods.pvp.AutoTotem;
import org.sapporo.client.mods.pvp.CrystalAura;
import org.sapporo.client.gui.Hud;
import org.sapporo.client.mods.util.NoFall;
import org.sapporo.client.mods.util.Velocity;

import java.util.ArrayList;


public class ModuleManager {
    public static ArrayList<Module> list = new ArrayList<>();
    public static ArrayList<Module> enabled = new ArrayList<>();


    public static void init() {
        list.add(new AutoSprint());
        list.add(new ElytraControl());
        list.add(new Speed());
        list.add(new AutoTotem());
        list.add(new CrystalAura());
        list.add(new CreativeFly());
        list.add(new NoFall());
        list.add(new AutoCrystal());
        list.add(new Velocity());
        list.add(new Hud());
    }
}
