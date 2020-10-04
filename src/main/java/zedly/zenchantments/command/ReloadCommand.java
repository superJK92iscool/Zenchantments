package zedly.zenchantments.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zedly.zenchantments.ZenchantmentsPlugin;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends ZenchantmentsCommand {
    public ReloadCommand(@NotNull ZenchantmentsPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!sender.hasPermission("zenchantments.command.reload")) {
            sender.sendMessage(ZenchantmentsCommand.MESSAGE_PREFIX + "You do not have permission to do this!");
            return;
        }

        this.plugin.getGlobalConfiguration().loadGlobalConfiguration();
        this.plugin.getWorldConfigurationProvider().loadWorldConfigurations();

        sender.sendMessage(ZenchantmentsCommand.MESSAGE_PREFIX + "Reloaded Zenchantments.");
    }

    @Override
    @Nullable
    public List<String> getTabCompleteOptions(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}