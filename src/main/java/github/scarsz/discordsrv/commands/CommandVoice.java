package github.scarsz.discordsrv.commands;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.DiscordUtil;
import github.scarsz.discordsrv.util.LangUtil;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandVoice {

    @Command(commandNames = { "voice" },
            helpMessage = "Join the Discord General voice channel",
            permission = "discordsrv.voice"
    )
    public static void execute(Player sender, String[] args) {
        String linkedId = DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(sender.getUniqueId());
        if (linkedId == null) {
            sender.sendMessage(ChatColor.RED + LangUtil.InternalMessage.LINK_FAIL_NOT_ASSOCIATED_WITH_AN_ACCOUNT.toString());
            return;
        }
        VoiceChannel channel = DiscordSRV.getPlugin().getGeneralVoiceChannel();
        if (channel != null) {
            Member member = DiscordUtil.getMemberById(linkedId);
            if (member == null || member.getVoiceState() == null || member.getVoiceState().inVoiceChannel()) {
                return;
            }
            Guild guild = DiscordSRV.getPlugin().getMainGuild();
            guild.getAudioManager().openAudioConnection(channel);
        }
    }
}
