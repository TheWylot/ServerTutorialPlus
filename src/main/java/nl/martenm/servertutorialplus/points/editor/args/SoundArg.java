package nl.martenm.servertutorialplus.points.editor.args;

import net.md_5.bungee.api.ChatColor;
import nl.martenm.servertutorialplus.helpers.dataholders.PlayerSound;
import nl.martenm.servertutorialplus.language.Lang;
import nl.martenm.servertutorialplus.objects.ServerTutorial;
import nl.martenm.servertutorialplus.points.ServerTutorialPoint;
import nl.martenm.servertutorialplus.points.editor.PointArg;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;

/**
 * @author MartenM
 * @since 29-11-2017.
 */
public class SoundArg extends PointArg {

    public SoundArg() {
        super("sound");
    }

    @Override
    public boolean run(ServerTutorial serverTutorial, ServerTutorialPoint point, CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Lang.WRONG_COMMAND_FORMAT + "/st editpoint <t> <p> sound <set/clear> (Sound, volume, pitch");
            return false;
        }

        switch (args[0]) {
            case "clear":
                point.setSoundInfo(null);
                break;
            case "set":
                if (args.length < 4) {
                    sender.sendMessage(Lang.WRONG_COMMAND_FORMAT + "/st editpoint <t> <p> sound <set/clear> (Sound, volume, pitch");
                    return false;
                }

                Sound sound = null;

                try {
                    // loc, sound, volume, pitch <-- I forget that all the damm time.
                    sound = Sound.valueOf(args[1].toUpperCase());
                    float volume = Float.parseFloat(args[2]);
                    float pitch = Float.parseFloat(args[3]);

                    PlayerSound soundInfo = new PlayerSound(sound, pitch, volume);
                    point.setSoundInfo(soundInfo);
                } catch (IllegalArgumentException ex) {
                    if (sound == null) {
                        sender.sendMessage(ChatColor.RED + "Unknown enum type for SOUND. Did you mean this?");
                        StringBuilder builder = new StringBuilder();
                        for (Sound s : Sound.values()) {
                            if (s.toString().contains(args[1].toUpperCase())) {
                                builder.append(s.toString());
                                builder.append(" ");
                            }
                        }
                        sender.sendMessage(builder.toString());
                    } else {
                        sender.sendMessage(Lang.WRONG_COMMAND_FORMAT + "/st editpoint <t> <p> sound <set/clear> (Sound, volume, pitch");
                        return false;
                    }
                } catch (Exception ex) {
                    sender.sendMessage(Lang.WRONG_COMMAND_FORMAT + "/st editpoint <t> <p> sound <set/clear> (Sound, volume, pitch");
                    return false;
                }
                break;

            default:
                sender.sendMessage(Lang.WRONG_COMMAND_FORMAT + "/st editpoint <t> <p> sound <set/clear> (Sound, volume, pitch");
                return false;
        }
        return true;
    }
}
