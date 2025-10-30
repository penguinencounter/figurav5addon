package dev.penguinencounter.figurabadgetool.ducks;

import java.net.http.HttpRequest;

public interface FiguraHttpAPIExt {
    HttpRequest figuraBadgeTool$setBadge(Integer badgeId);

    HttpRequest figuraBadgeTool$clearBadge();
}
