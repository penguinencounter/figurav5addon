package dev.penguinencounter.figurabadgetool;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import org.figuramc.figura.avatar.Badges;
import org.figuramc.figura.utils.FiguraClientCommandSource;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class FiguraBadgeCommand {
    public static LiteralArgumentBuilder<FiguraClientCommandSource> command() {
        LiteralArgumentBuilder<FiguraClientCommandSource> result = literal("badge");
        for (Badges.Pride badge : Badges.Pride.values()) {
            LiteralArgumentBuilder<FiguraClientCommandSource> option = literal(badge.name().toLowerCase());
            option.executes(new ExecutionTarget(badge));
            result.then(option);
        }

        LiteralArgumentBuilder<FiguraClientCommandSource> clear = literal("clear");
        clear.executes(FiguraBadgeCommand::clear);
        result.then(clear);

        return result;
    }

    private static int clear(CommandContext<FiguraClientCommandSource> context) {
        FiguraBadgeNetworkImpl.clearBadge();
        return 0;
    }

    @SuppressWarnings("ClassCanBeRecord")
    private static class ExecutionTarget implements Command<FiguraClientCommandSource> {
        public final Badges.Pride badge;

        private ExecutionTarget(Badges.Pride badge) {
            this.badge = badge;
        }

        @Override
        public int run(CommandContext<FiguraClientCommandSource> context) {
            FiguraBadgeNetworkImpl.setBadge(badge.ordinal());
            return 0;
        }
    }
}
