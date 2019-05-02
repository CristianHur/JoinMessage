/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joinmessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


/**
 *
 * @author byDekei
 */

public class JoinMessage extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
    }
    
    @EventHandler
    public void Join(PlayerJoinEvent e)
    {            
        Player jugador = e.getPlayer();
        
        int cantidadlineas = getConfig().getInt("Lines");
                
        for(int i = 0; i < cantidadlineas; i++)
            jugador.sendMessage(getConfig().getString("Message-"+(i+1)).replace("&", "§").replaceAll("%player%", e.getPlayer().getName()));
    }
    
    @Override
    public boolean onCommand(CommandSender Envio, Command Comando, String label, String[] args)
    {
        Player Jugador = (Player) Envio;
        
        if (Comando.getName().equalsIgnoreCase("jm") || label.equalsIgnoreCase("joinmessage")) 
        {
            if(args.length > 0)
            {
                if(args[0].equalsIgnoreCase("reload"))
                {
                    if(Jugador.hasPermission("jm.reload") || Jugador.hasPermission("jm.*"))
                    {
                        reloadConfig();
                        Jugador.sendMessage("JoinMessage reloaded.");
                        
                        return true;
                    }
                    else
                    {
                        Jugador.sendMessage(getConfig().getString("NoPermission").replace("&", "§"));
                        return true;
                    }
                }
                else
                    if(args[0].equalsIgnoreCase("lines"))
                    {
                        if(Jugador.hasPermission("jm.lines") || Jugador.hasPermission("jm.*"))
                        {
                            if(args.length == 1)
                            {
                                Jugador.sendMessage("Type a number of lines.".replace("&", "§"));
                                Jugador.performCommand("jm");
                                return true;  
                            }
                            else
                            {
                                int l;
                                
                                l = Integer.parseInt(args[1]);                         
                            
                                this.getConfig().set("Lines",l);
                                this.saveConfig();
                                this.reloadConfig();
                                Jugador.sendMessage("&f&lLines changed to ".replace("&", "§") + l);
                                return true;
                            }
                        }
                        else
                        {
                            Jugador.sendMessage(getConfig().getString("NoPermission").replace("&", "§"));
                            return true;
                        }
                    }
                    else
                        if(args[0].equalsIgnoreCase("message"))
                        {
                            if(Jugador.hasPermission("jm.message") || Jugador.hasPermission("jm.*"))
                            {
                                if(args.length == 1)
                                {
                                    Jugador.sendMessage("Type a number of message and a message.".replace("&", "§"));
                                    Jugador.performCommand("jm");
                                    return true;  
                                }
                                else
                                    if(args.length == 2)
                                    {
                                        Jugador.sendMessage("Write a message".replace("&", "§"));
                                        Jugador.performCommand("jm");
                                        return true;  
                                    }
                                    else
                                    {
                                        String m = args[2];
                                        
                                        for(int i = 3; i < args.length; i++)
                                        {
                                            m = m + " " + args[i];
                                        }
                                        
                                        this.getConfig().set("Message-"+args[1],m.replace("&", "§"));
                                        this.saveConfig();
                                        this.reloadConfig();
                                        Jugador.sendMessage("&f&lMessage of Line ".replace("&", "§") + args[1] + " changed.");
                                    }
                            
                                return true;
                            }
                            else
                            {
                                Jugador.sendMessage(getConfig().getString("NoPermission").replace("&", "§"));
                                return true;
                            }                            
                        }
                        else
                        {
                            Jugador.sendMessage("&e[JoinMessage] &cThis command does not exist.".replace("&", "§"));
                            Jugador.performCommand("jm");
                    
                            return true;
                        }
            }
            else
                if(Jugador.hasPermission("jm.help") || Jugador.hasPermission("jm.*"))
                {
                    String titulo, help, reload, lines, message;
            
                    titulo = "&6&lJoinMessage";
                    help = "&e&l/jm &b&lShow JoinMessage commands";
                    reload = "&e&l/jm reload &b&lReload JoinMessage";
                    lines = "&e&l/jm lines <lines> &b&lLines to be shown.";
                    message = "&e&l/jm message <line> <message> &b&lModifies the message of the indicated line.";
            
                    Jugador.sendMessage(titulo.replace("&", "§"));
                    Jugador.sendMessage(help.replace("&", "§"));
                    Jugador.sendMessage(reload.replace("&", "§"));
                    Jugador.sendMessage(lines.replace("&", "§"));
                    Jugador.sendMessage(message.replace("&", "§"));
                    
                    return true;
                }
                else
                {
                    Jugador.sendMessage(getConfig().getString("NoPermission").replace("&", "§"));
                    return true;
                }
                    
        }

        return false;
    }

}